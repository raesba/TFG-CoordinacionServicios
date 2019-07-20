package com.raesba.tfg_coordinacionservicios.ui.proveedorlista;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public class ProveedorListaActivity extends BaseActivity implements ProveedorListaContract.Activity {

    private ArrayList<Proveedor> proveedores;
    private ProveedoresAdapter adapter;

    private Spinner filtroProfessiones;
    private TextView emptyResults;
    private RecyclerView listaProveedores;

    private ProveedorListaPresenter presenter;
    private ArrayList<String> profesiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        // Recycler view es una lista pero mejor ListView
        listaProveedores = findViewById(R.id.lista_proveedores);
        emptyResults = findViewById(R.id.empty_results);
        filtroProfessiones = findViewById(R.id.filtro_profesiones);
        filtroProfessiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){
                    adapter.clear();
                    presenter.getProveedores(null);
                } else {
                    String profesionSeleccionada = profesiones.get(position);
                    adapter.clear();
                    presenter.getProveedores(profesionSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.clear();
                presenter.getProveedores(null);
            }
        });

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new ProveedorListaPresenter(databaseManager);

        proveedores = new ArrayList<>();

        adapter = new ProveedoresAdapter(proveedores);
        listaProveedores.setAdapter(adapter);
        listaProveedores.setLayoutManager(new LinearLayoutManager(this));

        /*View.OnClickListener mi_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProveedorDetalleActivity.class);
                startActivityForResult(intent, 0);

*//*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        };*/

        presenter.getProfesiones();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.vistaActiva(this);
    }

    @Override
    protected void onStop() {
        presenter.vistaInactiva();
        super.onStop();
    }
//
//    @Override
//    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        Proveedor proveedor = dataSnapshot.getValue(Proveedor.class);
//        adapter.addProveedor(proveedor);
//    }
//
//    @Override
//    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//        Proveedor proveedor = dataSnapshot.getValue(Proveedor.class);
//        adapter.updateProveedor(proveedor);
//    }
//
//    @Override
//    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//    }
//
//    @Override
//    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }


    @Override
    public void mostrarProveedores(ArrayList<Proveedor> proveedores) {
        if (proveedores.size() == 0){
            emptyResults.setVisibility(View.VISIBLE);
            listaProveedores.setVisibility(View.GONE);
        } else {
            emptyResults.setVisibility(View.GONE);
            listaProveedores.setVisibility(View.VISIBLE);
            adapter.addProveedores(proveedores);
        }
    }

    @Override
    public void mostrarProfesiones(ArrayList<String> profesiones) {
        profesiones.add(0, "Sin filtro");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_adapter_spinner, profesiones);
        filtroProfessiones.setAdapter(adapter);
        this.profesiones = profesiones;
    }
}
