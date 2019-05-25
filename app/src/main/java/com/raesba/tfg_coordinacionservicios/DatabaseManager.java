package com.raesba.tfg_coordinacionservicios;

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
}
