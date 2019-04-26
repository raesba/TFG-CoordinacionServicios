package com.raesba.tfg_coordinacionservicios;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
                    callback.onLoginFailed("Error al hacer el login");
                }
            }
        });
    }

    public void createUser(String user, String password, long tipoUsuario, final LoginCallback callback){
        auth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    callback.onLoginSuccess("0", 0);
                } else {
                    callback.onLoginFailed("Error al hacer el login");
                }
            }
        });
    }

    public void getUserType(String user, final LoginCallback callback){
        firebaseDatabase.getReference().child("usuarios")
                .orderByChild("email")
                .equalTo(user)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            if ( dataSnapshot.getChildrenCount() > 0){
                                HashMap<String, Object> usuario = (HashMap<String, Object>) dataSnapshot.getChildren().iterator().next().getValue();
                                if (usuario != null){
                                    long tipoUsuario = (long) usuario.get("tipo_usuario");
                                    callback.onLoginSuccess((String) usuario.get("uid"), (int) tipoUsuario);
                                }

                            }
                        } else {
                            callback.onLoginFailed("Error al leer de la base de datos");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoginFailed("Petición a la base de datos cancelada");
                    }
                });
    }

    public void getProveedor(String uid, final GetProveedorCallback callback){
        firebaseDatabase.getReference().child("proveedores").orderByChild("uid").equalTo(uid).limitToFirst(1)
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
}
