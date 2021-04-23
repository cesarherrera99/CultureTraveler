package com.example.culturetraveler.ui.map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturetraveler.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private MapViewModel mViewModel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private GoogleMap mGoogleMap;
    private MapView mMapView;

    //widgets
    private EditText mSerachText;
    private ImageView mGps;

    //vars
    private static final String TAG = "MyActivity";
    private boolean mLocationPermission = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 18;
    private int mAnimated = 0; //Utilização: animar a camara


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mSerachText = (EditText) view.findViewById(R.id.input_search);
        mGps = (ImageView) view.findViewById(R.id.ic_gps);

        Places.initialize(getActivity(), "AIzaSyDx1gUQYv705FdeXUCWJySPwLKfOn9R8Wo");

        getLocationPermission();

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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationClickListener(this);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(41.14961, -8.61099)).title("Porto").snippet("go here"));

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

            init();
        }
    }

    //Função para a utilização de Widgets (SearchBar, MyLocationButton,....)
    public void init(){
        mSerachText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    //executar nossa procura de um PHC
                    geoLocate();
                }
                return false;
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
    public void geoLocate(){
        String searchString = mSerachText.getText().toString();
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geolocate: IOExtetion: "+ e.getMessage());
        }
        if (list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a Location: "+ address.toString());

            animatedCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
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
                            if (mAnimated == 0){
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                            }else{
                                animatedCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
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
    public void moveCamera(LatLng latLng, float zoom, String title){
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    //Camara animada
    public void animatedCamera(LatLng latLng, float zoom, String title){
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        if (!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions().position(latLng);
            mGoogleMap.addMarker(options);
        }
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


    //Inicio Botão mexer camara a minha localizacao
    @Override
    public boolean onMyLocationButtonClick() {
        return false;
        //the camera animates to the user's current position
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
    //Fim do Botão

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapViewModel.class);
    }

}
