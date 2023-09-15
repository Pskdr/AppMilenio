package com.example.milenioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvIniciarSesion;
    private String strDevice = "";
    private Button btnRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvIniciarSesion = findViewById(R.id.tvIniciarSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        Bundle miBundle = this.getIntent().getExtras();
        strDevice = miBundle.getString("device");

        tvIniciarSesion.setOnClickListener(view -> {

            Intent intent = new Intent( RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        btnRegistrarse.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainMenu.class);

            Bundle bundle = new Bundle();
            bundle.putString("device", strDevice);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }
}