package com.example.milenioapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnIngresar;
    public String strDevice = "";
    private TextView tvRegistro;

    private ArrayList<String> permissionsList;
    String[] permissionsStr = {android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS};
    private int permissionsCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = findViewById(R.id.btnIniciarsesion);
        tvRegistro = findViewById(R.id.tvRegistro);

        btnIngresar.setOnClickListener(view ->  {
            Intent intent = new Intent(MainActivity.this, MainMenu.class);

            Bundle miBundle = new Bundle();
            miBundle.putString("device", strDevice);
            intent.putExtras(miBundle);
            startActivity(intent);
            finish();
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

    private void insertarDatosIniciales() {
        List<Zona> zonaList = new ArrayList<>();
        zonaList.add(new Zona(0, "Sala vísceras blancas Sifón", "E1",0));
        zonaList.add(new Zona(1, "Lockers", "E2",0));
        zonaList.add(new Zona(2, "Oficinas", "E3",0));
        zonaList.add(new Zona(3, "Baños", "E4",0));

        List<Higiene> higieneList = new ArrayList<>();
        higieneList.add(new Higiene(0, "Area libre de residuos" ,0));
        higieneList.add(new Higiene(1, "Area sin acumulación de basura" ,0));
        higieneList.add(new Higiene(2, "Zona sucia sifón", 0));
        higieneList.add(new Higiene(3, "Baños", 0));
        higieneList.add(new Higiene(4, "Areas comunes", 0));

        List<Insecto> insectoList = new ArrayList<>();
        insectoList.add(new Insecto(0,"CUCARACHA AMERICANA",0));
        insectoList.add(new Insecto(1,"CUCARACHA ALEMANA",0));
        insectoList.add(new Insecto(2,"HORMIGAS",1));
        insectoList.add(new Insecto(3,"ROEDORES",1));
        insectoList.add(new Insecto(4,"PULGAS",1));
        insectoList.add(new Insecto(5,"CHINCHE",0));
        insectoList.add(new Insecto(6,"MOSCAS",0));

        List<TecnicaAplicacion> tecnicaAplicacionList = new ArrayList<>();
        tecnicaAplicacionList.add(new TecnicaAplicacion(0,"ASPERSION","",0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(1,"DISOLUCIÓN","",0));

        new Thread(() -> {

            AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insertDatos();
            AppDataBase.getInstance(getApplicationContext()).getZonaDAO().insertAll(zonaList);
            AppDataBase.getInstance(getApplicationContext()).getHigieneDAO().insertAll(higieneList);
            AppDataBase.getInstance(getApplicationContext()).getInsectoDAO().insertAll(insectoList);
            AppDataBase.getInstance(getApplicationContext()).getTecnicaAplicacionDAO().insertAll(tecnicaAplicacionList);

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
                        public void onActivityResult(Map<String,Boolean> result) {

                            ArrayList<Boolean> list = new ArrayList<>(result.values());
                            permissionsList = new ArrayList<>();
                            permissionsCount = 0;
                            for (int i = 0; i < list.size(); i++) {
                                if (shouldShowRequestPermissionRationale(permissionsStr[i])) {
                                    permissionsList.add(permissionsStr[i]);
                                }else if (!hasPermission(MainActivity.this, permissionsStr[i])){
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

                if(usuario != null){
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    Bundle miBundle = new Bundle();
                    miBundle.putString("device", strDevice);
                    miBundle.putLong("id",usuario.getId());
                    intent.putExtras(miBundle);
                    startActivity(intent);
                    finish();
                }else{
                    insertarDatosIniciales();
                }

            });

        }).start();
    }
    private boolean hasPermission(Context context, String permissionStr) {
        return ContextCompat.checkSelfPermission(context, permissionStr) == PackageManager.PERMISSION_GRANTED;
    }

}