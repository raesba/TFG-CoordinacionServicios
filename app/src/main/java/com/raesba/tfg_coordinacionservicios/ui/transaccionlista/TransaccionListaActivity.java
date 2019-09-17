package com.raesba.tfg_coordinacionservicios.ui.transaccionlista;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    private static final String TAG = "TransaccionListaAct: ";

    private RecyclerView listaTransacciones;
    private TextView emptyResults;
    private TransaccionListaPresenter presenter;
    private TransaccionListaAdapter adapter;

    private boolean[] transaccionesFiltroEditado = {true, true, true, true};
    private boolean[] transaccionesFiltroActual = {true, true, true, true};

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
        adapter = new TransaccionListaAdapter(userType, this);

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

    @Override
    public void comprobarListaVacia() {
        if (adapter.getItemCount() == 0){
            emptyResults.setVisibility(View.VISIBLE);
            listaTransacciones.setVisibility(View.GONE);
        } else {
            emptyResults.setVisibility(View.GONE);
            listaTransacciones.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaccion_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filtrado) {
            createDialogFiltrar();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialogFiltrar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        transaccionesFiltroEditado = transaccionesFiltroActual;

        String[] tiposTransaccion = {Constantes.TRANSANCIONES_PENDIENTES, Constantes.TRANSANCIONES_ACEPTADAS, Constantes.TRANSANCIONES_RECHAZADAS, Constantes.TRANSANCIONES_CANCELADAS};

        builder.setTitle(Constantes.PREGUNTA_FILTRADO)
                .setMultiChoiceItems(tiposTransaccion, transaccionesFiltroActual, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        transaccionesFiltroEditado[which] = isChecked;
                        Log.d(TAG, "ITEM: " + which + " CHECKED: " + isChecked);
                    }
                })
                .setPositiveButton(Constantes.DIALOGO_FILTRAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        transaccionesFiltroActual = transaccionesFiltroEditado;
                        filtrarLista();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Constantes.DIALOGO_CANCELAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void filtrarLista() {
        ArrayList<Integer> filtro = new ArrayList<>();

        for (int i = 0; i < transaccionesFiltroActual.length; i++){
            if (transaccionesFiltroActual[i]){
                filtro.add(i);
            }
        }

        adapter.filtrarTransacciones(filtro);
    }
}
