package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.lamparas;

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
import com.example.milenioapp.database.entity.LamparaGroup;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CrearOrdenServicioFragment;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.AdapterZonas;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.GrupoZonaMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.ProductoMostrar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CustomDialogLampara extends DialogFragment {

    private final CrearOrdenInspeccionFragment crearOrdenInspeccionFragment;
    private LamparaGroup lamparaGroup;
    private boolean bloquear;
    private int position;
    private long idOrden;

    public CustomDialogLampara(CrearOrdenInspeccionFragment crearOrdenInspeccionFragment,int position, LamparaGroup lamparaGroup,long idOrden, boolean bloquear) {
        this.crearOrdenInspeccionFragment = crearOrdenInspeccionFragment;
        this.position = position;
        this.lamparaGroup = lamparaGroup;
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

    private TextView errorText;
    private TextInputEditText tiTipoDeInsecto,tiLamparaNro,tiUbicacion,tiCantidad,tiObservaciones;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_lampara, container, false);
        ((MainMenu) getActivity()).noGirarPantalla();



        tiTipoDeInsecto = view.findViewById(R.id.tiTipoDeInsecto);
        tiLamparaNro = view.findViewById(R.id.tiLamparaNro);
        tiUbicacion = view.findViewById(R.id.tiUbicacion);
        tiCantidad = view.findViewById(R.id.tiCantidad);
        tiObservaciones = view.findViewById(R.id.tiObservaciones);

        errorText = view.findViewById(R.id.errorText);


        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCerrar = view.findViewById(R.id.btnCerrar);


        btnCerrar.setOnClickListener(v -> {
            dismiss();
        });



        if(lamparaGroup != null){
            llenarDatos();
        }else{
            btnGuardar.setOnClickListener(view1 -> {
                if(validarDatos()){
                    lamparaGroup = new LamparaGroup(tiTipoDeInsecto.getText().toString(),tiLamparaNro.getText().toString(),
                            tiUbicacion.getText().toString(),Integer.parseInt(tiCantidad.getText().toString()),
                            tiObservaciones.getText().toString(),idOrden);

                    crearOrdenInspeccionFragment.actualizarLamparaGroup(position,lamparaGroup);

                }
            });
        }

        if (bloquear){
            tiTipoDeInsecto.setEnabled(false);
            tiLamparaNro.setEnabled(false);
            tiUbicacion.setEnabled(false);
            tiCantidad.setEnabled(false);
            tiObservaciones.setEnabled(false);
            btnGuardar.setVisibility(View.GONE);
        }
        return view;
    }

    private void llenarDatos(){
        tiTipoDeInsecto.setText(lamparaGroup.getTipoDeInsecto());
        tiLamparaNro.setText(lamparaGroup.getLamparaN());
        tiUbicacion.setText(lamparaGroup.getUbicacionLampara());
        tiCantidad.setText(lamparaGroup.getCantadidadEncontrada());
        tiObservaciones.setText(lamparaGroup.getObservaciones());

        btnGuardar.setOnClickListener(view -> {
            if(validarDatos()){
                lamparaGroup.setTipoDeInsecto(tiTipoDeInsecto.getText().toString());
                lamparaGroup.setLamparaN(tiLamparaNro.getText().toString());
                lamparaGroup.setUbicacionLampara(tiUbicacion.getText().toString());
                lamparaGroup.setCantadidadEncontrada(Integer.parseInt(tiCantidad.getText().toString()));
                lamparaGroup.setObservaciones(tiObservaciones.getText().toString());

                crearOrdenInspeccionFragment.actualizarLamparaGroup(position,lamparaGroup);

            }
        });
    }
    private boolean validarDatos() {
        if(tiTipoDeInsecto.getText().toString().equals("") || tiLamparaNro.getText().toString().equals("") || tiUbicacion.getText().toString().equals("") || tiCantidad.getText().toString().equals("") || tiObservaciones.getText().toString().equals("")){
            errorText.setText("ERROR FALTA UN DATO");
            errorText.setVisibility(View.VISIBLE);
            return false;
        }
        return true;

    }



}
