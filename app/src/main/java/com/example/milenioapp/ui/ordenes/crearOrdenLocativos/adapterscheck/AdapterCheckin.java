package com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.CrearOrdenLocativosFragment;

import java.util.ArrayList;

public class AdapterCheckin extends RecyclerView.Adapter<AdapterCheckin.ViewHolderCliente> {

    private final AdapterCheckin.onItemListener onItemListener;
    private final Fragment instancia;
    ArrayList<ObjetoAdapter> insectosArraylist;
    private boolean bloquear;
    private String letra;

    public AdapterCheckin(AdapterCheckin.onItemListener onItemListener, ArrayList<ObjetoAdapter> arrayList, Fragment instancia, boolean bloquear, String letra) {
        this.onItemListener = onItemListener;
        this.insectosArraylist = arrayList;
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
        holder.tvNombre.setText(insectosArraylist.get(position).getNombre());
        holder.rbSeleccionar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
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
    }

    @Override
    public int getItemCount() {
        return insectosArraylist.size();
    }

    public interface onItemListener {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvNombre, tvNivelDeInfestacion;
        private final RadioButton rbSeleccionar;
        private final AdapterCheckin.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterCheckin.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
            tvNivelDeInfestacion = itemView.findViewById(R.id.tvNivelDeInfestacion);
            rbSeleccionar = itemView.findViewById(R.id.rbSelect);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
