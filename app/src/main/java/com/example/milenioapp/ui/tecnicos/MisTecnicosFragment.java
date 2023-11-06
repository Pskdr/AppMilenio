package com.example.milenioapp.ui.tecnicos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;

import java.util.ArrayList;

public class MisTecnicosFragment extends Fragment {

    private RecyclerView rvTecnicos;
    private TextView btnCrearTecnico;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        btnCrearTecnico = view.findViewById(R.id.btnCrearTecnico);
        rvTecnicos = view.findViewById(R.id.rvTecnicos);
        rvTecnicos.setLayoutManager(new LinearLayoutManager(getContext()));

        traerEmpleado();

        btnCrearTecnico.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            ViewKt.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_crearEmpleadoFragment, bundle);

        });
        return view;
    }

    private void traerEmpleado() {
        new Thread(() -> {

            tecnicoArrayList = (ArrayList<Tecnico>) AppDataBase.getInstance(getContext()).getEmpleadoDAO().getTecnicos();


            getActivity().runOnUiThread(() -> {
                llenarTecnicos();
            });

        }).start();
    }

    private ArrayList<Tecnico> tecnicoArrayList;
    private AdapterTecnico adapterTecnico;
    private void llenarTecnicos() {

        adapterTecnico = new AdapterTecnico(new AdapterTecnico.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },tecnicoArrayList,this);

        rvTecnicos.setAdapter(adapterTecnico);

    }

    public void agregarTarea(long id) {

        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        ViewKt.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_asignarServicioFragment, bundle);
    }

    public void editarTecnico(long id) {

        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        ViewKt.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_crearEmpleadoFragment, bundle);
    }
}