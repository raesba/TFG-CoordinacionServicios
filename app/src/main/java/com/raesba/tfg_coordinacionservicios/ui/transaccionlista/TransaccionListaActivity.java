package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class TransaccionListaActivity extends BaseActivity implements TransaccionListaContract.Activity {

    private RecyclerView listaTransacciones;
    private TextView emptyResults;
    private TransaccionListaPresenter presenter;
    private TransaccionListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        listaTransacciones = findViewById(R.id.lista_transacciones);
        emptyResults = findViewById(R.id.empty_results);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new TransaccionListaPresenter(databaseManager);

        String uid = null;
        int userType = 0;
        if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID);
            userType = 0;
        } else if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
            userType = 1;
        } else {
            mostrarToast(Constantes.ERROR_LECTURA_BBDD);
            finish();
        }
        adapter = new TransaccionListaAdapter(userType);

        listaTransacciones.setAdapter(adapter);
        listaTransacciones.setLayoutManager(new LinearLayoutManager(this));

        presenter.getTransacciones(userType, uid);
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

    @Override
    public void mostrarTransacciones(ArrayList<Transaccion> transacciones) {
        if (transacciones.size() == 0){
            emptyResults.setVisibility(View.VISIBLE);
            listaTransacciones.setVisibility(View.GONE);
        } else {
            emptyResults.setVisibility(View.GONE);
            listaTransacciones.setVisibility(View.VISIBLE);
            adapter.addTransacciones(transacciones);
        }
    }
}
