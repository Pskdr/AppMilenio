package com.example.milenioapp.ui.home;

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
import com.example.milenioapp.ui.ordenes.crearOrden.HygieneItem;

import java.util.ArrayList;


public class AdapterHigiene extends RecyclerView.Adapter<AdapterHigiene.ViewHolderCliente> implements Filterable {

    private final AdapterHigiene.onItemListener onItemListener;
    ArrayList<HygieneItem> higieneArrayList;
    ArrayList<Cliente> copiaListaDatos;

    public AdapterHigiene(AdapterHigiene.onItemListener onItemListener, ArrayList<HygieneItem> arrayList) {
        this.onItemListener = onItemListener;
        this.higieneArrayList = arrayList;
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
        holder.tvNombre.setText(higieneArrayList.get(position).getItemName());
        holder.rbSi.setChecked(higieneArrayList.get(position).isChecked());
        holder.rbNo.setChecked(!higieneArrayList.get(position).isChecked());

        holder.rbSi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setChecked(true);
                notifyItemChanged(position);
            }
        });

        holder.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setChecked(false);
                notifyItemChanged(position);
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
        private final RadioButton rbSi, rbNo;
        private final AdapterHigiene.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterHigiene.onItemListener onItemListener) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.itemName);
            rbSi = itemView.findViewById(R.id.radioYes);
            rbNo = itemView.findViewById(R.id.radioNo);

            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
