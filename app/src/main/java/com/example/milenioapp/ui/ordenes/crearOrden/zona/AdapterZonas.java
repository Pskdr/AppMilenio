package com.example.milenioapp.ui.ordenes.crearOrden.zona;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.ordenes.crearOrden.CrearOrdenInspeccionFragment;

import java.util.ArrayList;

public class AdapterZonas extends RecyclerView.Adapter<AdapterZonas.ViewHolderCliente> {

    private final ArrayList<GrupoZonaMostrar> grupoZonas;

    private final AdapterZonas.onItemListener onItemListener;
    private final CrearOrdenInspeccionFragment instancia;
    private final boolean bloquear;

    public AdapterZonas(ArrayList<GrupoZonaMostrar> grupoZonas,
                        AdapterZonas.onItemListener onItemListener,
                        CrearOrdenInspeccionFragment itemsFragment, boolean bloquear) {
        this.onItemListener = onItemListener;
        this.grupoZonas = grupoZonas;
        this.instancia = itemsFragment;
        this.bloquear = bloquear;
    }


    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_zona,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        int finalPosition = position;
        holder.tvZona.setText(grupoZonas.get(position).getNombre());
        holder.tvProducto.setText(grupoZonas.get(position).getProducto());
        holder.tvDocificacion.setText(grupoZonas.get(position).getDocificacion());
        holder.tvIngrediente.setText(grupoZonas.get(position).getIngredienteActivo());
        holder.tvBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instancia.borrarZona(finalPosition);
            }
        });

        holder.cvCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instancia.abrirCustomDialog(grupoZonas.get(finalPosition), finalPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return grupoZonas.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }
    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvIngrediente,tvZona,tvDocificacion,tvBorrar,tvProducto;
        private final AdapterZonas.onItemListener onItemListener;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterZonas.onItemListener onItemListener) {
            super(itemView);

            tvIngrediente = itemView.findViewById(R.id.tvIngrediente);
            tvZona = itemView.findViewById(R.id.tvZona);
            tvBorrar = itemView.findViewById(R.id.tvBorrar);
            tvDocificacion = itemView.findViewById(R.id.tvDocificacion);
            tvProducto = itemView.findViewById(R.id.tvProducto);

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
