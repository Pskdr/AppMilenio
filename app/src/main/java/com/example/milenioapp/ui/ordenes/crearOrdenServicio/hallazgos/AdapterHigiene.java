package com.example.milenioapp.ui.ordenes.crearOrdenServicio.hallazgos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.Cliente;

import java.util.ArrayList;


public class AdapterHigiene extends RecyclerView.Adapter<AdapterHigiene.ViewHolderCliente> implements Filterable {

    private final AdapterHigiene.onItemListener onItemListener;
    ArrayList<HygieneItem> higieneArrayList;
    ArrayList<Cliente> copiaListaDatos;
    private boolean bloquear;

    public AdapterHigiene(AdapterHigiene.onItemListener onItemListener, ArrayList<HygieneItem> arrayList, boolean bloquear) {
        this.onItemListener = onItemListener;
        this.higieneArrayList = arrayList;
        this.bloquear = bloquear;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hygiene_item_layout, null, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

        if(bloquear){
            holder.rbSi.setEnabled(false);
            holder.rbNo.setEnabled(false);
            holder.rbNA.setEnabled(false);
        }

        holder.tvNombre.setText(higieneArrayList.get(position).getItemName());
        holder.rbSi.setChecked(higieneArrayList.get(position).getS().equals("S"));
        holder.rbNo.setChecked(higieneArrayList.get(position).getS().equals("N"));
        holder.rbNA.setChecked(higieneArrayList.get(position).getS().equals("NA"));

        holder.rbSi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("S");
            }
        });

        holder.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("N");
            }
        });
        holder.rbNA.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("NA");
            }
        });


        Log.d("pesk", "onBindViewHolder: entró "+higieneArrayList.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return higieneArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return empresaFilter;
    }

    private final Filter empresaFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Cliente> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                listaFiltrada.addAll(copiaListaDatos);


            }else{
                String filterpattern = charSequence.toString().toLowerCase().trim();

                for(Cliente empresa : copiaListaDatos){
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
            higieneArrayList.clear();
            higieneArrayList.addAll((ArrayList<HygieneItem>)filterResults.values);
            notifyDataSetChanged();
        }
    };


    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvNombre;
        private final RadioButton rbSi, rbNo, rbNA;
        private final AdapterHigiene.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterHigiene.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
            rbSi = itemView.findViewById(R.id.radioYes);
            rbNo = itemView.findViewById(R.id.radioNo);
            rbNA = itemView.findViewById(R.id.radioNa);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
