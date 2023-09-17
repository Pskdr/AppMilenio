package com.example.milenioapp.ui.cronogramaServicios;

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


public class AdapterCronograma extends RecyclerView.Adapter<AdapterCronograma.ViewHolderCliente> implements Filterable {

    private final AdapterCronograma.onItemListener onItemListener;
    ArrayList<Orden> ordenArrayList;
    ArrayList<Orden> copiaListaDatos;

    public AdapterCronograma(AdapterCronograma.onItemListener onItemListener, ArrayList<Orden> arrayList) {
        this.onItemListener = onItemListener;
        this.ordenArrayList = arrayList;
        this.copiaListaDatos = new ArrayList<>(arrayList);
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_orden, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        holder.tvLugar.setText(ordenArrayList.get(position).getLugar());
        holder.tvTecnico.setText(ordenArrayList.get(position).getEncargado());
        holder.tvHora.setText(ordenArrayList.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return ordenArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return empresaFilter;
    }

    private final Filter empresaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Orden> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                listaFiltrada.addAll(copiaListaDatos);


            }else{
                String filterpattern = charSequence.toString().toLowerCase().trim();

                for(Orden empresa : copiaListaDatos){
                    if(empresa.getEncargado().toLowerCase().contains(filterpattern)) {
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
            ordenArrayList.clear();
            ordenArrayList.addAll((ArrayList<Orden>)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvLugar, tvTecnico, tvHora;
        private final CardView cvCliente;
        private final AdapterCronograma.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterCronograma.onItemListener onItemListener) {
            super(itemView);

            tvLugar = itemView.findViewById(R.id.tvLugar);
            tvTecnico = itemView.findViewById(R.id.tvTecnico);
            tvHora = itemView.findViewById(R.id.tvHora);
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
