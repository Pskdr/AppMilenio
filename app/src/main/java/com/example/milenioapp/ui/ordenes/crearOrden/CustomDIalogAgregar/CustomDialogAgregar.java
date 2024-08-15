package com.example.milenioapp.ui.ordenes.crearOrden.CustomDIalogAgregar;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.ui.ordenes.crearOrden.CrearOrdenInspeccionFragment;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.GrupoZonaMostrar;
import java.util.ArrayList;
import java.util.List;

public class CustomDialogAgregar extends DialogFragment {

    private CrearOrdenInspeccionFragment instancia;
    private Cliente cliente;
    private final long opcion; // Z(0) - H(1) - I(2)
    private TextView tvAgregarNombre;
    private RecyclerView rvItemsAll;
    private LinearLayout lyTipo;
    private Button btnCerrar;

    public CustomDialogAgregar(CrearOrdenInspeccionFragment instancia,Cliente cliente, long opcion) {
        this.instancia = instancia;
        this.cliente = cliente;
        this.opcion = opcion;
    }

    private Spinner spinnerTipo,spinnerInfestacion;

    private String infestacionNivel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog_agregar_insecto, container, false);

        tvAgregarNombre = view.findViewById(R.id.tvAgregarNombre);
        lyTipo = view.findViewById(R.id.lyTipo);
        rvItemsAll = view.findViewById(R.id.rvItemsAll);
        btnCerrar = view.findViewById(R.id.btnCerrar);
        spinnerTipo = view.findViewById(R.id.spinnerTipo);
        spinnerInfestacion = view.findViewById(R.id.spinnerInfestacion);


        switch ((int) opcion){
            case 0:
                tvAgregarNombre.setText("AGREGAR ZONA");
                lyTipo.setVisibility(View.GONE);

                traerDatos();
                break;
            case 1:
                tvAgregarNombre.setText("AGREGAR HIGIENE LOCATIVOS");
                lyTipo.setVisibility(View.GONE);

                traerDatos();
                break;
            case 2:
                tvAgregarNombre.setText("AGREGAR INSECTO");
                llenarTipoInsectos();
                break;
            case 3:

                tvAgregarNombre.setText("AGREGAR ELEMENTOS Y/O EQUIPO");

                traerDatos();
                break;
            default:

        }
        ((MainMenu) getActivity()).noGirarPantalla();
        rvItemsAll.setLayoutManager(new LinearLayoutManager(getContext()));
        btnCerrar.setOnClickListener(v -> {
            dismiss();

        });


        return view;
    }

    private void llenarTipoInsectos() {

        ArrayList<TipoInsectosAgregar> tipoInsectosAgregars = new ArrayList<>();
        tipoInsectosAgregars.add(new TipoInsectosAgregar(0,"Volador"));
        tipoInsectosAgregars.add(new TipoInsectosAgregar(1,"Terrestre"));
        ArrayAdapter<TipoInsectosAgregar> ArrayAdapterInsecto = new ArrayAdapter<TipoInsectosAgregar>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipoInsectosAgregars){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getView(position, convertView, parent);

                TipoInsectosAgregar producto = getItem(position);
                label.setHint(producto.getNombre());
                label.setText(producto.getNombre());

                return label;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                TipoInsectosAgregar producto = getItem(position);
                label.setText(producto.getNombre());
                return label;
            }
        };

        spinnerTipo.setAdapter(ArrayAdapterInsecto);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoInsecto  = tipoInsectosAgregars.get(position).getId();
                traerDatos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> nivelInfestacion = new ArrayList<>();
        nivelInfestacion.add("ALTO");
        nivelInfestacion.add("MEDIO");
        nivelInfestacion.add("BAJO");


        ArrayAdapter<String> arrayAdapterDocis = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,nivelInfestacion){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getView(position, convertView, parent);

                String producto = getItem(position);
                label.setHint(producto);
                label.setText(producto);

                return label;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                String producto = getItem(position);
                label.setText(producto);
                return label;
            }
        };

        spinnerInfestacion.setAdapter(arrayAdapterDocis);
        spinnerInfestacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                infestacionNivel = arrayAdapterDocis.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private long tipoInsecto;

    private ArrayList<GrupoZonaMostrar> grupoZonas;
    private AdapterAgregar adapterAgregar;
    private ArrayList<ItemMostrar> itemMostrars;

    private void traerDatos() {
        new Thread(() -> {
            if(itemMostrars != null){
                itemMostrars.clear();
            }

            switch ((int) opcion){
                case 0:
                    itemMostrars = (ArrayList<ItemMostrar>) AppDataBase.getInstance(getContext()).getZonaDAO().getByTypeDefaultAgregar(cliente.getIdTipo());
                    break;
                case 1:
                    itemMostrars = (ArrayList<ItemMostrar>) AppDataBase.getInstance(getContext()).getHigieneDAO().getAllAgregar();
                    break;
                case 2:
                    itemMostrars = (ArrayList<ItemMostrar>) AppDataBase.getInstance(getContext()).getInsectoDAO().gettAllAgregar(tipoInsecto);
                    break;
                case 3:
                    itemMostrars = (ArrayList<ItemMostrar>) AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().gettAllAgregar();
                default:
                    itemMostrars = new ArrayList<>();
            }

            getActivity().runOnUiThread(() -> {

                adapterAgregar = new AdapterAgregar(new AdapterAgregar.onItemListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch ((int) opcion){
                            case 0:
                                instancia.agregarZona(itemMostrars.get(position).getId());
                                break;
                            case 1:
                                instancia.agregarHigiene(itemMostrars.get(position).getId());
                                break;
                            case 2:
                                if(!infestacionNivel.equals("")) {
                                    instancia.agregarInsecto(itemMostrars.get(position).getId(), infestacionNivel);
                                }else{
                                    return;
                                }
                            case 3:
                                instancia.agregarElemento(itemMostrars.get(position).getId());
                                break;
                            default:
                        }
                        dismiss();

                    }
                },itemMostrars);
                rvItemsAll.setAdapter(adapterAgregar);

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
