package com.example.milenioapp.ui.ordenes.crearOrden;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;


public class MaterialAdapterDialog extends RecyclerView.Adapter<MaterialAdapterDialog.ViewHolderCliente> implements Filterable {

    private ArrayList<AgregarObjeto> materialArrayList;
    private ArrayList<AgregarObjeto> copiaListaDatos;
    private final MaterialAdapterDialog.onItemListener onItemListener;

    public MaterialAdapterDialog(ArrayList<AgregarObjeto> materialArrayList, MaterialAdapterDialog.onItemListener onItemListener) {
        this.materialArrayList = materialArrayList;
        this.copiaListaDatos = materialArrayList;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_items_cobro,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        holder.tvItemname.setText(materialArrayList.get(position).getDescription());
        holder.tvCodigo.setText(position + "");
        //holder.tvUnidad.setText(materialArrayList.get(position).getUnidad());
        //holder.tvCantidadDisponible.setText(materialArrayList.get(position).getCantidadDisponible() + "");
        holder.tvTilte.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return materialArrayList.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }

    @Override
    public Filter getFilter() {
        return codigoFilter;
    }

    private final Filter codigoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<AgregarObjeto> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                listaFiltrada.addAll(copiaListaDatos);
            }else{
                String filterpattern = charSequence.toString().trim();

                for(AgregarObjeto diagnostico : copiaListaDatos){
                    if(diagnostico.getDescription().contains(filterpattern)) {
                        listaFiltrada.add(diagnostico);
                    }
                }

            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;

            return resultado;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            materialArrayList.clear();
            materialArrayList.addAll((ArrayList<AgregarObjeto>) filterResults.values);
            notifyDataSetChanged();
        }

    };



    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvItemname, tvCodigo, tvUnidad, tvCantidadDisponible, tvTilte;
        private final MaterialAdapterDialog.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, MaterialAdapterDialog.onItemListener onItemListener) {
            super(itemView);

            tvItemname = itemView.findViewById(R.id.tvItemName);
            tvCodigo = itemView.findViewById(R.id.tvCodigoEmer);
            tvUnidad = itemView.findViewById(R.id.tvUnidad);
            tvCantidadDisponible = itemView.findViewById(R.id.tvCantidadDisponible);
            tvTilte = itemView.findViewById(R.id.lblcant);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }



}
