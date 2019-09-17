package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.proveedorperfil.ProveedorPerfilActivity;
import com.raesba.tfg_coordinacionservicios.ui.transaccionnueva.TransaccionNuevaActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;

public class ProveedorDetalleActivity extends BaseActivity implements ProveedorDetalleContract.Activity {

    private EditText email;
    //    private EditText contrasena;
    private EditText nombre;
    private EditText dni;
    private EditText direccion;
    private EditText poblacion;
    private EditText provincia;
    private EditText telefonoFijo;
    private EditText movil;
    private EditText profesion_et;
    private Spinner profesion_spinner;
    private EditText precioHora;
    private EditText descripcion;

    private FloatingActionButton nuevaTransaccion;

    private Button botonGuardar;
    private Button botonDescripcion;

    private boolean newProvider = false;
    private long diaFiltrado;
    private String uid;

    private Proveedor proveedor;

    private DatabaseManager databaseManager;

    private ProveedorDetallePresenter presenter;
    private ArrayList<String> profesiones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        databaseManager = DatabaseManager.getInstance();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        dni = findViewById(R.id.dni);
        direccion = findViewById(R.id.direccion);
        poblacion = findViewById(R.id.poblacion);
        provincia = findViewById(R.id.provincia);
        telefonoFijo = findViewById(R.id.telefonoFijo);
        movil = findViewById(R.id.movil);
        profesion_et = findViewById(R.id.profesion_et);
        profesion_spinner = findViewById(R.id.profesion_spinner);
        precioHora = findViewById(R.id.precioHora);
        botonDescripcion = findViewById(R.id.buttonDescripcionProveedorRegistro);
        nuevaTransaccion = findViewById(R.id.nuevaTransaccion);

        presenter = new ProveedorDetallePresenter(databaseManager);

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
                uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
                presenter.getDatosProveedor(uid);
            }
            if (getIntent().hasExtra(Constantes.EXTRA_DIA_FILTRADO)){
                diaFiltrado = getIntent().getLongExtra(Constantes.EXTRA_DIA_FILTRADO, 0);
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
                proveedor.setProfesion(profesiones.get(profesion_spinner.getSelectedItemPosition()));
                proveedor.setPrecioHora(Float.parseFloat(precioHora.getText().toString()));

                presenter.updateProveedor(proveedor);

                Toast.makeText(getApplicationContext(), Constantes.MSG_GUARDADO, Toast.LENGTH_LONG).show();

                finish();
            }
        });

        nuevaTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProveedorDetalleActivity.this, TransaccionNuevaActivity.class);
                intent.putExtra(Constantes.EXTRA_EMPRESA_UID, databaseManager.getUid());
                intent.putExtra(Constantes.EXTRA_PROVEEDOR_UID, proveedor.getUid());
                intent.putExtra(Constantes.EXTRA_DIA_FILTRADO, diaFiltrado);
                startActivity(intent);
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

    @SuppressLint("RestrictedApi")
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
//        profesion.setText(proveedor.getProfesion());
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
//        profesion.setEnabled(newProvider);
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
            profesion_et.setHint("");
            precioHora.setHint("");
            profesion_et.setVisibility(View.VISIBLE);
            profesion_et.setEnabled(newProvider);
            profesion_et.setText(proveedor.getProfesion());
            profesion_spinner.setVisibility(View.GONE);
        } else {
            presenter.getProfesiones();
            profesion_et.setVisibility(View.GONE);
            profesion_spinner.setVisibility(View.VISIBLE);
            nuevaTransaccion.setVisibility(View.GONE);
        }
    }

    @Override
    public void mostrarProfesiones(ArrayList<String> profesiones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_adapter_spinner, profesiones);
        profesion_spinner.setAdapter(adapter);
        this.profesiones = profesiones;

        if (proveedor.getProfesion() != null){
            for (int i = 0; i <profesiones.size(); i++){
                if (proveedor.getProfesion().equals(profesiones.get(i))){
                        profesion_spinner.setSelection(i);
                    break;
                }
            }
        } else {
            profesion_spinner.setSelection(profesiones.size()-1);
        }
    }

    @Override
    public void resultadoBaja() {
        setResult(ProveedorPerfilActivity.RESULT_CODE_BAJA);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dar_baja) {
            createDialogBaja();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialogBaja(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Constantes.PREGUNTA_BAJA)
                .setMessage(Constantes.MENSAJE_BAJA)
                .setPositiveButton(Constantes.DIALOGO_BORRAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.darDeBaja(uid);
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

}
