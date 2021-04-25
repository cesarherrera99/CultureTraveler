package com.example.culturetraveler.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturetraveler.LoginActivity;
import com.example.culturetraveler.R;
import com.example.culturetraveler.Utilizador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button logOut;
    private TextView userId_txt, email_txt, nome_txt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        email_txt = root.findViewById(R.id.txtV_Email);
        userId_txt = root.findViewById(R.id.txtV_UserID);
        nome_txt = root.findViewById(R.id.txtV_Nome);

        logOut = root.findViewById(R.id.logOut_B);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilizador userProfile = snapshot.getValue(Utilizador.class);

                if (userProfile != null){
                    String email_U = userProfile.email;
                    String nome = userProfile.nome;

                    email_txt.setText(email_U);
                    userId_txt.setText(userID);
                    nome_txt.setText(nome);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Erro em carregar Perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

}