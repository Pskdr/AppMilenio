package com.example.milenioapp.ui.home.crearCliente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milenioapp.MainMenu;
import com.example.milenioapp.R;
import com.example.milenioapp.database.AppDataBase;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.TipoCliente;
import com.example.milenioapp.ui.utilidades.Utilities;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CrearClienteFragment extends Fragment {

    public CrearClienteFragment() {
        // Required empty public constructor
    }

    private TextInputEditText tiRut, tiNit,tiDireccion,tiTelefono,tiNombre,tiSede,tiEmail;

    private Button btnCrear;
    private Spinner spinnerTipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_cliente, container, false);


        tiRut = view.findViewById(R.id.tiRut);
        tiNit = view.findViewById(R.id.tiNit);
        tiDireccion = view.findViewById(R.id.tiDireccion);
        tiTelefono = view.findViewById(R.id.tiTelefono);
        tiNombre = view.findViewById(R.id.tiNombre);
        tiSede = view.findViewById(R.id.tiSede);
        tiEmail = view.findViewById(R.id.tiEmail);

        spinnerTipo = view.findViewById(R.id.spinnerTipo);

        btnCrear = view.findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarDatos()){
                    Cliente cliente = new Cliente(tiRut.getText().toString(),tiNit.getText().toString(),
                            tiDireccion.getText().toString(),tiTelefono.getText().toString(),tiNombre.getText().toString(),
                            tiSede.getText().toString(), tiEmail.getText().toString(), tipoSeleccionado);

                    ingresarCliente(cliente);
                }

            }
        });

        llenarSpinner();


        return view;
    }

    Utilities utilities = new Utilities();
    private List<TipoCliente> tipoClienteList;
    private void llenarSpinner() {

        new Thread(() -> {

            tipoClienteList = AppDataBase.getInstance(getContext()).getTipoClienteDAO().getAll();

            getActivity().runOnUiThread(() -> {

                ArrayAdapter<TipoCliente> arrayAdapter = new ArrayAdapter<TipoCliente>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tipoClienteList) {

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getView(position, convertView, parent);

                        TipoCliente estadoActividad = getItem(position);
                        label.setHint(estadoActividad.getNombre());
                        label.setText(utilities.abreviarTexto(estadoActividad.getNombre()));

                        return label;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);

                        TipoCliente estadoActividad = getItem(position);
                        label.setText(estadoActividad.getNombre());

                        return label;
                    }
                };
                spinnerTipo.setAdapter(arrayAdapter);

                spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tipoSeleccionado = ((TipoCliente) adapterView.getSelectedItem()).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            });
        }).start();


        ;


    }

    private long tipoSeleccionado;
    private void ingresarCliente(Cliente cliente) {
        new Thread(() -> {

            AppDataBase.getInstance(getContext()).getClienteDAO().insert(cliente);

            getActivity().runOnUiThread(() -> {

                Toast.makeText(getContext(),"Cliente ingresado correctamente",Toast.LENGTH_LONG).show();
                ((MainMenu)getActivity()).onBackPressed();

            });

        }).start();
    }

    private boolean validarDatos() {

        if(tiRut.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"El RUT es obligatorio.",Toast.LENGTH_LONG).show();
            tiRut.requestFocus();
            tiRut.setError("Este campo es obligatorio");
            return false;

        }
        if(tiNit.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"El NIT es obligatorio.",Toast.LENGTH_LONG).show();
            tiNit.requestFocus();
            tiNit.setError("Este campo es obligatorio");
            return false;

        }

        if(tiDireccion.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"La dirección es obligatorio.",Toast.LENGTH_LONG).show();
            tiDireccion.requestFocus();
            tiDireccion.setError("Este campo es obligatorio");
            return false;

        }

        if(tiTelefono.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"El teléfono es obligatorio.",Toast.LENGTH_LONG).show();
            tiTelefono.requestFocus();
            tiTelefono.setError("Este campo es obligatorio");
            return false;

        }
        if(tiNombre.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"El nombre es obligatorio.",Toast.LENGTH_LONG).show();
            tiNombre.requestFocus();
            tiNombre.setError("Este campo es obligatorio");
            return false;

        }
        if(tiSede.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"La sede es obligatoria.",Toast.LENGTH_LONG).show();
            tiSede.requestFocus();
            tiSede.setError("Este campo es obligatorio");
            return false;

        }
        if(tiEmail.getText().toString().trim().equals("")){

            Toast.makeText(getContext(),"El e-mail es obligatoria.",Toast.LENGTH_LONG).show();
            tiEmail.requestFocus();
            tiEmail.setError("Este campo es obligatorio");
            return false;

        }

        return true;


    }
}