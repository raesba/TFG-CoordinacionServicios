package com.raesba.tfg_coordinacionservicios;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity: ";

    private EditText email;
    private EditText password;
    private Button entrar;
    private Button registrarse;

    private String usuarioIntroducido;
    private String passwordIntroducida;

    // TIPO DE USUARIO: EMPRESA 0 PROVEEDOR 1
    private long tipoUsuario = -1;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("usuarios");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        entrar = findViewById(R.id.entrar);
        registrarse = findViewById(R.id.registrarse);

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            mAuth.signOut();
        }

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioIntroducido = email.getText().toString();
                passwordIntroducida = password.getText().toString();

                if (usuarioIntroducido.equals("") || passwordIntroducida.equals("")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(usuarioIntroducido, passwordIntroducida)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    compruebaTipoUsuario();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioIntroducido = email.getText().toString();
                passwordIntroducida = password.getText().toString();

                if (usuarioIntroducido.equals("") || passwordIntroducida.equals("")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show();
                    return;
                }

                createDialog();
            }
        });
    }

    private void compruebaTipoUsuario() {
        FirebaseDatabase.getInstance().getReference().child("usuarios")
                .orderByChild("email")
                .equalTo(usuarioIntroducido)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            if ( dataSnapshot.getChildrenCount() > 0){
                                HashMap<String, Object> usuario = (HashMap<String, Object>) dataSnapshot.getChildren().iterator().next().getValue();
                                if (usuario != null){
                                    tipoUsuario = (long) usuario.get("tipo_usuario");
                                    startActivityWithTipoUsuario();
                                }

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "El usuario no está en la base de datos", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Eres empresa o proveedor?")
                .setPositiveButton("SOY EMPRESA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoUsuario = 0;
                        dialog.dismiss();
                        iniciarRegistro();
                    }
                })
                .setNegativeButton("SOY PROVEEDOR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoUsuario = 1;
                        dialog.dismiss();
                        iniciarRegistro();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        tipoUsuario = -1;
                        Toast.makeText(getApplicationContext(), "Registro cancelado", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void iniciarRegistro(){

        mAuth.createUserWithEmailAndPassword(usuarioIntroducido, passwordIntroducida)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Name, email address, and profile photo Url
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            Uri photoUrl = user.getPhotoUrl();

                            // Check if user's email is verified
                            boolean emailVerified = user.isEmailVerified();

                            String uid = user.getUid();

                            HashMap<String, Object> usuario = new HashMap<>();
                            usuario.put("nombre", name);
                            usuario.put("email", email);
                            usuario.put("verificado", emailVerified);
                            usuario.put("uid", uid);
                            usuario.put("tipo_usuario", tipoUsuario);

                            String uidKey = myRef.push().getKey();

                            myRef.child(uidKey).setValue(usuario);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                            if (tipoUsuario == 0){
                                reference = reference.child("empresas");
                            } else if (tipoUsuario == 1){
                                reference = reference.child("proveedores");
                            }

                            reference.child(uidKey).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_LONG).show();
                                        startActivityWithTipoUsuario();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Error al escribir en la bbdd",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                            // The user's ID, unique to the Firebase project. Do NOT use this value to
                            // authenticate with your backend server, if you have one. Use
                            // FirebaseUser.getIdToken() instead.




                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void startActivityWithTipoUsuario(){

        if (tipoUsuario != -1){
            Intent intent = null;

            if (tipoUsuario == 0){
                intent = new Intent(this, ListaActivity.class);
            } else if (tipoUsuario == 1){
                intent = new Intent(this, ProveedorRegistroActivity.class);
            } else {
                Log.d("LOGIN", "Opción no válida");
            }


            if (intent != null){
                Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        } else {
            Log.w("LOGIN", "Opción no válida");
        }
    }
}
