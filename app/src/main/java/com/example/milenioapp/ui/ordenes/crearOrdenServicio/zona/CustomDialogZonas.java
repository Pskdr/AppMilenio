package com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona;

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
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CrearOrdenServicioFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CustomDialogZonas extends DialogFragment {

    private final CrearOrdenServicioFragment crearOrdenServicioFragment;
    private GrupoZonaMostrar zonaAgregada;
    private final long idTipo;
    private Spinner spinnerProducto,spinnerdocificacion,spinnerTecnica;

    private TextView tvZona, tvProducto, tvIngrediente,tvDocificacion;
    private int position;
    private boolean bloquear;
    private TextView tvIngredienteActivo;

    public CustomDialogZonas(CrearOrdenServicioFragment crearOrdenServicioFragment, GrupoZonaMostrar zonaAgregada, long idTipo, int position, boolean bloquear) {
        this.crearOrdenServicioFragment = crearOrdenServicioFragment;
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
    private TextInputEditText tiFecha;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_zonas, container, false);
        ((MainMenu) getActivity()).noGirarPantalla();

        tvZona = view.findViewById(R.id.tvZona);
        tvProducto = view.findViewById(R.id.tvProducto);
        tiFecha = view.findViewById(R.id.tiFecha);
        tvIngrediente = view.findViewById(R.id.tvIngrediente);
        tvDocificacion = view.findViewById(R.id.tvDocificacion);
        tvIngredienteActivo = view.findViewById(R.id.tvIngredienteActivo);

        spinnerProducto = view.findViewById(R.id.spinnerProducto);
        spinnerdocificacion = view.findViewById(R.id.spinnerDocis);
        spinnerTecnica = view.findViewById(R.id.spinnerTecnica);

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
                this.zonaAgregada.setFechaVencimiento(tiFecha.getText().toString());

                crearOrdenServicioFragment.actualizarZona(zonaAgregada, position);
                this.dismiss();
            }else{
                errorText.setVisibility(View.VISIBLE);
            }
        });
        tiFecha.setText(zonaAgregada.getFechaVencimiento());
        llenarSpinners();

        if (bloquear){
            spinnerProducto.setEnabled(false);
            spinnerdocificacion.setEnabled(false);
            spinnerTecnica.setEnabled(false);
            btnGuardar.setVisibility(View.GONE);
        }
        return view;
    }

    private boolean validarDatos() {
        if(tvProducto.getText().toString().equals("-Seleccione-") || tvZona.getText().toString().equals("-Seleccione-") || tvDocificacion.getText().toString().equals("-Seleccione-") || zonaAgregada.getTecnicaAplicacion().equals("-Seleccione-")){
            return false;
        }
        return true;

    }


    AdapterZonas adapterZonas;
    private void llenarSpinners() {
        new Thread(() -> {

           ArrayList<ProductoMostrar> productosList = new ArrayList<>();

           productosList.add(new ProductoMostrar("MURDER 10%", "ALFACIPERMETRINA"));
           productosList.add(new ProductoMostrar("HAWKER PLUS","TETRAMETRINA+CIPERMETRINA"));
           productosList.add(new ProductoMostrar("TEMPRID SC","BETACYFLUTHRIN+IMIDACLOPRID"));
           productosList.add(new ProductoMostrar("STUKA GRANOS","Deltametrina+ Pirimifox metil"));
           productosList.add(new ProductoMostrar("SAMBAMETRINA","ALFACIPERMETRINA"));
           productosList.add(new ProductoMostrar("BECIBUX 10%","Betacipermetrina+Butóxido de piperonilo"));
           productosList.add(new ProductoMostrar("FENDONA SC","ALFACIPERMETRINA"));
           productosList.add(new ProductoMostrar("PYBUTHRIN 33","Piretrina natural"));

            ArrayList<String> ingredienteList = new ArrayList<>();

            ArrayList<String> docis = new ArrayList<>();

            docis.add("6 cm/ lt agua");
            docis.add("4 cm/ lt agua");
            docis.add("10 cm/ lt agua");
            docis.add("puro");

            ArrayList<String> tecnicaAplicacion = new ArrayList<>();
            tecnicaAplicacion.add("ASPERSIÓN");
            tecnicaAplicacion.add("DISOLUCIÓN");
            getActivity().runOnUiThread(() -> {

                if(zonaAgregada.getProducto().equals("") && zonaAgregada.getIngredienteActivo().equals("") && zonaAgregada.getDocificacion().equals("") && zonaAgregada.getTecnicaAplicacion().equals("")) {
                    productosList.add(0, new ProductoMostrar("-Seleccione-", "-Seleccione-"));
                    ingredienteList.add(0, "-Seleccione-");
                    docis.add(0, "-Seleccione-");
                    tecnicaAplicacion.add(0, "-Seleccione-");
                }else{
                    productosList.add(0, new ProductoMostrar(zonaAgregada.getProducto(),zonaAgregada.getIngredienteActivo()));
                    ingredienteList.add(0, zonaAgregada.getIngredienteActivo());
                    docis.add(0, zonaAgregada.getDocificacion());
                    tecnicaAplicacion.add(0, zonaAgregada.getTecnicaAplicacion());
                }
                ArrayAdapter<ProductoMostrar> arrayAdapterProducto = new ArrayAdapter<ProductoMostrar>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,productosList){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getView(position, convertView, parent);

                        ProductoMostrar producto = getItem(position);
                        label.setHint(producto.getProducto());
                        label.setText(producto.getProducto());

                        return label;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                        ProductoMostrar producto = getItem(position);
                        label.setText(producto.getProducto());
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

                ArrayAdapter<String> arrayAdapterTecnica = new ArrayAdapter<String>(getContext(),androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tecnicaAplicacion){

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
                spinnerdocificacion.setAdapter(arrayAdapterDocis);
                spinnerTecnica.setAdapter(arrayAdapterTecnica);

                spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvProducto.setText(productosList.get(position).getProducto());
                        tvIngrediente.setText(productosList.get(position).getIngredienteActivo());
                        tvIngredienteActivo.setText(productosList.get(position).getIngredienteActivo());
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
                spinnerTecnica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        zonaAgregada.setTecnicaAplicacion(tecnicaAplicacion.get(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            });

        }).start();
    }
}
