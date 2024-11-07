package com.example.milenioapp.ui.agregarDatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.TipoInsectosAgregar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AgregarDatosFragment extends Fragment {



    public AgregarDatosFragment() {
        // Required empty public constructor
    }

    Spinner spinnerTipo;
    private long tipoInsecto;
    private Button btnAgregarInsecto, btnAgregarHigiene, btnAgregarZona, btnAgregarElementos;
    private TextInputEditText tiInsecto, tiHigiene, tiZona,tiAbreviacion,tiElementos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregar_datos, container, false);

        btnAgregarInsecto = view.findViewById(R.id.btnAgregarInsecto);
        btnAgregarHigiene = view.findViewById(R.id.btnAgregarHigiene);
        btnAgregarZona = view.findViewById(R.id.btnAgregarZona);
        btnAgregarElementos = view.findViewById(R.id.btnAgregarElementos);
        tiInsecto = view.findViewById(R.id.tiInsecto);
        tiHigiene = view.findViewById(R.id.tiHigiene);
        tiZona = view.findViewById(R.id.tiZona);
        tiAbreviacion = view.findViewById(R.id.tiAbreviacion);
        tiElementos = view.findViewById(R.id.tiElementos);
        tiElementos = view.findViewById(R.id.tiElementos);

        spinnerTipo = view.findViewById(R.id.spinnerTipo);
        llenarTipoInsecto();

        btnAgregarInsecto.setOnClickListener(view1 -> {

            if(!tiInsecto.getText().toString().trim().equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Seguro que desea agregar este insecto")
                        .setTitle("Confirmación")
                        .setCancelable(false)  // Impide que se cierre tocando fuera del diálogo
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acción a realizar al aceptar
                                agregarInsecto(tiInsecto.getText().toString(),tipoInsecto);
                                dialog.dismiss();// Llamada a la función para agregar el insecto
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cierra el diálogo sin hacer nada
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
        btnAgregarHigiene.setOnClickListener(view1 -> {
            if(!tiHigiene.getText().toString().trim().equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Seguro que desea agregar esta area locativa")
                        .setTitle("Confirmación")
                        .setCancelable(false)  // Impide que se cierre tocando fuera del diálogo
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acción a realizar al aceptar
                                agregarHigiene(tiHigiene.getText().toString());
                                dialog.dismiss();// Llamada a la función para agregar el insecto
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cierra el diálogo sin hacer nada
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnAgregarZona.setOnClickListener(view1 -> {
            if(!tiZona.getText().toString().trim().equals("") && !tiAbreviacion.getText().toString().trim().equals("")  ){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Seguro que desea agregar esta zona")
                        .setTitle("Confirmación")
                        .setCancelable(false)  // Impide que se cierre tocando fuera del diálogo
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acción a realizar al aceptar
                                agregarZona(tiZona.getText().toString(),tiAbreviacion.getText().toString());
                                dialog.dismiss();// Llamada a la función para agregar el insecto
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cierra el diálogo sin hacer nada
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnAgregarElementos.setOnClickListener(view1 -> {
            if(!tiElementos.getText().toString().trim().equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Seguro que desea agregar este elemento")
                        .setTitle("Confirmación")
                        .setCancelable(false)  // Impide que se cierre tocando fuera del diálogo
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acción a realizar al aceptar
                                agregarElementos(tiElementos.getText().toString());
                                dialog.dismiss();// Llamada a la función para agregar el insecto
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Cierra el diálogo sin hacer nada
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;

    }

    private void agregarElementos(String string) {

        ElementoUtilizado elementoUtilizado = new ElementoUtilizado(string);

        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().insert(elementoUtilizado);

            getActivity().runOnUiThread(() -> {

                Toast.makeText(getContext(),"Elemento utilizado agregado con éxito.",Toast.LENGTH_LONG).show();

            });

        }).start();
    }

    long zonaId;
    private void agregarZona(String tiZona, String tiAbreviacion) {
        new Thread(() -> {

            zonaId= AppDataBase.getInstance(getContext()).getZonaDAO().getZonaCount();

            getActivity().runOnUiThread(() -> {
                new Thread(() -> {
                    AppDataBase.getInstance(getContext()).getZonaDAO().insert(new Zona(zonaId,tiZona,tiAbreviacion,0));

                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(),"Zona agregada con éxito.",Toast.LENGTH_LONG).show();
                    });
                }).start();
            });

        }).start();
    }

    private void agregarHigiene(String string) {
        Higiene higiene = new Higiene(string,0);
        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getHigieneDAO().insert(higiene);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(),"Area locativa agregada con éxito",Toast.LENGTH_LONG).show();
            });

        }).start();
    }

    private void agregarInsecto(String string, long tipoInsecto) {
        Insecto insectoAgregar = new Insecto(string,tipoInsecto);
            new Thread(() -> {

                AppDataBase.getInstance(getContext()).getInsectoDAO().insert(insectoAgregar);

                getActivity().runOnUiThread(() -> {

                    Toast.makeText(getContext(),"Insecto agregado con exito",Toast.LENGTH_LONG).show();

                });


            }).start();
    }

    private void llenarTipoInsecto() {
        ArrayList<TipoInsectosAgregar> tipoInsectosAgregars = new ArrayList<>();
        tipoInsectosAgregars.add(new TipoInsectosAgregar(0,"Volador"));
        tipoInsectosAgregars.add(new TipoInsectosAgregar(1,"Terrestre"));
        ArrayAdapter<TipoInsectosAgregar> ArrayAdapterInsecto = new ArrayAdapter<TipoInsectosAgregar>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipoInsectosAgregars){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getView(position, convertView, parent);

                TipoInsectosAgregar producto = getItem(position);
                label.setHint(producto.getNombre());
                label.setText(producto.getNombre());

                return label;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                TipoInsectosAgregar producto = getItem(position);
                label.setText(producto.getNombre());
                return label;
            }
        };

        spinnerTipo.setAdapter(ArrayAdapterInsecto);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoInsecto  = tipoInsectosAgregars.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}