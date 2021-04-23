package com.example.culturetraveler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayInicial extends AppCompatActivity {

    int tempoDeEspera = 1000 * 8;

    private ProgressBar spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayinicial);

        spinner = (ProgressBar)findViewById(R.id.Progressbar1);

        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        trocarTela();

    }

    private void trocarTela() {

        new Handler().postDelayed(() -> {

            Intent trocarDeTela = new Intent(DisplayInicial.this, LoginActivity.class);

            startActivity(trocarDeTela);
            finish();
        },tempoDeEspera);

    }

}
