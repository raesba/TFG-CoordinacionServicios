package com.raesba.tfg_coordinacionservicios.ui.proveedorlista;

import com.raesba.tfg_coordinacionservicios.base.BasePresenter;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedoresCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;

import java.util.ArrayList;

public class ProveedorListaPresenter extends BasePresenter<ProveedorListaContract.Activity>
        implements ProveedorListaContract.Presenter {

    private DatabaseManager databaseManager;

    public ProveedorListaPresenter(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void getProveedores(String profesion) {
        if (vista != null){
            vista.setProgessBar(true);
        }
        databaseManager.getProveedores(profesion, new GetProveedoresCallback() {
            @Override
            public void onSuccess(ArrayList<Proveedor> proveedores) {
                if (vista != null){
                    vista.setProgessBar(false);
                    vista.mostrarProveedores(proveedores);
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
}
