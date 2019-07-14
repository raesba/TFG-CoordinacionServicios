package com.raesba.tfg_coordinacionservicios.base;

import android.content.Context;

public interface InterfaceBaseActivity {
    void mostrarToast(String mensaje);
    void setProgessBar(boolean visible);
    Context getContexto();
}
