package com.example.milenioapp.ui.ordenes.crearOrden.certificado;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.milenioapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CertificadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CertificadoFragment extends Fragment {


    private TextView tvFecha,tvNombre,tvNit,tvDireccion,tvFechaServicio,tvPlagaEncontrada,tvTecnico, tvSede,tvObservaciones;
    private RecyclerView rvZonas;
    public CertificadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_certificado, container, false);



        return view;
    }
}