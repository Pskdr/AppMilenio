package com.example.milenioapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TextView btnCrearCliente;
    private RecyclerView rvEmpresas;
    private ArrayList<Empresa> empresas = new ArrayList<>();
    private SearchView svEmpresas;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        btnCrearCliente = view.findViewById(R.id.btnCrearCliente);
        rvEmpresas = view.findViewById(R.id.rvEmpresas);
        rvEmpresas.setLayoutManager(new LinearLayoutManager(getContext()));

        svEmpresas = view.findViewById(R.id.svEmpresas);

        llenarEmpresas();

        btnCrearCliente.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            ViewKt.findNavController(getView()).navigate(R.id.action_nav_home_to_crearClienteFragment2, bundle);
        });

        svEmpresas.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals(null) && adapterEmpresa != null){
                    adapterEmpresa.getFilter().filter(newText);
                    adapterEmpresa.notifyDataSetChanged();
                }else{
                    llenarEmpresas();
                }

                return false;
            }
        });

        return view;
    }

    AdapterEmpresa adapterEmpresa;

    private void llenarEmpresas() {
        empresas.add(new Empresa(0, "PLAFA"));
        empresas.add(new Empresa(1, "Empresa random 1"));
        empresas.add(new Empresa(2, "Empresa random 2"));
        empresas.add(new Empresa(3, "Empresa random 3"));
        empresas.add(new Empresa(4, "Empresa random 4"));

        adapterEmpresa = new AdapterEmpresa(new AdapterEmpresa.onItemListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                ViewKt.findNavController(getView()).navigate(R.id.action_nav_home_to_empresaDetalleFragment, bundle);
            }
        }, empresas);

        rvEmpresas.setAdapter(adapterEmpresa);

    }


}