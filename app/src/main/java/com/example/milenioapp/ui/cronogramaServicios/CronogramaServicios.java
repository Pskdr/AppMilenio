package com.example.milenioapp.ui.cronogramaServicios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.milenioapp.R;

import java.util.ArrayList;

public class CronogramaServicios extends Fragment {


    public CronogramaServicios() {
        // Required empty public constructor
    }

    private String fecha;
    private TextView tvFecha;
    private RecyclerView rvCronograma;
    private AdapterCronograma adapterCronograma;
    private ArrayList<Orden> ordenArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cronograma_servicios, container, false);

        tvFecha = view.findViewById(R.id.tvFecha);
        rvCronograma = view.findViewById(R.id.rvCronograma);
        rvCronograma.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();

        fecha = bundle.getString("day-month");

        tvFecha.setText(fecha);

        llenarAdapterCronograma();
        return view;
    }

    private void llenarAdapterCronograma() {
        ordenArrayList.clear();
        ordenArrayList.add(new Orden(0,"EL TROZO","Alexander calle", "8:00 PM"));
        ordenArrayList.add(new Orden(1,"EL TROZO","Alexander calle", "8:00 PM"));
        ordenArrayList.add(new Orden(2,"EL TROZO","Alexander calle", "8:00 PM"));
        ordenArrayList.add(new Orden(3,"EL TROZO","Alexander calle", "8:00 PM"));

        adapterCronograma = new AdapterCronograma(new AdapterCronograma.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },ordenArrayList);

        rvCronograma.setAdapter(adapterCronograma);
    }
}