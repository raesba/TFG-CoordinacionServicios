package com.raesba.tfg_coordinacionservicios.ui.transaccionnueva;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransaccionNuevaActivity extends BaseActivity implements TransaccionNuevaContract.Activity {

    private EditText nombre;
    private Spinner fecha;
    private EditText direccion;
    private EditText precioEstimado;
    private EditText observaciones;

    private long diaFiltrado;

    private Transaccion transaccion;

    private TransaccionNuevaPresenter presenter;
    private Proveedor proveedor;
    private Empresa empresa;

    private ArrayList<Long> disposicionesLong = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_nueva);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        nombre = findViewById(R.id.nombre);
        fecha = findViewById(R.id.fechaDisposicion);
        direccion = findViewById(R.id.direccion);
        precioEstimado = findViewById(R.id.precioEstimado);
        observaciones = findViewById(R.id.observaciones);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new TransaccionNuevaPresenter(databaseManager);

        transaccion = new Transaccion();

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
                String proveedorUid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
                transaccion.setUidProveedor(proveedorUid);
                presenter.getProveedor(proveedorUid);
            }
            if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
                String empresaUid = getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID);
                transaccion.setUidEmpresa(empresaUid);
                presenter.getEmpresa(empresaUid);
            }
            if (getIntent().hasExtra(Constantes.EXTRA_DIA_FILTRADO)){
                diaFiltrado = getIntent().getLongExtra(Constantes.EXTRA_DIA_FILTRADO, 0);
            }
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long disposicionSeleccionada = disposicionesLong.get(fecha.getSelectedItemPosition());

                transaccion.setFechaCreacion(System.currentTimeMillis());
                transaccion.setFechaDisposicion(disposicionSeleccionada);
                transaccion.setDireccion(direccion.getText().toString());
                transaccion.setObservaciones(observaciones.getText().toString());
                transaccion.setEstadoTransaccion(Constantes.TRANSACCION_ESTADO_PENDIENTE);
                transaccion.setPrecioEstimado(Double.valueOf(precioEstimado.getText().toString()));
                transaccion.setNombreProveedor(proveedor.getNombre());
                transaccion.setNombreEmpresa(empresa.getRazonSocial());

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
        mostrarDisposiciones(proveedor.getDisposiciones());
    }

    @Override
    public void mostrarDatosEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public void onFinishTransaccion() {
        finish();
    }

    private void mostrarDisposiciones(HashMap<String, Boolean> disposiciones){

        ArrayList<String> disposicionesDisponibles = new ArrayList<>();
        long hoy = Utils.getToday();

        for (Map.Entry<String, Boolean> entry: disposiciones.entrySet()){

            long fechaLong = Long.parseLong(entry.getKey());

            if (entry.getValue() && (fechaLong >= hoy)){
                disposicionesDisponibles.add(Utils.getDayText(fechaLong));
                disposicionesLong.add(fechaLong);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_adapter_spinner, disposicionesDisponibles);
        fecha.setAdapter(adapter);

        if (diaFiltrado != 0){
            for (int i = 0; i <disposicionesLong.size(); i++){
                if (diaFiltrado == disposicionesLong.get(i)){
                    fecha.setSelection(i);
                    break;
                }
            }
        }
    }
}
