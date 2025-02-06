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
import com.example.milenioapp.database.entity.Ambiente;
import com.example.milenioapp.database.entity.Material;
import com.example.milenioapp.database.entity.Hallazgo;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.TipoCliente;
import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;
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
        higieneList.add(new Higiene("Area libre de residuos", 0));
        higieneList.add(new Higiene("Area sin acumulación de basura", 0));
        higieneList.add(new Higiene("Zona sucia sifón", 0));
        higieneList.add(new Higiene("Baños", 0));
        higieneList.add(new Higiene("Areas comunes", 0));


        List<Material> materialList = new ArrayList<>();
        materialList.add(new Material("Alambre"));
        materialList.add(new Material("Banda de Neopreno"));
        materialList.add(new Material("Malla Inoxidable"));
        materialList.add(new Material("Malla plástica"));
        materialList.add(new Material("Bisel de Aluminio"));
        materialList.add(new Material("Rejillas"));
        materialList.add(new Material("Cemento"));
        materialList.add(new Material("Resinas"));
        materialList.add(new Material("Silicona"));
        materialList.add(new Material("Otros"));

        List<TecnicaAplicacion> tecnicaAplicacionList = new ArrayList<>();
        tecnicaAplicacionList.add(new TecnicaAplicacion(0, "ASPERSION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(1, "NEBULIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(2, "TERMONEBULIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(3, "POLVO SECO", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(4, "INSPECCIÓN", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(5, "DESRATIZACION", "", 0));
        tecnicaAplicacionList.add(new TecnicaAplicacion(6, "APLICACION GEL", "", 0));
        String uuid = Secure.getString(this.getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
        Usuario usuario = new Usuario(0, "correo@ejemplo.com", "Olga Cecilia", "", uuid, "A");

        List<Ambiente> ambienteList = new ArrayList<>();

        ambienteList.add(new Ambiente(1, "AREA SOCIAL"));
        ambienteList.add(new Ambiente(2, "BAÑOS"));
        ambienteList.add(new Ambiente(3, "COCINETA"));
        ambienteList.add(new Ambiente(4, "ZONAS COMUNES"));
        ambienteList.add(new Ambiente(5, "SIFONES"));
        ambienteList.add(new Ambiente(6, "PATIO"));
        ambienteList.add(new Ambiente(7, "PAREDES"));
        ambienteList.add(new Ambiente(8, "PISOS"));
        ambienteList.add(new Ambiente(9, "AMBIENTES"));
        ambienteList.add(new Ambiente(10, "SUPERFICIES"));
        ambienteList.add(new Ambiente(11, "VENTANAS"));
        ambienteList.add(new Ambiente(12, "ESCALERAS"));
        ambienteList.add(new Ambiente(13, "BODEGA"));
        ambienteList.add(new Ambiente(14, "OFICINA"));
        ambienteList.add(new Ambiente(15, "CAFETIN"));
        ambienteList.add(new Ambiente(16, "PARQUEADERO"));
        ambienteList.add(new Ambiente(17, "SALA BENEFICIO BOVINO"));
        ambienteList.add(new Ambiente(18, "SALA BENEFICIO PORCINO"));
        ambienteList.add(new Ambiente(19, "ZONA SUBPRODUCTO"));
        ambienteList.add(new Ambiente(20, "SALIDA A CORRALES"));
        ambienteList.add(new Ambiente(21, "INGRESOS"));
        ambienteList.add(new Ambiente(22, "AREA PTAR"));
        ambienteList.add(new Ambiente(23, "ZONAS COMUNES"));
        ambienteList.add(new Ambiente(24, "ZONA MOTOBOMBA"));
        ambienteList.add(new Ambiente(25, "OTROS"));

        List<Hallazgo> hallazgoList = new ArrayList<>();

// Agregar los datos a la lista
        hallazgoList.add(new Hallazgo(1, "Bacteria"));
        hallazgoList.add(new Hallazgo(2, "Hongos"));
        hallazgoList.add(new Hallazgo(3, "Levaduras"));
        hallazgoList.add(new Hallazgo(4, "Virus"));
        hallazgoList.add(new Hallazgo(5, "Otro"));

        List<TipoInsecto> tipoInsectoList = new ArrayList<>();

        List<Insecto> insectoList = new ArrayList<>();
        // Insectos Rastreros (Tipo 1)
        insectoList.add(new Insecto("Cucaracha Americana", 2));
        insectoList.add(new Insecto("Cucaracha Alemana", 2));
        insectoList.add(new Insecto("Cucaracha Banda Café", 2));
        insectoList.add(new Insecto("Cucaracha Oriental", 2));
        insectoList.add(new Insecto("Chinche de Cama", 2));
        insectoList.add(new Insecto("Pescadito de la Plata", 2));
        insectoList.add(new Insecto("Termitas", 2));
        insectoList.add(new Insecto("Alacranes", 2));
        insectoList.add(new Insecto("Tijerilla", 2));
        insectoList.add(new Insecto("Escarabajo", 2));
        insectoList.add(new Insecto("Pulga de Perro", 2));
        insectoList.add(new Insecto("Garrapata", 2));
        insectoList.add(new Insecto("Hormigas", 2));

        // Insectos Voladores (Tipo 0)

        insectoList.add(new Insecto("Mosca del Drenaje", 1));
        insectoList.add(new Insecto("Mosca de la Fruta", 1));
        insectoList.add(new Insecto("Mosca de la Carne", 1));
        insectoList.add(new Insecto("Mosca Doméstica", 1));
        insectoList.add(new Insecto("Mosquito", 1));
        insectoList.add(new Insecto("Larva", 1));
        insectoList.add(new Insecto("Avispa", 1));
        insectoList.add(new Insecto("Abeja", 1));
        insectoList.add(new Insecto("Aves Plagas", 1));
        insectoList.add(new Insecto("Gallinazos", 1));
        insectoList.add(new Insecto("Murciélagos", 1));

        // Plagas de Granos Almacenados (Tipo 2)

        insectoList.add(new Insecto("Gorgojo del Maíz", 3));
        insectoList.add(new Insecto("Gorgojo del Pan", 3));
        insectoList.add(new Insecto("Gorgojo Confuso de la Harina", 3));
        insectoList.add(new Insecto("Gorgojo Aserrado del Grano", 3));
        insectoList.add(new Insecto("Gorgojo del Frijol", 3));
        insectoList.add(new Insecto("Gorgojo Plano de los Granos", 3));
        insectoList.add(new Insecto("Escarabajo de los Molinos", 3));
        insectoList.add(new Insecto("Barrenador Menor de los Granos", 3));
        insectoList.add(new Insecto("Barrenador Mayor de los Granos", 3));
        insectoList.add(new Insecto("Picudo del Grano", 3));
        insectoList.add(new Insecto("Palomilla Mediterránea de la Harina", 3));
        insectoList.add(new Insecto("Palomilla India", 3));
        insectoList.add(new Insecto("Palomilla de la Ropa", 3));

        // Roedores (Tipo 3)
        tipoInsectoList.add(new TipoInsecto("Voladores", "VO"));
        tipoInsectoList.add(new TipoInsecto("Insectos Rastreros", "IR"));
        tipoInsectoList.add(new TipoInsecto("Plagas de Granos Almacenados", "PGA"));
        tipoInsectoList.add(new TipoInsecto("Roedores", "R"));

        insectoList.add(new Insecto("Ratón Doméstico", 4));
        insectoList.add(new Insecto("Ratón de Campo", 4));
        insectoList.add(new Insecto("Rata de los Tejados", 4));
        insectoList.add(new Insecto("Rata Parda / Noruega", 4));


        tipoClientes.add(new TipoCliente(9, "Polleria"));
        tipoClientes.add(new TipoCliente(10, "Carnicería"));
        tipoClientes.add(new TipoCliente(11, "Planta de Cárnicos"));
        tipoClientes.add(new TipoCliente(12, "Planta de Beneficio"));
        tipoClientes.add(new TipoCliente(13, "Planta de Granos Almacenados"));
        tipoClientes.add(new TipoCliente(14, "Planta de Alimentos"));
        tipoClientes.add(new TipoCliente(15, "Restaurantes"));
        tipoClientes.add(new TipoCliente(16, "Supermercados"));
        tipoClientes.add(new TipoCliente(17, "Comercializadora de Grasas"));
        tipoClientes.add(new TipoCliente(18, "Hoteles"));
        tipoClientes.add(new TipoCliente(19, "Atracciones Mecánicas"));


        // Zonas para Polleria (ID: 9)
        zonaList.add(new Zona("Bodega", 9));
        zonaList.add(new Zona("Materia Prima", 9));
        zonaList.add(new Zona("Producción", 9));
        zonaList.add(new Zona("Empaque", 9));
        zonaList.add(new Zona("Despacho", 9));
        zonaList.add(new Zona("Oficinas", 9));
        zonaList.add(new Zona("Cafetín", 9));
        zonaList.add(new Zona("Lockers", 9));
        zonaList.add(new Zona("Exteriores", 9));
        zonaList.add(new Zona("Estantería", 9));
        zonaList.add(new Zona("Recepción", 9));
        zonaList.add(new Zona("Zócalos", 9));
        zonaList.add(new Zona("Área Social", 9));
        zonaList.add(new Zona("Otros", 9));

        // Zonas para Carnicería (ID: 10)
        zonaList.add(new Zona("Oficinas", 10));
        zonaList.add(new Zona("Cuarto de Residuos", 10));
        zonaList.add(new Zona("Producción", 10));
        zonaList.add(new Zona("Áreas Comunes", 10));
        zonaList.add(new Zona("Exteriores", 10));
        zonaList.add(new Zona("Bodegas", 10));
        zonaList.add(new Zona("Lockers", 10));
        zonaList.add(new Zona("Baños", 10));
        zonaList.add(new Zona("Cafetín", 10));
        zonaList.add(new Zona("Recepción", 10));
        zonaList.add(new Zona("Despachos", 10));

        // Zonas para Planta de Cárnicos (ID: 11)
        zonaList.add(new Zona("Lavado de Canastillas", 11));
        zonaList.add(new Zona("Puerta de Cava", 11));
        zonaList.add(new Zona("Refrigeradores", 11));
        zonaList.add(new Zona("Cuarto Químicos", 11));
        zonaList.add(new Zona("Zona de Motores", 11));
        zonaList.add(new Zona("Ingreso Principal", 11));
        zonaList.add(new Zona("Bodega Materia Prima", 11));
        zonaList.add(new Zona("Despostes", 11));
        zonaList.add(new Zona("Cámaras", 11));
        zonaList.add(new Zona("Cuarto de Residuos", 11));
        zonaList.add(new Zona("Zona Oreo", 11));
        zonaList.add(new Zona("Oficinas", 11));
        zonaList.add(new Zona("Mantenimiento", 11));
        zonaList.add(new Zona("Unidades Sanitarias", 11));
        zonaList.add(new Zona("Área Social", 11));
        zonaList.add(new Zona("Área de Canastillas", 11));
        zonaList.add(new Zona("Área de Reciclaje", 11));

        // Zonas para Planta de Beneficio (ID: 12)
        zonaList.add(new Zona("Sala de Vísceras Blancas Sifón", 12));
        zonaList.add(new Zona("Sala Vísceras Rojas Sifón", 12));
        zonaList.add(new Zona("Lockers", 12));
        zonaList.add(new Zona("Oficinas", 12));
        zonaList.add(new Zona("Comedor", 12));
        zonaList.add(new Zona("Cuartos Almacenamiento", 12));
        zonaList.add(new Zona("Zona Sucia Sifón", 12));
        zonaList.add(new Zona("Zona Intermedia Sifón", 12));
        zonaList.add(new Zona("Ingreso de Áreas", 12));
        zonaList.add(new Zona("Despacho Subproducto", 12));
        zonaList.add(new Zona("Área Insumos", 12));
        zonaList.add(new Zona("Áreas Comunes", 12));
        zonaList.add(new Zona("Baños", 12));
        zonaList.add(new Zona("Zócalos", 12));
        zonaList.add(new Zona("Cafetín", 12));
        zonaList.add(new Zona("Área Subproducto", 12));
        zonaList.add(new Zona("Sifón Sala Bovinos", 12));
        zonaList.add(new Zona("Sifón Sala Porcinos", 12));
        zonaList.add(new Zona("Parqueadero", 12));
        zonaList.add(new Zona("Salida a Corrales", 12));
        zonaList.add(new Zona("Ingreso Instalaciones", 12));
        zonaList.add(new Zona("Área PTAR", 12));
        zonaList.add(new Zona("Zonas Comunes", 12));
        zonaList.add(new Zona("Zona Motobomba", 12));
        zonaList.add(new Zona("Pasillos", 12));
        zonaList.add(new Zona("Ingresos", 12));
        zonaList.add(new Zona("Exteriores", 12));

        // Zonas para Planta de Granos Almacenados (ID: 13)
        zonaList.add(new Zona("Oficinas", 13));
        zonaList.add(new Zona("Cocineta", 13));
        zonaList.add(new Zona("Vestier", 13));
        zonaList.add(new Zona("Ingresos", 13));
        zonaList.add(new Zona("Área de Producción", 13));
        zonaList.add(new Zona("Área de Empaque", 13));
        zonaList.add(new Zona("Área de Almacenamiento", 13));
        zonaList.add(new Zona("Área de Recepción", 13));
        zonaList.add(new Zona("Muelles de Carga y Descarga", 13));
        zonaList.add(new Zona("Área de Manejo de Residuos", 13));
        zonaList.add(new Zona("Baños", 13));
        zonaList.add(new Zona("Lavado de Canastillas", 13));
        zonaList.add(new Zona("Bodega", 13));

        // Zonas para Planta de Alimentos (ID: 14)
        zonaList.add(new Zona("Cafetín", 14));
        zonaList.add(new Zona("Baños", 14));
        zonaList.add(new Zona("Bodega", 14));
        zonaList.add(new Zona("Pisos", 14));
        zonaList.add(new Zona("Producción", 14));
        zonaList.add(new Zona("Zona de Residuos", 14));
        zonaList.add(new Zona("Lockers", 14));
        zonaList.add(new Zona("Paredes", 14));
        zonaList.add(new Zona("Eléctricos", 14));
        zonaList.add(new Zona("Bodega de Materia Prima", 14));
        zonaList.add(new Zona("Recepción de Leche", 14));
        zonaList.add(new Zona("Pasteurizador", 14));
        zonaList.add(new Zona("Empaque", 14));
        zonaList.add(new Zona("Patio Trasero", 14));
        zonaList.add(new Zona("Oficinas", 14));
        zonaList.add(new Zona("Exteriores", 14));

        // Zonas para Restaurantes (ID: 15)
        zonaList.add(new Zona("Cocina", 15));
        zonaList.add(new Zona("Baños", 15));
        zonaList.add(new Zona("Bodega Materia Prima", 15));
        zonaList.add(new Zona("Lockers", 15));
        zonaList.add(new Zona("Mesas", 15));
        zonaList.add(new Zona("Áreas Comunes", 15));
        zonaList.add(new Zona("Exteriores", 15));
        zonaList.add(new Zona("Eléctricos", 15));
        zonaList.add(new Zona("Zócalos", 15));
        zonaList.add(new Zona("Pasillos", 15));
        zonaList.add(new Zona("Recepción", 15));

        // Zonas para Supermercados (ID: 16)
        zonaList.add(new Zona("Carnicería", 16));
        zonaList.add(new Zona("Legumbrería", 16));
        zonaList.add(new Zona("Bodegas", 16));
        zonaList.add(new Zona("Gerencia", 16));
        zonaList.add(new Zona("Oficinas", 16));
        zonaList.add(new Zona("Registradora", 16));
        zonaList.add(new Zona("Cafetería", 16));
        zonaList.add(new Zona("Estanterías", 16));
        zonaList.add(new Zona("Refrigeradores", 16));
        zonaList.add(new Zona("Pasillos", 16));
        zonaList.add(new Zona("Área Social", 16));
        zonaList.add(new Zona("Lockers", 16));

        // Zonas para Comercializadora de Grasas (ID: 17)
        zonaList.add(new Zona("Bodega", 17));
        zonaList.add(new Zona("Oficinas", 17));
        zonaList.add(new Zona("Producción", 17));
        zonaList.add(new Zona("PTARL", 17));
        zonaList.add(new Zona("Calderas", 17));
        zonaList.add(new Zona("Pasillos", 17));
        zonaList.add(new Zona("Zonas Verdes", 17));
        zonaList.add(new Zona("Zona Social", 17));
        zonaList.add(new Zona("Baños", 17));
        zonaList.add(new Zona("Lockers", 17));

        // Zonas para Hoteles (ID: 18)
        zonaList.add(new Zona("Habitaciones", 18));
        zonaList.add(new Zona("Baños", 18));
        zonaList.add(new Zona("Closets", 18));
        zonaList.add(new Zona("Recepción", 18));
        zonaList.add(new Zona("Pasillos", 18));
        zonaList.add(new Zona("Refrigeradores", 18));
        zonaList.add(new Zona("Bodegas", 18));
        zonaList.add(new Zona("Zócalos", 18));
        zonaList.add(new Zona("Parqueaderos", 18));
        zonaList.add(new Zona("Zonas Comunes", 18));
        zonaList.add(new Zona("Ingresos", 18));
        zonaList.add(new Zona("Lavandería", 18));

        // Zonas para Atracciones Mecánicas (ID: 19)
        zonaList.add(new Zona("Recepción", 19));
        zonaList.add(new Zona("Baños", 19));
        zonaList.add(new Zona("Bodega", 19));
        zonaList.add(new Zona("Lockers", 19));
        zonaList.add(new Zona("Oficinas", 19));
        zonaList.add(new Zona("Áreas Comunes", 19));
        zonaList.add(new Zona("Zonas de Juego", 19));
        zonaList.add(new Zona("Eléctricos", 19));
        zonaList.add(new Zona("Taquillas", 19));
        zonaList.add(new Zona("Happy Food’s", 19));
        zonaList.add(new Zona("Sala de Eventos", 19));
        zonaList.add(new Zona("Otros", 19));
        new Thread(() -> {

            //AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insertDatos();
            AppDataBase.getInstance(getApplicationContext()).getTipoClienteDAO().insertAll(tipoClientes);
            AppDataBase.getInstance(getApplicationContext()).getUsuarioDAO().insert(usuario);
            AppDataBase.getInstance(getApplicationContext()).getZonaDAO().insertAll(zonaList);
            AppDataBase.getInstance(getApplicationContext()).getHigieneDAO().insertAll(higieneList);
            AppDataBase.getInstance(getApplicationContext()).getInsectoDAO().insertAll(insectoList);


            runOnUiThread(() -> {
                new Thread(() -> {

                    AppDataBase.getInstance(getApplicationContext()).getTecnicaAplicacionDAO().insertAll(tecnicaAplicacionList);
                    AppDataBase.getInstance(getApplicationContext()).getElementoUtilizadoDAO().insertAll(materialList);
                    AppDataBase.getInstance(getApplicationContext()).getAmbienteDAO().insertAll(ambienteList);
                    AppDataBase.getInstance(getApplicationContext()).getHallazgoDAO().insertAll(hallazgoList);
                    AppDataBase.getInstance(getApplicationContext()).getTipoInsectoDAO().insertAll(tipoInsectoList);
                }).start();
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