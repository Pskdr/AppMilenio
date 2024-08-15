package com.example.milenioapp.ui.ordenes.crearOrden;

import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.ViewKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.database.entity.GrupoZona;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.HigieneGroup;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.InsectoGroup;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrden.CustomDIalogAgregar.CustomDialogAgregar;
import com.example.milenioapp.ui.ordenes.crearOrden.elementosUtilizados.AdapterElementosUtilizados;
import com.example.milenioapp.ui.ordenes.crearOrden.elementosUtilizados.ElementoUtilizadoMostrar;
import com.example.milenioapp.ui.ordenes.crearOrden.firma.FirmaFragment;
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

public class CrearOrdenInspeccionFragment extends Fragment {

    private Cliente cliente;

    public CrearOrdenInspeccionFragment() {
        // Required empty public constructor
    }
    long idOrden,id;
    private RecyclerView rvHigiene, rvZonas,rvInsectos,rvElementosUtilizados;

    private Button btnGuardar, btnFirmaAyudante, btnFirmaOperario,btnCertificado;
    private  ArrayList<HygieneItem> hygieneItems;
    private List<Zona> zonasTratadas;
    private Orden orden;

    private TextInputEditText tiEmpresa, tiFecha, tiCliente,
            tiNit,tiContacto,tiTelefono,tiDireccion, tiSede,
            tiFechaActual, tiOperario;

    private TextInputEditText tiObservaciones, tiCorrectivos,tiObjetivoDelServicio;

    private TextView tvHoraIngreso, tvHoraSalida;
    int thour, tminute;

    private Button btnAgregarZona, btnAgregarAreaLocativa, btnAgregarEspecie,btnAgregarElementos;
    private Spinner spinnerTipoDeServicio;
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
        tvHoraIngreso = view.findViewById(R.id.tvTimeIngreso);
        tvHoraSalida = view.findViewById(R.id.tvTimeSalida);

        spinnerTipoDeServicio = view.findViewById(R.id.spinnerTipoDeServicio);

        tiObservaciones = view.findViewById(R.id.tiObservaciones);
        tiCorrectivos = view.findViewById(R.id.tiCorrectivos);
        tiObjetivoDelServicio = view.findViewById(R.id.tiObjetivoDelServicio);

        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnFirmaAyudante = view.findViewById(R.id.btnFirmaAyudante);
        btnFirmaOperario = view.findViewById(R.id.btnFirmaTecnico);
        btnCertificado = view.findViewById(R.id.btnCertificado);

        rvHigiene = view.findViewById(R.id.rvHigiene);
        rvZonas = view.findViewById(R.id.rvZonas);
        rvInsectos = view.findViewById(R.id.rvInsectos);
        rvElementosUtilizados = view.findViewById(R.id.rvElementos);

        btnAgregarZona = view.findViewById(R.id.btnAgregarZona);
        btnAgregarAreaLocativa = view.findViewById(R.id.btnAgregarAreaLocativa);
        btnAgregarEspecie = view.findViewById(R.id.btnAgregarEspecie);
        btnAgregarElementos = view.findViewById(R.id.btnAgregarElementeos);



        Bundle bundle = getArguments();
        id = bundle.getLong("id",-1);
        idOrden = bundle.getLong("idOrden",-1);


        if(idOrden != -1){
            obtenerEmpresa(id, idOrden);
        }else{
            obtenerEmpresa(id);
        }


        rvElementosUtilizados.setLayoutManager(new LinearLayoutManager(getContext()));
        rvInsectos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHigiene.setLayoutManager(new LinearLayoutManager(getContext()));
        rvZonas.setLayoutManager(new LinearLayoutManager(getContext()));


        btnAgregarZona.setOnClickListener(v -> {

            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,0);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });

        btnAgregarAreaLocativa.setOnClickListener(v -> {

            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,1);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });

        btnAgregarEspecie.setOnClickListener(v -> {

            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,2);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });
        btnAgregarElementos.setOnClickListener(v -> {

            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,3);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });
        return view;
    }
    private ArrayList<Insecto> insectoArrayList;
    private ArrayList<InsectoGroupMostrar> insectoGroupArrayList;
    private void traerDatosEspecies() {
        insectoArrayList = new ArrayList<>();
        insectoGroupArrayList = new ArrayList<>();
        llenarAdapterInsecto();

    }
    private ArrayList<ElementoUtilizadoMostrar> elementoUtilizadoArray;
    private void traerDatosElementos() {
        elementoUtilizadoArray = new ArrayList<>();

        llenarAdapterElementos();

    }
    private AdapterElementosUtilizados adapterElementosUtilizados;
    private void llenarAdapterElementos() {
        adapterElementosUtilizados = new AdapterElementosUtilizados(new AdapterElementosUtilizados.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, elementoUtilizadoArray,this, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
        rvElementosUtilizados.setAdapter(adapterElementosUtilizados);
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
    private void traerDatosElementos(Orden orden) {
        elementoUtilizadoArray = new ArrayList<>();
        new Thread(() -> {

            elementoUtilizadoArray = (ArrayList<ElementoUtilizadoMostrar>) AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().getElementosGuardados(orden.getId());

            getActivity().runOnUiThread(() -> {
                llenarAdapterElementos();
            });

        }).start();

    }
    private AdapterInsectos adapterInsectos;
    private void llenarAdapterInsecto() {
        adapterInsectos = new AdapterInsectos(new AdapterInsectos.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },insectoGroupArrayList,this, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
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
                    hygieneItems.add(new HygieneItem(0,higienes.get(i).getId(),higienes.get(i).getNombre(), "NA" ));
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


                Utilities utilities = new Utilities();
                Calendar calendarActual = Calendar.getInstance();
                tiEmpresa.setText(cliente.getNombre());
                tiFecha.setText(utilities.getFechaString(calendarActual));
                tiFechaActual.setText(utilities.getFechaActual());
                tiCliente.setText(cliente.getNombreContacto());
                tiNit.setText(cliente.getNIT());
                tiContacto.setText(cliente.getNombreContacto());
                tiTelefono.setText(cliente.getTelefono());
                tiDireccion.setText(cliente.getDireccion());
                tiSede.setText(cliente.getSede());
                btnFirmaAyudante.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, true, firmaAyudante, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");

                });

                btnFirmaOperario.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, false,firmaOperario, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
                });
                traerZonasDefault();
                zonasTratadas = new ArrayList<>();

                traerDatosHigiene();
                traerDatosEspecies();
                traerDatosElementos();

                //insert
                btnGuardar.setOnClickListener(v -> {
                    if(validarDatos()){
                        if (orden == null){
                            Calendar calendar = Calendar.getInstance();
                            orden = new Orden(0,calendar.getTimeInMillis(),calendar.getTimeInMillis(),
                                    0,0,cliente.getId(),utilities.generarSerial(),tiOperario.getText().toString(),
                                    horaEntrada.getTimeInMillis(),horaSalida.getTimeInMillis(),tiObservaciones.getText().toString(),
                                    tiCorrectivos.getText().toString(),utilities.bitMapToString(firmaOperario),
                                    utilities.bitMapToString(firmaAyudante),tipoDeServicio,tiObjetivoDelServicio.getText().toString(),"N");
                            insertarOrdenNueva(orden);
                        }
                    }

                });


                Calendar c = Calendar.getInstance();
                final int hora=c.get(Calendar.HOUR_OF_DAY);
                final int minuto=c.get(Calendar.MINUTE);
                tvHoraIngreso.setOnClickListener(v -> {
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

                                    tvHoraIngreso.setText(f12Hours.format(date).toLowerCase());
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
                tvHoraSalida.setOnClickListener(v -> {
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

                                    tvHoraSalida.setText(f12Hours.format(date).toLowerCase());
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
                List<String> tipoDeServicioList = new ArrayList<>();
                tipoDeServicioList.add("Aspersión");
                tipoDeServicioList.add("Nebulización");
                tipoDeServicioList.add("Termonebulización");
                tipoDeServicioList.add("Polvo Seco");
                tipoDeServicioList.add("Insepcción");
                tipoDeServicioList.add("Desratización");
                tipoDeServicioList.add("Aplicación de gel");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipoDeServicioList){

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

                spinnerTipoDeServicio.setAdapter(arrayAdapter);
                spinnerTipoDeServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tipoDeServicio = arrayAdapter.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            });

        }).start();
    }



    private String tipoDeServicio;

    private void insertarOrdenNueva(Orden orden) {
        new Thread(() -> {

            long idOrden = AppDataBase.getInstance(getContext()).getOrdenDAO().insert(orden);

            getActivity().runOnUiThread(() -> {

                ArrayList<GrupoZona> grupoZonaInsert = new ArrayList<>();
                for (int i = 0; i < grupoZonas.size(); i++) {
                    grupoZonaInsert.add(new GrupoZona(grupoZonas.get(i).getIdZona(),
                            idOrden,grupoZonas.get(i).getProducto(),grupoZonas.get(i).getIngredienteActivo(),
                            grupoZonas.get(i).getDocificacion(),grupoZonas.get(i).getTecnicaAplicacion(),grupoZonas.get(i).getFechaVencimiento()));
                }

                ArrayList<HigieneGroup> higieneGroupsInsert = new ArrayList<>();
                for (int i = 0; i < hygieneItems.size(); i++) {
                    higieneGroupsInsert.add(new HigieneGroup(idOrden,hygieneItems.get(i).getIdHigiene(),
                            hygieneItems.get(i).getS()));
                }

                ArrayList<InsectoGroup> insectoGroupsInsert = new ArrayList<>();
                for (int i = 0; i < insectoGroupArrayList.size(); i++) {
                    insectoGroupsInsert.add(new InsectoGroup(idOrden,insectoGroupArrayList.get(i).getIdInsecto(),
                            insectoGroupArrayList.get(i).getS(),insectoGroupArrayList.get(i).getNivelInfestacion()));
                }

                new Thread(() -> {
                    AppDataBase.getInstance(getContext()).getGrupoZonaDAO().insertAll(grupoZonaInsert);
                    AppDataBase.getInstance(getContext()).getHigieneGroupDAO().insertAll(higieneGroupsInsert);
                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupsInsert);

                    getActivity().runOnUiThread(() -> {

                        Toast.makeText(getContext(),"Inserto exitoso",Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    });
                }).start();
            });

        }).start();
    }

    private boolean validarDatos() {

        if(tiOperario.getText().toString().equals("") || tvHoraIngreso.getText().toString().equals("")
                || tvHoraSalida.getText().toString().equals("") ){

            Toast.makeText(getContext(),"Tiene que seleccionar el operario, la hora de ingreso y la hora de salida",Toast.LENGTH_LONG).show();
            return false;
        }

        for (int i = 0; i < insectoGroupArrayList.size(); i++) {
            if(insectoGroupArrayList.get(i).getS().equals("")){
                Toast.makeText(getContext(),"Tiene que seleccionar todas las especies",Toast.LENGTH_LONG).show();
                return false;
            }

        }
        for (int i = 0; i < hygieneItems.size(); i++) {
            if(hygieneItems.get(i).getS().equals("")){
                Toast.makeText(getContext(),"Tiene que seleccionar todas las areas locativas ",Toast.LENGTH_LONG).show();
                return false;
            }

        }
        for (int i = 0; i < grupoZonas.size(); i++) {
            if(grupoZonas.get(i).getIngredienteActivo().equals("") || grupoZonas.get(i).getProducto().equals("") || grupoZonas.get(i).getDocificacion().equals("")){
                Toast.makeText(getContext(),"Tiene que seleccionar todos los datos de las zonas ",Toast.LENGTH_LONG).show();
                return false;
            }

        }

        if(firmaOperario == null || firmaAyudante == null){
            Toast.makeText(getContext(),"Las firmas son obligatorias",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private Calendar horaEntrada= Calendar.getInstance(), horaSalida = Calendar.getInstance();
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
        if(orden!= null && cliente != null){
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
            tiObjetivoDelServicio.setText(orden.getObjetivoDelServicio());

            horaIngreso.setTimeInMillis(orden.getHoraIngreso());
            tvHoraIngreso.setText(utilities.refactorFecha(utilities.split(utilities.getFechaString(horaIngreso),1)));

            horaSalida.setTimeInMillis(orden.getHoraSalida());
            tvHoraSalida.setText(utilities.refactorFecha(utilities.split(utilities.getFechaString(horaSalida),1)));

            guardarFirmaOperario(utilities.stringToBitMap(orden.getFirmaOperario()));
            guardarFirmaAcompa(utilities.stringToBitMap(orden.getFirmaAyudante()));

            tiObservaciones.setText(orden.getObservacionesTecnicas());
            tiCorrectivos.setText(orden.getCorrectivos());

            traerZonasAgregadas(orden);
            traerDatosHigiene(orden);
            traerDatosEspecies(orden);
            traerDatosElementos(orden);

            if(orden.getEstadoEnvio().equals("E")){
                tiObservaciones.setEnabled(false);
                tiCorrectivos.setEnabled(false);
                tiOperario.setEnabled(false);
                tvHoraIngreso.setEnabled(false);
                tvHoraSalida.setEnabled(false);
            }

            //update
            btnGuardar.setOnClickListener(v -> {
                if(validarDatos()){
                    if (orden == null){
                       Calendar calendar2 = Calendar.getInstance();
                        orden = new Orden(0,calendar2.getTimeInMillis(),calendar2.getTimeInMillis(),
                                0,0,cliente.getId(),utilities.generarSerial(),tiOperario.getText().toString(),
                                horaEntrada.getTimeInMillis(),horaSalida.getTimeInMillis(),tiObservaciones.getText().toString(),
                                tiCorrectivos.getText().toString(),utilities.bitMapToString(firmaOperario),utilities.bitMapToString(firmaAyudante)
                                ,tipoDeServicio,tiObjetivoDelServicio.getText().toString(),"N");
                        insertarOrdenNueva(orden);
                    }else{
                        orden.setOperario(tiOperario.getText().toString());
                        orden.setHoraIngreso(horaEntrada.getTimeInMillis());
                        orden.setHoraSalida(horaSalida.getTimeInMillis());
                        orden.setObservacionesTecnicas(tiObservaciones.getText().toString());
                        orden.setCorrectivos(tiCorrectivos.getText().toString());
                        orden.setFirmaOperario(utilities.bitMapToString(firmaOperario));
                        orden.setFirmaAyudante(utilities.bitMapToString(firmaAyudante));

                        updateOrden(orden);

                    }
                }

            });

            btnFirmaAyudante.setOnClickListener(v -> {

                final DialogFragment dialog = new FirmaFragment(this, true,utilities.stringToBitMap(orden.getFirmaAyudante()), false);
                dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");

            });

            btnFirmaOperario.setOnClickListener(v -> {

                final DialogFragment dialog = new FirmaFragment(this, false,utilities.stringToBitMap(orden.getFirmaOperario()), false);
                dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
            });

            Calendar c = Calendar.getInstance();
            final int hora=c.get(Calendar.HOUR_OF_DAY);
            final int minuto=c.get(Calendar.MINUTE);
            tvHoraIngreso.setOnClickListener(v -> {
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

                                tvHoraIngreso.setText(f12Hours.format(date).toLowerCase());
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
            tvHoraSalida.setOnClickListener(v -> {
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

                                tvHoraSalida.setText(f12Hours.format(date).toLowerCase());
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
            btnCertificado.setOnClickListener(v -> {
                if(validarDatos()){
                    Bundle enviar = new Bundle();
                    enviar.putLong("idCliente", cliente.getId());
                    enviar.putLong("idOrden", orden.getId());
                    ViewKt.findNavController(getView()).navigate(R.id.action_crearOrdenInspeccionFragment_to_certificadoFragment, enviar);
                }
            });

            List<String> tipoDeServicioList = new ArrayList<>();
            tipoDeServicioList.add(0,orden.getTipoServicio());
            tipoDeServicioList.add("Aspersión");
            tipoDeServicioList.add("Nebulización");
            tipoDeServicioList.add("Termonebulización");
            tipoDeServicioList.add("Polvo Seco");
            tipoDeServicioList.add("Insepcción");
            tipoDeServicioList.add("Desratización");
            tipoDeServicioList.add("Aplicación de gel");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipoDeServicioList){

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

            spinnerTipoDeServicio.setAdapter(arrayAdapter);
            spinnerTipoDeServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tipoDeServicio = arrayAdapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    private void updateOrden(Orden orden) {
        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getOrdenDAO().update(orden);

            getActivity().runOnUiThread(() -> {


                ArrayList<GrupoZona> grupoZonaInsert = new ArrayList<>();
                for (int i = 0; i < grupoZonas.size(); i++) {
                    grupoZonaInsert.add(new GrupoZona(grupoZonas.get(i).getIdZona(),
                            orden.getId(),grupoZonas.get(i).getProducto(),grupoZonas.get(i).getIngredienteActivo(),
                            grupoZonas.get(i).getDocificacion(),grupoZonas.get(i).getTecnicaAplicacion(),grupoZonas.get(i).getFechaVencimiento()));
                    grupoZonaInsert.get(i).setId(grupoZonas.get(i).getId());
                }

                ArrayList<HigieneGroup> higieneGroupsInsert = new ArrayList<>();
                for (int i = 0; i < hygieneItems.size(); i++) {
                    higieneGroupsInsert.add(new HigieneGroup(orden.getId(),hygieneItems.get(i).getIdHigiene(),
                            hygieneItems.get(i).getS()));
                    higieneGroupsInsert.get(i).setId(hygieneItems.get(i).getId());
                }

                ArrayList<InsectoGroup> insectoGroupsInsert = new ArrayList<>();
                for (int i = 0; i < insectoGroupArrayList.size(); i++) {
                    insectoGroupsInsert.add(new InsectoGroup(orden.getId(),insectoGroupArrayList.get(i).getIdInsecto(),
                            insectoGroupArrayList.get(i).getS(),insectoGroupArrayList.get(i).getNivelInfestacion()));
                    insectoGroupsInsert.get(i).setId(insectoGroupArrayList.get(i).getId());
                }

                new Thread(() -> {
                    AppDataBase.getInstance(getContext()).getGrupoZonaDAO().insertAll(grupoZonaInsert);
                    AppDataBase.getInstance(getContext()).getHigieneGroupDAO().insertAll(higieneGroupsInsert);
                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupsInsert);

                    getActivity().runOnUiThread(() -> {

                        Toast.makeText(getContext(),"Inserto exitoso",Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    });
                }).start();
            });

        }).start();
    }

    private void traerZonasAgregadas(Orden orden) {

        new Thread(() -> {

            grupoZonas = (ArrayList<GrupoZonaMostrar>) AppDataBase.getInstance(getContext()).getGrupoZonaDAO().getZonasAgregadas(orden.getId());

            getActivity().runOnUiThread(() -> {
                cargarAdapterZonas();
            });
        }).start();
    }

    public void agregarInsecto(long idInsecto, String infestacionNivel) {
        new Thread(() -> {

            Insecto insecto = AppDataBase.getInstance(getContext()).getInsectoDAO().getById(idInsecto);

            getActivity().runOnUiThread(() -> {

                insectoGroupArrayList.add(new InsectoGroupMostrar(0,insecto.getDescripcion(),insecto.getId(),"",infestacionNivel));
                llenarAdapterInsecto();

            });

        }).start();
    }
    public void agregarElemento(long idElemento) {
        new Thread(() -> {

            ElementoUtilizado elementoUtilizado = AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().getById(idElemento);

            getActivity().runOnUiThread(() -> {

                elementoUtilizadoArray.add(new ElementoUtilizadoMostrar(0,elementoUtilizado.getId(),elementoUtilizado.getDescripcion()));
                llenarAdapterElementos();

            });

        }).start();
    }

    public void agregarZona(long idZona) {
        new Thread(() -> {

            Zona zona = AppDataBase.getInstance(getContext()).getZonaDAO().getById(idZona);

            getActivity().runOnUiThread(() -> {
                grupoZonas.add(new GrupoZonaMostrar(0,zona.getId(),orden != null ? orden.getId() : 0,zona.getDescripcion(),"","","","",""));
                cargarAdapterZonas();

            });

        }).start();
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
    private Bitmap firmaAyudante, firmaOperario;
    public void guardarFirmaAcompa(Bitmap bitmap) {
        this.firmaAyudante = bitmap;
    }

    public void guardarFirmaOperario(Bitmap bitmap) {
        this.firmaOperario = bitmap;
    }

    public void agregarHigiene(long idHigiene) {
        new Thread(() -> {

            Higiene higiene = AppDataBase.getInstance(getContext()).getHigieneDAO().getId(idHigiene);

            getActivity().runOnUiThread(() -> {

                hygieneItems.add(new HygieneItem(0,higiene.getId(),higiene.getNombre(),""));
                llenarAdapterHigiene();

            });

        }).start();
    }

    public void eliminarInsecto(long id) {
        for (int i = 0; i<insectoGroupArrayList.size(); i++){
            if(id == insectoGroupArrayList.get(i).getIdInsecto()){
                insectoGroupArrayList.remove(i);
                break;
            }
        }
        llenarAdapterInsecto();
    }

    public void eliminarElementoUtilizado(long id) {
        for (int i = 0; i<elementoUtilizadoArray.size(); i++){
            if(id == elementoUtilizadoArray.get(i).getIdElemento()){
                elementoUtilizadoArray.remove(i);
                break;
            }
        }
        llenarAdapterElementos();
    }
}