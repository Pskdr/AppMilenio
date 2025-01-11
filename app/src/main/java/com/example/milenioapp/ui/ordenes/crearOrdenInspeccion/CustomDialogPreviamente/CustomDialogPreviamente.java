package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CustomDialogPreviamente;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.ui.home.empresa.AdapterOrdenes;
import com.example.milenioapp.ui.home.empresa.OrdenMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CrearOrdenServicioFragment;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.AdapterAgregar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.TipoInsectosAgregar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.GrupoZonaMostrar;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogPreviamente extends DialogFragment {

    private Fragment instancia;
    private RecyclerView rvOrdenesAll;
    private Button btnManual;

    public CustomDialogPreviamente(Fragment instancia) {
        this.instancia = instancia;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog_agregar_previamente, container, false);

        rvOrdenesAll = view.findViewById(R.id.rvOrdenesAll);
        btnManual = view.findViewById(R.id.btnManual);

        btnManual.setOnClickListener((view1) -> {
            ((CrearOrdenInspeccionFragment) instancia).llenarDatosNormal();
            dismiss();
        });

        ((MainMenu) getActivity()).noGirarPantalla();
        rvOrdenesAll.setLayoutManager(new LinearLayoutManager(getContext()));
        traerDatos();

        return view;
    }

    OrdenMostrar seleccionado;
    private AdapterOrdenes adapterOrdenes;
    ArrayList<OrdenMostrar> ordenesSeleccionar;

    private void traerDatos() {
        new Thread(() -> {

            ordenesSeleccionar = (ArrayList<OrdenMostrar>) AppDataBase.getInstance(getContext()).getOrdenDAO().getAllByDate();

            getActivity().runOnUiThread(() -> {
                adapterOrdenes = new AdapterOrdenes(new AdapterOrdenes.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                        seleccionado = ordenesSeleccionar.get(position);

                        ((CrearOrdenInspeccionFragment) instancia).traerDatosAnterior(seleccionado);
                        dismiss();
                    }
                },ordenesSeleccionar);

                rvOrdenesAll.setAdapter(adapterOrdenes);
            });

        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);
            dialog.getWindow().setLayout(width, height);
        }
    }


}
