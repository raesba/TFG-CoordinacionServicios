package com.raesba.tfg_coordinacionservicios;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void createUser(String user, String password, long tipoUsuario, final LoginCallback callback){
       firebaseManager.createUser(user, password, tipoUsuario, callback);
    }

    public void getProveedor(String uid, GetProveedorCallback callback) {
        firebaseManager.getProveedor(uid, callback);
    }
}
