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
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.UserAuth;
import com.raesba.tfg_coordinacionservicios.data.callbacks.LoginCallback;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

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
                                    callback.onLoginSuccess(
                                            (String) usuario.get(Constantes.FIREBASE_USUARIOS_UID),
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

    //TODO: Añadir callback para que avance de actividad solo si sale bien.
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

                            callback.onSuccess(profesiones);
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

    public void getEmpresa(String uid, final GetEmpresaCallback callback) {
        firebaseDatabase.getReference().child(Constantes.FIREBASE_EMPRESAS_KEY)
                .orderByChild(Constantes.FIREBASE_EMPRESAS_UID)
                .equalTo(uid).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null){
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Empresa empresa = iterator.next().getValue(Empresa.class);
                            callback.onSuccess(empresa);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void updateEmpresa(Empresa empresa) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_EMPRESAS_KEY)
                .child(empresa.getClave())
                .setValue(empresa);
    }

    public String  getUid() {
        if (auth.getCurrentUser() != null){
            return auth.getCurrentUser().getUid();
        } else {
            return "";
        }
    }

    public void pushTransaccion(Transaccion transaccion, final OnCompletadoCallback callback) {
        String key = firebaseDatabase.getReference().child(Constantes.FIREBASE_TRANSACCIONES_KEY).push().getKey();
        transaccion.setIdTransaccion(key);

        if (key != null){
            firebaseDatabase.getReference()
                    .child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                    .child(key)
                    .setValue(transaccion)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                callback.onSuccess(Constantes.MSG_TRANSACCION_ENVIADA);
                            } else {
                                callback.onError(Constantes.ERROR_ESCRITURA_TRANSACCION);
                            }
                        }
                    });

            //TODO: (JUANJE) Añadir esta key a proveedor y a empresa
        }
    }

    public void getTransacciones(int userType, String uid, final GetTransaccionesCallback callback) {
        String campo = "";

        if (userType == 0){
            campo = Constantes.FIREBASE_TRANSACCIONES_ID_EMPRESA;
        } else if (userType == 1){
            campo = Constantes.FIREBASE_TRANSACCIONES_ID_PROVEEDOR;
        } else {
            callback.onError(Constantes.ERROR_LECTURA_BBDD);
            return;
        }

        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                .orderByChild(campo)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Transaccion> transacciones = new ArrayList<>();

                        for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext(); ) {
                            DataSnapshot elemento = it.next();
                            Transaccion transaccion = elemento.getValue(Transaccion.class);

                            if (transaccion.getIdTransaccion() == null){
                                transaccion.setIdTransaccion(elemento.getKey());
                            }

                            transacciones.add(transaccion);
                        }

                        callback.onSuccess(transacciones);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError(databaseError.getMessage());
                    }
                });
    }

    public void getTransaccion(String uid, final GetTransaccionCallback callback) {
        firebaseDatabase.getReference().child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null){
                            Transaccion transaccion = dataSnapshot.getValue(Transaccion.class);
                            callback.onSuccess(transaccion);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError(databaseError.getMessage());
                    }
                });
    }
}
