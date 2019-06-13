package com.raesba.tfg_coordinacionservicios.data.managers;

import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.ui.login.LoginCallback;

public class DatabaseManager {

    private static DatabaseManager instance;

    private FirebaseManager firebaseManager;

    public static DatabaseManager getInstance(){
        if (instance == null){
            instance = new DatabaseManager();
        }

        return instance;
    }

    private DatabaseManager() {
        firebaseManager = FirebaseManager.getInstance();
    }


    public void checkLogin(String user, String password, final LoginCallback callback){
        firebaseManager.checkLogin(user, password, callback);
    }

    public void createUser(String user, String password, long userType, final LoginCallback
            callback){
        firebaseManager.createUser(user, password, userType, callback);
    }

    public void getProveedor(String uid, GetProveedorCallback callback) {
        firebaseManager.getProveedor(uid, callback);
    }

    public void updateProveedor(Proveedor proveedor){
        firebaseManager.updateProveedor(proveedor);
    }

    public void getProfesiones(GetProfesionesCallback callback){
        firebaseManager.getProfesiones(callback);
    }

    public void getEmpresa(String uid, GetEmpresaCallback callback) {
        firebaseManager.getEmpresa(uid, callback);
    }

    public void updateEmpresa(Empresa empresa) {
        firebaseManager.updateEmpresa(empresa);
    }
}
