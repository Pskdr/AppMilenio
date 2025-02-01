package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogCebaderos extends DialogFragment {

    private final CrearOrdenInspeccionFragment crearOrdenInspeccionFragment;
    private CebaderoGroup cebaderoGroup;
    private boolean bloquear;
    private int position;
    private long idOrden;

    public CustomDialogCebaderos(CrearOrdenInspeccionFragment crearOrdenInspeccionFragment, int position, CebaderoGroup cebaderoGroup, long idOrden, boolean bloquear) {
        this.crearOrdenInspeccionFragment = crearOrdenInspeccionFragment;
        this.position = position;
        this.cebaderoGroup = cebaderoGroup;
        this.idOrden = idOrden;
        this.bloquear = bloquear;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
            dialog.getWindow().setLayout(width, height);
        }
    }
    private Button btnGuardar, btnCerrar;

    private TextView errorText, tvEstadoCebadro;
    private EstadoCebadero estadoCebadero;
    private TextInputEditText tiZona,tiNro,tiObservaciones;
    private Spinner spinnerEstado;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_cebadero, container, false);
        ((MainMenu) getActivity()).noGirarPantalla();



        tiZona = view.findViewById(R.id.tiZona);
        tvEstadoCebadro = view.findViewById(R.id.tvEstadoCebadro);
        tiNro = view.findViewById(R.id.tiNro);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);
        tiObservaciones = view.findViewById(R.id.tiObservaciones);

        errorText = view.findViewById(R.id.errorText);


        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCerrar = view.findViewById(R.id.btnCerrar);


        btnCerrar.setOnClickListener(v -> {
            dismiss();
        });

        tvEstadoCebadro.setText("Estado Cebadero:");

        if(cebaderoGroup != null){
            llenarDatos();
        }else{

            List<EstadoCebadero> estadoCebaderoList = new ArrayList<>();
            estadoCebaderoList.add(new EstadoCebadero("- Seleccione una opción -",""));
            estadoCebaderoList.add(new EstadoCebadero("I  ", "Inactiva"));
            estadoCebaderoList.add(new EstadoCebadero("CA","Captura"));
            estadoCebaderoList.add(new EstadoCebadero("CO","Consumo"));
            estadoCebaderoList.add(new EstadoCebadero("SU","Sucia"));
            estadoCebaderoList.add(new EstadoCebadero("DE","Deterioro"));
            estadoCebaderoList.add(new EstadoCebadero("NU","Nueva"));

            ArrayAdapter<EstadoCebadero> arrayAdapterDocis = new ArrayAdapter<EstadoCebadero>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,estadoCebaderoList){

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView label = (TextView) super.getView(position, convertView, parent);

                    EstadoCebadero producto = getItem(position);
                    label.setHint(producto.getAbreviado());
                    label.setText(producto.getAbreviado());

                    return label;
                }

                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                    EstadoCebadero producto = getItem(position);
                    label.setText(producto.getText());
                    return label;
                }
            };

            spinnerEstado.setAdapter(arrayAdapterDocis);
            spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    estadoCebadero = new EstadoCebadero(arrayAdapterDocis.getItem(position).getAbreviado(),arrayAdapterDocis.getItem(position).getText());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btnGuardar.setOnClickListener(view1 -> {
                if(validarDatos()){
                    cebaderoGroup = new CebaderoGroup(idOrden,tiZona.getText().toString(),tiNro.getText().toString(),
                            estadoCebadero.getAbreviado(), tiObservaciones.getText().toString());
                    crearOrdenInspeccionFragment.insertarCebadero(cebaderoGroup);

                    dismiss();
                }
            });
        }

        if (bloquear){
            tiZona.setEnabled(false);
            tiNro.setEnabled(false);
            spinnerEstado.setEnabled(false);
            tiObservaciones.setEnabled(false);
            btnGuardar.setVisibility(View.GONE);
        }
        return view;
    }

    private void llenarDatos(){
        tiZona.setText(cebaderoGroup.getZona());
        tiNro.setText(cebaderoGroup.getNro());
        tiObservaciones.setText(cebaderoGroup.getObservaciones());

        List<EstadoCebadero> estadoCebaderoList = new ArrayList<>();
        estadoCebaderoList.add(new EstadoCebadero("I  ", "Inactiva"));
        estadoCebaderoList.add(new EstadoCebadero("CA","Captura"));
        estadoCebaderoList.add(new EstadoCebadero("CO","Consumo"));
        estadoCebaderoList.add(new EstadoCebadero("SU","Sucia"));
        estadoCebaderoList.add(new EstadoCebadero("DE","Deterioro"));
        estadoCebaderoList.add(new EstadoCebadero("NU","Nueva"));

        for (int i = 0; i < estadoCebaderoList.size(); i++) {
            if(estadoCebaderoList.get(i).getAbreviado().equals(cebaderoGroup.getEstado())){
                estadoCebadero = new EstadoCebadero(cebaderoGroup.getEstado(),estadoCebaderoList.get(i).getText());
                estadoCebaderoList.remove(i);
                estadoCebaderoList.add(0,estadoCebadero);
                break;
            }
        }
        ArrayAdapter<EstadoCebadero> arrayAdapterDocis = new ArrayAdapter<EstadoCebadero>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,estadoCebaderoList){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getView(position, convertView, parent);

                EstadoCebadero producto = getItem(position);
                label.setHint(producto.getAbreviado());
                label.setText(producto.getAbreviado());

                return label;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                EstadoCebadero producto = getItem(position);
                label.setText(producto.getText());
                return label;
            }
        };

        spinnerEstado.setAdapter(arrayAdapterDocis);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoCebadero = new EstadoCebadero(arrayAdapterDocis.getItem(position).getAbreviado(),arrayAdapterDocis.getItem(position).getText());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(view -> {
            if(validarDatos()){
                cebaderoGroup.setZona(tiZona.getText().toString());
                cebaderoGroup.setNro(tiNro.getText().toString());
                cebaderoGroup.setEstado(estadoCebadero.getAbreviado());
                cebaderoGroup.setObservaciones(tiObservaciones.getText().toString());

                crearOrdenInspeccionFragment.actualizarCebadero(position, cebaderoGroup);
                dismiss();
            }
        });
    }
    private boolean validarDatos() {
        if(tiZona.getText().toString().equals("") || tiNro.getText().toString().equals("") || estadoCebadero.getAbreviado().isEmpty() || estadoCebadero.getText().isEmpty()){
            errorText.setText("ERROR FALTA UN DATO");
            errorText.setVisibility(View.VISIBLE);
            return false;
        }
        return true;

    }



}
