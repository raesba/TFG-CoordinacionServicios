package com.raesba.tfg_coordinacionservicios.ui.listaproveedor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.ui.proveedordetalle.ProveedorDetalleActivity;

import java.util.ArrayList;

public class ProveedoresAdapter extends
        RecyclerView.Adapter<ProveedoresAdapter.ProveedoresViewHolder>{


    private ArrayList<Proveedor> listaProveedores;

    public ProveedoresAdapter(ArrayList<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    @NonNull
    @Override
    public ProveedoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_proveedor,
                viewGroup, false);
        ProveedoresViewHolder proveedoresViewHolder = new ProveedoresViewHolder(view);
        return proveedoresViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedoresViewHolder proveedoresViewHolder, int i) {
        proveedoresViewHolder.bindItem(listaProveedores.get(i));
    }

    public void addProveedor(Proveedor nuevoProveedor) {
        this.listaProveedores.add(nuevoProveedor);
        this.notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return listaProveedores.size();
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
            v.getContext().startActivity(intent);
        }
    }
}
