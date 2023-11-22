package com.example.milenioapp.ui.ordenes.crearOrden;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.GrupoZona;

import java.util.ArrayList;

public class AdapterZonas extends RecyclerView.Adapter<AdapterZonas.ViewHolderCliente> {

    private final ArrayList<GrupoZona> grupoZonas;

    private final AdapterZonas.onItemListener onItemListener;
    private final CrearOrdenFragment instancia;
    private final boolean bloquear;

    public AdapterZonas(ArrayList<GrupoZona> grupoZonas,
                                AdapterZonas.onItemListener onItemListener,
                                CrearOrdenFragment itemsFragment, boolean bloquear) {
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

        private final TextView tvIngrediente,tvZona,tvBorrar;
        private final AdapterZonas.onItemListener onItemListener;
        private final Spinner spinnerTecnica;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterZonas.onItemListener onItemListener) {
            super(itemView);

            tvIngrediente = itemView.findViewById(R.id.tvIngrediente);
            tvZona = itemView.findViewById(R.id.tvZona);
            tvBorrar = itemView.findViewById(R.id.tvBorrar);

            spinnerTecnica = itemView.findViewById(R.id.spinnerTecnica);

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
