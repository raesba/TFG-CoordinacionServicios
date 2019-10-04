package com.raesba.tfg_coordinacionservicios.data.managers;

import android.icu.text.SymbolTable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetDisposicionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetEmpresaCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProfesionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedorCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetProveedoresCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.GetTransaccionesCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.LoginCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnCompletadoCallback;
import com.raesba.tfg_coordinacionservicios.data.callbacks.OnDefaultCallback;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Disposicion;
import com.raesba.tfg_coordinacionservicios.data.modelo.negocio.Transaccion;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.UserAuth;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FirebaseManager {

    private static FirebaseManager instance;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;

    private Proveedor proveedorActual; // Soy yo (Current user)

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    private FirebaseManager() {
        this.auth = FirebaseAuth.getInstance();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void checkLogin(final String user, String password, final LoginCallback callback) {
        auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getUserType(user, callback);
                } else {
                    callback.onLoginFailed(Constantes.ERROR_LOGIN);
                }
            }
        });
    }

    public void createUser(final String user, String password, final long tipoUsuario, final LoginCallback
            callback) {
        auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
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

    public void getUserType(String user, final LoginCallback callback) {

        firebaseDatabase.getReference().child(Constantes.FIREBASE_USUARIOS_KEY)
                .orderByChild(Constantes.FIREBASE_USUARIOS_EMAIL)
                .equalTo(user)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                HashMap<String, Object> usuario = (HashMap<String, Object>)
                                        dataSnapshot.getChildren().iterator().next().getValue();
                                if (usuario != null) {
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

    public void getProveedor(String uid, final GetProveedorCallback callback) {
        firebaseDatabase.getReference().child(Constantes.FIREBASE_PROVEEDORES_KEY)
                .orderByChild(Constantes.FIREBASE_PROVEEDORES_UID)
                .equalTo(uid).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getValue() != null) {
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Proveedor proveedor = iterator.next().getValue(Proveedor.class);

                            boolean currentUser;
                            if (proveedor.getUid().equals(auth.getCurrentUser().getUid())) {
                                currentUser = true;
                            } else {
                                currentUser = false;
                            }

                            proveedorActual = proveedor;

                            callback.onSuccess(proveedor, currentUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void createNewUserInDatabase(final UserAuth userAuth, final LoginCallback callback) {

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
                        if (task.isSuccessful()) {
                            createUsuarioConTipo(userAuth, callback);
                        } else {
                            callback.onLoginFailed(Constantes.ERROR_ESCRITURA_BBDD_USUARIOS);
                        }
                    }
                });
    }

    private void createUsuarioConTipo(final UserAuth userAuth, final LoginCallback callback) {
        String tipoUsuarioKey = Constantes.FIREBASE_PROVEEDORES_KEY;

        if (userAuth.getTipo_usuario() == 0) {
            // HAGO REGISTRO EMPRESA
            tipoUsuarioKey = Constantes.FIREBASE_EMPRESAS_KEY;
        } else if (userAuth.getTipo_usuario() == 1) {
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
                        if (task.isSuccessful()) {
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

                        try {
                            ArrayList<String> profesiones = new ArrayList<>();
                            // Iterator<DataSnapshot> listaSnapshots = dataSnapshot.getChildren().iterator();
                            // Bucle for/each, quiere decir -> Para cada elemento "profesion", del tipo DataSnapshot, del iterador "dataSnapshot.getChildren"
                            for (DataSnapshot profesionDataSnapshot : dataSnapshot.getChildren()) {
                                String profesion = (String) profesionDataSnapshot.getValue();
                                profesiones.add(profesion);
                            }

                            callback.onSuccess(profesiones);
                        } catch (Exception e) {
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

                        if (dataSnapshot.getValue() != null) {
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

    public String getUid() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        } else {
            return "";
        }
    }

    public void pushTransaccion(Transaccion transaccion, final OnCompletadoCallback callback) {
        String key = firebaseDatabase.getReference().child(Constantes.FIREBASE_TRANSACCIONES_KEY).push().getKey();
        transaccion.setIdTransaccion(key);

        if (key != null) {
            firebaseDatabase.getReference()
                    .child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                    .child(key)
                    .setValue(transaccion)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callback.onSuccess(Constantes.MSG_TRANSACCION_ENVIADA);
                            } else {
                                callback.onError(Constantes.ERROR_ESCRITURA_TRANSACCION);
                            }
                        }
                    });
        }
    }

    public void getTransacciones(int userType, final String uid, final GetTransaccionesCallback callback) {
        String campo;

        if (userType == 0) {
            campo = Constantes.FIREBASE_TRANSACCIONES_UID_EMPRESA;
        } else if (userType == 1) {
            campo = Constantes.FIREBASE_TRANSACCIONES_UID_PROVEEDOR;
        } else {
            callback.onError(Constantes.ERROR_LECTURA_BBDD);
            return;
        }

        final String finalCampo = campo;

        borrarTransacciones(uid, userType, true, new OnDefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                getTransacciones(uid, finalCampo, callback);
            }

            @Override
            public void onError(String mensaje) {
                callback.onError(mensaje);
            }
        });
    }


    private void getTransacciones(String uid, String campo, final GetTransaccionesCallback callback){
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

                            if (transaccion.getIdTransaccion() == null) {
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

                        if (dataSnapshot.getValue() != null) {
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

    public void updateTransaccion(final String uid, final long fechaDisposicion, final int estadoTransaccion, final OnDefaultCallback<Integer> callback) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                .child(uid)
                .child(Constantes.FIREBASE_TRANSACCIONES_ESTADO_TRANSACCION)
                .setValue(estadoTransaccion)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateEstadoDisposicion(fechaDisposicion, estadoTransaccion, callback);
                        } else {
                            callback.onError(Constantes.ERROR_ESCRITURA_TRANSACCION);
                        }
                    }
                });


    }

    private void updateEstadoDisposicion(final long fechaDisposicion, final int estadoTransaccion, final OnDefaultCallback<Integer> callback) {
        final boolean estado;
        if (estadoTransaccion == Constantes.TRANSACCION_ESTADO_ACEPTADA) {
            estado = false;
        } else {
            estado = true;
        }

        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_PROVEEDORES_KEY)
                .child(proveedorActual.getClave())
                .child(Constantes.FIREBASE_PROVEEDORES_DISPOSICIONES)
                .child(String.valueOf(fechaDisposicion))
                .setValue(estado)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (proveedorActual != null && proveedorActual.getDisposiciones() != null) {
                                proveedorActual.getDisposiciones().put(String.valueOf(fechaDisposicion), estado);
                            }
                            callback.onSuccess(estadoTransaccion);
                        } else {
                            callback.onError(Constantes.ERROR_ESCRITURA_TRANSACCION);
                        }
                    }
                });
    }

    public void getProveedores(String profesion, final GetProveedoresCallback callback) {
        if (profesion != null) {
            firebaseDatabase.getReference()
                    .child(Constantes.FIREBASE_PROVEEDORES_KEY)
                    .orderByChild(Constantes.FIREBASE_PROVEEDORES_PROFESION)
                    .equalTo(profesion)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ArrayList<Proveedor> proveedores = new ArrayList<>();

                            for (DataSnapshot item : dataSnapshot.getChildren()) {
                                Proveedor proveedor = item.getValue(Proveedor.class);
                                proveedores.add(proveedor);
                            }

                            callback.onSuccess(proveedores);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            callback.onError(databaseError.getMessage());
                        }
                    });

        } else {
            firebaseDatabase.getReference()
                    .child(Constantes.FIREBASE_PROVEEDORES_KEY)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<Proveedor> proveedores = new ArrayList<>();

                            for (DataSnapshot item : dataSnapshot.getChildren()) {
                                Proveedor proveedor = item.getValue(Proveedor.class);
                                proveedores.add(proveedor);
                            }

                            callback.onSuccess(proveedores);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            callback.onError(databaseError.getMessage());
                        }
                    });
        }
    }

    public void pushDisposiciones(final String uid, final HashMap<String, Boolean> disposiciones, final GetDisposicionesCallback callback) {

        if (proveedorActual == null) {
            getProveedor(uid, new GetProveedorCallback() {
                @Override
                public void onSuccess(Proveedor proveedor, boolean currentUser) {
                    pushDisposiciones(disposiciones, callback);
                }

                @Override
                public void onError(String error) {
                    Log.e("Error", Constantes.ERROR_LECTURA_BBDD);
                }
            });
        } else {
            pushDisposiciones(disposiciones, callback);
        }
    }

    private void pushDisposiciones(final HashMap<String, Boolean> disposiciones, final GetDisposicionesCallback callback) {

        final HashMap<String, Boolean> disposicionesPrevias = proveedorActual.getDisposiciones() != null ? proveedorActual.getDisposiciones() : new HashMap<String, Boolean>();

        for (Map.Entry<String, Boolean> entry : disposiciones.entrySet()) {
            Disposicion disposicion = new Disposicion();

            disposicion.setFechaDisposicion(Long.parseLong(entry.getKey()));
            disposicion.setEstadoDisposicion(entry.getValue());
            disposicion.setUidProveedor(proveedorActual.getUid());
            disposicion.setProfesionProveedor(proveedorActual.getProfesion());
            disposicion.setUpdatedAt(System.currentTimeMillis());

            String key = firebaseDatabase.getReference().child(Constantes.FIREBASE_DISPOSICION_KEY).push().getKey();
            disposicion.setIdDisposicion(key);

            if (key != null) {
                firebaseDatabase.getReference()
                        .child(Constantes.FIREBASE_DISPOSICION_KEY)
                        .child(key)
                        .setValue(disposicion);
            }

            disposicionesPrevias.put(entry.getKey(), entry.getValue());
        }

        proveedorActual.setDisposiciones(disposicionesPrevias);

        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_PROVEEDORES_KEY)
                .child(proveedorActual.getClave())
                .child(Constantes.FIREBASE_PROVEEDORES_DISPOSICIONES)
                .setValue(disposicionesPrevias)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(disposicionesPrevias);
                        } else {
                            callback.onError(Constantes.ERROR_ESCRITURA_DISPOSICION);
                        }
                    }
                });
    }

    public void getDisposiciones(String uid, final GetDisposicionesCallback callback) {
        if (proveedorActual == null) {
            getProveedor(uid, new GetProveedorCallback() {
                @Override
                public void onSuccess(Proveedor proveedor, boolean currentUser) {
                    callback.onSuccess(proveedorActual.getDisposiciones());
                }

                @Override
                public void onError(String error) {
                    Log.e("Error", Constantes.ERROR_LECTURA_BBDD);
                }
            });
        } else {
            callback.onSuccess(proveedorActual.getDisposiciones());
        }
    }

    public void darDeBaja(String uid, int tipoUsuario, OnDefaultCallback<Boolean> callback) {
        //BORRAR USUARIO
        //BORRAR EMPRESA/PROVEEDOR
        //BORRAR TRANSACCIONES
        //BORRAR DISPOSCIONES
        //BORRAR FIREBASE AUTH

        borrarUsuario(uid, tipoUsuario, callback);

    }

    private void borrarUsuario(final String uid, final int tipoUsuario, final OnDefaultCallback<Boolean> callback) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_USUARIOS_KEY)
                .orderByChild(Constantes.FIREBASE_USUARIOS_UID)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            item.getRef().removeValue();
                            break;
                        }

                        borrarEmpresaOProveedor(uid, tipoUsuario, callback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError("Error borrando el usuario");
                    }
                });

    }

    private void borrarEmpresaOProveedor(final String uid, final int tipoUsuario, final OnDefaultCallback<Boolean> callback) {
        String tipoUsuarioKey;

        if (tipoUsuario == Constantes.USUARIO_TIPO_EMPRESA) {
            // HAGO REGISTRO EMPRESA
            tipoUsuarioKey = Constantes.FIREBASE_EMPRESAS_KEY;
        } else if (tipoUsuario == Constantes.USUARIO_TIPO_PROVEEDOR) {
            // HAGO REGISTRO PROVEEDOR
            tipoUsuarioKey = Constantes.FIREBASE_PROVEEDORES_KEY;
        } else {
            callback.onError(Constantes.ERROR_TIPOUSUARIO_NO_VALIDO);
            return;
        }

        firebaseDatabase.getReference()
                .child(tipoUsuarioKey)
                .orderByChild(Constantes.FIREBASE_USUARIOS_UID)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            item.getRef().removeValue();
                            break;
                        }

                        borrarTransacciones(uid, tipoUsuario, false, callback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError("Error borrando la empresa/proveedor");
                    }
                });

    }

    private void borrarTransacciones(final String uid, final int tipoUsuario, final boolean anteriores, final OnDefaultCallback<Boolean> callback) {
        String tipoUsuarioKey;

        if (tipoUsuario == Constantes.USUARIO_TIPO_EMPRESA) {
            // HAGO REGISTRO EMPRESA
            tipoUsuarioKey = Constantes.FIREBASE_TRANSACCIONES_UID_EMPRESA;
        } else if (tipoUsuario == Constantes.USUARIO_TIPO_PROVEEDOR) {
            // HAGO REGISTRO PROVEEDOR
            tipoUsuarioKey = Constantes.FIREBASE_TRANSACCIONES_UID_PROVEEDOR;
        } else {
            callback.onError(Constantes.ERROR_TIPOUSUARIO_NO_VALIDO);
            return;
        }

        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_TRANSACCIONES_KEY)
                .orderByChild(tipoUsuarioKey)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            Transaccion transaccion = item.getValue(Transaccion.class);

                            if (anteriores){
                                if (transaccion != null && transaccion.getFechaDisposicion() < System.currentTimeMillis()){
                                    item.getRef().removeValue();
                                }
                            } else {
                                item.getRef().removeValue();
                            }
                        }

                        if (!anteriores){
                            if (tipoUsuario == Constantes.USUARIO_TIPO_PROVEEDOR) {
                                borrarDisposiciones(uid, callback);
                            } else {
                                borrarFirebaseAuth(callback);
                            }
                        } else {
                            callback.onSuccess(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError("Error borrando las transacciones");
                    }
                });

    }

    private void borrarFirebaseAuth(final OnDefaultCallback<Boolean> callback) {

        if (auth.getCurrentUser() != null) {
            auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onSuccess(true);
                    } else {
                        callback.onError("Error borrando el firebase auth");
                    }
                }
            });
        } else {
            callback.onError("El usuario es nulo");
        }
    }

    private void borrarDisposiciones(final String uid, final OnDefaultCallback<Boolean> callback) {
        firebaseDatabase.getReference()
                .child(Constantes.FIREBASE_DISPOSICION_KEY)
                .orderByChild(Constantes.FIREBASE_DISPOSICION_UID_PROVEEDOR)
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            item.getRef().removeValue();
                        }

                        borrarFirebaseAuth(callback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onError("Error borrando las disposiciones");
                    }
                });

    }
}
