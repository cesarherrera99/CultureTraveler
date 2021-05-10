package com.example.culturetraveler.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturetraveler.MainActivity;
import com.example.culturetraveler.PHC;
import com.example.culturetraveler.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    ArrayList<PHC> listaPHC;
    private final Listener listener;

    public ListaAdapter(ArrayList<PHC> listaPHC, Listener listener) {
        this.listaPHC = listaPHC;
        this.listener = listener;
    }


    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista_item, null, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        try {
            holder.txtNome.setText(listaPHC.get(position).getNome());
            holder.txtinformacao.setText(listaPHC.get(position).getDescricao());
            holder.imagem.setImageResource(listaPHC.get(position).getIdimagem());
            holder.direcoes.setImageResource(listaPHC.get(position).getDirecoes());
            if (MainActivity.currentPosition != null) {
                holder.txtDistance.setText(getDistance(SphericalUtil.computeDistanceBetween(new LatLng(MainActivity.currentPosition.getLatitude(), MainActivity.currentPosition.getLongitude()),
                        new LatLng(listaPHC.get(position).getLatitud(), listaPHC.get(position).getLongitud()))));
            }
            holder.itemView.setOnClickListener(view -> listener.clickListener(holder.itemView, listaPHC.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaPHC.size();
    }

    private String getDistance(Double distance) {
        return String.format(Locale.getDefault(), "%.2f", distance / 1000) + "km";
    }

    public class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtinformacao, txtDistance;
        ImageView imagem, direcoes;

        public ListaViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.idnome);
            txtinformacao = itemView.findViewById(R.id.idinfo);
            imagem = itemView.findViewById(R.id.idimagem);
            direcoes = itemView.findViewById(R.id.direcoes);
            txtDistance = itemView.findViewById(R.id.idDistance);
        }

    }

    public interface Listener {
        void clickListener(View view, PHC model);
    }
}