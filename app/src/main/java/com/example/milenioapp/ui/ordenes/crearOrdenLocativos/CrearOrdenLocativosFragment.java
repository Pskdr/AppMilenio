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
import android.widget.TextView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
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

    private RecyclerView rvPlagas, rvAreas, rvMateriales;
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

        btnFirmaAyudante = view.findViewById(R.id.btnFirmaAyudante);
        btnFirmaOperario = view.findViewById(R.id.btnFirmaTecnico);

        rvPlagas = view.findViewById(R.id.rvPlagas);
        rvAreas = view.findViewById(R.id.rvAreas);
        rvMateriales = view.findViewById(R.id.rvMateriales);
        rvPlagas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAreas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMateriales.setLayoutManager(new LinearLayoutManager(getContext()));

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
            });
        }).start();

    }

    ArrayList<ObjetoAdapter> elementosMostrar = new ArrayList<>();

    private void traerEelementos() {
        new Thread(() -> {

            elementosMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getElementoUtilizadoDAO().getAgregar();

            getActivity().runOnUiThread(() -> {

                adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                }, elementosMostrar, this, false, "E");
                rvMateriales.setAdapter(adapterCheckin);

            });
        }).start();
    }

    ArrayList<ObjetoAdapter> zonazMostrar = new ArrayList<>();

    private void traerZonas() {

        new Thread(() -> {

            zonazMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getZonaDAO().getZonaMostrar();

            getActivity().runOnUiThread(() -> {
                adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                }, zonazMostrar, this, false, "A");
                rvAreas.setAdapter(adapterCheckin);
            });

        }).start();

    }

    ArrayList<ObjetoAdapter> plagasMostrar = new ArrayList<ObjetoAdapter>();
    AdapterCheckin adapterCheckin;

    private void traerPlagas() {

        new Thread(() -> {

            plagasMostrar = (ArrayList<ObjetoAdapter>) AppDataBase.getInstance(getContext()).getInsectoDAO().getPlagaMostrar();

            getActivity().runOnUiThread(() -> {

                adapterCheckin = new AdapterCheckin(new AdapterCheckin.onItemListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                }, plagasMostrar, this, false, "P");

                rvPlagas.setAdapter(adapterCheckin);

            });

        }).start();
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
}