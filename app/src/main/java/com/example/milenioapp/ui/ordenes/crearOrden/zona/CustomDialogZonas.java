package com.example.milenioapp.ui.ordenes.crearOrden.zona;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrden.CrearOrdenFragment;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.AdapterZonas;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.GrupoZonaMostrar;

import java.util.ArrayList;
import java.util.List;

public class CustomDialogZonas extends DialogFragment {

    private final CrearOrdenFragment crearOrdenFragment;
    private GrupoZonaMostrar zonaAgregada;
    private final long idTipo;
    private Spinner spinnerProducto,spinnerIngredientes,spinnerdocificacion;

    private TextView tvZona, tvProducto, tvIngrediente,tvDocificacion;
    private int position;
    private boolean bloquear;

    public CustomDialogZonas(CrearOrdenFragment crearOrdenFragment, GrupoZonaMostrar zonaAgregada, long idTipo, int position, boolean bloquear) {
        this.crearOrdenFragment = crearOrdenFragment;
        this.zonaAgregada = zonaAgregada;
        this.idTipo = idTipo;
        this.position = position;
        this.bloquear = bloquear;
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
    private Button btnGuardar, btnCerrar;

    private TextView errorText;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_zonas, container, false);
        ((MainMenu) getActivity()).noGirarPantalla();

        tvZona = view.findViewById(R.id.tvZona);
        tvProducto = view.findViewById(R.id.tvProducto);
        tvIngrediente = view.findViewById(R.id.tvIngrediente);
        tvDocificacion = view.findViewById(R.id.tvDocificacion);

        spinnerProducto = view.findViewById(R.id.spinnerProducto);
        spinnerIngredientes = view.findViewById(R.id.spinnerIngredientes);
        spinnerdocificacion = view.findViewById(R.id.spinnerDocis);

        errorText = view.findViewById(R.id.errorText);

        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnCerrar = view.findViewById(R.id.btnCerrar);

        tvZona.setText(zonaAgregada.getNombre());
        tvProducto.setText(zonaAgregada.getProducto());
        tvIngrediente.setText(zonaAgregada.getIngredienteActivo());
        tvDocificacion.setText(zonaAgregada.getDocificacion());

        btnCerrar.setOnClickListener(v -> {
            dismiss();
        });

        btnGuardar.setOnClickListener(v ->{
            if(validarDatos()){
                this.zonaAgregada.setProducto(tvProducto.getText().toString());
                this.zonaAgregada.setDocificacion(tvDocificacion.getText().toString());
                this.zonaAgregada.setIngredienteActivo(tvIngrediente.getText().toString());

                crearOrdenFragment.actualizarZona(zonaAgregada, position);
                this.dismiss();
            }else{
                errorText.setVisibility(View.VISIBLE);
            }
        });

        llenarSpinners();

        if (bloquear){
            spinnerProducto.setEnabled(false);
            spinnerIngredientes.setEnabled(false);
            spinnerdocificacion.setEnabled(false);
            btnGuardar.setVisibility(View.GONE);
        }
        return view;
    }

    private boolean validarDatos() {
        if(tvProducto.getText().toString().equals("-Seleccione-") || tvZona.getText().toString().equals("-Seleccione-") || tvDocificacion.getText().toString().equals("-Seleccione-")){
            return false;
        }
        return true;

    }


    AdapterZonas adapterZonas;
    private void llenarSpinners() {
        new Thread(() -> {

           ArrayList<String> productosList = new ArrayList<>();

           productosList.add("MURDER 10%");
           productosList.add("HAWKER PLUS");
           productosList.add("TEMPRID SC");
           productosList.add("STUKA GRANOS");
           productosList.add("STUKA GRANOS");
           productosList.add("SAMBAMETRINA");
           productosList.add("BECIBUX 10%");
           productosList.add("FENDONA SC");
           productosList.add("PYBUTHRIN 33");

            ArrayList<String> ingredienteList = new ArrayList<>();
            ingredienteList.add("ALFACIPERMETRINA");
            ingredienteList.add("TETRAMETRINA+CIPERMETRINA");
            ingredienteList.add("BETACYFLUTHRIN+IMIDACLOPRID");
            ingredienteList.add("Deltametrina+ Pirimifox metil");
            ingredienteList.add("Betacipermetrina+Butóxido de piperonilo");
            ingredienteList.add("Piretrina natural");

            ArrayList<String> docis = new ArrayList<>();

            docis.add("6 cm/ lt agua");
            docis.add("4 cm/ lt agua");
            docis.add("10 cm/ lt agua");
            docis.add("puro");
            getActivity().runOnUiThread(() -> {

                if(!zonaAgregada.getProducto().equals("") && !zonaAgregada.getIngredienteActivo().equals("") && !zonaAgregada.getDocificacion().equals("")) {
                    productosList.add(0, "-Seleccione-");
                    ingredienteList.add(0, "-Seleccione-");
                    docis.add(0, "-Seleccione-");
                }else{
                    productosList.add(0, zonaAgregada.getProducto());
                    ingredienteList.add(0, zonaAgregada.getIngredienteActivo());
                    docis.add(0, zonaAgregada.getDocificacion());
                }
                ArrayAdapter<String> arrayAdapterProducto = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,productosList){

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


                ArrayAdapter<String> arrayAdapterIngredientes = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ingredienteList){

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
                ArrayAdapter<String> arrayAdapterDocis = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,docis){

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
                spinnerProducto.setAdapter(arrayAdapterProducto);
                spinnerIngredientes.setAdapter(arrayAdapterIngredientes);
                spinnerdocificacion.setAdapter(arrayAdapterDocis);

                spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvProducto.setText(productosList.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerIngredientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvIngrediente.setText(ingredienteList.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinnerdocificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvDocificacion.setText(docis.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            });

        }).start();
    }
}
