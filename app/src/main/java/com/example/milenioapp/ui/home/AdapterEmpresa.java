package com.example.milenioapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;

import java.util.ArrayList;


public class AdapterEmpresa extends RecyclerView.Adapter<AdapterEmpresa.ViewHolderCliente> implements Filterable {

    private final AdapterEmpresa.onItemListener onItemListener;
    ArrayList<Empresa> empresaArrayList;
    ArrayList<Empresa> copiaListaDatos;

    public AdapterEmpresa(AdapterEmpresa.onItemListener onItemListener, ArrayList<Empresa> arrayList) {
        this.onItemListener = onItemListener;
        this.empresaArrayList = arrayList;
        this.copiaListaDatos = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_empresa, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        holder.tvEmpresa.setText(empresaArrayList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return empresaArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return empresaFilter;
    }

    private final Filter empresaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Empresa> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                listaFiltrada.addAll(copiaListaDatos);


            }else{
                String filterpattern = charSequence.toString().toLowerCase().trim();

                for(Empresa empresa : copiaListaDatos){
                    if(empresa.getNombre().toLowerCase().contains(filterpattern)) {
                        listaFiltrada.add(empresa);
                    }
                }

            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;

            return resultado;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            empresaArrayList.clear();
            empresaArrayList.addAll((ArrayList<Empresa>)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvEmpresa;
        private final CardView cvCliente;
        private final AdapterEmpresa.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterEmpresa.onItemListener onItemListener) {
            super(itemView);

            tvEmpresa = itemView.findViewById(R.id.tvEmpresa);
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
