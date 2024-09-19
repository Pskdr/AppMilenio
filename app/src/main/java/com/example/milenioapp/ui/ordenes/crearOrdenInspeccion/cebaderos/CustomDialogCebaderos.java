package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;
import com.google.android.material.textfield.TextInputEditText;

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

    private TextView errorText;
    private TextInputEditText tiZona,tiNro,tiEstado,tiObservaciones;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_cebadero, container, false);
        ((MainMenu) getActivity()).noGirarPantalla();



        tiZona = view.findViewById(R.id.tiZona);
        tiNro = view.findViewById(R.id.tiNro);
        tiEstado = view.findViewById(R.id.tiEstado);
        tiObservaciones = view.findViewById(R.id.tiObservaciones);

        errorText = view.findViewById(R.id.errorText);


        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCerrar = view.findViewById(R.id.btnCerrar);


        btnCerrar.setOnClickListener(v -> {
            dismiss();
        });



        if(cebaderoGroup != null){
            llenarDatos();
        }else{
            btnGuardar.setOnClickListener(view1 -> {
                if(validarDatos()){
                    cebaderoGroup = new CebaderoGroup(idOrden,tiZona.getText().toString(),tiNro.getText().toString(),
                            tiEstado.getText().toString(),tiObservaciones.getText().toString());

                    crearOrdenInspeccionFragment.actualizarCebadero(position, cebaderoGroup);

                }
            });
        }

        if (bloquear){
            tiZona.setEnabled(false);
            tiNro.setEnabled(false);
            tiEstado.setEnabled(false);
            tiObservaciones.setEnabled(false);
            btnGuardar.setVisibility(View.GONE);
        }
        return view;
    }

    private void llenarDatos(){
        tiZona.setText(cebaderoGroup.getZona());
        tiNro.setText(cebaderoGroup.getNro());
        tiEstado.setText(cebaderoGroup.getEstado());
        tiObservaciones.setText(cebaderoGroup.getObservaciones());

        btnGuardar.setOnClickListener(view -> {
            if(validarDatos()){
                cebaderoGroup.setZona(tiZona.getText().toString());
                cebaderoGroup.setNro(tiNro.getText().toString());
                cebaderoGroup.setEstado(tiEstado.getText().toString());
                cebaderoGroup.setObservaciones(tiObservaciones.getText().toString());

                crearOrdenInspeccionFragment.actualizarCebadero(position, cebaderoGroup);

            }
        });
    }
    private boolean validarDatos() {
        if(tiZona.getText().toString().equals("") || tiNro.getText().toString().equals("") || tiEstado.getText().toString().equals("") || tiObservaciones.getText().toString().equals("")){
            errorText.setText("ERROR FALTA UN DATO");
            errorText.setVisibility(View.VISIBLE);
            return false;
        }
        return true;

    }



}
