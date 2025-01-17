package com.example.milenioapp.ui.ordenes.crearOrdenLocativos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.milenioapp.R;
import com.google.android.material.textfield.TextInputEditText;


public class CrearOrdenLocativosFragment extends Fragment {

    public CrearOrdenLocativosFragment() {
        // Required empty public constructor
    }


    private TextInputEditText tiEmpresa, tiFecha, tiCliente,
            tiNit, tiContacto, tiTelefono, tiDireccion, tiSede,
            tiFechaActual, tiOperario;
    private TextView tvHoraIngreso, tvHoraSalida;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden_locativos, container, false);


        tiEmpresa = view.findViewById(R.id.tiEmpresa);
        tiFecha = view.findViewById(R.id.tifecha);
        tiCliente = view.findViewById(R.id.tiCliente);
        tiNit = view.findViewById(R.id.tiNit);
        tiContacto = view.findViewById(R.id.tiContacto);
        tiTelefono = view.findViewById(R.id.tiTelefono);
        tiDireccion = view.findViewById(R.id.tiDireccion);
        tiSede = view.findViewById(R.id.tiSede);
        tiFechaActual = view.findViewById(R.id.tiFechaActual);
        tiOperario = view.findViewById(R.id.tiOperario);
        tvHoraIngreso = view.findViewById(R.id.tvTimeIngreso);
        tvHoraSalida = view.findViewById(R.id.tvTimeSalida);


        return view;
    }
}