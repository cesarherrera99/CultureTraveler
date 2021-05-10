package com.example.culturetraveler.ui.lista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.culturetraveler.MainActivity;
import com.example.culturetraveler.PHC;
import com.example.culturetraveler.R;
import com.example.culturetraveler.ui.adapter.ListaAdapter;
import com.google.maps.model.LatLng;

import java.util.ArrayList;


public class ListaFragment extends Fragment implements ListaAdapter.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<PHC> listaPHC;

    public ListaFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListaFragment newInstance(String param1, String param2) {
        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_phc, container, false);

        listaPHC = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        definirlista();

        ListaAdapter adapter = new ListaAdapter(listaPHC, this);
        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void clickListener(View view, PHC model) {
        ListaFragmentDirections.ActionNavigationListaToNavigationMap action = ListaFragmentDirections.actionNavigationListaToNavigationMap();
        action.setData(model);
        Navigation.findNavController(view).navigate(action);
    }

    private void definirlista() {

        listaPHC.add(new PHC("Avenida dos Aliados", "Aliados, 4000-067 Porto, Portugal", "O centro e o coração da cidade do Porto", R.drawable.aliados, R.drawable.ic_direction_24, 4.8,41.1483, -8.6108));
        listaPHC.add(new PHC("Mercado do Bolhão", "R. de Sá da Bandeira 356, 4000-248 Porto, Portugal", "A rua mais comercial do Porto", R.drawable.mercado_bolhao, R.drawable.ic_direction_24, 4.1, 41.1493, -8.6072));
        listaPHC.add(new PHC("Rua de Santa Catarina", "Rua de Santa Catarina 1262, 4000-099 Porto, Portugal", "Pensado ao pormenor para acolher \nos comerciantes e os clientes", R.drawable.santa_catarina, R.drawable.ic_direction_24, 4.8, 41.1535, -8.6047));
        listaPHC.add(new PHC("Galerias de Paris", "R. da Galeria de Paris 37, 4050-151 Porto, Portugal", "Um dos locais mais importantes da \nnoite do Porto", R.drawable.galerias, R.drawable.ic_direction_24, 4.5, 41.1469, -8.6145));
        listaPHC.add(new PHC("Livraria Lello", "R. das Carmelitas 144, 4050-161 Porto, Portugal", "Considerada uma das mais lindas \nlivrarias do mundo", R.drawable.lello, R.drawable.ic_direction_24, 4.2, 41.1469, -8.6148));
        listaPHC.add(new PHC("Torre dos Clérigos", "Rua da Assunção 37, 4050-151 Porto, Portugal", "Considerada por muitos o ex-líbris \nda cidade", R.drawable.clerigos, R.drawable.ic_direction_24, 4.6, 41.1457, -8.6146));
        listaPHC.add(new PHC("Palácio da Bolsa", "Palácio da Bolsa, R. de Ferreira Borges, 4050-253 Porto, Portugal", "Estilo neoclássico e inspirado no \nestilo mourisco", R.drawable.palacio_bolsa, R.drawable.ic_direction_24, 4.5, 41.1414, -8.6157));
        listaPHC.add(new PHC("Igreja de São Francisco", "Palácio da Bolsa, R. de Ferreira Borges, 4050-253 Porto, Portugal", "A mais bela da cidade do Porto", R.drawable.igreja, R.drawable.ic_direction_24, 4.5, 41.141, -8.6157));
        listaPHC.add(new PHC("Sé do Porto", "R. de Dom Hugo 3, 4000 Porto, Portugal", "Construída para proteger a cidade \ndos invasores", R.drawable.sedoporto, R.drawable.ic_direction_24, 4.3, 41.1429, -8.6111));
        listaPHC.add(new PHC("Ribeira", "R. de Saraiva de Carvalho 1, 4000-165 Porto, Portugal", "Um dos locais mais antigos e típicos \ndo Porto", R.drawable.ribeira, R.drawable.ic_direction_24, 4.3, 41.1433, -8.6103));
        listaPHC.add(new PHC("Serra do Pilar", "Av. Dom João II 317, 4430-999 Vila Nova de Gaia, Portugal", "Um miradouro único para a cidade \ndo Porto", R.drawable.serrapilar, R.drawable.ic_direction_24, 4.6, 41.1333, -8.6));
        listaPHC.add(new PHC("Palácio de Cristal", "Jardins do Palácio de Cristal, R. de Dom Manuel II, 4050-346 Porto, Portugal", "Um dos espaços verdes mais \nvisitados pelos habitantes do Porto e não só", R.drawable.palaciocristal, R.drawable.ic_direction_24, 4.8, 41.1483, -8.6254));
        listaPHC.add(new PHC("Casa da Música", "R. de 5 de Outubro 25, 4050 Porto, Portugal", "Um dos edíficios mais originais e \nbelos da cidade do Porto", R.drawable.casadamusica, R.drawable.ic_direction_24, 4.6, 41.1589, -8.6307));
        listaPHC.add(new PHC("Foz do Porto", "R. de Sobreiras 360, 4150-713 Porto, Portugal", "A zona onde desagua o rio Douro", R.drawable.fozporto, R.drawable.ic_direction_24, 4.3, 41.1466, -8.658));
        listaPHC.add(new PHC("Parque da Cidade ", "Estrada da Circunvalação 15443-15053, 4100 Porto, Portugal", "Conhecido como o pulmão da cidade \n\n\n\n\n", R.drawable.parquedacidade, R.drawable.ic_direction_24, 4.7, 41.1704, -8.6789));

    }
}