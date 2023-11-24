package com.example.milenioapp.ui.ordenes.crearOrden;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.GrupoZona;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.Zona;

import java.util.ArrayList;
import java.util.List;

public class CrearOrdenFragment extends Fragment {

    private Cliente cliente;

    public CrearOrdenFragment() {
        // Required empty public constructor
    }
    long idOrden,id;
    RecyclerView rvZonas;

    private Button btnAgregarEspecie, btnAgregarZonas, btnAgregarCebaderos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden, container, false);

        btnAgregarEspecie = view.findViewById(R.id.btnAgregarEspecie);
        btnAgregarEspecie = view.findViewById(R.id.btnAgregarEspecie);
        btnAgregarEspecie = view.findViewById(R.id.btnAgregarEspecie);

        rvZonas = view.findViewById(R.id.rvZonas);
        rvZonas.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
         id = bundle.getLong("id",-1);
         idOrden = bundle.getLong("idOrden",-1);


        if(idOrden != -1){
            obtenerEmpresa(id, idOrden);
        }else{
            obtenerEmpresa(id);
            traerZonasDefault();
        }

        return view;
    }
    AdapterZonas adapterZonas;
    private void traerZonasDefault() {
        new Thread(() -> {

            List<Zona> zonaList = AppDataBase.getInstance(getContext()).getZonaDAO().getByTypeDefault(cliente.getIdTipo());

            getActivity().runOnUiThread(() ->{

                ArrayList<GrupoZona> grupoZonas = new ArrayList<>();

                for(int i = 0; i<zonaList.size(); i++){

                    grupoZonas.add(new GrupoZona(zonaList.get(i).getId(),idOrden,-1));

                }
                adapterZonas = new AdapterZonas(grupoZonas, new AdapterZonas.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                },this,false);

                rvZonas.setAdapter(adapterZonas);

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