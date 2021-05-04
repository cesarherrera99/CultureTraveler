package com.example.culturetraveler.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturetraveler.PHC;
import com.example.culturetraveler.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder>
        implements View.OnClickListener {

    ArrayList<PHC> listaPHC;
    private View.OnClickListener listener;

    public ListaAdapter(ArrayList<PHC> listaPHC) {
        this.listaPHC=listaPHC;
    }


    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista_item, null, false);
        view.setOnClickListener(this);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        holder.txtNome.setText(listaPHC.get(position).getNome());
        holder.txtinformacao.setText(listaPHC.get(position).getDescricao());
        holder.imagem.setImageResource(listaPHC.get(position).getIdimagem());
        holder.direcoes.setImageResource(listaPHC.get(position).getDirecoes());

    }

    @Override
    public int getItemCount() {
        return listaPHC.size();
    }

    public void setOnCliclListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {

        if (listener!=null ) {
            listener.onClick(view);
        }

    }

    public class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtinformacao;
        ImageView imagem, direcoes;

        public ListaViewHolder(View itemView) {
            super(itemView);
            txtNome = (TextView) itemView.findViewById(R.id.idnome);
            txtinformacao = (TextView) itemView.findViewById(R.id.idinfo);
            imagem = (ImageView) itemView.findViewById(R.id.idimagem);
            direcoes = (ImageView) itemView.findViewById(R.id.direcoes);

        }

    }
}