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
import com.raesba.tfg_coordinacionservicios.ui.transacciondetalle.TransaccionDetalleActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class TransaccionListaAdapter extends
        RecyclerView.Adapter<TransaccionListaAdapter.TransaccionListaViewHolder>{


    private ArrayList<Transaccion> listaTransacciones;
    private int userType = -1;

    public TransaccionListaAdapter(int userType) {
        this.listaTransacciones = new ArrayList<>();
        this.userType = userType;
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

    public class TransaccionListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            itemView.setOnClickListener(this);

        }

        public void bindItem(Transaccion transaccion){
            this.transaccion = transaccion;

            String texto = "";
            if (userType == Constantes.USUARIO_TIPO_PROVEEDOR){
                texto = transaccion.getNombreEmpresa();
            } else if (userType == Constantes.USUARIO_TIPO_EMPRESA){
                texto = transaccion.getNombreProveedor();
            }
            nombre.setText(texto);
            fecha.setText(transaccion.getFechaDisposicion());
            direccion.setText(transaccion.getDireccion());
            precioHora.setText(String.valueOf(transaccion.getPrecioEstimado()));

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), TransaccionDetalleActivity.class);
            intent.putExtra(Constantes.EXTRA_TIPO_USUARIO, userType);
            intent.putExtra(Constantes.EXTRA_TRANSACCION_UID, transaccion.getIdTransaccion());
            v.getContext().startActivity(intent);
        }
    }
}
