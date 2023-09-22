package com.example.milenioapp.ui.home.empresa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.ui.home.AdapterEmpresa;
import com.example.milenioapp.ui.home.Empresa;

import java.util.ArrayList;

public class EmpresaDetalleFragment extends Fragment {

    private Button btnCrear;
    private ArrayList<Empresa> empresaArrayList = new ArrayList<>();
    private RecyclerView rvOpciones;
    boolean abierto = false;
    public EmpresaDetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empresa_detalle, container, false);

        btnCrear = view.findViewById(R.id.btnCrear);
        rvOpciones = view.findViewById(R.id.rvOpciones);
        rvOpciones.setLayoutManager(new LinearLayoutManager(getContext()));

        btnCrear.setOnClickListener(view1 -> {

            desplegarAdapter();
            String finalText = "Crear ";
            if(abierto) {
                finalText += "►";
            }else{
                finalText += "▼";
            }
            rvOpciones.setVisibility(View.VISIBLE);
            btnCrear.setText(finalText);
        });

        ((MainMenu)getActivity()).setActionBarTitle("Cliente: Empresa#");
        return view;
    }

    AdapterEmpresa adapterEmpresa;
    private void desplegarAdapter() {
        empresaArrayList.clear();
        empresaArrayList.add(new Empresa(0, "Fomato de orden"));
        empresaArrayList.add(new Empresa(1, "Fomato de inspección"));
        empresaArrayList.add(new Empresa(2, "Fomato de inspección #2 planta"));
        empresaArrayList.add(new Empresa(3, "Fomato de arreglos locativos"));
        empresaArrayList.add(new Empresa(4, "Fomato de desinfección"));
        abierto = true;

        adapterEmpresa = new AdapterEmpresa(new AdapterEmpresa.onItemListener() {
            @Override
            public void onItemClick(int position) {
                abierto = false;
                rvOpciones.setVisibility(View.GONE);

                String finalText = "Crear ▼";
                btnCrear.setText(finalText);

            }
        },empresaArrayList);
        rvOpciones.setAdapter(adapterEmpresa);
    }
}