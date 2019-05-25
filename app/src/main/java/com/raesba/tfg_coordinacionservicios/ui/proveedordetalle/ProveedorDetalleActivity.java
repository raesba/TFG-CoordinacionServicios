package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.Constantes;
import com.raesba.tfg_coordinacionservicios.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.Proveedor;
import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;

public class ProveedorDetalleActivity extends BaseActivity implements ProveedorDetalleContract.Vista {

    private EditText email;
    //    private EditText contrasena;
    private EditText nombre;
    private EditText dni;
    private EditText direccion;
    private EditText poblacion;
    private EditText provincia;
    private EditText telefonoFijo;
    private EditText movil;
    private EditText profesion;
    private EditText precioHora;
    private EditText descripcion;

    private Button botonGuardar;
    private Button botonDescripcion;

    private boolean newProvider = false;

    private Proveedor proveedor;

    private DatabaseManager databaseManager;

    private ProveedorDetallePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseManager = DatabaseManager.getInstance();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        dni = findViewById(R.id.dni);
        direccion = findViewById(R.id.direccion);
        poblacion = findViewById(R.id.poblacion);
        provincia = findViewById(R.id.provincia);
        telefonoFijo = findViewById(R.id.telefonoFijo);
        movil = findViewById(R.id.movil);
        profesion= findViewById(R.id.profesion);
        precioHora = findViewById(R.id.precioHora);
        botonDescripcion = findViewById(R.id.buttonDescripcionProveedorRegistro);

        presenter = new ProveedorDetallePresenter(databaseManager);

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
                String uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
                presenter.getDatosProveedor(uid);
            }
        } else {
            Toast.makeText(this, Constantes.ERROR_LECTURA_BBDD, Toast.LENGTH_LONG).show();
            finish();
        }

        botonGuardar = findViewById(R.id.buttonGuardarProveedorRegistro);
        botonDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proveedor.setNombre(nombre.getText().toString());
                proveedor.setEmail(email.getText().toString());
                proveedor.setDni(dni.getText().toString());
                proveedor.setDireccion(direccion.getText().toString());
                proveedor.setPoblacion(poblacion.getText().toString());
                proveedor.setProvincia(provincia.getText().toString());
                proveedor.setTelefonoFijo(telefonoFijo.getText().toString());
                proveedor.setMovil(movil.getText().toString());
                proveedor.setProfesion(profesion.getText().toString());
                proveedor.setPrecioHora(Float.parseFloat(precioHora.getText().toString()));

                databaseManager.updateProveedor(proveedor);

                Toast.makeText(getApplicationContext(), Constantes.MSG_GUARDADO, Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    private void createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Constantes.TITULO_DESCRIPCION);

        if (newProvider){
            final EditText descripcion = new EditText(this);
            descripcion.setHint(Constantes.PISTA_DESCRIPCION);
            descripcion.setText(proveedor.getDescripcion());
            builder.setView(descripcion);
            builder.setPositiveButton(Constantes.DIALOGO_GUARDAR, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String descripcionText = descripcion.getText().toString();
                    proveedor.setDescripcion(descripcionText);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(Constantes.DIALOGO_CANCELAR, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        } else {
            if (proveedor.getDescripcion() == null || proveedor.getDescripcion().equals("")){
                builder.setMessage(Constantes.PISTA_DESCRIPCION_VACIA);
            } else {
                builder.setMessage(proveedor.getDescripcion());
            }
            builder.setPositiveButton(Constantes.DIALOGO_OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        builder.create().show();
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
    public void mostrarDatosProveedor(Proveedor proveedor, boolean currentUser) {
        this.proveedor = proveedor;

        nombre.setText(proveedor.getNombre());
        email.setText(proveedor.getEmail());
        dni.setText(proveedor.getDni());
        direccion.setText(proveedor.getDireccion());
        poblacion.setText(proveedor.getPoblacion());
        provincia.setText(proveedor.getProvincia());
        telefonoFijo.setText(proveedor.getTelefonoFijo());
        movil.setText(proveedor.getMovil());
        profesion.setText(proveedor.getProfesion());
        precioHora.setText(String.valueOf(proveedor.getPrecioHora()));

        newProvider = currentUser;

        nombre.setEnabled(newProvider);
        email.setEnabled(false);
        dni.setEnabled(newProvider);
        direccion.setEnabled(newProvider);
        poblacion.setEnabled(newProvider);
        provincia.setEnabled(newProvider);
        telefonoFijo.setEnabled(newProvider);
        movil.setEnabled(newProvider);
        profesion.setEnabled(newProvider);
        precioHora.setEnabled(newProvider);

        if (!currentUser){
            botonGuardar.setVisibility(View.GONE);
            nombre.setHint("");
            email.setHint("");
            dni.setHint("");
            direccion.setHint("");
            poblacion.setHint("");
            provincia.setHint("");
            telefonoFijo.setHint("");
            movil.setHint("");
            profesion.setHint("");
            precioHora.setHint("");
        }
    }
}
