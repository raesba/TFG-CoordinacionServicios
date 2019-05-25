package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.Proveedor;
import com.raesba.tfg_coordinacionservicios.base.BasePresenter;

public class ProveedorDetallePresenter extends BasePresenter<ProveedorDetalleContract.Vista>
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
            public void onFailure(String error) {
            }
        });
    }
}
