package com.example.milenioapp.ui.ordenes.crearOrden;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.database.entity.Zona;

import java.util.ArrayList;

public class CustomDialogAgregarFragment extends DialogFragment {

    private static final String TAG = "cdmf";
    RecyclerView rvMaterialesAll;
    Button btnCerrar;
    ArrayList<AgregarObjeto> itemArrayList = new ArrayList<>();
    ArrayList<AgregarObjeto> materialArray;
    MaterialAdapterDialog materialAdapterDialog;
    CrearOrdenInspeccionFragment instancia;
    SearchView svCodigo;
    private Spinner spinner;
    private LinearLayout lyTipo;
    private String tipo;

    private TextView tvTipode;

    public CustomDialogAgregarFragment(ArrayList<AgregarObjeto> itemArray,
                                       CrearOrdenInspeccionFragment materialesFragment, String tipo) {
        this.materialArray = itemArray;
        this.instancia = materialesFragment;
        this.tipo = tipo;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_agregar_insecto, container, false);
        ((MainMenu)getActivity()).noGirarPantalla();
        rvMaterialesAll = view.findViewById(R.id.rvItemsAll);
        btnCerrar = view.findViewById(R.id.btnCerrar);
        svCodigo = view.findViewById(R.id.svCodigoBuscar);
        spinner = view.findViewById(R.id.spinnerTipo);
        lyTipo = view.findViewById(R.id.lyTipo);
        tvTipode = view.findViewById(R.id.tvTipode);

        rvMaterialesAll.setLayoutManager(new LinearLayoutManager(getContext()));

        svCodigo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.equals(null) && materialAdapterDialog != null){
                    materialAdapterDialog.getFilter().filter(s);
                    materialAdapterDialog.notifyDataSetChanged();
                }else{
                    obtenerItems();
                }

                return false;
            }
        });


        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainMenu)getActivity()).girarPantallaNormal();
                dismiss();
            }
        });


        switch (tipo){
            case "E":
                lyTipo.setVisibility(View.VISIBLE);
                tvTipode.setText("Tipo de especie:");
                ArrayList<TipoInsecto> tipoInsecto = new ArrayList<>();
                tipoInsecto.add(new TipoInsecto("Volador","V"));
                tipoInsecto.add(new TipoInsecto("Terrestre","T"));
                ArrayAdapter<TipoInsecto> arrayAdapter = new ArrayAdapter<TipoInsecto>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipoInsecto){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getView(position, convertView, parent);

                        TipoInsecto estadoActividad = getItem(position);
                        label.setHint(estadoActividad.getDescripcion());
                        label.setText(estadoActividad.getDescripcion());

                        return label;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                        TipoInsecto estadoActividad = getItem(position);
                        label.setText(estadoActividad.getDescripcion());

                        return label;
                    }
                };

                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tipoInsectoSeleccionado = (TipoInsecto) adapterView.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                break;
            case "C":
            case "Z":
                lyTipo.setVisibility(View.GONE);
            default:
                break;
        }

        obtenerItems();
        return view;
    }
    TipoInsecto tipoInsectoSeleccionado;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void obtenerItems() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (tipo){
                    case "E":
                        if(tipoInsectoSeleccionado == null){
                            return;
                        }
                        itemArrayList = (ArrayList<AgregarObjeto>) AppDataBase.getInstance(getContext()).getInsectoDAO().getByTipoMostrar(tipoInsectoSeleccionado.getId());
                        break;
                    case "Z":
                        //itemArrayList = (ArrayList<AgregarObjeto>) AppDataBase.getInstance(getContext()).getZonaDAO().getAll();
                        break;
                    case "C":

                        //itemArrayList = (ArrayList<AgregarObjeto>) AppDataBase.getInstance(getContext()).getCebaderoDAO().getAllMostrar();
                        break;
                    default:
                        break;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listarRecyclerView();

                    }
                });
            }
        }).start();
    }



    private void listarRecyclerView(){
        materialAdapterDialog = new MaterialAdapterDialog(itemArrayList, new MaterialAdapterDialog.onItemListener() {
            @Override
            public void onItemClick(int position)
            {
                switch (tipo){
                    case "E":
                        Insecto insecto = new Insecto(itemArrayList.get(position).getId(),itemArrayList.get(position).getDescription(),itemArrayList.get(position).getIdTipo());
                        insecto.setId(itemArrayList.get(position).getId());
                            //instancia.agregarInsecto(insecto);
                        break;
                    case "Z":
                        //instancia.agregarZona(new Zona(itemArrayList.get(position).getId(),itemArrayList.get(position).getDescription(),"N",itemArrayList.get(position).getIdTipo()));
                        break;
                    case "C":
                        instancia.agregarCebadero(new Cebadero(itemArrayList.get(position).getId(),itemArrayList.get(position).getDescription(),itemArrayList.get(position).getIdTipo() + ""));
                        break;
                    default:
                        break;
                }
            }
        });
        rvMaterialesAll.setAdapter(materialAdapterDialog);
    }

}
