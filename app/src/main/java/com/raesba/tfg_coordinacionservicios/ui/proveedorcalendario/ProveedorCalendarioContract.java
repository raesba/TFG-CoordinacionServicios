package com.raesba.tfg_coordinacionservicios.ui.proveedorcalendario;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;

import java.util.HashMap;

public interface ProveedorCalendarioContract {
    interface Activity extends InterfaceBaseActivity {
        void mostrarDisposiciones(HashMap<String, Boolean> disposiciones);
    }

    interface Presenter extends InterfaceBasePresenter<ProveedorCalendarioContract.Activity> {
        void getDisposiciones(String uid);

        void pushDisposiciones(String uid, HashMap<String, Boolean> disposiciones);
    }
}

