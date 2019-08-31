package com.raesba.tfg_coordinacionservicios.ui.transacciondetalle;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

public class TransaccionDetalleActivity extends BaseActivity implements TransaccionDetalleContract.Activity, View.OnClickListener {

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
        setBotonBack();

        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fechaDisposicion);
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

            if (userType == Constantes.USUARIO_TIPO_PROVEEDOR){
                aceptar.setVisibility(View.VISIBLE);
                rechazar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.GONE);
            } else if (userType == Constantes.USUARIO_TIPO_EMPRESA){
                aceptar.setVisibility(View.GONE);
                rechazar.setVisibility(View.GONE);
                cancelar.setVisibility(View.VISIBLE);
            }

        } else {
            mostrarToast("ERROR");
            finish();
        }

        if (getIntent().hasExtra(Constantes.EXTRA_TRANSACCION_ID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_TRANSACCION_ID);
            presenter.getTransaccion(uid);
        } else {
            mostrarToast("ERROR");
            finish();
        }

        aceptar.setOnClickListener(this);
        rechazar.setOnClickListener(this);
        cancelar.setOnClickListener(this);
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
        fecha.setText(Utils.getDayText(transaccion.getFechaDisposicion()));
        direccion.setText(transaccion.getDireccion());
        precioEstimado.setText(String.valueOf(transaccion.getPrecioEstimado()));
        observaciones.setText(transaccion.getObservaciones());

        updateEstadoTransaccion(transaccion.getEstadoTransaccion());
    }

    @Override
    public void onFinishTransaccion(int estadoTransaccion) {
        transaccion.setEstadoTransaccion(estadoTransaccion);
        updateEstadoTransaccion(estadoTransaccion);
    }

    private void updateEstadoTransaccion(int estadoTransaccion) {
        String estado = "";

        if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_PENDIENTE){
            estado = "Pendiente";
        } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_ACEPTADA){
            estado = "Aceptada";
            esconderBotones();
        } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_RECHAZADA){
            estado = "Rechazada";
            esconderBotones();
        } else if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_CANCELADA){
            estado = "Cancelada";
            esconderBotones();
        }

        estadoActual.setText(estado);
    }

    private void esconderBotones(){
        aceptar.setVisibility(View.GONE);
        rechazar.setVisibility(View.GONE);
        cancelar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int estadoTransaccion = -1;

        switch (v.getId()){
            case R.id.button_aceptar:
                estadoTransaccion = Constantes.TRANSACCION_ESTADO_ACEPTADA;
                break;
            case R.id.button_rechazar:
                estadoTransaccion = Constantes.TRANSACCION_ESTADO_RECHAZADA;
                break;
            case R.id.button_cancelar:
                estadoTransaccion = Constantes.TRANSACCION_ESTADO_CANCELADA;
                break;
        }

        if (estadoTransaccion != -1){
            presenter.updateEstado(transaccion.getIdTransaccion(), transaccion.getFechaDisposicion(), estadoTransaccion);
        }
    }
}
