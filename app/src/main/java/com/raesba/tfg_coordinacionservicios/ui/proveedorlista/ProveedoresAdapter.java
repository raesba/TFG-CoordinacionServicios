package com.raesba.tfg_coordinacionservicios.ui.proveedorlista;

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
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.proveedordetalle.ProveedorDetalleActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProveedoresAdapter extends
        RecyclerView.Adapter<ProveedoresAdapter.ProveedoresViewHolder> implements Filterable {


    private ArrayList<Proveedor> listaProveedores;
    private ArrayList<Proveedor> listaProveedoresFiltrados;
    private long diaFiltrado;
    private Filter mFilter = new FiltroProveedor();

    public ProveedoresAdapter() {
        this.listaProveedores = new ArrayList<>();
        this.listaProveedoresFiltrados = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProveedoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_proveedor_lista,
                viewGroup, false);
        ProveedoresViewHolder proveedoresViewHolder = new ProveedoresViewHolder(view);
        return proveedoresViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedoresViewHolder proveedoresViewHolder, int i) {
        proveedoresViewHolder.bindItem(listaProveedoresFiltrados.get(i));
    }

//    public void addProveedor(Proveedor nuevoProveedor) {
//        int prevSize = getItemCount();
//        this.listaProveedores.add(nuevoProveedor);
//        this.notifyItemInserted(prevSize);
//    }

    @Override
    public int getItemCount() {
        return listaProveedoresFiltrados.size();
    }

    public void updateProveedor(Proveedor proveedorActualizado) {
        for (int i = 0; i< listaProveedores.size(); i++){
            Proveedor proveedorAntiguo = listaProveedores.get(i);

            if (proveedorAntiguo.getUid().equals(proveedorActualizado.getUid())){
                listaProveedores.set(i, proveedorActualizado);
                this.notifyItemChanged(i);
                break;
            }
        }
    }

    public void addProveedores(ArrayList<Proveedor> proveedores) {
        int prevSize = getItemCount();
        this.listaProveedores.addAll(proveedores);
        this.listaProveedoresFiltrados.addAll(proveedores);
        this.notifyItemRangeInserted(prevSize, proveedores.size());

        if (diaFiltrado != 0){
            mFilter.filter(String.valueOf(diaFiltrado));
        }
    }

    public void clear() {
        this.listaProveedores.clear();
        this.listaProveedoresFiltrados.clear();
        this.notifyDataSetChanged();
    }

    public void filtroPorDia(long diaFiltrado) {
        this.diaFiltrado = diaFiltrado;
        mFilter.filter(String.valueOf(diaFiltrado));
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class FiltroProveedor extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Proveedor> listaCompleta = listaProveedores;

            int count = listaCompleta.size();
            final ArrayList<Proveedor> listaFiltrada = new ArrayList<>(count);

            for (int i = 0; i < count; i++) {
                Proveedor proveedor = listaCompleta.get(i);

                if (proveedor.getDisposiciones() != null){
                    HashMap<String, Boolean> disposiciones = proveedor.getDisposiciones();

                    if (disposiciones.containsKey(String.valueOf(constraint))){
                        listaFiltrada.add(proveedor);
                    }
                }
            }

            results.values = listaFiltrada;
            results.count = listaFiltrada.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaProveedoresFiltrados = (ArrayList<Proveedor>) results.values;
            notifyDataSetChanged();
        }
    }

    public class ProveedoresViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView email;
        private TextView nombre;
//        private TextView dni;
        private TextView direccion;
        private TextView poblacion;
        private TextView provincia;
        private TextView telefonoFijo;
        private TextView movil;
        private TextView profesion;
        private TextView precioHora;
//        private TextView descripcion;

        private Proveedor proveedor;

        public ProveedoresViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.email = itemView.findViewById(R.id.email);
            this.nombre = itemView.findViewById(R.id.nombre);
//            this.dni = itemView.findViewById(R.id.dni);
            this.direccion = itemView.findViewById(R.id.direccion);
            this.poblacion = itemView.findViewById(R.id.poblacion);
            this.provincia = itemView.findViewById(R.id.provincia);
            this.telefonoFijo = itemView.findViewById(R.id.telefonoFijo);
            this.movil = itemView.findViewById(R.id.movil);
            this.profesion = itemView.findViewById(R.id.profesion);
            this.precioHora = itemView.findViewById(R.id.precioHora);
//            this.descripcion = itemView.findViewById(R.id.descripcion);
        }

        public void bindItem(Proveedor proveedor){

           this.proveedor = proveedor;

            email.setText(proveedor.getEmail());
            nombre.setText(proveedor.getNombre());
//            dni.setText(proveedor.getDni());
            direccion.setText(proveedor.getDireccion());
            poblacion.setText(proveedor.getPoblacion());
            provincia.setText(proveedor.getProvincia());
            telefonoFijo.setText(proveedor.getTelefonoFijo());
            movil.setText(proveedor.getMovil());
            profesion.setText(proveedor.getProfesion());
            precioHora.setText(String.valueOf(proveedor.getPrecioHora()));
//            descripcion.setText(proveedor.getDescripcion());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ProveedorDetalleActivity.class);
            intent.putExtra(Constantes.EXTRA_PROVEEDOR_UID, proveedor.getUid());
            intent.putExtra(Constantes.EXTRA_DIA_FILTRADO, diaFiltrado);
            v.getContext().startActivity(intent);
        }
    }
}
