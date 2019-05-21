package com.raesba.tfg_coordinacionservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProveedorDetalleActivity extends AppCompatActivity {

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

    private boolean newProvider = false;

    private Proveedor proveedor;

    private DatabaseManager databaseManager;


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

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
                String uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);

                databaseManager.getProveedor(uid, new GetProveedorCallback() {
                    @Override
                    public void onSuccess(Proveedor proveedor, boolean currentUser) {
                        setUI(proveedor, currentUser);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
            }


        } else {

        }

        botonGuardar = findViewById(R.id.buttonGuardarProveedorRegistro);
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

                Intent intent = new Intent();
                intent.putExtra(Constantes.EXTRA_PROVEEDOR, proveedor);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setUI(Proveedor proveedor, boolean currentUser) {
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
