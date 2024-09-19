package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.lamparas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.LamparaGroup;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;

import java.util.ArrayList;

public class AdapterLamparas extends RecyclerView.Adapter<AdapterLamparas.ViewHolderCliente> {

    private final ArrayList<LamparaGroup> lamparaGroupArrayList;
    private final AdapterLamparas.onItemListener onItemListener;
    private final CrearOrdenInspeccionFragment instancia;
    private final boolean bloquear;

    public AdapterLamparas(ArrayList<LamparaGroup> lamparaGroupArrayList, AdapterLamparas.onItemListener onItemListener,
                           CrearOrdenInspeccionFragment instancia, boolean bloquear) {
        this.lamparaGroupArrayList = lamparaGroupArrayList;
        this.onItemListener = onItemListener;
        this.instancia = instancia;
        this.bloquear = bloquear;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_lampara,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new AdapterLamparas.ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        holder.tvTipoDeInsecto.setText(lamparaGroupArrayList.get(position).getTipoDeInsecto());
        holder.tvLampara.setText(lamparaGroupArrayList.get(position).getLamparaN());
        holder.tvUbicacion.setText(lamparaGroupArrayList.get(position).getUbicacionLampara());
        holder.tvCantidadEncontrada.setText(lamparaGroupArrayList.get(position).getCantadidadEncontrada() + "");

        holder.cvCliente.setOnClickListener(view -> {
            instancia.abrirCustomDialogLampara(position);
        });

    }

    @Override
    public int getItemCount() {
        return lamparaGroupArrayList.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }
    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvTipoDeInsecto,tvLampara,tvUbicacion,tvCantidadEncontrada;
        private final AdapterLamparas.onItemListener onItemListener;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterLamparas.onItemListener onItemListener) {
            super(itemView);

            tvTipoDeInsecto = itemView.findViewById(R.id.tvTipoDeInsecto);
            tvLampara = itemView.findViewById(R.id.tvLampara);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            tvCantidadEncontrada = itemView.findViewById(R.id.tvCantidadEncontrada);

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
