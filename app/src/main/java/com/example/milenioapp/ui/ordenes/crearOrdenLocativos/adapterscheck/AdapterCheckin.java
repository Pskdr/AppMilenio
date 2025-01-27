package com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.CrearOrdenLocativosFragment;

import java.util.ArrayList;

public class AdapterCheckin extends RecyclerView.Adapter<AdapterCheckin.ViewHolderCliente> {

    private final AdapterCheckin.onItemListener onItemListener;
    private final Fragment instancia;
    ArrayList<ObjetoAdapter> itemArrayList;
    private boolean bloquear;
    private String letra;

    public AdapterCheckin(AdapterCheckin.onItemListener onItemListener, ArrayList<ObjetoAdapter> arrayList, Fragment instancia, boolean bloquear, String letra) {
        this.onItemListener = onItemListener;
        this.itemArrayList = arrayList;
        this.instancia = instancia;
        this.bloquear = bloquear;
        this.letra = letra;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plaga_item_list, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        int finalPosition = position;
        holder.tvNombre.setText(itemArrayList.get(position).getNombre());

        if (itemArrayList.get(finalPosition).getHallado().equals("S")) {
            holder.rbSeleccionar.setChecked(true);
        }

        holder.tvNombre.setOnClickListener(view -> {
            if (instancia instanceof CrearOrdenLocativosFragment) {
                switch (letra) {
                    case "P":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoPlagaMostrar(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                        }
                        break;
                    case "A":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoArea(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                        }
                        break;
                    case "E":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoElemento(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                        }
                        break;
                }
            }
        });

        holder.rbSeleccionar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Psk", "onCheckedChanged: " + itemArrayList.get(finalPosition).getNombre() + " estado: " + b);
                if (instancia instanceof CrearOrdenLocativosFragment) {
                    switch (letra) {
                        case "P":
                            ((CrearOrdenLocativosFragment) instancia).cambiarPlagaMostrar(b, finalPosition);
                            break;
                        case "A":
                            ((CrearOrdenLocativosFragment) instancia).cambiarZonaMostrar(b, finalPosition);
                            break;
                        case "E":

                            ((CrearOrdenLocativosFragment) instancia).cambiarMaterialesMostrar(b, finalPosition);
                            break;
                    }
                }
            }
        });

        holder.cvCliente.setOnClickListener(view -> {
            if (instancia instanceof CrearOrdenLocativosFragment) {
                switch (letra) {
                    case "P":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoPlagaMostrar(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                            ((CrearOrdenLocativosFragment) instancia).cambiarPlagaMostrar(false, finalPosition);
                        }
                        break;
                    case "A":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoArea(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                            ((CrearOrdenLocativosFragment) instancia).cambiarZonaMostrar(false, finalPosition);
                        }
                        break;
                    case "E":
                        if (((CrearOrdenLocativosFragment) instancia).getEstadoElemento(finalPosition)) {
                            holder.rbSeleccionar.setChecked(false);
                            ((CrearOrdenLocativosFragment) instancia).cambiarMaterialesMostrar(false, finalPosition);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public interface onItemListener {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvNombre;
        private final RadioButton rbSeleccionar;
        private final CardView cvCliente;
        private final AdapterCheckin.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterCheckin.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
            rbSeleccionar = itemView.findViewById(R.id.rbSelect);
            cvCliente = itemView.findViewById(R.id.cvCliente);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
