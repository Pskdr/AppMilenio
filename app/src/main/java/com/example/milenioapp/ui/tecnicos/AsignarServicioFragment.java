package com.example.milenioapp.ui.tecnicos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.home.Empresa;
import com.example.milenioapp.ui.home.empresa.AdapterOpciones;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AsignarServicioFragment extends Fragment {


    public AsignarServicioFragment() {
        // Required empty public constructor
    }


    private TextView tvTecnico, tvCronogramaServicios;
    TextInputEditText tifecha, tiHora, tiDescripcion, tiNota;

    private AdapterOpciones adapterEmpresa;
    private ArrayList<Empresa> empresaArrayList = new ArrayList<>();
    private Button btnEmpresa, btnAsignarServicio;
    boolean abierto = false;
    private RecyclerView rvEmpresa;
    private Empresa empresaSeleccionada;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asignar_servicio, container, false);

        btnAsignarServicio = view.findViewById(R.id.btnAsignarServicio);
        btnEmpresa = view.findViewById(R.id.btnEmpresa);
        rvEmpresa = view.findViewById(R.id.rvEmpresas);
        rvEmpresa.setLayoutManager(new LinearLayoutManager(getContext()));


        btnEmpresa.setOnClickListener(view1 -> {

            llenarAdapter();
            String finalText = "Seleccionar empresa ";
            if(abierto) {
                finalText += "►";
            }else{
                finalText += "▼";
            }
            btnEmpresa.setText(finalText);
            empresaSeleccionada = null;

            rvEmpresa.setVisibility(View.VISIBLE);

        });
        return view;
    }

    private void llenarAdapter() {

        empresaArrayList.clear();
        empresaArrayList.add(new Empresa(0, "Fomato de orden"));
        empresaArrayList.add(new Empresa(1, "Fomato de inspección"));
        empresaArrayList.add(new Empresa(2, "Fomato de inspección #2 planta"));
        empresaArrayList.add(new Empresa(3, "Fomato de arreglos locativos"));
        empresaArrayList.add(new Empresa(4, "Fomato de desinfección"));
        abierto = true;

        adapterEmpresa = new AdapterOpciones(new AdapterOpciones.onItemListener() {
            @Override
            public void onItemClick(int position) {
                btnEmpresa.setText(empresaArrayList.get(position).getNombre());
                abierto = false;
                empresaSeleccionada = empresaArrayList.get(position);
                rvEmpresa.setVisibility(View.GONE);
            }
        },empresaArrayList);
        rvEmpresa.setAdapter(adapterEmpresa);

    }
}