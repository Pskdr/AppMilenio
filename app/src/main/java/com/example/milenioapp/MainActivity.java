package com.example.milenioapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.utilidades.LoginTask;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.*;

import android.provider.Settings.Secure;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnIngresar;
    public String strDevice = "";
    private TextView tvRegistro;
    private static final String LOGIN_URL = "http://192.168.56.1:3000/api/usuario/login"; 

    private ArrayList<String> permissionsList;
    String[] permissionsStr = {android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS};
    private int permissionsCount = 0;
    private TextInputEditText ticorreo, tiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = findViewById(R.id.btnIniciarsesion);
        tvRegistro = findViewById(R.id.tvRegistro);
        ticorreo = findViewById(R.id.tiCorreo);
        tiPassword = findViewById(R.id.tiPassword);

        btnIngresar.setOnClickListener(view -> {

            if (validarDatos()) {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);

                Bundle miBundle = new Bundle();
                miBundle.putString("device", strDevice);
                intent.putExtras(miBundle);
                //startActivity(intent);
                //finish();

                String uuid = Settings.Secure.getString(this.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                loginUsuario(ticorreo.getText().toString(), tiPassword.getText().toString(),uuid);
            }

        });

        tvRegistro.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

            Bundle miBundle = new Bundle();
            miBundle.putString("device", strDevice);
            intent.putExtras(miBundle);
            startActivity(intent);
            finish();
        });

        obtenerUsuario();


        permissionsList = new ArrayList<>();
        permissionsList.addAll(Arrays.asList(permissionsStr));
        askForPermissions(permissionsList);
    }

    private boolean validarDatos() {
        if (ticorreo.getText().toString().equals("") || tiPassword.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void insertarDatosIniciales() {
        List<Zona> zonaList = new ArrayList<>();
        zonaList.add(new Zona(0, "Sala vísceras blancas Sifón", "E1", 0));
        zonaList.add(new Zona(1, "Lockers", "E2", 0));
        zonaList.add(new Zona(2, "Oficinas", "E3", 0));
        zonaList.add(new Zona(3, "Baños", "E4", 0));

        List<Higiene> higieneList = new ArrayList<>();
        higieneList.add(new Higiene( "Area libre de residuos", 0));
        higieneList.add(new Higiene( "Area sin acumulación de basura", 0));
        higieneList.add(new Higiene( "Zona sucia sifón", 0));
        higieneList.add(new Higiene( "Baños", 0));
        higieneList.add(new Higiene( "Areas comunes", 0));

        List<Insecto> insectoList = new ArrayList<>();
        insectoList.add(new Insecto( "CUCARACHA AMERICANA", 0));
        insectoList.add(new Insecto( "CUCARACHA ALEMANA", 0));
        insectoList.add(new Insecto( "HORMIGAS", 1));
        insectoList.add(new Insecto( "ROEDORES", 1));
        insectoList.add(new Insecto( "PULGAS", 1));
        insectoList.add(new Insecto( "CHINCHE", 0));
        insectoList.add(new Insecto("MOSCAS", 0));

        List<ElementoUtilizado> elementoUtilizadoList = new ArrayList<>();
        elementoUtilizadoList.add(new ElementoUtilizado( "Banda de neopreno"));
        elementoUtilizadoList.add(new ElementoUtilizado( "Mallas"));
        elementoUtilizadoList.add(new ElementoUtilizado( "Trampas adhesivas"));
        elementoUtilizadoList.add(new ElementoUtilizado( "Insecticidas"));

        List<TecnicaAplicacion> tecnicaAplicacionList = new ArrayList<>();
        tecnicaAplicacionList.add(new TecnicaAplicacion(0, "ASPERSION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(1, "DISOLUCIÓN", "", 0));
        String uuid = Secure.getString(this.getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        Usuario usuario = new Usuario(0,"correo@ejemplo.com","Olga Cecilia","",uuid,"A");
        new Thread(() -> {

            //AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insertDatos();
            AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insert(usuario);
            AppDataBase.getInstance(getApplicationContext()).getZonaDAO().insertAll(zonaList);
            AppDataBase.getInstance(getApplicationContext()).getHigieneDAO().insertAll(higieneList);
            AppDataBase.getInstance(getApplicationContext()).getInsectoDAO().insertAll(insectoList);
            AppDataBase.getInstance(getApplicationContext()).getTecnicaAplicacionDAO().insertAll(tecnicaAplicacionList);
            AppDataBase.getInstance(getApplicationContext()).getElementoUtilizadoDAO().insertAll(elementoUtilizadoList);

            runOnUiThread(() -> {
                obtenerUsuario();
            });

        }).start();
    }

    private void askForPermissions(ArrayList<String> permissionsList) {
        String[] newPermissionStr = new String[permissionsList.size()];
        for (int i = 0; i < newPermissionStr.length; i++) {
            newPermissionStr[i] = permissionsList.get(i);
        }
        if (newPermissionStr.length > 0) {
            permissionsLauncher.launch(newPermissionStr);
        } else {
            ///showPermissionDialog();
        }
    }

    ActivityResultLauncher<String[]> permissionsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<Map<String, Boolean>>() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onActivityResult(Map<String, Boolean> result) {

                            ArrayList<Boolean> list = new ArrayList<>(result.values());
                            permissionsList = new ArrayList<>();
                            permissionsCount = 0;
                            for (int i = 0; i < list.size(); i++) {
                                if (shouldShowRequestPermissionRationale(permissionsStr[i])) {
                                    permissionsList.add(permissionsStr[i]);
                                } else if (!hasPermission(MainActivity.this, permissionsStr[i])) {
                                    permissionsCount++;
                                }
                            }
                            if (permissionsList.size() > 0) {
                                //Some permissions are denied and can be asked again.
                                askForPermissions(permissionsList);
                            } else if (permissionsCount > 0) {
                                //Show alert dialog
                                //showPermissionDialog();
                            } else {
                                //All permissions granted. Do your stuff 🤞
                            }

                        }
                    });

    private void obtenerUsuario() {
        new Thread(() -> {
            Usuario usuario = AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().getUser();

            runOnUiThread(() -> {

                if (usuario != null) {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    Bundle miBundle = new Bundle();
                    miBundle.putString("device", strDevice);
                    miBundle.putLong("id", usuario.getId());
                    intent.putExtras(miBundle);
                    startActivity(intent);
                    finish();
                } else {
                    insertarDatosIniciales();
                }

            });

        }).start();
    }

    private boolean hasPermission(Context context, String permissionStr) {
        return ContextCompat.checkSelfPermission(context, permissionStr) == PackageManager.PERMISSION_GRANTED;
    }



    private void loginUsuario(String correo, String password, String strDevice) {
        OkHttpClient client = new OkHttpClient();

        // Crear el cuerpo de la solicitud (payload JSON)
        JSONObject loginData = new JSONObject();
        try {
            loginData.put("correo", correo);
            loginData.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(loginData.toString(), MediaType.get("application/json; charset=utf-8"));

        // Crear la solicitud POST
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(body)
                .build();

        // Ejecutar la solicitud en un hilo separado
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                e.printStackTrace();
                // Manejar el error de la solicitud fallida
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Manejar la respuesta del servidor
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa
                    String responseData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseData);
                            String mensaje = jsonResponse.getString("mensaje");

                            // Verificar si el login es exitoso
                            if (mensaje.equals("Login exitoso")) {

                                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                                Bundle miBundle = new Bundle();
                                miBundle.putString("device", strDevice);
                                intent.putExtras(miBundle);
                                startActivity(intent);
                                finish();

                            } else {
                                // Si el login falla
                                Toast.makeText(MainActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    // Si la respuesta no es exitosa (error en login)
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Login fallido: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

}