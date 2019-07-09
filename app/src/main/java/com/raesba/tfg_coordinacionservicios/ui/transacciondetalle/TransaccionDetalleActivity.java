package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.transaccionnueva.TransaccionNuevaPresenter;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class TransaccionDetalleActivity extends BaseActivity implements TransaccionDetalleContract.Activity {

    private TextView nombre;
    private TextView fecha;
    private TextView direccion;
    private TextView precioEstimado;
    private TextView observaciones;
    private TextView estadoActual;

    private Button aceptar;
    private Button rechazar;
    private Button cancelar;

    private Transaccion transaccion;

    private TransaccionDetallePresenter presenter;

    int userType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fecha);
        direccion = findViewById(R.id.direccion);
        precioEstimado = findViewById(R.id.precioEstimado);
        observaciones = findViewById(R.id.observaciones);
        estadoActual = findViewById(R.id.estado_actual);

        aceptar = findViewById(R.id.button_aceptar);
        rechazar = findViewById(R.id.button_rechazar);
        cancelar = findViewById(R.id.button_cancelar);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new TransaccionDetallePresenter(databaseManager);

        transaccion = new Transaccion();


        String uid = null;
        if (getIntent().hasExtra(Constantes.EXTRA_TIPO_USUARIO)){
            userType = getIntent().getIntExtra(Constantes.EXTRA_TIPO_USUARIO, -1);
        } else {
            mostrarToast("ERROR");
            finish();
        }

        if (getIntent().hasExtra(Constantes.EXTRA_TRANSACCION_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_TRANSACCION_UID);
            presenter.getTransaccion(uid);
        } else {
            mostrarToast("ERROR");
            finish();
        }
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
    public void mostrarTransaccion(Transaccion transaccion) {
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
        precioEstimado.setText(String.valueOf(transaccion.getPrecioEstimado()));
        observaciones.setText(transaccion.getObservaciones());

        String estado = "";

        if (transaccion.isAceptado()){
            estado = "Aceptado";
        } else {
            estado = "Pendiente";
        }

        estadoActual.setText(estado);

    }

    @Override
    public void onFinishTransaccion() {

    }
}
