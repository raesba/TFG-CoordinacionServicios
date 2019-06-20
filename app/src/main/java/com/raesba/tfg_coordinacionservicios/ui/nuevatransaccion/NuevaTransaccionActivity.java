package com.raesba.tfg_coordinacionservicios.ui.nuevatransaccion;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.Transaction;
import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class NuevaTransaccionActivity extends BaseActivity implements NuevaTransaccionContract.Vista {

    private EditText nombre;
    private EditText fecha;
    private EditText direccion;
    private EditText precioEstimado;
    private EditText observaciones;

    private Transaccion transaccion;

    private NuevaTransaccionPresenter presenter;
    private Proveedor proveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_transaccion);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fecha);
        direccion = findViewById(R.id.direccion);
        precioEstimado = findViewById(R.id.precioEstimado);
        observaciones = findViewById(R.id.observaciones);

        DatabaseManager databaseManager = DatabaseManager.getInstance();

        presenter = new NuevaTransaccionPresenter(databaseManager);

        transaccion = new Transaccion();

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
                transaccion.setIdProveedor(getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID));
            }
            if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
                transaccion.setIdEmpresa(getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID));
            }
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transaccion.setFechaCreacion(System.currentTimeMillis());
                transaccion.setFechaDisposicion(fecha.getText().toString());
                transaccion.setDireccion(direccion.getText().toString());
                transaccion.setObservaciones(observaciones.getText().toString());
                transaccion.setAceptado(false);
                transaccion.setPrecioEstimado(Double.valueOf(precioEstimado.getText().toString()));

                presenter.pushTransaccion(transaccion);
            }
        });
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
    public void mostrarDatosProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        this.nombre.setText(proveedor.getNombre());
        this.precioEstimado.setText(String.valueOf(proveedor.getPrecioHora()));
    }

    @Override
    public void onFinishTransaccion() {
        finish();
    }
}
