package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public class ProveedorDetallePresenter extends BasePresenter<ProveedorDetalleContract.Activity>
                                        implements ProveedorDetalleContract.Presenter {

    private DatabaseManager databaseManager;

    public ProveedorDetallePresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getDatosProveedor(String uid) {
        databaseManager.getProveedor(uid, new GetProveedorCallback() {
            @Override
            public void onSuccess(Proveedor proveedor, boolean currentUser) {
                if (vista != null){
                    vista.mostrarDatosProveedor(proveedor, currentUser);
                }
            }
            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.mostrarToast(error);
                }
            }
        });
    }

    @Override
    public void getProfesiones() {
        databaseManager.getProfesiones(new GetProfesionesCallback() {
            @Override
            public void onSuccess(ArrayList<String> profesiones) {
                if (vista != null){
                    vista.mostrarProfesiones(profesiones);
                }
            }

            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.mostrarToast(error);
                }
            }
        });
    }
}
