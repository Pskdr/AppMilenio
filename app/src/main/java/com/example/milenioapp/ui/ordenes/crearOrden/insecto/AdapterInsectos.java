package com.example.milenioapp.ui.ordenes.crearOrden.insecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrden.CrearOrdenInspeccionFragment;

import java.util.ArrayList;

public class AdapterInsectos extends RecyclerView.Adapter<AdapterInsectos.ViewHolderCliente> {

    private final AdapterInsectos.onItemListener onItemListener;
    private final CrearOrdenInspeccionFragment instancia;
    ArrayList<InsectoGroupMostrar> insectosArraylist;
    private boolean bloquear;

    public AdapterInsectos(AdapterInsectos.onItemListener onItemListener, ArrayList<InsectoGroupMostrar> arrayList, CrearOrdenInspeccionFragment instancia, boolean bloquear) {
        this.onItemListener = onItemListener;
        this.insectosArraylist = arrayList;
        this.instancia = instancia;
        this.bloquear = bloquear;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insecto_item_list, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        holder.tvNombre.setText(insectosArraylist.get(position).getNombre());
        holder.tvNivelDeInfestacion.setText(insectosArraylist.get(position).getNivelInfestacion());

        holder.btnBorrar.setOnClickListener(v -> {
            instancia.eliminarInsecto(insectosArraylist.get(position).getIdInsecto());
        });
    }

    @Override
    public int getItemCount() {
        return insectosArraylist.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvNombre,tvNivelDeInfestacion;
        private final TextView btnBorrar;
        private final AdapterInsectos.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterInsectos.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
            tvNivelDeInfestacion = itemView.findViewById(R.id.tvNivelDeInfestacion);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
