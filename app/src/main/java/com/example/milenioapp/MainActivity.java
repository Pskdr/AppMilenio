package com.example.milenioapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.TipoCliente;
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
    public TextInputEditText ticorreo;
    public TextInputEditText tiPassword;

    private AlertDialog progressDialog;

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

    public boolean validarDatos() {
        if (ticorreo.getText().toString().equals("") || tiPassword.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void insertarDatosIniciales() {

        List<TipoCliente> tipoClientes = new ArrayList<>();

        List<Zona> zonaList = new ArrayList<>();
        tipoClientes.add(new TipoCliente(0, "LOGISTICA"));
        zonaList.add(new Zona("ELECTRICOS", 0));
        zonaList.add(new Zona("CAFETIN", 0));
        zonaList.add(new Zona("BAÑOS", 0));
        zonaList.add(new Zona("PRODUCCIÓN", 0));
        zonaList.add(new Zona("OFICINAS", 0));
        zonaList.add(new Zona("BODEGA", 0));
        zonaList.add(new Zona("PASILLOS", 0));
        zonaList.add(new Zona("CUARTO UTIL", 0));

        tipoClientes.add(new TipoCliente(1, "CESDE UNIVERSIDAD MENSUAL"));
        zonaList.add(new Zona("SALÓN 601", 1));
        zonaList.add(new Zona("SALÓN 602", 1));
        zonaList.add(new Zona("ECONOMATO", 1));
        zonaList.add(new Zona("SALON 801", 1));
        zonaList.add(new Zona("SALÓN 802", 1));
        zonaList.add(new Zona("SALÓN 803", 1));
        zonaList.add(new Zona("SALÓN 804", 1));
        zonaList.add(new Zona("SALÓN 610", 1));
        zonaList.add(new Zona("ZONA DE RESIDUOS", 1));
        zonaList.add(new Zona("SHUTS AYACUCHO", 1));
        zonaList.add(new Zona("CAFETIN ÁREA ADMO 2DO", 1));
        zonaList.add(new Zona("ÁREA ADMO 1ER PISO", 1));
        zonaList.add(new Zona("CAFETIN 1ER PISO", 1));
        zonaList.add(new Zona("COOWORKING 5TO PISO", 1));
        zonaList.add(new Zona("PLAZOLETA", 1));
        zonaList.add(new Zona("COCINAS MOVILES", 1));
        zonaList.add(new Zona("CAFETIN SEDE ALTERNA", 1));
        zonaList.add(new Zona("BAÑOS SEDE ALT.", 1));
        zonaList.add(new Zona("CUARTO DE ASEO S. ALT", 1));

        tipoClientes.add(new TipoCliente(2, "INSTITUCIONES EDUCATIVAS"));
        zonaList.add(new Zona("ÁREA SOCIAL", 2));
        zonaList.add(new Zona("BAÑOS", 2));
        zonaList.add(new Zona("COCINETA", 2));
        zonaList.add(new Zona("ZONAS COMUNES", 2));
        zonaList.add(new Zona("SIFONES", 2));
        zonaList.add(new Zona("PATIOS", 2));
        zonaList.add(new Zona("SALONES", 2));
        zonaList.add(new Zona("BODEGA", 2));
        zonaList.add(new Zona("CUARTO RESIDUOS", 2));
        zonaList.add(new Zona("RESTAURANTES", 2));

        tipoClientes.add(new TipoCliente(3, "HOSPITALES"));

        zonaList.add(new Zona("OFICINAS", 3));
        zonaList.add(new Zona("RECEPCIÓN", 3));
        zonaList.add(new Zona("PASILLOS", 3));
        zonaList.add(new Zona("JARDINES", 3));
        zonaList.add(new Zona("ÁREA DE RESIDUOS", 3));
        zonaList.add(new Zona("LAVANDERIA", 3));
        zonaList.add(new Zona("COCINA", 3));
        zonaList.add(new Zona("FARMACIA", 3));
        zonaList.add(new Zona("URGENCIAS", 3));
        zonaList.add(new Zona("BAÑOS", 3));
        zonaList.add(new Zona("CONSULTORIOS", 3));
        zonaList.add(new Zona("ADMINISTRACIÓN", 3));
        zonaList.add(new Zona("ZONAS COMUNES", 3));
        zonaList.add(new Zona("CAFETIN", 3));
        zonaList.add(new Zona("PATIO", 3));

        tipoClientes.add(new TipoCliente(4, "CONSULTORIO ODONTOL")); // Agregar el tipoCliente con id 4

        zonaList.add(new Zona("BODEGA", 4));
        zonaList.add(new Zona("COCINA", 4));
        zonaList.add(new Zona("BAÑO", 4));
        zonaList.add(new Zona("MOSTRADOR", 4));
        zonaList.add(new Zona("ELECTRICOS", 4));
        zonaList.add(new Zona("PASILLOS", 4));
        zonaList.add(new Zona("ZONAS COMUNES", 4));
        zonaList.add(new Zona("CONSULTORIO", 4));

        tipoClientes.add(new TipoCliente(5, "OBRAS")); // Agregar el tipoCliente con id 5

        zonaList.add(new Zona("CAFETIN", 5));
        zonaList.add(new Zona("BAÑOS", 5));
        zonaList.add(new Zona("BODEGAS", 5));
        zonaList.add(new Zona("ZONAS HUMEDAS", 5));
        zonaList.add(new Zona("ARCHIVO", 5));
        zonaList.add(new Zona("ALMACEN", 5));
        zonaList.add(new Zona("SOTANOS", 5));
        zonaList.add(new Zona("CAMPAMENTOS", 5));
        zonaList.add(new Zona("OFICINAS", 5));
        zonaList.add(new Zona("EXTERIORES", 5));
        zonaList.add(new Zona("VESTIER", 5));
        zonaList.add(new Zona("LABORATORIO", 5));
        zonaList.add(new Zona("ZONA DE RESIDUOS", 5));
        zonaList.add(new Zona("PASILLOS", 5));
        zonaList.add(new Zona("PORTERIA", 5));
        zonaList.add(new Zona("TIENDA", 5));

        tipoClientes.add(new TipoCliente(6, "UNIDADES RESIDENCIALES")); // Agregar el tipoCliente con id 6

        zonaList.add(new Zona("SHUT DE BASURAS", 6));
        zonaList.add(new Zona("ACOPIO DE RESIDUOS", 6));
        zonaList.add(new Zona("PASILLOS", 6));
        zonaList.add(new Zona("APARTAMENTOS", 6));
        zonaList.add(new Zona("PARQUEADEROS", 6));
        zonaList.add(new Zona("PORTERIA", 6));
        zonaList.add(new Zona("ZONAS COMUNES", 6));
        zonaList.add(new Zona("CUARTO UTIL", 6));
        zonaList.add(new Zona("SALÓN SOCIAL", 6));
        zonaList.add(new Zona("LOCAL COMERCIAL", 6));
        zonaList.add(new Zona("CUARTO DE RECICLAJE", 6));
        zonaList.add(new Zona("MANJOLES", 6));

        tipoClientes.add(new TipoCliente(7, "CASAS")); // Agregar el tipoCliente con id 7

        zonaList.add(new Zona("COCINA", 7));
        zonaList.add(new Zona("BAÑOS", 7));
        zonaList.add(new Zona("HABITACIONES", 7));
        zonaList.add(new Zona("CLOSETS", 7));
        zonaList.add(new Zona("MESONES", 7));
        zonaList.add(new Zona("EXTERIORES", 7));
        zonaList.add(new Zona("PAREDES", 7));
        zonaList.add(new Zona("ELECTRICOS", 7));
        zonaList.add(new Zona("PASILLOS", 7));
        zonaList.add(new Zona("PATIO", 7));
        zonaList.add(new Zona("ZÓCALOS", 7));
        zonaList.add(new Zona("TECHO FALSO", 7));
        zonaList.add(new Zona("CUARTO UTIL", 7));
        zonaList.add(new Zona("OTROS", 7));

        tipoClientes.add(new TipoCliente(8, "VEHICULOS")); // Agregar el tipoCliente con id 8

        zonaList.add(new Zona("CAVINA", 8));
        zonaList.add(new Zona("CAVAS", 8));
        zonaList.add(new Zona("COJINERÍA", 8));
        zonaList.add(new Zona("EMPAQUE DE PUERTAS", 8));
        zonaList.add(new Zona("BISAGRAS", 8));
        zonaList.add(new Zona("TAPICERÍA", 8));
        zonaList.add(new Zona("CONTENEDOR", 8));


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
        tecnicaAplicacionList.add(new TecnicaAplicacion(1, "NEBULIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(2, "TERMONEBULIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(3, "POLVO SECO", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(4, "INSPECCIÓN", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(5, "DESRATIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(6, "APLICACION GEL", "", 0));
        String uuid = Secure.getString(this.getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        Usuario usuario = new Usuario(0,"correo@ejemplo.com","Olga Cecilia","",uuid,"A");
        new Thread(() -> {

            //AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insertDatos();
            AppDataBase.getInstance(getApplicationContext()).getTipoClienteDAO().insertAll(tipoClientes);
            AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insert(usuario);
            AppDataBase.getInstance(getApplicationContext()).getZonaDAO().insertAll(zonaList);
            AppDataBase.getInstance(getApplicationContext()).getHigieneDAO().insertAll(higieneList);
            AppDataBase.getInstance(getApplicationContext()).getInsectoDAO().insertAll(insectoList);
            AppDataBase.getInstance(getApplicationContext()).getTecnicaAplicacionDAO().insertAll(tecnicaAplicacionList);
            AppDataBase.getInstance(getApplicationContext()).getElementoUtilizadoDAO().insertAll(elementoUtilizadoList);


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

    Usuario usuario;
    private void obtenerUsuario() {
        new Thread(() -> {
            usuario = AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().getUser();

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

                    new InsertarDatosTask().execute();
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


    private void mostrarMensajeEsperar() {
        // Crear el AlertDialog con un ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Por favor espere")
                .setMessage("Trayendo datos...")
                .setCancelable(false); // Impide que se cierre al tocar fuera del diálogo

        // Crear y agregar un ProgressBar al AlertDialog
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true); // Establecer que sea indeterminado (sin progreso visible)
        builder.setView(progressBar);

        progressDialog = builder.create();
        progressDialog.show();
    }

    private void ocultarMensajeEsperar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss(); // Cerrar el dialogo cuando termine la tarea
        }
    }

    private class InsertarDatosTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar el mensaje de "Trayendo datos..." antes de la tarea
            mostrarMensajeEsperar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Simulación de la inserción de datos (reemplazar con tu lógica real)
            try {
                Thread.sleep(30000); // Simular un retraso de 5 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ocultarMensajeEsperar();

            new Thread(() -> {

                usuario = AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().getUser();

                runOnUiThread(() -> {
                    if (usuario != null) {
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        Bundle miBundle = new Bundle();
                        miBundle.putString("device", strDevice);
                        miBundle.putLong("id", usuario.getId());
                        intent.putExtras(miBundle);
                        startActivity(intent);
                        finish();
                    }
                });

            }).start();

        }
    }
}