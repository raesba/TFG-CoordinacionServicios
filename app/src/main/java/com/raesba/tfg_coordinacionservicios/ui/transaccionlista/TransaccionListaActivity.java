package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class TransaccionListaActivity extends BaseActivity implements TransaccionListaContract.Vista {

    private RecyclerView listaTransacciones;
    private TransaccionListaPresenter presenter;
    private TransaccionListaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaTransacciones = findViewById(R.id.lista_transacciones);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new TransaccionListaPresenter(databaseManager);
        adapter = new TransaccionListaAdapter();

        listaTransacciones.setAdapter(adapter);
        listaTransacciones.setLayoutManager(new LinearLayoutManager(this));

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
        adapter.addTransacciones(transacciones);
    }
}
