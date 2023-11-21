package com.example.milenioapp.ui.ordenes.crearOrden;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.ui.home.Empresa;

public class CrearOrdenFragment extends Fragment {

    private Cliente empresa;

    public CrearOrdenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden, container, false);

        Bundle bundle = getArguments();
        long id = bundle.getLong("id",-1);

        if(id != -1){
            obtenerEmpresa(id);
        }

        return view;
    }

    private void obtenerEmpresa(long id) {

        new Thread(() -> {

            empresa = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);

        }).start();
    }
}