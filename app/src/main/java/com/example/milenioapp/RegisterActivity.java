package com.example.milenioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Usuario;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextView tvIniciarSesion;
    private String strDevice = "";
    private Button btnRegistrarse;
    private TextInputEditText tiCorreo,tiNombre, tiPassword, tiConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tiNombre = findViewById(R.id.tiNombre);
        tiCorreo = findViewById(R.id.tiCorreo);
        tiPassword = findViewById(R.id.tiPassword);
        tiConfirmPassword = findViewById(R.id.tiConfirmPassword);

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
            if(validarDatos()){
                String uuid = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                Usuario usuario = new Usuario(0,tiCorreo.getText().toString(),tiNombre.getText().toString(),tiPassword.getText().toString(),uuid,"P");
                new Thread(() -> {
                    AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insert(usuario);
                    runOnUiThread(() -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("device", strDevice);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    });
                }).start();
            }
        });
    }

    private boolean validarDatos() {

        if(tiCorreo.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"dEl correo es obligatorio.",Toast.LENGTH_LONG).show();
            tiCorreo.setError("Este campo es obligatorio");
            return false;
        }
        if(tiPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"La contraseña es obligatoria.",Toast.LENGTH_LONG).show();
            tiPassword.setError("Este campo es obligatorio");
            return false;
        }
        if(tiConfirmPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"La contraseña es obligatoria.",Toast.LENGTH_LONG).show();
            tiConfirmPassword.setError("Este campo es obligatorio");
            return false;
        }
        if(tiNombre.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"El nombre es obligatorio.",Toast.LENGTH_LONG).show();
            tiNombre.setError("Este campo es obligatorio");
            return false;
        }

        if(tiConfirmPassword.getText().toString().trim().equals(tiPassword.getText().toString().trim())){

            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden.",Toast.LENGTH_LONG).show();
            tiConfirmPassword.setError("Este campo es obligatorio");
            tiPassword.setError("Este campo es obligatorio");
            return false;
        }

        return true;

    }
}