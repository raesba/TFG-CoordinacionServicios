package com.raesba.tfg_coordinacionservicios.ui.empresaperfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.ui.empresadetalle.EmpresaDetalleActivity;
import com.raesba.tfg_coordinacionservicios.ui.proveedorlista.ProveedorListaActivity;
import com.raesba.tfg_coordinacionservicios.ui.transaccionlista.TransaccionListaActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class EmpresaPerfilActivity extends AppCompatActivity {

    private Button botonInformacion, botonBusqueda, botonTransaccion;

    private String uid;

    public static final int REQUEST_CODE_INFORMACION = 101;
    public static final int RESULT_CODE_BAJA = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_perfil);
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
                Intent intent = new Intent(getApplicationContext(), ProveedorListaActivity.class);
                startActivity(intent);
            }
        });

        botonTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransaccionListaActivity.class);
                intent.putExtra(Constantes.EXTRA_EMPRESA_UID, uid);
                startActivity(intent);
            }
        });

        botonInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmpresaDetalleActivity.class);
                intent.putExtra(Constantes.EXTRA_EMPRESA_UID, uid);
                startActivityForResult(intent, REQUEST_CODE_INFORMACION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_INFORMACION){
            if (resultCode == RESULT_CODE_BAJA){
                finish();
            }
        }
    }

}
