package com.example.milenioapp.ui.home.empresa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.ui.home.Empresa;

import java.util.ArrayList;

public class EmpresaDetalleFragment extends Fragment {

    private Button btnCrear;
    private ArrayList<Empresa> empresaArrayList = new ArrayList<>();
    private RecyclerView rvOpciones, rvOrdenes;
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
        rvOrdenes = view.findViewById(R.id.rvOrdenes);
        rvOrdenes.setLayoutManager(new LinearLayoutManager(getContext()));

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

        Bundle bundle = getArguments();

        long id = bundle.getLong("id",-1);
        if(id != -1){

            traerCliente(id);

        }

        return view;
    }

    private Cliente cliente;
    private void traerCliente(long id) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);

            getActivity().runOnUiThread(() -> {

                ((MainMenu)getActivity()).setActionBarTitle("Cliente: "+cliente.getNombre());

                traerOrdenes();
            });

        }).start();
    }
    ArrayList<OrdenMostrar> ordenArrayList;
    private AdapterOrdenes adapterOrdenes;
    private void traerOrdenes() {
        new Thread(() -> {

            ordenArrayList = (ArrayList<OrdenMostrar>) AppDataBase.getInstance(getContext()).getOrdenDAO().getAllMostrar();

            getActivity().runOnUiThread(() -> {

                adapterOrdenes = new AdapterOrdenes(new AdapterOrdenes.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                        Bundle bundle = new Bundle();
                        bundle.putLong("id", cliente.getId());
                        bundle.putLong("idOrden", ordenArrayList.get(position).getId());
                        switch (ordenArrayList.get(position).getTipoOrden()){
                            case "S":
                                ViewKt.findNavController(getView()).navigate(R.id.action_empresaDetalleFragment_to_crearOrdenFragment, bundle);
                                break;
                            case "I":
                                ViewKt.findNavController(getView()).navigate(R.id.action_empresaDetalleFragment_to_crearOrdenInspeccionFragment, bundle);
                                break;
                            default:
                                return;
                        }
                    }
                },ordenArrayList);

                rvOrdenes.setAdapter(adapterOrdenes);

            });
        }).start();
    }

    AdapterOpciones adapterEmpresa;
    private void desplegarAdapter() {
        empresaArrayList.clear();
        empresaArrayList.add(new Empresa(0, "Fomato de orden"));
        empresaArrayList.add(new Empresa(1, "Fomato de inspección"));
        abierto = true;

        adapterEmpresa = new AdapterOpciones(new AdapterOpciones.onItemListener() {
            @Override
            public void onItemClick(int position) {
                abierto = false;
                rvOpciones.setVisibility(View.GONE);

                String finalText = "Crear ▼";
                btnCrear.setText(finalText);

                Bundle bundle = new Bundle();
                bundle.putLong("id", cliente.getId());
                switch (position){
                    case 0:
                        ViewKt.findNavController(getView()).navigate(R.id.action_empresaDetalleFragment_to_crearOrdenFragment, bundle);
                        break;
                    case 1:

                        ViewKt.findNavController(getView()).navigate(R.id.action_empresaDetalleFragment_to_crearOrdenInspeccionFragment, bundle);
                        break;
                    default:
                        return;
                }

            }
        },empresaArrayList);
        rvOpciones.setAdapter(adapterEmpresa);
    }
}