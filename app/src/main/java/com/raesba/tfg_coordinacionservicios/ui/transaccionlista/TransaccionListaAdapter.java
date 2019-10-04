package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.proveedorlista.ProveedoresAdapter;
import com.raesba.tfg_coordinacionservicios.ui.transacciondetalle.TransaccionDetalleActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class TransaccionListaAdapter extends
        RecyclerView.Adapter<TransaccionListaAdapter.TransaccionListaViewHolder> implements Filterable {


    private ArrayList<Transaccion> listaTransacciones;
    private ArrayList<Transaccion> listaTransaccionesFiltrados;
    private int userType;
    private Filter mFilter = new FiltroTransacciones();

    private TransaccionListaContract.Activity callback;
    private ArrayList<Integer> transaccionesEstados;

    public TransaccionListaAdapter(int userType, TransaccionListaContract.Activity callback) {
        this.listaTransacciones = new ArrayList<>();
        this.listaTransaccionesFiltrados = new ArrayList<>();
        this.transaccionesEstados = new ArrayList<>();
        this.transaccionesEstados.add(0);
        this.transaccionesEstados.add(1);
        this.transaccionesEstados.add(2);
        this.transaccionesEstados.add(3);
        this.userType = userType;
        this.callback = callback;
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
        transaccionesViewHolder.bindItem(listaTransaccionesFiltrados.get(i));
    }

    public void addTransaccion(Transaccion nuevoTransaccion) {
        this.listaTransacciones.add(nuevoTransaccion);
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void addTransacciones(ArrayList<Transaccion> transacciones){
        this.listaTransacciones.addAll(transacciones);
        this.listaTransaccionesFiltrados.addAll(transacciones);
        ordenarLista();

        notifyDataSetChanged();
    }

    private void ordenarLista(){
        Collections.sort(listaTransaccionesFiltrados, new Comparator<Transaccion>() {
            @Override
            public int compare(Transaccion o1, Transaccion o2) {
                long fecha1 = o1.getFechaDisposicion();
                long fecha2 = o2.getFechaDisposicion();

                if (fecha1 > fecha2){
                    return 1;
                } else if (fecha1<fecha2){
                    return -1;
                } else {
                    if (o1.getFechaCreacion() > o2.getFechaCreacion()){
                        return 1;
                    } else if (o1.getFechaCreacion() < o2.getFechaCreacion()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTransaccionesFiltrados.size();
    }

    public void updateTransaccion(Transaccion transaccionActualizada) {
        for (int i = 0; i< listaTransacciones.size(); i++){
            Transaccion transaccionAntigua = listaTransacciones.get(i);

//            if (transaccionAntigua.getIdDisposicion().equals(transaccionActualizada.getIdDisposicion())){
//                listaTransacciones.set(i, transaccionActualizada);
//                this.notifyItemChanged(i);
//                break;
//            }
        }
    }

    public void filtrarTransacciones(ArrayList<Integer> transaccionesEstados){
        this.transaccionesEstados.clear();
        this.transaccionesEstados.addAll(transaccionesEstados);
        mFilter.filter("");
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void updateTransaccion(String uidTransaccion, int nuevoEstado) {
        for (int i = 0; i< listaTransaccionesFiltrados.size(); i++){
            if (listaTransaccionesFiltrados.get(i).getIdTransaccion().equals(uidTransaccion)){
                listaTransaccionesFiltrados.get(i).setEstadoTransaccion(nuevoEstado);
                notifyItemChanged(i);
                break;
            }
        }
    }

    private class FiltroTransacciones extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            final List<Transaccion> listaCompleta = listaTransacciones;

            int count = listaCompleta.size();
            final ArrayList<Transaccion> listaFiltrada = new ArrayList<>(count);

            if (transaccionesEstados != null){
                for (int i = 0; i < count; i++) {
                    Transaccion transaccion = listaCompleta.get(i);

                    if (transaccionesEstados.contains(transaccion.getEstadoTransaccion())){
                        listaFiltrada.add(transaccion);
                    }
                }
            } else {
                listaFiltrada.addAll(listaCompleta);
            }

            results.values = listaFiltrada;
            results.count = listaFiltrada.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaTransaccionesFiltrados = (ArrayList<Transaccion>) results.values;

            ordenarLista();

            notifyDataSetChanged();
            callback.comprobarListaVacia();
        }
    }

    public class TransaccionListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nombre;
        private TextView direccion;
        private TextView fecha;
        private TextView precioHora;
        private TextView estado;

        private Transaccion transaccion;

        public TransaccionListaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            fecha = itemView.findViewById(R.id.fechaDisposicion);
            direccion = itemView.findViewById(R.id.direccion);
            precioHora = itemView.findViewById(R.id.precioEstimado);
            estado = itemView.findViewById(R.id.estado_actual);

            itemView.setOnClickListener(this);

        }

        public void bindItem(Transaccion transaccion){
            this.transaccion = transaccion;

            String texto = "";
            String estado_texto = "";

            if (userType == Constantes.USUARIO_TIPO_PROVEEDOR){
                texto = transaccion.getNombreEmpresa();
            } else if (userType == Constantes.USUARIO_TIPO_EMPRESA){
                texto = transaccion.getNombreProveedor();
            }

            estado_texto = updateEstadoTransaccion(transaccion.getEstadoTransaccion());

            nombre.setText(texto);
            fecha.setText(Utils.getDayText(transaccion.getFechaDisposicion()));
            direccion.setText(transaccion.getDireccion());
            precioHora.setText(String.valueOf(transaccion.getPrecioEstimado()));
            estado.setText(estado_texto);
        }

        private String updateEstadoTransaccion(int estadoTransaccion) {
            String estado = "";
            if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_PENDIENTE){
                estado = "Pendiente";
            } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_ACEPTADA){
                estado = "Aceptada";
            } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_RECHAZADA){
                estado = "Rechazada";
            } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_CANCELADA){
                estado = "Cancelada";
            }
            return (estado);
        }

        @Override
        public void onClick(View v) {
            callback.mostrarTransaccion(transaccion);
        }
    }
}
