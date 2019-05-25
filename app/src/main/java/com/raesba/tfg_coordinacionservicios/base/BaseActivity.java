package com.raesba.tfg_coordinacionservicios.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity implements InterfaceBaseVista {
    @Override
    public void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContexto() {
        return getApplicationContext();
    }
}
