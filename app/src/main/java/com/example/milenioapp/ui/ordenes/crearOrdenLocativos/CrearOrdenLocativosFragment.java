package com.example.milenioapp.ui.ordenes.crearOrdenLocativos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.milenioapp.R;


public class CrearOrdenLocativosFragment extends Fragment {

    public CrearOrdenLocativosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_orden_locativos, container, false);

        return view;
    }
}