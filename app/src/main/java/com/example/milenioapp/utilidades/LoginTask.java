package com.example.milenioapp.utilidades;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String correo = params[0];
        String password = params[1];
        String apiUrl = "http://localhost:3000/api/login"; // URL de tu API de login

        try {
            // Crear URL y conexión HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);

            // Crear el JSON con los datos del login
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("correo", correo);
            jsonBody.put("password", password);

            // Enviar el JSON al servidor
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonBody.toString().getBytes("UTF-8"));
            os.close();

            // Leer la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            Log.e("LoginTask", "Error al hacer login", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && !result.startsWith("Error")) {
            // Procesar la respuesta, por ejemplo, mostrar un mensaje de éxito o continuar
            Log.d("LoginTask", "Login exitoso: " + result);
        } else {
            // Mostrar un mensaje de error al usuario
            Log.d("LoginTask", "Error en el login");
        }
    }
}
