package com.example.milenioapp.ui.ordenes.crearOrden;

import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.milenioapp.ui.utilidades.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearOrdenFragment extends Fragment {

    private Cliente cliente;

    public CrearOrdenFragment() {
        // Required empty public constructor
    }
    long idOrden,id;
    private RecyclerView rvHigiene, rvZonas,rvInsectos;

    private Button btnSobreElServicio, btnGuardar;
    private  ArrayList<HygieneItem> hygieneItems;
    private List<Zona> zonasTratadas;
    private Orden orden;

    private TextInputEditText tiEmpresa, tiFecha, tiCliente,
            tiNit,tiContacto,tiTelefono,tiDireccion, tiSede,
            tiFechaActual, tiOperario,tiHoraIngreso,tiHoraSalida;

    private TextInputEditText tiObservaciones, tiCorrectivos;


    int thour, tminute;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden, container, false);

        tiEmpresa = view.findViewById(R.id.tiEmpresa);
        tiFecha = view.findViewById(R.id.tifecha);
        tiCliente = view.findViewById(R.id.tiCliente);
        tiNit = view.findViewById(R.id.tiNit);
        tiContacto = view.findViewById(R.id.tiContacto);
        tiTelefono = view.findViewById(R.id.tiTelefono);
        tiDireccion = view.findViewById(R.id.tiDireccion);
        tiSede = view.findViewById(R.id.tiSede);
        tiFechaActual = view.findViewById(R.id.tiFechaActual);
        tiOperario = view.findViewById(R.id.tiOperario);
        tiHoraIngreso = view.findViewById(R.id.tvTimeIngreso);
        tiHoraSalida = view.findViewById(R.id.tvTimeSalida);

        tiObservaciones = view.findViewById(R.id.tiObservaciones);
        tiCorrectivos = view.findViewById(R.id.tiCorrectivos);

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
    private void traerDatosEspecies(Orden orden) {
        insectoGroupArrayList = new ArrayList<>();
        new Thread(() -> {

            insectoGroupArrayList = (ArrayList<InsectoGroupMostrar>) AppDataBase.getInstance(getContext()).getInsectoDAO().getInsectosGuardados(orden.getId());

            getActivity().runOnUiThread(() -> {
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
        },insectoGroupArrayList, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
        rvInsectos.setAdapter(adapterInsectos);

    }

    private AdapterHigiene adapter;
    private void traerDatosHigiene() {
        hygieneItems = new ArrayList<>();
        new Thread(() -> {

          ArrayList<Higiene> higienes = (ArrayList<Higiene>) AppDataBase.getInstance(getContext()).getHigieneDAO().getAll();

            getActivity().runOnUiThread(() -> {
                hygieneItems.clear();
                for (int i = 0; i < higienes.size(); i++) {
                    hygieneItems.add(new HygieneItem(higienes.get(i).getId(),higienes.get(i).getNombre(), "NA" ));
                }
                llenarAdapterHigiene();

            });

        }).start();

    }

    private void traerDatosHigiene(Orden orden) {
        hygieneItems = new ArrayList<>();
        new Thread(() -> {

            hygieneItems = (ArrayList<HygieneItem>) AppDataBase.getInstance(getContext()).getHigieneDAO().getAgregados(orden.getId());

            getActivity().runOnUiThread(() -> {
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
        }, hygieneItems, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));

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
        },this, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));

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

                //insert
                btnGuardar.setOnClickListener(v -> {

                    //orden = new Orden(0,);

                });


                Calendar c = Calendar.getInstance();
                final int hora=c.get(Calendar.HOUR_OF_DAY);
                final int minuto=c.get(Calendar.MINUTE);
                tiHoraIngreso.setOnClickListener(v -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            (view1, hourOfDay, minute) -> {
                                thour = hourOfDay;
                                tminute = minute;
                                String time = thour + ":" + tminute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");

                                    tiHoraIngreso.setText(f12Hours.format(date).toLowerCase());
                                    horaEntrada.setTimeInMillis(date.getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }, hora, minuto, false
                    );
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //timePickerDialog.updateTime(thour,tminute);
                    timePickerDialog.show();

                });
                tiHoraSalida.setOnClickListener(v -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            getActivity(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            (view1, hourOfDay, minute) -> {
                                thour = hourOfDay;
                                tminute = minute;
                                String time = thour + ":" + tminute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");

                                    tiHoraSalida.setText(f12Hours.format(date).toLowerCase());
                                    horaSalida.setTimeInMillis(date.getTime());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }, hora, minuto, false
                    );
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //timePickerDialog.updateTime(thour,tminute);
                    timePickerDialog.show();

                });

            });

        }).start();
    }
    private Calendar horaEntrada, horaSalida;
    private void obtenerEmpresa(long id, long idOrden) {

        new Thread(() -> {

            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);
            orden = AppDataBase.getInstance(getContext()).getOrdenDAO().getByid(idOrden);


            getActivity().runOnUiThread(() -> {

                llenarDatos();

            });

        }).start();
    }

    private void llenarDatos() {
        Utilities utilities = new Utilities();
        Calendar calendar = Calendar.getInstance();
        Calendar horaIngreso = Calendar.getInstance();
        Calendar horaSalida = Calendar.getInstance();
        if(orden!= null){
            calendar.setTimeInMillis(orden.getFechaUsuario());
            tiEmpresa.setText(cliente.getNombre());
            tiFecha.setText(utilities.getFechaString(calendar));
            tiFechaActual.setText(utilities.getFechaActual());
            tiCliente.setText(cliente.getNombreContacto());
            tiNit.setText(cliente.getNIT());
            tiContacto.setText(cliente.getNombreContacto());
            tiTelefono.setText(cliente.getTelefono());
            tiDireccion.setText(cliente.getDireccion());
            tiSede.setText(cliente.getSede());
            tiOperario.setText(orden.getOperario());

            horaIngreso.setTimeInMillis(orden.getHoraIngreso());
            tiHoraIngreso.setText(utilities.getFechaString(horaIngreso));

            horaSalida.setTimeInMillis(orden.getHoraSalida());
            tiHoraSalida.setText(utilities.getFechaString(horaSalida));

            guardarFirmaOperario(utilities.stringToBitMap(orden.getFirmaOperario()));
            guardarFirmaAcompa(utilities.stringToBitMap(orden.getFirmaAyudante()));

            tiObservaciones.setText(orden.getObservacionesTecnicas());
            tiCorrectivos.setText(orden.getCorrectivos());

            traerZonasAgregadas(orden);
            traerDatosHigiene(orden);
            traerDatosEspecies(orden);

            if(orden.getEstadoEnvio().equals("E")){
                tiObservaciones.setEnabled(false);
                tiCorrectivos.setEnabled(false);
                tiOperario.setEnabled(false);
                tiHoraIngreso.setEnabled(false);
                tiHoraSalida.setEnabled(false);
            }

            //update
            btnGuardar.setOnClickListener(v -> {

            });
        }
    }

    private void traerZonasAgregadas(Orden orden) {

        new Thread(() -> {

            grupoZonas = (ArrayList<GrupoZonaMostrar>) AppDataBase.getInstance(getContext()).getGrupoZonaDAO().getZonasAgregadas(orden.getId());

            getActivity().runOnUiThread(() -> {
                cargarAdapterZonas();
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

        final CustomDialogZonas dialog = new CustomDialogZonas(this, grupoZonaMostrar,cliente.getIdTipo(),
                position, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
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