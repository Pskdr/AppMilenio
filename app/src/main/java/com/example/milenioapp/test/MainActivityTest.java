package com.example.milenioapp.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.milenioapp.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;


public class MainActivityTest {
    private MainActivity mainActivity;
    private TextInputEditText ticorreo;
    private TextInputEditText tiPassword;

    @Before
    public void setUp() {
        // Crear el contexto de la aplicación
        Context context = ApplicationProvider.getApplicationContext();

        // Crear una instancia de MainActivity sin inicializar completamente
        mainActivity = new MainActivity();

        // Simular los campos de texto de correo y contraseña
        ticorreo = new TextInputEditText(context);
        tiPassword = new TextInputEditText(context);

        // Inyectar los campos simulados en MainActivity
        mainActivity.ticorreo = ticorreo;
        mainActivity.tiPassword = tiPassword;
    }

    @Test
    public void validarDatos_CamposVacios() {
        ticorreo.setText("");
        tiPassword.setText("");
        assertFalse(mainActivity.validarDatos());
    }

    @Test
    public void validarDatos_CampoCorreoLleno() {
        ticorreo.setText("correo@ejemplo.com");
        tiPassword.setText("");
        assertFalse(mainActivity.validarDatos());
    }

    @Test
    public void validarDatos_CampoPasswordLleno() {
        ticorreo.setText("");
        tiPassword.setText("password123");
        assertFalse(mainActivity.validarDatos());
    }

    @Test
    public void validarDatos_CamposLlenos() {
        ticorreo.setText("correo@ejemplo.com");
        tiPassword.setText("password123");
        assertTrue(mainActivity.validarDatos());
    }
}
