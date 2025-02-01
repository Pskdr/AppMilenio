package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.CrearOrdenInspeccionFragment;

import java.util.ArrayList;

public class AdapterCebaderos extends RecyclerView.Adapter<AdapterCebaderos.ViewHolderCliente> {

    private final ArrayList<CebaderoGroup> cebaderoGroups;
    private final AdapterCebaderos.onItemListener onItemListener;
    private final CrearOrdenInspeccionFragment instancia;
    private final boolean bloquear;

    public AdapterCebaderos(ArrayList<CebaderoGroup> cebaderoGroups, AdapterCebaderos.onItemListener onItemListener,
                            CrearOrdenInspeccionFragment instancia, boolean bloquear) {
        this.cebaderoGroups = cebaderoGroups;
        this.onItemListener = onItemListener;
        this.instancia = instancia;
        this.bloquear = bloquear;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_cebadero,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new AdapterCebaderos.ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        holder.tvZona.setText(cebaderoGroups.get(position).getZona());
        holder.tvNro.setText(cebaderoGroups.get(position).getNro());
        holder.tvEstado.setText(cebaderoGroups.get(position).getEstado());


        holder.tvBorrar.setOnClickListener(v -> {
            instancia.eliminarCebadero(position);
        });

    }

    @Override
    public int getItemCount() {
        return cebaderoGroups.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }
    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvZona, tvNro, tvEstado,tvBorrar;
        private final AdapterCebaderos.onItemListener onItemListener;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterCebaderos.onItemListener onItemListener) {
            super(itemView);

            tvZona = itemView.findViewById(R.id.tvZona);
            tvNro = itemView.findViewById(R.id.tvNro);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvBorrar = itemView.findViewById(R.id.tvBorrar);

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
