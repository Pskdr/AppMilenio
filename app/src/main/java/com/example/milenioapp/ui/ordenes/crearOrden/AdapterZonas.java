package com.example.milenioapp.ui.ordenes.crearOrden;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milenioapp.R;
import com.example.milenioapp.database.entity.Zona;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterZonas extends RecyclerView.Adapter<AdapterZonas.ViewHolderCliente> {

    ArrayList<Zona> liquidacionObraArrayList = new ArrayList<>();

    private final AdapterZonas.onItemListener onItemListener;
    private final CrearOrdenFragment instancia;
    private final boolean bloquear;
    private final long contrato;

    public AdapterZonas(ArrayList<Zona> liquidacionObraArrayList,
                                AdapterZonas.onItemListener onItemListener,
                                CrearOrdenFragment itemsFragment, boolean bloquear, long contrato) {
        this.onItemListener = onItemListener;
        this.liquidacionObraArrayList = liquidacionObraArrayList;
        this.instancia = itemsFragment;
        this.bloquear = bloquear;
        this.contrato= contrato;
    }


    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_zona,null,false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        return new ViewHolderCliente(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {

    }

    @Override
    public int getItemCount() {
        return liquidacionObraArrayList.size();
    }

    public interface onItemListener
    {
        void onItemClick(int position);
    }
    public static class ViewHolderCliente extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvItem,tvTipoUnidad,tvValorCantidad, tvCodigo, tvValor, tvTotal, tvEstacion;
        private final EditText etCantidad;
        private final Button btnEliminar;
        private final AdapterZonas.onItemListener onItemListener;
        private final LinearLayout lyValores, lLEstaciones;
        private final CardView cvCliente;

        public ViewHolderCliente(@NonNull View itemView, AdapterZonas.onItemListener onItemListener) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tvItem);
            tvTipoUnidad = itemView.findViewById(R.id.tvTipoUnidad);
            tvValorCantidad = itemView.findViewById(R.id.tvValorCantidad);
            tvCodigo = itemView.findViewById(R.id.tvCodigoItem);
            tvEstacion = itemView.findViewById(R.id.tvEstacion);
            etCantidad = itemView.findViewById(R.id.etCantidad);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            lyValores = itemView.findViewById(R.id.lyValores);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            lLEstaciones = itemView.findViewById(R.id.lLEstaciones);
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
