package com.example.culturetraveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextNome;
    private Button continuar, login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email_ETxt);
        editTextPassword = findViewById(R.id.password_R_ETxt);
        editTextNome = findViewById(R.id.nome_ETxt);
        continuar = findViewById(R.id.registo_R_Button);
        continuar.setOnClickListener(this);
        login = findViewById(R.id.login_R_Button);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_R_Button:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registo_R_Button:
                registerUser();
                break;
        }

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nome = editTextNome.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Por favor insira um email");
            editTextEmail.requestFocus();
            return;
        }else if (password.isEmpty()){
            editTextPassword.setError("Por favor insira uma password");
            editTextPassword.requestFocus();
            return;
        }else if (nome.isEmpty()){
            editTextNome.setError("Por favor insira um Nome");
            editTextNome.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Por favor insira um email valido");
            editTextEmail.requestFocus();
            return;
        }else if (password.length() < 6){
            editTextPassword.setError("Por favor insira uma password com mais de 6 carateres");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Utilizador utilizador = new Utilizador(email, nome);
                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(utilizador).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegistoActivity.this, "Registo Completado", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegistoActivity.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(RegistoActivity.this, "Erro na Criação da conta", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegistoActivity.this, "Erro na Criação da conta", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}