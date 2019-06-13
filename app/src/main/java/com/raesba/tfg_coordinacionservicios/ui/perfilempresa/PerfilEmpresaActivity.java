package com.raesba.tfg_coordinacionservicios.ui.perfilempresa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.ui.empresadetalle.EmpresaDetalleActivity;
import com.raesba.tfg_coordinacionservicios.ui.listaproveedor.ListaProveedorActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class PerfilEmpresaActivity extends AppCompatActivity {

    private Button botonInformacion, botonBusqueda, botonTransaccion;

    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_empresa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        botonInformacion = findViewById(R.id.button_info);
        botonBusqueda = findViewById(R.id.button_busqueda);
        botonTransaccion = findViewById(R.id.button_transaccion);

        if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID);
        }

        botonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaProveedorActivity.class);
                startActivity(intent);
            }
        });

        botonTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilEmpresaActivity.this, Constantes.MSG_PROCESANDO,
                        Toast.LENGTH_SHORT).show();
            }
        });

        botonInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmpresaDetalleActivity.class);
                intent.putExtra(Constantes.EXTRA_EMPRESA_UID, uid);
                startActivity(intent);
            }
        });
    }



}
