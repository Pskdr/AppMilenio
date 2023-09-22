package com.example.milenioapp.ui.tecnicos;

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
import com.example.milenioapp.ui.home.Empresa;

import java.util.ArrayList;


public class AdapterTecnico extends RecyclerView.Adapter<AdapterTecnico.ViewHolderCliente> implements Filterable {

    private final AdapterTecnico.onItemListener onItemListener;
    ArrayList<Tecnico> tecnicoArraylist;
    ArrayList<Tecnico> copiaListaDatos;

    public AdapterTecnico(AdapterTecnico.onItemListener onItemListener, ArrayList<Tecnico> arrayList) {
        this.onItemListener = onItemListener;
        this.tecnicoArraylist = arrayList;
        this.copiaListaDatos = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_tecnico, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        holder.tvAgregarTarea.setText(tecnicoArraylist.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return tecnicoArraylist.size();
    }

    @Override
    public Filter getFilter() {
        return empresaFilter;
    }

    private final Filter empresaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Tecnico> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                listaFiltrada.addAll(copiaListaDatos);


            }else{
                String filterpattern = charSequence.toString().toLowerCase().trim();

                for(Tecnico tecnico : copiaListaDatos){
                    if(tecnico.getNombre().toLowerCase().contains(filterpattern)) {
                        listaFiltrada.add(tecnico);
                    }
                }

            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;

            return resultado;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            tecnicoArraylist.clear();
            tecnicoArraylist.addAll((ArrayList<Tecnico>)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvAgregarTarea, tvEditarTecnico;
        private final CardView cvCliente;
        private final AdapterTecnico.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterTecnico.onItemListener onItemListener) {
            super(itemView);

            tvAgregarTarea = itemView.findViewById(R.id.tvAgregarTarea);
            tvEditarTecnico = itemView.findViewById(R.id.tvEditarTecnico);
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
