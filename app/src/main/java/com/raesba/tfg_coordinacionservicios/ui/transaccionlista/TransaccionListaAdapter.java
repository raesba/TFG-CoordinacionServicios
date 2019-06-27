package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.proveedordetalle.ProveedorDetalleActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class TransaccionListaAdapter extends
        RecyclerView.Adapter<TransaccionListaAdapter.TransaccionListaViewHolder>{


    private ArrayList<Transaccion> listaTransacciones;

    public TransaccionListaAdapter() {
        this.listaTransacciones = new ArrayList<>();
    }

    public TransaccionListaAdapter(ArrayList<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    @NonNull
    @Override
    public TransaccionListaAdapter.TransaccionListaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transaccion_lista,
                viewGroup, false);
        TransaccionListaAdapter.TransaccionListaViewHolder transaccionesViewHolder = new TransaccionListaAdapter.TransaccionListaViewHolder(view);
        return transaccionesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaccionListaAdapter.TransaccionListaViewHolder transaccionesViewHolder, int i) {
        transaccionesViewHolder.bindItem(listaTransacciones.get(i));
    }

    public void addTransaccion(Transaccion nuevoTransaccion) {
        this.listaTransacciones.add(nuevoTransaccion);
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void addTransacciones(ArrayList<Transaccion> transacciones){
        this.listaTransacciones.addAll(transacciones);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaTransacciones.size();
    }

    public void updateTransaccion(Transaccion transaccionActualizada) {
        for (int i = 0; i< listaTransacciones.size(); i++){
            Transaccion transaccionAntigua = listaTransacciones.get(i);

//            if (transaccionAntigua.getUid().equals(transaccionActualizada.getUid())){
//                listaTransacciones.set(i, transaccionActualizada);
//                this.notifyItemChanged(i);
//                break;
//            }
        }
    }

    public class TransaccionListaViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView direccion;
        private TextView fecha;
        private TextView precioHora;

        private Transaccion transaccion;

        public TransaccionListaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            fecha = itemView.findViewById(R.id.fecha);
            direccion = itemView.findViewById(R.id.direccion);
            precioHora = itemView.findViewById(R.id.precioEstimado);

        }

        public void bindItem(Transaccion transaccion){
            this.transaccion = this.transaccion;
            nombre.setText(transaccion.getIdProveedor());
            fecha.setText(transaccion.getFechaDisposicion());
            direccion.setText(transaccion.getDireccion());
            precioHora.setText(String.valueOf(transaccion.getPrecioEstimado()));

        }
    }
}
