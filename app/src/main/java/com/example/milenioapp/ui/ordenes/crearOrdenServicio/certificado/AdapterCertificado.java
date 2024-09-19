package com.example.milenioapp.ui.ordenes.crearOrdenServicio.certificado;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;

public class AdapterCertificado extends RecyclerView.Adapter<AdapterCertificado.ViewHolderCliente>{

    private final AdapterCertificado.onItemListener onItemListener;
    private final ArrayList<ItemZonasMostrar> itemMostrars;

    public AdapterCertificado(AdapterCertificado.onItemListener onItemListener, ArrayList<ItemZonasMostrar> itemMostrars) {
        this.onItemListener = onItemListener;
        this.itemMostrars = itemMostrars;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_zonas_pdf, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new AdapterCertificado.ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        holder.tvZona.setText(itemMostrars.get(position).getNombre());
        holder.tvTecnica.setText(itemMostrars.get(position).getTecnica());
        holder.tvIngrediente.setText(itemMostrars.get(position).getIngredienteActivo());

    }

    @Override
    public int getItemCount() {
        return itemMostrars.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvZona,tvTecnica,tvIngrediente;
        private final AdapterCertificado.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterCertificado.onItemListener onItemListener) {
            super(itemView);

            tvZona = itemView.findViewById(R.id.tvZona);
            tvTecnica = itemView.findViewById(R.id.tvTecnica);
            tvIngrediente = itemView.findViewById(R.id.tvIngrediente);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
