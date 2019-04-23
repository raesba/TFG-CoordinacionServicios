package com.raesba.tfg_coordinacionservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProveedorRegistroActivity extends AppCompatActivity {

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
    private boolean nuevo = false;

    private Proveedor proveedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_registro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("proveedor") != null){
            proveedor = (Proveedor) getIntent().getExtras().getSerializable("proveedor");

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
            

            nombre.setEnabled(nuevo);
            email.setEnabled(false);
            dni.setEnabled(nuevo);
            direccion.setEnabled(nuevo);
            poblacion.setEnabled(nuevo);
            provincia.setEnabled(nuevo);
            telefonoFijo.setEnabled(nuevo);
            movil.setEnabled(nuevo);
            profesion.setEnabled(nuevo);
            precioHora.setEnabled(nuevo);

            nuevo = false;


        } else {
            proveedor = new Proveedor();
            nuevo = true;
        }

        Button botonGuardar = findViewById(R.id.buttonGuardarProveedorRegistro);
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
                intent.putExtra("proveedor", proveedor);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
