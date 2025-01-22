package com.example.milenioapp.ui.ordenes.crearOrdenLocativos;

import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.MaterialGroup;
import com.example.milenioapp.database.entity.ZonaGroup;
import com.example.milenioapp.database.entity.InsectoGroup;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.AdapterCheckin;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.ObjetoAdapter;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.firma.FirmaFragment;
import com.example.milenioapp.ui.utilidades.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CrearOrdenLocativosFragment extends Fragment {

    public CrearOrdenLocativosFragment() {
        // Required empty public constructor
    }


    private TextInputEditText tiEmpresa, tiFecha, tiCliente,
            tiNit, tiContacto, tiTelefono, tiDireccion, tiSede,
            tiFechaActual;
    private TextView tvHoraIngreso, tvHoraSalida;
    int thour, tminute;

    private Button btnFirmaAyudante, btnFirmaOperario;
    private Bitmap firmaAyudante, firmaOperario;
    private Calendar horaEntrada = Calendar.getInstance(), horaSalida = Calendar.getInstance();
    private long id, idOrden;

    private Button btnGuardar;

    private TextInputEditText tiObservacionesTecnicas, tiRecomendaciones, tiDescripcion;

    private RecyclerView rvPlagas, rvAreas, rvMateriales;

    private RadioButton rbInstalacion, rbArregloLocativo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden_locativos, container, false);


        tiEmpresa = view.findViewById(R.id.tiEmpresa);
        tiFecha = view.findViewById(R.id.tifecha);
        tiCliente = view.findViewById(R.id.tiCliente);
        tiNit = view.findViewById(R.id.tiNit);
        tiContacto = view.findViewById(R.id.tiContacto);
        tiTelefono = view.findViewById(R.id.tiTelefono);
        tiDireccion = view.findViewById(R.id.tiDireccion);
        tiSede = view.findViewById(R.id.tiSede);
        tiFechaActual = view.findViewById(R.id.tiFechaActual);
        tvHoraIngreso = view.findViewById(R.id.tvTimeIngreso);
        tvHoraSalida = view.findViewById(R.id.tvTimeSalida);

        tiObservacionesTecnicas = view.findViewById(R.id.tiObservacionesTecnicas);
        tiRecomendaciones = view.findViewById(R.id.tiRecomendaciones);

        btnFirmaAyudante = view.findViewById(R.id.btnFirmaAyudante);
        btnFirmaOperario = view.findViewById(R.id.btnFirmaTecnico);

        btnGuardar = view.findViewById(R.id.btnGuardar);

        rvPlagas = view.findViewById(R.id.rvPlagas);
        rvAreas = view.findViewById(R.id.rvAreas);
        rvMateriales = view.findViewById(R.id.rvMateriales);
        rvPlagas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAreas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMateriales.setLayoutManager(new LinearLayoutManager(getContext()));

        rbInstalacion = view.findViewById(R.id.rbInstalación);
        rbArregloLocativo = view.findViewById(R.id.rbArregloLocativo);
        tiDescripcion = view.findViewById(R.id.tiDescripcion);
        Bundle bundle = getArguments();
        id = bundle.getLong("id", -1);
        idOrden = bundle.getLong("idOrden", -1);


        if (idOrden != -1) {
            obtenerEmpresa(id, idOrden);
        } else {
            obtenerEmpresa(id);
        }


        return view;
    }

    private Orden orden;

    private void obtenerEmpresa(long id, long idOrden) {

        new Thread(() -> {
            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);
            orden = AppDataBase.getInstance(getContext()).getOrdenDAO().getByid(idOrden);
            getActivity().runOnUiThread(() -> {

                Calendar horaIngreso = Calendar.getInstance();
                Calendar horaSalida = Calendar.getInstance();
                Utilities utilities = new Utilities();
                llenarDatos();


                firmaOperario = utilities.stringToBitMap(orden.getFirmaOperario());
                firmaAyudante = utilities.stringToBitMap(orden.getFirmaAyudante());


                if (orden.getTipoServicio().equals("I")) {
                    rbInstalacion.setChecked(true);
                } else {
                    rbArregloLocativo.setChecked(true);
                }
                tiDescripcion.setText(orden.getObjetivoDelServicio());
                tiRecomendaciones.setText(orden.getCorrectivos());
                tiObservacionesTecnicas.setText(orden.getObservacionesTecnicas());


                horaIngreso.setTimeInMillis(orden.getHoraIngreso());
                tvHoraIngreso.setText(utilities.refactorFecha(utilities.split(utilities.getFechaString(horaIngreso), 1)));

                horaSalida.setTimeInMillis(orden.getHoraSalida());
                tvHoraSalida.setText(utilities.refactorFecha(utilities.split(utilities.getFechaString(horaSalida), 1)));

                Calendar c = Calendar.getInstance();
                final int hora = c.get(Calendar.HOUR_OF_DAY);
                final int minuto = c.get(Calendar.MINUTE);
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

                btnFirmaAyudante.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, true, firmaAyudante, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");

                });

                btnFirmaOperario.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, false, firmaOperario, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
                });
                btnGuardar.setOnClickListener(view -> {

                    btnGuardar.setEnabled(false);

                    if (!validarDatos()) {
                        return;
                    }
                    orden.setTipoServicio(rbInstalacion.isChecked() ? "I" : "A");
                    orden.setObjetivoDelServicio(tiDescripcion.getText().toString());
                    orden.setHoraIngreso(horaEntrada.getTimeInMillis());
                    orden.setHoraSalida(horaSalida.getTimeInMillis());
                    orden.setObservacionesTecnicas(tiObservacionesTecnicas.getText().toString());
                    orden.setCorrectivos(tiRecomendaciones.getText().toString());
                    orden.setFirmaOperario(utilities.bitMapToString(firmaOperario));
                    orden.setFirmaAyudante(utilities.bitMapToString(firmaAyudante));

                    updateOrden(orden);
                });

                new Thread(() -> {

                    plagasMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getInsectoDAO().getPlagaMostrar(orden.getId());
                    zonazMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getZonaDAO().getZonaMostrar(cliente.getIdTipo(), orden.getId());
                    elementosMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().getAgregar(orden.getId());


                    getActivity().runOnUiThread(() -> {
                        llenarAdapterPlagas();
                        llenarAdapterZonas();
                        llenarAdapterMateriales();
                    });
                }).start();


            });
        }).start();
    }

    private boolean validarDatos() {

        if (horaEntrada == null || horaSalida == null) {
            return false;
        }

        return true;

    }

    private void updateOrden(Orden orden) {

        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getOrdenDAO().update(orden);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Insertado correctamente!", Toast.LENGTH_LONG).show();
                List<InsectoGroup> insectoGroupInsertar = new ArrayList<>();
                for (int i = 0; i < plagasMostrar.size(); i++) {
                    InsectoGroup insectoGroup = new InsectoGroup(idOrden, plagasMostrar.get(i).getIdPrincipal(), plagasMostrar.get(i).getHallado(), "");
                    insectoGroup.setId(plagasMostrar.get(i).getIdGroup());
                    insectoGroupInsertar.add(insectoGroup);
                }
                List<ZonaGroup> zonaGroupList = new ArrayList<>();
                //las zonas en este formulario se guarda el hallado en  el campo producto
                for (int i = 0; i < zonazMostrar.size(); i++) {
                    ZonaGroup zonaGroup = new ZonaGroup(zonazMostrar.get(i).getIdPrincipal(), idOrden, zonazMostrar.get(i).getHallado(),
                            "", "", "", "");
                    zonaGroup.setId(zonazMostrar.get(i).getIdGroup());
                    zonaGroupList.add(zonaGroup);
                }

                List<MaterialGroup> materialGroupList = new ArrayList<>();

                for (int i = 0; i < elementosMostrar.size(); i++) {
                    MaterialGroup materialGroup = new MaterialGroup(elementosMostrar.get(i).getIdPrincipal(), idOrden, elementosMostrar.get(i).getHallado());
                    materialGroup.setId(elementosMostrar.get(i).getIdGroup());
                    materialGroupList.add(materialGroup);
                }

                new Thread(() -> {

                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().deleteByOrden(orden.getId());
                    AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupInsertar);


                    AppDataBase.getInstance(getContext()).getGrupoZonaDAO().deleteByOrden(orden.getId());
                    AppDataBase.getInstance(getContext()).getGrupoZonaDAO().insertAll(zonaGroupList);

                    AppDataBase.getInstance(getContext()).getMaterialGroupDAO().deleteByOrden(orden.getId());
                    AppDataBase.getInstance(getContext()).getMaterialGroupDAO().insertAll(materialGroupList);


                    getActivity().runOnUiThread(() -> {

                        getActivity().onBackPressed();

                    });

                }).start();


            });

        }).start();
    }

    private Cliente cliente;

    private void obtenerEmpresa(long id) {

        new Thread(() -> {
            cliente = AppDataBase.getInstance(getContext()).getClienteDAO().getById(id);

            getActivity().runOnUiThread(() -> {

                llenarDatos();

                btnFirmaAyudante.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, true, firmaAyudante, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");

                });

                btnFirmaOperario.setOnClickListener(v -> {

                    final DialogFragment dialog = new FirmaFragment(this, false, firmaOperario, false);
                    dialog.show(getActivity().getSupportFragmentManager(), "Dialogo");
                });
                Calendar c = Calendar.getInstance();
                final int hora = c.get(Calendar.HOUR_OF_DAY);
                final int minuto = c.get(Calendar.MINUTE);
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

                traerPlagas();
                traerZonas();
                traerEelementos();

                btnGuardar.setOnClickListener(view -> {

                    if (!validarDatos()) {
                        return;
                    }

                    btnGuardar.setEnabled(false);
                    Utilities utilities = new Utilities();

                    Calendar calendar2 = Calendar.getInstance();
                    orden = new Orden(0, calendar2.getTimeInMillis(), calendar2.getTimeInMillis(),
                            cliente.getId(), utilities.generarSerial(), "",
                            horaEntrada.getTimeInMillis(), horaSalida.getTimeInMillis(), tiObservacionesTecnicas.getText().toString(),
                            tiRecomendaciones.getText().toString(), utilities.bitMapToString(firmaOperario), utilities.bitMapToString(firmaAyudante)
                            , rbInstalacion.isChecked() ? "I" : "A", tiDescripcion.getText().toString(), "L", "N", null);

                    //Tipo de servicio rbInstalacion
                    //Objetivo del sericio tiDescripcion
                    //Correctivos tiRecomendaciones
                    //Tipo de orden L
                    //observaciones en tiObservacionesTecnicas


                    new Thread(() -> {

                        long idOrden = AppDataBase.getInstance(getContext()).getOrdenDAO().insert((orden));

                        getActivity().runOnUiThread(() -> {

                            Toast.makeText(getContext(), "Insertado correctamente!", Toast.LENGTH_LONG).show();
                            List<InsectoGroup> insectoGroupInsertar = new ArrayList<>();
                            for (int i = 0; i < plagasMostrar.size(); i++) {
                                insectoGroupInsertar.add(new InsectoGroup(idOrden, plagasMostrar.get(i).getIdPrincipal(), plagasMostrar.get(i).getHallado(), ""));
                            }
                            List<ZonaGroup> zonaGroupList = new ArrayList<>();
                            //las zonas en este formulario se guarda el hallado en  el campo producto
                            for (int i = 0; i < zonazMostrar.size(); i++) {
                                zonaGroupList.add(new ZonaGroup(zonazMostrar.get(i).getIdPrincipal(), idOrden, zonazMostrar.get(i).getHallado(),
                                        "", "", "", ""));
                            }

                            List<MaterialGroup> materialGroupList = new ArrayList<>();

                            for (int i = 0; i < elementosMostrar.size(); i++) {
                                materialGroupList.add(new MaterialGroup(elementosMostrar.get(i).getIdPrincipal(), idOrden, elementosMostrar.get(i).getHallado()));
                            }

                            new Thread(() -> {

                                AppDataBase.getInstance(getContext()).getInsectoGroupDAO().insertAll(insectoGroupInsertar);
                                AppDataBase.getInstance(getContext()).getGrupoZonaDAO().insertAll(zonaGroupList);
                                AppDataBase.getInstance(getContext()).getMaterialGroupDAO().insertAll(materialGroupList);


                                getActivity().runOnUiThread(() -> {

                                    getActivity().onBackPressed();

                                });

                            }).start();


                        });

                    }).start();

                });
            });
        }).start();

    }

    ArrayList<ObjetoAdapter> elementosMostrar = new ArrayList<>();

    private void traerEelementos() {
        new Thread(() -> {

            elementosMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().getAgregar();

            getActivity().runOnUiThread(() -> {
                llenarAdapterMateriales();


            });
        }).start();
    }

    private void llenarAdapterMateriales() {
        adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, elementosMostrar, this, false, "E");
        rvMateriales.setAdapter(adapterCheckin);
    }

    ArrayList<ObjetoAdapter> zonazMostrar = new ArrayList<>();

    private void traerZonas() {

        new Thread(() -> {

            zonazMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getZonaDAO().getZonaMostrar(cliente.getIdTipo());

            getActivity().runOnUiThread(() -> {
                llenarAdapterZonas();
            });

        }).start();

    }

    private void llenarAdapterZonas() {
        adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, zonazMostrar, this, false, "A");
        rvAreas.setAdapter(adapterCheckin);
    }

    ArrayList<ObjetoAdapter> plagasMostrar = new ArrayList<ObjetoAdapter>();
    AdapterCheckin adapterCheckin;

    private void traerPlagas() {

        new Thread(() -> {

            plagasMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getInsectoDAO().getPlagaMostrar();

            getActivity().runOnUiThread(() -> {

                llenarAdapterPlagas();

            });

        }).start();
    }

    private void llenarAdapterPlagas() {
        adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        }, plagasMostrar, this, false, "P");

        rvPlagas.setAdapter(adapterCheckin);
    }

    private void llenarDatos() {
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
    }

    public void cambiarPlagaMostrar(boolean b, int position) {
        plagasMostrar.get(position).setHallado(b ? "S" : "N");
    }

    public void cambiarZonaMostrar(boolean b, int finalPosition) {
        zonazMostrar.get(finalPosition).setHallado(b ? "S" : "N");
    }

    public void cambiarMaterialesMostrar(boolean b, int finalPosition) {
        elementosMostrar.get(finalPosition).setHallado(b ? "S" : "N");
    }

    public void guardarFirmaOperario(Bitmap firmaOperario) {
        this.firmaOperario = firmaOperario;
    }

    public void guardarFirmaAcompa(Bitmap firmaAyudante) {
        this.firmaAyudante = firmaAyudante;
    }

    public boolean getEstadoPlagaMostrar(int finalPosition) {

        if (plagasMostrar.get(finalPosition).getHallado().equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getEstadoArea(int finalPosition) {
        if (zonazMostrar.get(finalPosition).getHallado().equals("S")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getEstadoElemento(int finalPosition) {
        if (elementosMostrar.get(finalPosition).getHallado().equals("S")) {
            return true;
        } else {
            return false;
        }
    }
}