package com.example.milenioapp.ui.home.empresa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.ui.utilidades.Utilities;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterOrdenes extends RecyclerView.Adapter<AdapterOrdenes.ViewHolderCliente> {

    private final AdapterOrdenes.onItemListener onItemListener;
    private final ArrayList<OrdenMostrar> ordenArrayList;

    public AdapterOrdenes(AdapterOrdenes.onItemListener onItemListener, ArrayList<OrdenMostrar> ordenArrayList) {
        this.onItemListener = onItemListener;
        this.ordenArrayList = ordenArrayList;
    }

    @NonNull
    @Override
    public AdapterOrdenes.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_orden_agregada,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new AdapterOrdenes.ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrdenes.ViewHolderCliente holder, int position) {
        Utilities utilities = new Utilities();
        Calendar horaIngreso = Calendar.getInstance();
        Calendar horaSalida = Calendar.getInstance();
        Calendar fechaEnvio = Calendar.getInstance();

        horaIngreso.setTimeInMillis(ordenArrayList.get(position).getHoraEntrada());
        horaSalida.setTimeInMillis(ordenArrayList.get(position).getHoraSalida());
        fechaEnvio.setTimeInMillis(ordenArrayList.get(position).getFechaInicio());

        holder.tvOperario.setText(ordenArrayList.get(position).getNombre());
        holder.tvHoraIngreso.setText(utilities.split(utilities.getFechaString(horaIngreso),1));
        holder.tvHoraSalida.setText(utilities.split(utilities.getFechaString(horaSalida),1));
        holder.tvFecha.setText(utilities.split(utilities.getFechaString(fechaEnvio),0));
        holder.tvEstadoEnvio.setText(ordenArrayList.get(position).getEstadoEnvio().equals("E")  ? "ENVIADO" : "PENDIENTE");

    }

    @Override
    public int getItemCount() {
        return ordenArrayList.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }
    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvOperario,tvHoraIngreso,tvHoraSalida,tvFecha,tvEstadoEnvio;
        private final AdapterOrdenes.onItemListener onItemListener;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterOrdenes.onItemListener onItemListener) {
            super(itemView);

            tvOperario = itemView.findViewById(R.id.tvOperario);
            tvHoraIngreso = itemView.findViewById(R.id.tvHoraIngreso);
            tvHoraSalida = itemView.findViewById(R.id.tvHoraSalida);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstadoEnvio = itemView.findViewById(R.id.tvEstadoEnvio);

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
