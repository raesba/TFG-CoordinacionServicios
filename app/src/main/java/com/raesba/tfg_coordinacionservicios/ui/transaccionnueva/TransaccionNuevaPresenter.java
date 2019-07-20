package com.raesba.tfg_coordinacionservicios.ui.transaccionnueva;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

public class TransaccionNuevaPresenter extends BasePresenter<TransaccionNuevaContract.Activity>
        implements TransaccionNuevaContract.Presenter {

    private DatabaseManager databaseManager;

    public TransaccionNuevaPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getProveedor(String uid) {
        databaseManager.getProveedor(uid, new GetProveedorCallback() {

            @Override
            public void onSuccess(Proveedor proveedor, boolean currentUser) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarDatosProveedor(proveedor);
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
    public void getEmpresa(String uid) {

        databaseManager.getEmpresa(uid, new GetEmpresaCallback() {
            @Override
            public void onSuccess(Empresa empresa) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarDatosEmpresa(empresa);
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
    public void pushTransaccion(Transaccion transaccion) {
        if (vista != null){
            vista.setProgessBar(true);
        }
        databaseManager.pushTransaccion(transaccion, new OnCompletadoCallback() {
            @Override
            public void onSuccess(String mensaje) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.onFinishTransaccion();
                }
            }
            @Override
            public void onError(String mensaje) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarToast(mensaje);
                }
            }
        });
    }
}
