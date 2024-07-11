package com.example.milenioapp.ui.ordenes.crearOrden.insecto;

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
import com.example.milenioapp.ui.ordenes.crearOrden.hallazgos.AdapterHigiene;

import java.util.ArrayList;

public class AdapterInsectos extends RecyclerView.Adapter<AdapterInsectos.ViewHolderCliente> {

    private final AdapterInsectos.onItemListener onItemListener;
    ArrayList<InsectoGroupMostrar> higieneArrayList;

    public AdapterInsectos(AdapterInsectos.onItemListener onItemListener, ArrayList<InsectoGroupMostrar> arrayList) {
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
        holder.tvNombre.setText(higieneArrayList.get(position).getNombre());
        holder.rbSi.setChecked(higieneArrayList.get(position).getS().equals("S"));
        holder.rbNo.setChecked(higieneArrayList.get(position).getS().equals("N"));
        holder.rbNA.setChecked(higieneArrayList.get(position).getS().equals("NA"));

        holder.rbSi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("S");
                notifyItemChanged(position);
            }
        });

        holder.rbNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("N");
                notifyItemChanged(position);
            }
        });
        holder.rbNA.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                higieneArrayList.get(position).setS("NA");
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return higieneArrayList.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }

    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvNombre;
        private final RadioButton rbSi, rbNo, rbNA;
        private final AdapterInsectos.onItemListener onItemListener;

        public ViewHolderCliente(@NonNull View itemView, AdapterInsectos.onItemListener onItemListener) {
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
