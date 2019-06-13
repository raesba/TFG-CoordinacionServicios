package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import com.raesba.tfg_coordinacionservicios.base.InterfaceBasePresenter;
import com.raesba.tfg_coordinacionservicios.base.InterfaceBaseVista;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;

public interface EmpresaDetalleContract {
    interface Vista extends InterfaceBaseVista {

        void mostrarDatosEmpresa(Empresa empresa);
    }

    interface Presenter extends InterfaceBasePresenter<EmpresaDetalleContract.Vista> {

        void getDatosEmpresa(String uid);
    }
}