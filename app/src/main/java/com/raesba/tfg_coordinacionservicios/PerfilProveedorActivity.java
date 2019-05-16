package com.raesba.tfg_coordinacionservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PerfilProveedorActivity extends AppCompatActivity {

    private Button botonInformacion, botonCalendario, botonTransaccion;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_proveedor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        botonInformacion = findViewById(R.id.button_info);
        botonCalendario = findViewById(R.id.button_calendario);
        botonTransaccion = findViewById(R.id.button_transaccion);

        /*if(getIntent().hasExtra("uid")){
            uid = getIntent().getStringExtra("uid");
        }*/

        if (getIntent().hasExtra(Constantes.FIREBASE_PROVEEDORES_UID)){
            uid = getIntent().getStringExtra(Constantes.FIREBASE_PROVEEDORES_UID);
        }

        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilProveedorActivity.this, Constantes.MSG_PROCESANDO,
                        Toast.LENGTH_SHORT).show();
            }
        });

        botonTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilProveedorActivity.this, Constantes.MSG_PROCESANDO,
                        Toast.LENGTH_SHORT).show();
            }
        });

        botonInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(getApplicationContext(),ProveedorRegistroActivity.class);
                intent.putExtra("uid"  , uid);
                startActivity(intent);*/

                Intent intent = new Intent(getApplicationContext(),ProveedorRegistroActivity.class);
                intent.putExtra(Constantes.FIREBASE_PROVEEDORES_UID, uid);
                startActivity(intent);
            }
        });


    }

}
