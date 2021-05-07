package com.example.culturetraveler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailresetpassword;
    private Button resetpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        emailresetpassword = (EditText) findViewById(R.id.emailresetpassword);
        resetpassword = (Button) findViewById(R.id.resetpassword);

        mAuth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = emailresetpassword.getText().toString().trim();

                if (useremail.isEmpty()){
                    emailresetpassword.setError("Por favor insira um email");
                    emailresetpassword.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                    emailresetpassword.setError("Por favor insira um email válido");
                    emailresetpassword.requestFocus();
                    return;
                }else {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, "Email de recuperação da palavra-pase enviado!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Erro ao enviar o email de recuperação da palavra-passe!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }
}
