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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registo, login_B;
    private EditText editTextEmail_L, editTextPassword_L;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        registo = findViewById(R.id.registo_L_Button);
        registo.setOnClickListener(this);
        login_B = findViewById(R.id.login_L_Button);
        login_B.setOnClickListener(this);
        editTextEmail_L = findViewById(R.id.email_L_ETxt);
        editTextPassword_L = findViewById(R.id.password_L_ETxt);

        checkIfUserIsLoggedIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registo_L_Button:
                startActivity(new Intent(this, RegistoActivity.class));
                break;
            case R.id.login_L_Button:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail_L.getText().toString().trim();
        String password = editTextPassword_L.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail_L.setError("Por favor insira um email");
            editTextEmail_L.requestFocus();
            return;
        }else if (password.isEmpty()){
            editTextPassword_L.setError("Por favor insira uma password");
            editTextPassword_L.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail_L.setError("Por favor insira um email válido");
            editTextEmail_L.requestFocus();
            return;
        }else if (password.length() < 6){
            editTextPassword_L.setError("insira um password com mais de 6 caracteres");
            editTextPassword_L.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //login concluido
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"Erro no email ou password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void checkIfUserIsLoggedIn(){
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

}