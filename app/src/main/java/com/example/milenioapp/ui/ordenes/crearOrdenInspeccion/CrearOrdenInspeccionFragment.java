package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.HigieneGroup;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.InsectoGroup;
import com.example.milenioapp.database.entity.LamparaGroup;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos.AdapterCebaderos;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos.CustomDialogCebaderos;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.lamparas.AdapterLamparas;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.lamparas.CustomDialogLampara;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.CustomDialogAgregar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.firma.FirmaFragment;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.hallazgos.AdapterHigiene;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.hallazgos.HygieneItem;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.insecto.AdapterInsectos;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.insecto.InsectoGroupMostrar;
import com.example.milenioapp.ui.utilidades.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class CrearOrdenInspeccionFragment extends Fragment {

    public CrearOrdenInspeccionFragment() {
        // Required empty public constructor
    }

    long idOrden,id;

    private TextView tvInspección;
    private TextInputEditText tiEmpresa, tiFecha, tiCliente,
            tiNit, tiFechaActual,tiContacto,tiTelefono,tiDireccion,tiSede,tiObservaciones,tiRoedores,
            tiCebaderosConConsumo,tiCebaderosSucios,tiCebaderosInactivos,tiObservacionesTecnicas,tiCorrectivos;
    private RecyclerView rvInsectos, rvCebaderos,rvHigiene,rvLamparas;

    private Button btnAgregarEspecie, btnCebaderos,btnAgregarAreaLocativa,
            btnAgregarLampara,btnFirmaAyudante,btnFirmaTecnico,btnCertificado,btnGuardar;

    private Cliente cliente;

    private Orden orden;
    private Bitmap firmaAyudante, firmaOperario;
    private ArrayList<InsectoGroupMostrar> insectoGroupArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden_inspeccion, container, false);


        tvInspección = view.findViewById(R.id.tvInspección);

        tiEmpresa = view.findViewById(R.id.tiEmpresa);
        tiFecha = view.findViewById(R.id.tifecha);
        tiCliente = view.findViewById(R.id.tiCliente);
        tiNit = view.findViewById(R.id.tiNit);
        tiFechaActual = view.findViewById(R.id.tiFechaActual);
        tiContacto = view.findViewById(R.id.tiContacto);
        tiTelefono = view.findViewById(R.id.tiTelefono);
        tiDireccion = view.findViewById(R.id.tiDireccion);
        tiSede = view.findViewById(R.id.tiSede);

        rvInsectos = view.findViewById(R.id.rvInsectos);
        rvInsectos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCebaderos = view.findViewById(R.id.rvCebaderos);
        rvCebaderos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHigiene = view.findViewById(R.id.rvHigiene);
        rvHigiene.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLamparas = view.findViewById(R.id.rvLamparas);
        rvLamparas.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAgregarEspecie = view.findViewById(R.id.btnAgregarEspecie);
        btnCebaderos = view.findViewById(R.id.btnAgregarZona);

        tiObservaciones = view.findViewById(R.id.tiObservaciones);

        btnAgregarAreaLocativa = view.findViewById(R.id.btnAgregarAreaLocativa);
        btnAgregarLampara = view.findViewById(R.id.btnAgregarLampara);

        tiRoedores = view.findViewById(R.id.tiRoedores);
        tiCebaderosConConsumo = view.findViewById(R.id.tiCebaderosConConsumo);
        tiCebaderosSucios = view.findViewById(R.id.tiCebaderosSucios);
        tiCebaderosInactivos = view.findViewById(R.id.tiCebaderosInactivos);

        btnFirmaAyudante = view.findViewById(R.id.btnFirmaAyudante);
        btnFirmaTecnico = view.findViewById(R.id.btnFirmaTecnico);

        tiObservacionesTecnicas = view.findViewById(R.id.tiObservacionesTecnicas);
        tiCorrectivos = view.findViewById(R.id.tiCorrectivos);

        btnCertificado = view.findViewById(R.id.btnCertificado);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        Bundle bundle = getArguments();
        id = bundle.getLong("id",-1);
        idOrden = bundle.getLong("idOrden",-1);


        if(idOrden != -1){
            obtenerEmpresa(id, idOrden);
        }else{
            obtenerEmpresa(id);
        }
        btnAgregarEspecie.setOnClickListener(v ->{
            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,2);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });
        btnAgregarAreaLocativa.setOnClickListener(v -> {
            CustomDialogAgregar customDialogAgregar = new CustomDialogAgregar(this,cliente,1);
            customDialogAgregar.show(getChildFragmentManager(),"");
        });
        btnAgregarLampara.setOnClickListener(v -> {
            abrirCustomDialogLampara(-1);
        });
        btnCebaderos.setOnClickListener(v -> {
            abrirCustomDialogCebadero(-1);
        });
        return view;
    }

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

            guardarFirmaOperario(utilities.stringToBitMap(orden.getFirmaOperario()));
            guardarFirmaAcompa(utilities.stringToBitMap(orden.getFirmaAyudante()));

            tiObservaciones.setText(orden.getObservacionesTecnicas());
            tiCorrectivos.setText(orden.getCorrectivos());

            traerLamparasAgregadas(orden);
            traerDatosHigiene(orden);
            traerDatosEspecies(orden);
            traerDatosCebaderos(orden);

            if(orden.getEstadoEnvio().equals("E")){
                tiObservaciones.setEnabled(false);
                tiCorrectivos.setEnabled(false);
                tiRoedores.setEnabled(false);
                tiCebaderosConConsumo.setEnabled(false);
                tiCebaderosSucios.setEnabled(false);
                tiCebaderosInactivos.setEnabled(false);
                tiObservacionesTecnicas.setEnabled(false);
                tiCorrectivos.setEnabled(false);
            }

            //update
            btnGuardar.setOnClickListener(v -> {
                if(validarDatos()){
                    orden.setHoraIngreso(0);
                    orden.setHoraSalida(0);
                    orden.setObservacionesTecnicas(tiObservaciones.getText().toString());
                    orden.setCorrectivos(tiCorrectivos.getText().toString());
                    orden.setFirmaOperario(utilities.bitMapToString(firmaOperario));
                    orden.setFirmaAyudante(utilities.bitMapToString(firmaAyudante));

                    updateOrden(orden);


                }

            });

            btnFirmaAyudante.setOnClickListener(v -> {

                final DialogFragment dialog = new FirmaFragment(this, true,utilities.stringToBitMap(orden.getFirmaAyudante()), false);
                dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");

            });

            btnFirmaTecnico.setOnClickListener(v -> {

                final DialogFragment dialog = new FirmaFragment(this, false,utilities.stringToBitMap(orden.getFirmaOperario()), false);
                dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
            });

            btnCertificado.setEnabled(true);
            /*
            btnCertificado.setOnClickListener(v -> {
                if(validarDatos()){
                    Bundle enviar = new Bundle();
                    enviar.putLong("idCliente", cliente.getId());
                    enviar.putLong("idOrden", orden.getId());
                    ViewKt.findNavController(getView()).navigate(R.id.action_crearOrdenInspeccionFragment_to_certificadoFragment, enviar);
                }
            });

            */

        }
    }


    ArrayList<LamparaGroup> lamparaGroupArrayList;
    private AdapterLamparas adapterLamparas;
    private void traerLamparasAgregadas(Orden orden) {
        new Thread(() -> {

            lamparaGroupArrayList = (ArrayList<LamparaGroup>) AppDataBase.getInstance(getContext()).getLamparoGroupDAO().getById(orden.getId());

            getActivity().runOnUiThread(() -> {

               llenarAdapterLampara();

            });

        }).start();
    }

    private boolean validarDatos() {

        if(tiRoedores.getText().toString().equals("") || tiCebaderosConConsumo.getText().toString().equals("")
                || tiCebaderosSucios.getText().toString().equals("")|| tiCebaderosInactivos.getText().toString().equals("") ){

            Toast.makeText(getContext(),"Tiene que llenar todos los campos de #",Toast.LENGTH_LONG).show();
            return false;
        }
        for (int i = 0; i < hygieneItems.size(); i++) {
            if(hygieneItems.get(i).getS().equals("")){
                Toast.makeText(getContext(),"Tiene que seleccionar todas las areas locativas ",Toast.LENGTH_LONG).show();
                return false;
            }

        }
        for (int i = 0; i < lamparaGroupArrayList.size(); i++) {
            if(lamparaGroupArrayList.get(i).getLamparaN().equals("")){
                Toast.makeText(getContext(),"Tiene que seleccionar todas las areas locativas ",Toast.LENGTH_LONG).show();
                return false;
            }

        }

        if(firmaOperario == null || firmaAyudante == null){
            Toast.makeText(getContext(),"Las firmas son obligatorias",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
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

                btnFirmaTecnico.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, false,firmaOperario, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
                });
                traerDatosHigiene();
                traerDatosEspecies();
                traerDatosLampara();
                traerDatosCebaderos();

                //insert
                btnGuardar.setOnClickListener(v -> {
                    if(validarDatos()){
                        if (orden == null){
                            Calendar calendar = Calendar.getInstance();
                            orden = new Orden(0,calendar.getTimeInMillis(),calendar.getTimeInMillis(),
                                    0,0,cliente.getId(),utilities.generarSerial(),"",
                                    0,0, Objects.requireNonNull(tiObservaciones.getText()).toString(),
                                    Objects.requireNonNull(tiCorrectivos.getText()).toString(),utilities.bitMapToString(firmaOperario),
                                    utilities.bitMapToString(firmaAyudante),"","","I","N");
                            insertarOrdenNueva(orden);
                        }
                    }

                });
                btnAgregarLampara.setOnClickListener(view -> {

                    final CustomDialogLampara dialog = new CustomDialogLampara(this, 0,null,
                            -1, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
                });


            });

        }).start();
    }


    private void traerDatosLampara() {
        lamparaGroupArrayList = new ArrayList<>();
        llenarAdapterLampara();
    }

    private void llenarAdapterLampara() {
        adapterLamparas = new AdapterLamparas(lamparaGroupArrayList, new AdapterLamparas.onItemListener() {
            @Override
            public void onItemClick(int position) {
                abrirCustomDialogLampara(position);
            }
        },this,false);
        rvLamparas.setAdapter(adapterLamparas);
    }

    public void abrirCustomDialogLampara( int position) {

        final CustomDialogLampara dialog = new CustomDialogLampara(CrearOrdenInspeccionFragment.this, position,lamparaGroupArrayList != null ? lamparaGroupArrayList.get(position): null,
                lamparaGroupArrayList.get(position).getIdOrden(), (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
        dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
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
    private ArrayList<CebaderoGroup> cebaderoGroups;
    private void traerDatosCebaderos(Orden orden) {
        cebaderoGroups = new ArrayList<>();
        new Thread(() -> {


            cebaderoGroups = (ArrayList<CebaderoGroup>) AppDataBase.getInstance(getContext()).getCebaderoGroupDAO().getByOrdenId(orden.getId());

            getActivity().runOnUiThread(() -> {
                llenarAdapterCebadero();
            });

        }).start();
    }
    private void traerDatosCebaderos() {
        cebaderoGroups = new ArrayList<>();
        llenarAdapterCebadero();
    }
    AdapterCebaderos adapterCebaderos;
    private void llenarAdapterCebadero() {
        adapterCebaderos = new AdapterCebaderos(cebaderoGroups, new AdapterCebaderos.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },this,( orden != null ? orden.getEstadoEnvio().equals("E") : false));

        rvCebaderos.setAdapter(adapterCebaderos);
    }

    private void traerDatosEspecies() {
        insectoGroupArrayList = new ArrayList<>();
        llenarAdapterInsecto();

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
    private AdapterInsectos adapterInsectos;
    private void llenarAdapterInsecto() {
        adapterInsectos = new AdapterInsectos(new AdapterInsectos.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        },insectoGroupArrayList,this, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
        rvInsectos.setAdapter(adapterInsectos);

    }
    private  ArrayList<HygieneItem> hygieneItems;
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

    public void guardarFirmaAcompa(Bitmap bitmap) {
        this.firmaAyudante = bitmap;
    }
    public void guardarFirmaOperario(Bitmap bitmap) {
        this.firmaOperario = bitmap;
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
    private void insertarOrdenNueva(Orden orden) {
        new Thread(() -> {

            long idOrden = AppDataBase.getInstance(getContext()).getOrdenDAO().insert(orden);

            getActivity().runOnUiThread(() -> {

                ArrayList<LamparaGroup> lamparaGroups = new ArrayList<>();
                for (int i = 0; i < lamparaGroupArrayList.size(); i++) {
                    lamparaGroups.add(new LamparaGroup(lamparaGroupArrayList.get(i).getTipoDeInsecto(),lamparaGroupArrayList.get(i).getLamparaN(),
                            lamparaGroupArrayList.get(i).getUbicacionLampara(),
                            lamparaGroupArrayList.get(i).getCantadidadEncontrada(),lamparaGroupArrayList.get(i).getObservaciones(),idOrden));
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
                ArrayList<CebaderoGroup> cebaderosInsert = new ArrayList<>();
                for (int i = 0; i < cebaderoGroups.size(); i++) {
                    cebaderosInsert.add(new CebaderoGroup(idOrden,cebaderoGroups.get(i).getZona(),
                            cebaderoGroups.get(i).getNro(),cebaderoGroups.get(i).getEstado(),cebaderoGroups.get(i).getObservaciones()));
                }
                new Thread(() -> {
                    AppDataBase.getInstance(getContext()).getLamparoGroupDAO().insertAll(lamparaGroups);
                    AppDataBase.getInstance(getContext()).getHigieneGroupDAO().insertAll(higieneGroupsInsert);
                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupsInsert);
                    AppDataBase.getInstance(getContext()).getCebaderoGroupDAO().insertAll(cebaderosInsert);

                    getActivity().runOnUiThread(() -> {

                        Toast.makeText(getContext(),"Inserto exitoso",Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    });
                }).start();
            });

        }).start();
    }

    private void updateOrden(Orden orden) {
        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getOrdenDAO().update(orden);

            getActivity().runOnUiThread(() -> {


                ArrayList<LamparaGroup> lamparaGroups = new ArrayList<>();
                for (int i = 0; i < lamparaGroupArrayList.size(); i++) {
                    lamparaGroups.add(new LamparaGroup(lamparaGroupArrayList.get(i).getTipoDeInsecto(),lamparaGroupArrayList.get(i).getLamparaN(),
                            lamparaGroupArrayList.get(i).getUbicacionLampara(),
                            lamparaGroupArrayList.get(i).getCantadidadEncontrada(),lamparaGroupArrayList.get(i).getObservaciones(),idOrden));
                    lamparaGroups.get(i).setId(lamparaGroupArrayList.get(i).getId());
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
                ArrayList<CebaderoGroup> cebaderoGroupsInsert = new ArrayList<>();
                for (int i = 0; i < cebaderoGroups.size(); i++) {
                    cebaderoGroupsInsert.add(new CebaderoGroup(orden.getId(),cebaderoGroups.get(i).getZona(),
                            cebaderoGroups.get(i).getNro(),cebaderoGroups.get(i).getEstado(),cebaderoGroups.get(i).getObservaciones()));
                    cebaderoGroupsInsert.get(i).setId(cebaderoGroups.get(i).getId());
                }


                new Thread(() -> {
                    AppDataBase.getInstance(getContext()).getLamparoGroupDAO().insertAll(lamparaGroups);
                    AppDataBase.getInstance(getContext()).getHigieneGroupDAO().insertAll(higieneGroupsInsert);
                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupsInsert);
                    AppDataBase.getInstance(getContext()).getCebaderoGroupDAO().insertAll(cebaderoGroupsInsert);

                    getActivity().runOnUiThread(() -> {

                        Toast.makeText(getContext(),"Inserto exitoso",Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    });
                }).start();
            });

        }).start();
    }

    public void actualizarLamparaGroup(int position, LamparaGroup lamparaGroup) {
        lamparaGroupArrayList.set(position,lamparaGroup);
        llenarAdapterLampara();
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

    public void agregarHigiene(long idHigiene) {
        new Thread(() -> {

            Higiene higiene = AppDataBase.getInstance(getContext()).getHigieneDAO().getId(idHigiene);

            getActivity().runOnUiThread(() -> {

                hygieneItems.add(new HygieneItem(0,higiene.getId(),higiene.getNombre(),""));
                llenarAdapterHigiene();

            });

        }).start();
    }

    public void actualizarCebadero(int position, CebaderoGroup cebaderoGroup) {

        cebaderoGroups.set(position,cebaderoGroup);
        llenarAdapterCebadero();
    }

    public void eliminarCebadero(int position) {
        cebaderoGroups.remove(position);
        llenarAdapterCebadero();
    }

    public void abrirCustomDialogCebadero(int position) {
        final CustomDialogCebaderos dialog = new CustomDialogCebaderos(this, position,cebaderoGroups == null ? null : cebaderoGroups.get(position),
                idOrden, (orden != null ? (orden.getEstadoEnvio().equals("S") ? true : false ) : false));
        dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
    }
}