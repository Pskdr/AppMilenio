package com.example.milenioapp.ui.tecnicos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;

public class MisTecnicosFragment extends Fragment {

    private RecyclerView rvTecnicos;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        rvTecnicos = view.findViewById(R.id.rvTecnicos);
        rvTecnicos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarTecnicos();
        return view;
    }

    private ArrayList<Tecnico> tecnicoArrayList = new ArrayList<>();
    private AdapterTecnico adapterTecnico;
    private void llenarTecnicos() {
        tecnicoArrayList.clear();
        tecnicoArrayList.add(new Tecnico(0,"Alexander Calle"));
        tecnicoArrayList.add(new Tecnico(1,"Alejandro Perez"));
        tecnicoArrayList.add(new Tecnico(2,"Jesus Armando"));

        adapterTecnico = new AdapterTecnico(new AdapterTecnico.onItemListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                ViewKt.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_asignarServicioFragment, bundle);
            }
        },tecnicoArrayList);

        rvTecnicos.setAdapter(adapterTecnico);

    }
}