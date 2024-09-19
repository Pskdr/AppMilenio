package com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;

public class AdapterAgregar extends RecyclerView.Adapter<AdapterAgregar.ViewHolderCliente> {
    private final AdapterAgregar.onItemListener onItemListener;
    private final ArrayList<ItemMostrar> itemMostrars;

    public AdapterAgregar(AdapterAgregar.onItemListener onItemListener, ArrayList<ItemMostrar> itemMostrars) {
        this.onItemListener = onItemListener;
        this.itemMostrars = itemMostrars;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_items_agregar, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new AdapterAgregar.ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        holder.tvId.setText(itemMostrars.get(position).getId() + "");
        holder.tvNombre.setText(itemMostrars.get(position).getNombre());
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

        private final TextView tvId,tvNombre;
        private final AdapterAgregar.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterAgregar.onItemListener onItemListener) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvNombre = itemView.findViewById(R.id.tvNombre);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
