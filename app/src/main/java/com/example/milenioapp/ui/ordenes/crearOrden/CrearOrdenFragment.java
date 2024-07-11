package com.example.milenioapp.ui.ordenes.crearOrden;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrden.hallazgos.AdapterHigiene;
import com.example.milenioapp.ui.ordenes.crearOrden.hallazgos.HygieneItem;
import com.example.milenioapp.ui.ordenes.crearOrden.insecto.AdapterInsectos;
import com.example.milenioapp.ui.ordenes.crearOrden.insecto.InsectoGroupMostrar;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.AdapterZonas;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.CustomDialogZonas;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.GrupoZonaMostrar;

import java.util.ArrayList;
import java.util.List;

public class CrearOrdenFragment extends Fragment {

    private Cliente cliente;

    public CrearOrdenFragment() {
        // Required empty public constructor
    }
    long idOrden,id;
    private RecyclerView rvHigiene, rvZonas,rvInsectos;

    private Button btnSobreElServicio, btnGuardar;
    private  ArrayList<HygieneItem> items;
    private List<Zona> zonasTratadas;
    private Orden orden;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden, container, false);

        btnSobreElServicio = view.findViewById(R.id.btnSobreElServicio);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        rvHigiene = view.findViewById(R.id.rvHigiene);
        rvZonas = view.findViewById(R.id.rvZonas);
        rvInsectos = view.findViewById(R.id.rvInsectos);



        Bundle bundle = getArguments();
        id = bundle.getLong("id",-1);
        idOrden = bundle.getLong("idOrden",-1);


        if(idOrden != -1){
            obtenerEmpresa(id, idOrden);
        }else{
            obtenerEmpresa(id);
        }

        rvHigiene.setLayoutManager(new LinearLayoutManager(getContext()));
        rvZonas.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    private ArrayList<Insecto> insectoArrayList;
    private ArrayList<InsectoGroupMostrar> insectoGroupArrayList;
    private void traerDatosEspecies() {
        insectoArrayList = new ArrayList<>();
        insectoGroupArrayList = new ArrayList<>();
        new Thread(() -> {

            insectoArrayList = (ArrayList<Insecto>) AppDataBase.getInstance(getContext()).getInsectoDAO().getAll();

            getActivity().runOnUiThread(() -> {

                for (int i = 0; i < insectoArrayList.size(); i++) {
                    insectoGroupArrayList.add(new InsectoGroupMostrar(i,insectoArrayList.get(i).getDescripcion(), insectoArrayList.get(i).getId(),"" ));
                }
                llenarAdapterInsecto();

            });

        }).start();

    }
    private AdapterInsectos adapterInsectos;
    private void llenarAdapterInsecto() {
        adapterInsectos = new AdapterInsectos(new AdapterInsectos.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },insectoGroupArrayList);
        rvInsectos.setAdapter(adapterInsectos);

    }

    private AdapterHigiene adapter;
    private void traerDatosHigiene() {
        items = new ArrayList<>();
        new Thread(() -> {

          ArrayList<Higiene> higienes = (ArrayList<Higiene>) AppDataBase.getInstance(getContext()).getHigieneDAO().getAll();

            getActivity().runOnUiThread(() -> {
                items.clear();
                for (int i = 0; i < higienes.size(); i++) {
                    items.add(new HygieneItem(higienes.get(i).getId(),higienes.get(i).getNombre(), higienes.get(i).getS() ));
                }
                llenarAdapterHigiene();

            });

        }).start();

    }

    private void llenarAdapterHigiene() {
        adapter = new AdapterHigiene( new AdapterHigiene.onItemListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("pesk", "onItemClick: click");
            }
        }, items);

        rvHigiene.setAdapter(adapter);
    }
    AdapterZonas adapterZonas;
    private ArrayList<GrupoZonaMostrar> grupoZonas;
    private void traerZonasDefault() {
        new Thread(() -> {

            grupoZonas = (ArrayList<GrupoZonaMostrar>) AppDataBase.getInstance(getContext()).getZonaDAO().getByTypeDefault(cliente.getIdTipo());

            getActivity().runOnUiThread(() ->{
                
                cargarAdapterZonas();
               
            });

        }).start();
    }

    private void cargarAdapterZonas() {
        adapterZonas = new AdapterZonas(grupoZonas, new AdapterZonas.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },this,false);

        rvZonas.setAdapter(adapterZonas);
    }

    private void obtenerEmpresa(long id) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);

            getActivity().runOnUiThread(() -> {
                traerZonasDefault();
                zonasTratadas = new ArrayList<>();

                traerDatosHigiene();
                traerDatosEspecies();
            });

        }).start();
    }
    private void obtenerEmpresa(long id, long idOrden) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);
            orden = AppDataBase.getInstance(getContext()).getOrdenDAO().getByid(idOrden);


            getActivity().runOnUiThread(() -> {



            });

        }).start();
    }

    public void agregarInsecto(Insecto insecto) {
    }

    public void agregarZona(Zona n) {
    }

    public void agregarCebadero(Cebadero cebadero) {
    }

    public void borrarZona(int finalPosition) {
        grupoZonas.remove(finalPosition);
        cargarAdapterZonas();
    }

    public void abrirCustomDialog(GrupoZonaMostrar grupoZonaMostrar, int position) {

        final CustomDialogZonas dialog = new CustomDialogZonas(this, grupoZonaMostrar,cliente.getIdTipo(), position);
        dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
    }

    public void actualizarZona(GrupoZonaMostrar zonaAgregada, int position) {
        grupoZonas.set(position,zonaAgregada);
        cargarAdapterZonas();
    }
    private Bitmap firmaAcompa, firmaOperario;
    public void guardarFirmaAcompa(Bitmap bitmap) {
        this.firmaAcompa = bitmap;
    }

    public void guardarFirmaOperario(Bitmap bitmap) {
        this.firmaOperario = bitmap;
    }
}