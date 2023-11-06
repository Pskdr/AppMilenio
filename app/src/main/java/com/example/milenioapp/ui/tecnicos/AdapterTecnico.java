package com.example.milenioapp.ui.tecnicos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.home.Empresa;
import com.example.milenioapp.ui.utilidades.Utilities;

import java.util.ArrayList;


public class AdapterTecnico extends RecyclerView.Adapter<AdapterTecnico.ViewHolderCliente> implements Filterable {

    private final AdapterTecnico.onItemListener onItemListener;
    ArrayList<Tecnico> tecnicoArraylist;
    ArrayList<Tecnico> copiaListaDatos;
    private MisTecnicosFragment instancia;

    public AdapterTecnico(AdapterTecnico.onItemListener onItemListener, ArrayList<Tecnico> arrayList,MisTecnicosFragment instancia) {
        this.onItemListener = onItemListener;
        this.tecnicoArraylist = arrayList;
        this.copiaListaDatos = new ArrayList<>(arrayList);
        this.instancia = instancia;
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
        Utilities utilities = new Utilities();
        holder.tvNombreTecnico.setText(tecnicoArraylist.get(position).getNombre());
        holder.tvAgregarTarea.setOnClickListener((v -> {
            instancia.agregarTarea(tecnicoArraylist.get(holder.getAdapterPosition()).getId());
        }));
        holder.tvEditarTecnico.setOnClickListener((v -> {
            instancia.editarTecnico(tecnicoArraylist.get(holder.getAdapterPosition()).getId());
        }));
        holder.ivEmpleado.setImageBitmap(utilities.stringToBitMap(tecnicoArraylist.get(holder.getAdapterPosition()).getFoto()));
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

        private final Button tvAgregarTarea, tvEditarTecnico;
        private final CardView cvCliente;
        private final ImageView ivEmpleado;
        private final AdapterTecnico.onItemListener onItemListener;
        private TextView tvNombreTecnico;

        public ViewHolderCliente(@NonNull View itemView, AdapterTecnico.onItemListener onItemListener) {
            super(itemView);

            tvAgregarTarea = itemView.findViewById(R.id.tvAgregarTarea);
            tvEditarTecnico = itemView.findViewById(R.id.tvEditarTecnico);
            cvCliente = itemView.findViewById(R.id.cvCliente);
            tvNombreTecnico = itemView.findViewById(R.id.tvNombreTecnico);
            ivEmpleado = itemView.findViewById(R.id.ivEmpleado);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
