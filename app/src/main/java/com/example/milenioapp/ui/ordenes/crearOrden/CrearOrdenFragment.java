package com.example.milenioapp.ui.ordenes.crearOrden;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.Zona;

import java.util.List;

public class CrearOrdenFragment extends Fragment {

    private Cliente cliente;

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
        long idOrden = bundle.getLong("idOrden",-1);

        if(idOrden != -1){
            obtenerEmpresa(id, idOrden);
        }else{
            obtenerEmpresa(id);
            traerZonasDefault();
        }

        return view;
    }

    private void traerZonasDefault() {
        new Thread(() -> {

            List<Zona> zonaList = AppDataBase.getInstance(getContext()).getZonaDAO().getByTypeDefault(cliente.getIdTipo());

            getActivity().runOnUiThread(() ->{



            });

        }).start();
    }

    private void obtenerEmpresa(long id) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);

            getActivity().runOnUiThread(() -> {
                traerZonasDefault();
            });

        }).start();
    }
    private void obtenerEmpresa(long id, long idOrden) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);
            Orden orden = AppDataBase.getInstance(getContext()).getOrdenDAO().getByid(idOrden);

        }).start();
    }
}