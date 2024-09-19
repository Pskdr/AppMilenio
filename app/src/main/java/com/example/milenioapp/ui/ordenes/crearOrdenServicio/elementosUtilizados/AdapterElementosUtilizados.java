package com.example.milenioapp.ui.ordenes.crearOrdenServicio.elementosUtilizados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CrearOrdenServicioFragment;

import java.util.ArrayList;

public class AdapterElementosUtilizados extends RecyclerView.Adapter<AdapterElementosUtilizados.ViewHolderCliente> {

    private final AdapterElementosUtilizados.onItemListener onItemListener;
    private final CrearOrdenServicioFragment instancia;
    ArrayList<ElementoUtilizadoMostrar> insectosArraylist;
    private boolean bloquear;

    public AdapterElementosUtilizados(AdapterElementosUtilizados.onItemListener onItemListener, ArrayList<ElementoUtilizadoMostrar> arrayList, CrearOrdenServicioFragment instancia, boolean bloquear) {
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

        holder.tvNombre.setText(insectosArraylist.get(position).getDescripcion());

        holder.btnBorrar.setOnClickListener(v -> {
            instancia.eliminarElementoUtilizado(insectosArraylist.get(position).getIdElemento());
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

        private final TextView tvNombre;
        private final TextView btnBorrar;
        private final AdapterElementosUtilizados.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterElementosUtilizados.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
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
