package com.example.milenioapp.ui.tecnicos.crearTecnico;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Empleado;
import com.example.milenioapp.ui.utilidades.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CrearEmpleadoFragment extends Fragment {

    public CrearEmpleadoFragment() {
        // Required empty public constructor
    }

    private TextInputEditText tiNombre;
    private Button btnGuardar, btnAgregarFoto;
    private TextView btnBorrar;
    private ImageView ivFoto;

    private Bitmap bitmap;
    private static final int PICK_IMAGENES_CODE = 0;
    private Uri imageUri;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGENES_CODE:
                if(resultCode == Activity.RESULT_OK){
                    if(data.getClipData() != null){
                        //multiply images
                        int cantidadDeImagenes = data.getClipData().getItemCount();
                        for(int i = 0; i<cantidadDeImagenes; i++){
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            try {
                                bitmap = null;
                                //imageurl = getRealPathFromURI(imageUri);
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageUri);
                                bitmap = getResizedBitmap(bitmap,1000);

                                ivFoto.setVisibility(View.VISIBLE);
                                ivFoto.setImageBitmap(bitmap);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                    }else{
                        //single image

                        if(data.getData() != null){
                            Uri imageUri = data.getData();
                            try {
                                bitmap = null;
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageUri);
                                bitmap = getResizedBitmap(bitmap,1000);

                                ivFoto.setVisibility(View.VISIBLE);
                                ivFoto.setImageBitmap(bitmap);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                break;
            default:
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_empleado, container, false);

        tiNombre = view.findViewById(R.id.tiNombre);
        btnAgregarFoto = view.findViewById(R.id.btnAgregarFoto);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnBorrar = view.findViewById(R.id.btnBorrar);
        ivFoto = view.findViewById(R.id.ivFoto);

        Bundle bundle = getArguments();

        long id = bundle.getLong("id",-1);
        boolean nuevo;
        if(id != -1){
            nuevo = false;
            traerEmpleado(id);

        } else {
            nuevo = true;
        }

        btnAgregarFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED){

                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setTitle("Solicitud de Permiso")
                        .setMessage("El permiso para utilizar la cámara se encuentra denegado, se debe habilitar manualmente en su celular para utilizar esta funcionalidad en la app.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        })
                        .create().show();
                return;
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿De dónde vendrá la imagen?")
                    .setPositiveButton("Galería", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            traerDesdeGaleria();
                        }
                    })
                    .setNegativeButton("Cámara", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            abrirCamara();
                        }
                    });
            builder.create();
            builder.show();
        });

        btnGuardar.setOnClickListener(v -> {
            Utilities utilities = new Utilities();
            if(validarDatos()) {
                empleado = new Empleado(tiNombre.getText().toString(), utilities.bitMapToString(bitmap));
                if (nuevo) {
                    new Thread(() -> {
                        AppDataBase.getInstance(getContext()).getEmpleadoDAO().insert(empleado);
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(),"Empleado insertado correctamente.",Toast.LENGTH_LONG).show();
                            ((MainMenu) getActivity()).onBackPressed();
                        });
                    }).start();
                } else {
                    new Thread(() -> {
                        AppDataBase.getInstance(getContext()).getEmpleadoDAO().update(empleado);
                        getActivity().runOnUiThread(() -> {

                            Toast.makeText(getContext(),"Empleado actualizado correctamente.",Toast.LENGTH_LONG).show();
                            ((MainMenu) getActivity()).onBackPressed();
                        });
                    }).start();
                }
            }

        });

        return view;
    }

    private boolean validarDatos() {
        if(tiNombre.getText().toString().trim().equals("")){
            tiNombre.setError("Este campo es obligatorio");
            Toast.makeText(getContext(),"El nombre es obligatorio",Toast.LENGTH_LONG).show();
            return false;
        }
        if(bitmap == null){
            Toast.makeText(getContext(),"La foto es obligatoria",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private Empleado empleado;
    private void traerEmpleado(long id) {
        new Thread(() ->{

            empleado = AppDataBase.getInstance(getContext()).getEmpleadoDAO().getById(id);

            getActivity().runOnUiThread(() -> {

                llenarDatos();

            });

        }).start();
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    private ContentValues values;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        resultCamera(PICTURE_RESULT, result.getResultCode());
                    }
                });
    }

    private static final int PICTURE_RESULT = 122 ;
    public void resultCamera(int requestCode, int resultCode){
        switch (requestCode) {
            case PICTURE_RESULT:
                if (requestCode == PICTURE_RESULT)
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            bitmap = null;
                            if (Build.VERSION.SDK_INT >= 29) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getApplicationContext().getContentResolver(), imageUri);
                                try {
                                    bitmap = ImageDecoder.decodeBitmap(source);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), imageUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            bitmap = getResizedBitmap(bitmap,1000);

                            ivFoto.setImageBitmap(bitmap);
                            ivFoto.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }

    }

    private void traerDesdeGaleria() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Seleccionar la Aplicacion"), PICK_IMAGENES_CODE);

    }
    private void abrirCamara() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "MyPicture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
        imageUri = getActivity().getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //startActivityForResult(intent, PICTURE_RESULT);
        someActivityResultLauncher.launch(intent);

    }

    private void llenarDatos() {

        Utilities utilities = new Utilities();

        tiNombre.setText(empleado.getNombre());
        ivFoto.setVisibility(View.VISIBLE);
        ivFoto.setImageBitmap(utilities.stringToBitMap(empleado.getFoto()));
        btnBorrar.setVisibility(View.VISIBLE);
        btnBorrar.setOnClickListener(v ->  {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Seguro desea eliminar el empleado?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            new Thread(() -> {
                                AppDataBase.getInstance(getContext()).getEmpleadoDAO().delete(empleado);
                                getActivity().runOnUiThread(() -> {
                                    Toast.makeText(getContext(),"Eliminado exitosamente.",Toast.LENGTH_LONG).show();
                                    ((MainMenu)getActivity()).onBackPressed();
                                });

                            }).start();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();

        });

    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}