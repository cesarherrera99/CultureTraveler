package com.example.culturetraveler.ui.lista;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.culturetraveler.PHC;
import com.example.culturetraveler.R;
import com.example.culturetraveler.ui.adapter.ListaAdapter;

import java.util.ArrayList;


public class ListaFragment extends Fragment {

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

        ListaAdapter adapter = new ListaAdapter(listaPHC);
        recyclerView.setAdapter(adapter);

        adapter.setOnCliclListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ""+listaPHC.get(recyclerView.getChildAdapterPosition(view)).getNome(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    private void definirlista() {

        listaPHC.add(new PHC("Avenida dos Aliados", "O centro e o coração da cidade do Porto",R.drawable.aliados, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Mercado do Bolhão", "A rua mais comercial do Porto",R.drawable.mercado_bolhao, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Rua de Santa Catarina", "Pensado ao pormenor para acolher \nos comerciantes e os clientes",R.drawable.santa_catarina, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Galerias de Paris", "Um dos locais mais importantes da \nnoite do Porto",R.drawable.galerias, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Livraria Lello", "Considerada uma das mais lindas \nlivrarias do mundo",R.drawable.lello, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Torre dos Clérigos", "Considerada por muitos o ex-líbris \nda cidade",R.drawable.clerigos,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Palácio da Bolsa", "Estilo neoclássico e inspirado no \nestilo mourisco",R.drawable.palacio_bolsa, R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Igreja de São Francisco", "A mais bela da cidade do Porto",R.drawable.igreja,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Sé do Porto", "Construída para proteger a cidade \ndos invasores",R.drawable.sedoporto,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Ribeira", "Um dos locais mais antigos e típicos \ndo Porto",R.drawable.ribeira,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Serra do Pilar", "Um miradouro único para a cidade \ndo Porto",R.drawable.serrapilar,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Palácio de Cristal", "Um dos espaços verdes mais \nvisitados pelos habitantes do Porto e não só",R.drawable.palaciocristal,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Casa da Música", "Um dos edíficios mais originais e \nbelos da cidade do Porto",R.drawable.casadamusica,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Foz do Porto", "A zona onde desagua o rio Douro",R.drawable.fozporto,R.drawable.ic_directions_24));
        listaPHC.add(new PHC("Parque da Cidade ", "Conhecido como o pulmão da cidade \n\n\n\n\n",R.drawable.parquedacidade,R.drawable.ic_directions_24));

    }
}
