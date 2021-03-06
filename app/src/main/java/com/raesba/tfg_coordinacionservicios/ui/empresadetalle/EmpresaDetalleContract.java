package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseActivity;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;

public interface EmpresaDetalleContract {
    interface Activity extends InterfaceBaseActivity {

        void mostrarDatosEmpresa(Empresa empresa);
        void resultadoBaja();
    }

    interface Presenter extends InterfaceBasePresenter<Activity> {

        void getDatosEmpresa(String uid);
        void updateEmpresa(Empresa empresa);
    }
}