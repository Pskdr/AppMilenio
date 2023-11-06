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
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TextView btnCrearCliente;
    private RecyclerView rvEmpresas;
    private ArrayList<Cliente> empresas;
    private SearchView svEmpresas;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        btnCrearCliente = view.findViewById(R.id.btnCrearCliente);
        rvEmpresas = view.findViewById(R.id.rvEmpresas);
        rvEmpresas.setLayoutManager(new LinearLayoutManager(getContext()));

        svEmpresas = view.findViewById(R.id.svEmpresas);
        traerClientes();

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
                    traerClientes();
                }

                return false;
            }
        });

        return view;
    }

    private void traerClientes() {
        new Thread(() -> {

            empresas = (ArrayList<Cliente>) AppDataBase.getInstance(getContext()).getClienteDAO().getAll();

            getActivity().runOnUiThread(() -> {

                llenarEmpresas();

            });

        }).start();
    }

    AdapterEmpresa adapterEmpresa;

    private void llenarEmpresas() {

        adapterEmpresa = new AdapterEmpresa(new AdapterEmpresa.onItemListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                bundle.putLong("id",
                        empresas.get(position).getId());
                ViewKt.findNavController(getView()).navigate(R.id.action_nav_home_to_empresaDetalleFragment, bundle);
            }
        }, empresas);

        rvEmpresas.setAdapter(adapterEmpresa);

    }


}