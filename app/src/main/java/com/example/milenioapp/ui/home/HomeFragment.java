package com.example.milenioapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ViewKt;

import com.example.milenioapp.R;

public class HomeFragment extends Fragment {

    private TextView btnCrearCliente;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        btnCrearCliente = view.findViewById(R.id.btnCrearCliente);

        btnCrearCliente.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            ViewKt.findNavController(getView()).navigate(R.id.action_nav_home_to_crearClienteFragment2, bundle);
        });

        return view;
    }
}