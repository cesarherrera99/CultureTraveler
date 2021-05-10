package com.example.culturetraveler.ui.map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturetraveler.CustomInfoWindowAdapter;
import com.example.culturetraveler.MainActivity;
import com.example.culturetraveler.PHC;
import com.example.culturetraveler.R;
import com.example.culturetraveler.RegistoActivity;
import com.example.culturetraveler.utility.DirectionPointListener;
import com.example.culturetraveler.utility.GetPathFromLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment
        implements
        OnConnectionFailedListener,
        OnMapReadyCallback {


    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private boolean isDirection = false;
    List<Place.Field> fields;

    private MapViewModel mViewModel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    //vars
    private FirebaseDatabase mFirebaseDataBase;
    private DatabaseReference mRef;
    private PlacesClient placesClient;
    private AutocompleteSessionToken token;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private boolean mLocationPermission = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public Marker mMarker;
    LatLng source, destination;
    Map<String,LatLng> latLnghshmp;

    //widgets
    private EditText mSerachText;
    private ImageView mGps, imgDirection;

    private static final String TAG = "MyActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 18;
    private int mAnimated = 0; //Utilização: animar a camara

    private boolean isFromList = false;
    private PHC model = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mSerachText = (EditText) view.findViewById(R.id.input_search);
        mGps = (ImageView) view.findViewById(R.id.ic_gps);
        imgDirection = view.findViewById(R.id.imageView3);

        getLocationPermission();

        mFirebaseDataBase = FirebaseDatabase.getInstance();

        Places.initialize(getActivity(), "AIzaSyDx1gUQYv705FdeXUCWJySPwLKfOn9R8Wo");
        placesClient = Places.createClient(getActivity());
        token = AutocompleteSessionToken.newInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //É CRIADA A VIEW
        mMapView = (MapView) view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        try {
            if (getArguments() != null) {
                MapFragmentArgs args = MapFragmentArgs.fromBundle(getArguments());
                if (args.getData() != null) {
                    model = args.getData();
                    if (model.getLatitud() != 0.0 && model.getLongitud() != 0.0) {
                        isFromList = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mLocationPermission) {
            mAnimated = 0;
            getDiviceLocation(mAnimated);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mGoogleMap.getUiSettings().setCompassEnabled(false);

            if (isFromList) {
                if (model.getLatitud() != 0.0 && model.getLongitud() != 0.0) {

                    String snippet = "Morada: " + model.getMorada() + "\n" +
                            "Descrição: " + model.getDescricao() + "\n" +
                            "Rating: " + model.getRating();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(model.getLatitud(), model.getLongitud()));
                    markerOptions.title(model.getNome());
                    markerOptions.snippet(snippet);
                    if (mGoogleMap != null) {
                        mGoogleMap.clear();
                        mGoogleMap.addMarker(markerOptions);
                        getDirectionApiCall(new LatLng(MainActivity.currentPosition.getLatitude(), MainActivity.currentPosition.getLongitude())
                                , new LatLng(model.getLatitud(), model.getLongitud()));
                    }
                }
            }

            init();
        }
    }

    //Função para a utilização de Widgets (SearchBar, MyLocationButton,....)
    public void init(){

        mSerachText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.RATING, Place.Field.WEBSITE_URI);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .setTypeFilter(TypeFilter.ESTABLISHMENT)
                        .setLocationBias(RectangularBounds.newInstance(
                                new LatLng(41.133486, -8.573132),
                                new LatLng(41.175115, -8.657916)))
                        .setCountry("PT").build(getActivity());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        imgDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDirection){
                    isDirection = true;
                    imgDirection.setImageResource(R.drawable.ic_close);
                    getDirectionApiCall(source, destination);
                }else {
                    isDirection = false;
                    imgDirection.setImageResource(R.drawable.ic_go);
                    clearDirection();
                }

            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked GPS icon");
                mAnimated = 1;
                getDiviceLocation(mAnimated);
            }
        });
    }

    //Função de procura de um PHC no searchBar
    public void geoLocate(String nome, Place place){
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(nome, 1);
        } catch (IOException e) {
            Log.e(TAG, "geolocate: IOExtetion: " + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a Location: " + address.toString());
            destination = new LatLng(address.getLatitude(), address.getLongitude());
            animatedCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, place);
        }
    }


    private void clearDirection() {
        mGoogleMap.clear();
        getDiviceLocation(mAnimated);
        markers();
    }

    private void getDirectionApiCall(LatLng source, LatLng destination) {
        //source = new LatLng(41.133486, -8.573132);

        new GetPathFromLocation(source, destination, polyLine -> {
            mGoogleMap.addPolyline(polyLine);

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(source);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);

            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);

            mMarker.hideInfoWindow();
        }).execute();
    }


    //Obter a Localizaçao do Utilizador
    public void getDiviceLocation(int mAnimated){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            if (mLocationPermission){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation != null) {
                                MainActivity.currentPosition = new Location(currentLocation);
                            }
                            if (mAnimated == 0 && currentLocation != null){
                                try {
                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                                    source = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                }catch (NullPointerException e){
                                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                                    source = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                }
                            }else{
                                try {
                                    animatedCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                                    source = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                }catch (NullPointerException e){
                                    animatedCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                                    source = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                }
                            }
                        }else {
                            Toast.makeText(getContext(),"não foi possivel obter Localização Atual", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: "+ e.getMessage());
        }
    }

    //Inicio de Movimentos da Camara
    //Camara estatica
    public void moveCamera(LatLng latLng, float zoom){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    //Camara animada
    public void animatedCamera(LatLng latLng, float zoom){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
    public void animatedCamera(LatLng latLng, float zoom, Place place){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        String snippet = "Morada: " + place.getAddress() + "\n" +
                "Website: " + place.getWebsiteUri() + "\n" +
                "Rating: " + place.getRating();
        mMarker = mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(place.getName())
                .snippet(snippet));

    }
    //Fim de Movimentos da Camara


    // Começo das Permissoes de Localização
    private void getLocationPermission(){;
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermission = true;
                MapsInitializer.initialize(getContext());
            }else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        mLocationPermission = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermission = false;
                            return;
                        }
                    }
                    mLocationPermission = true;
                    //inicializa o mapa
                    MapsInitializer.initialize(getContext());
                }
            }
        }
    }
    // Final das Permissoes de Localização

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);

        mFirebaseDataBase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDataBase.getReference();

        markers();
    }

    public void markers(){
        mRef.child("phc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latLnghshmp = new HashMap<>();
                mGoogleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));

                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        LatLng latlng = latLnghshmp.get(marker.getId());
                        if(latlng !=  null){
                            destination = latlng;
                        }
                        return false;
                    }
                });

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    PHC phc = snapshot.getValue(PHC.class);
                    LatLng latLng = new LatLng(phc.getLatitud(), phc.getLongitud());

                    String snippet = "Morada: " + phc.getMorada() + "\n" +
                            "Descrição: " + phc.getDescricao() + "\n" +
                            "Rating: " + phc.getRating();

                    mMarker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(phc.getNome())
                            .snippet(snippet));

                    latLnghshmp.put(mMarker.getId(), latLng);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Função de autocomplete searchBar
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                mSerachText.setText(place.getName());

                geoLocate(place.getName(), place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getActivity(), status.toString(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // O utilizador cancela a procura
                Toast.makeText(getActivity(), "Local não selecionado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}