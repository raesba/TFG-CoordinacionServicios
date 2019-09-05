package com.raesba.tfg_coordinacionservicios.ui.proveedordetalle;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnDefaultCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

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
                    vista.setProgessBar(false);
                    vista.mostrarDatosProveedor(proveedor, currentUser);
                }
            }
            @Override
            public void onError(String error) {
                if (vista != null){
                    vista.setProgessBar(false);
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

    @Override
    public void updateProveedor(Proveedor proveedor) {
        databaseManager.updateProveedor(proveedor);
    }

    @Override
    public void darDeBaja(String uid) {
        databaseManager.darDeBaja(uid, Constantes.USUARIO_TIPO_PROVEEDOR, new OnDefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (vista != null){
                    vista.resultadoBaja();
                }
            }

            @Override
            public void onError(String mensaje) {

            }
        });
    }
}
