package com.raesba.tfg_coordinacionservicios.data.managers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.ui.login.LoginCallback;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.UserAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FirebaseManager {

    private static FirebaseManager instance;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    public static FirebaseManager getInstance(){
        if (instance == null){
            instance = new FirebaseManager();
        }

        return instance;
    }

    private FirebaseManager() {
        this.auth = FirebaseAuth.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void checkLogin(final String user, String password, final LoginCallback callback){
        auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    getUserType(user, callback);
                } else {
                    callback.onLoginFailed(Constantes.ERROR_LOGIN);
                }
            }
        });
    }

    public void createUser(final String user, String password, final long tipoUsuario, final LoginCallback
            callback){
        auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser userAuthentication = auth.getCurrentUser();
                    UserAuth userAuth = new UserAuth();

                    userAuth.setUid(userAuthentication.getUid());
                    userAuth.setEmail(userAuthentication.getEmail());
                    userAuth.setTipo_usuario((int) tipoUsuario);
                    userAuth.setVerificado(userAuthentication.isEmailVerified());

                    createNewUserInDatabase(userAuth, callback);
                } else {
                    callback.onLoginFailed(Constantes.ERROR_LOGIN);
                }
            }
        });
    }

    public void getUserType(String user, final LoginCallback callback){

//        "tipo_usuario"

          /*firebaseDatabase.getReference().child("usuarios")
                .orderByChild("email")
                .equalTo(user)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            if ( dataSnapshot.getChildrenCount() > 0){
                                HashMap<String, Object> usuario = (HashMap<String, Object>)
                                        dataSnapshot.getChildren().iterator().next().getValue();
                                if (usuario != null){
                                    long tipoUsuario = (long) usuario.get
                                            ("tipo_usuario");
                                    callback.onLoginSuccess
                                            ((String) usuario.get("uid"),
                                                    (int) tipoUsuario);
                                }

                            }
                        } else {
                            callback.onLoginFailed(Constantes.ERROR_LECTURA_BBDD);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoginFailed(Constantes.ERROR_CANCELAR_BBDD);
                    }
                });*/
        firebaseDatabase.getReference().child(Constantes.FIREBASE_USUARIOS_KEY)
                .orderByChild(Constantes.FIREBASE_USUARIOS_EMAIL)
                .equalTo(user)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            if ( dataSnapshot.getChildrenCount() > 0){
                                HashMap<String, Object> usuario = (HashMap<String, Object>)
                                        dataSnapshot.getChildren().iterator().next().getValue();
                                if (usuario != null){
                                    long tipoUsuario = (long) usuario.get
                                            (Constantes.FIREBASE_USUARIOS_TIPOUSUARIO);
                                    callback.onLoginSuccess
                                            ((String) usuario.get(Constantes.FIREBASE_USUARIOS_UID),
                                            (int) tipoUsuario);
                                }

                            }
                        } else {
                            callback.onLoginFailed(Constantes.ERROR_LECTURA_BBDD);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoginFailed(Constantes.ERROR_CANCELAR_BBDD);
                    }
                });
    }

    public void getProveedor(String uid, final GetProveedorCallback callback){
        firebaseDatabase.getReference().child(Constantes.FIREBASE_PROVEEDORES_KEY)
                .orderByChild(Constantes.FIREBASE_PROVEEDORES_UID)
                .equalTo(uid).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null){
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Proveedor proveedor = iterator.next().getValue(Proveedor.class);

                            boolean currentUser;
                            if (proveedor.getUid().equals(auth.getCurrentUser().getUid())){
                                currentUser = true;
                            } else {
                                currentUser = false;
                            }

                            callback.onSuccess(proveedor, currentUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void createNewUserInDatabase(final UserAuth userAuth, final LoginCallback callback){

        String key = firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_USUARIOS_KEY)
                .push()
                .getKey();

        userAuth.setClave(key);

        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_USUARIOS_KEY)
                .child(key)
                .setValue(userAuth)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            createUsuarioConTipo(userAuth, callback);
                        } else {
                            callback.onLoginFailed(Constantes.ERROR_ESCRITURA_BBDD_USUARIOS);
                        }
                    }
                });
    }

    private void createUsuarioConTipo(final UserAuth userAuth, final LoginCallback callback){
        String tipoUsuarioKey = Constantes.FIREBASE_PROVEEDORES_KEY;

        if (userAuth.getTipo_usuario() == 0){
            // HAGO REGISTRO EMPRESA
            tipoUsuarioKey = Constantes.FIREBASE_EMPRESAS_KEY;
        } else if (userAuth.getTipo_usuario() == 1){
            // HAGO REGISTRO PROVEEDOR
            tipoUsuarioKey = Constantes.FIREBASE_PROVEEDORES_KEY;
        } else {
            callback.onLoginFailed(Constantes.ERROR_TIPOUSUARIO_NO_VALIDO);
            return;
        }

        firebaseDatabase.getReference()
                .child(tipoUsuarioKey)
                .child(userAuth.getClave())
                .setValue(userAuth)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callback.onLoginSuccess(userAuth.getUid(), userAuth.getTipo_usuario());
                        } else {
                            callback.onLoginFailed(Constantes.ERROR_ESCRITURA_BBDD_PROVEEDOR_EMPRESA);
                        }
                    }
                });
    }

    public void updateProveedor(Proveedor proveedor) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_PROVEEDORES_KEY)
                .child(proveedor.getClave())
                .setValue(proveedor);
    }

    public void getProfesiones(final GetProfesionesCallback callback) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_CONFIG_KEY)
                .child(Constantes.FIREBASE_PROFESIONES_KEY)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try{
                            ArrayList<String> profesiones = new ArrayList<>();
                            // Iterator<DataSnapshot> listaSnapshots = dataSnapshot.getChildren().iterator();
                            // Bucle for/each, quiere decir -> Para cada elemento "profesion", del tipo DataSnapshot, del iterador "dataSnapshot.getChildren"
                            for (DataSnapshot profesionDataSnapshot : dataSnapshot.getChildren()){
                                String profesion = (String) profesionDataSnapshot.getValue();
                                profesiones.add(profesion);
                            }

                            callback.onProfesionesFinish(profesiones);
                        } catch (Exception e){
                            callback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError(databaseError.getMessage());
                    }
                });
    }
}
