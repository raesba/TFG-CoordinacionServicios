package com.raesba.tfg_coordinacionservicios.ui.perfilproveedor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.ui.proveedordetalle.ProveedorDetalleActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class PerfilProveedorActivity extends AppCompatActivity {

    private Button botonInformacion, botonCalendario, botonTransaccion;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_perfil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        botonInformacion = findViewById(R.id.button_info);
        botonCalendario = findViewById(R.id.button_calendario);
        botonTransaccion = findViewById(R.id.button_transaccion);

        if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
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

                Intent intent = new Intent(getApplicationContext(), ProveedorDetalleActivity.class);
                intent.putExtra(Constantes.EXTRA_PROVEEDOR_UID, uid);
                startActivity(intent);
            }
        });


    }

}
