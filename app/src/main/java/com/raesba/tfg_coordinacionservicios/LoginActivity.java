package com.raesba.tfg_coordinacionservicios;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        entrar = findViewById(R.id.entrar);
        registrarse = findViewById(R.id.registrarse);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioIntroducido = email.getText().toString();
                passwordIntroducida = password.getText().toString();

                if (usuarioIntroducido.equals("") || passwordIntroducida.equals("")){
                    Toast.makeText(getApplicationContext(), Constantes.ERROR_CAMPO_VACIO,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                databaseManager.checkLogin(usuarioIntroducido, passwordIntroducida,
                        new LoginCallback() {
                    @Override
                    public void onLoginSuccess(String uid, int userType) {
                        tipoUsuario = userType;
                        startActivityWithTipoUsuario(uid);
                    }

                    @Override
                    public void onLoginFailed(String error) {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), Constantes.ERROR_CAMPO_VACIO,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                createDialog();
            }
        });

        databaseManager = DatabaseManager.getInstance();
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Constantes.PREGUNTA_QUIEN)
                .setPositiveButton(Constantes.DIALOGO_EMPRESA, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipoUsuario = 0;
                        dialog.dismiss();
                        iniciarRegistro();
                    }
                })
                .setNegativeButton(Constantes.DIALOGO_PROVEEDOR, new DialogInterface.OnClickListener() {
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
                        Toast.makeText(getApplicationContext(), Constantes.ERROR_CANCELAR_DIALOGO,
                                Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void iniciarRegistro() {
        databaseManager.createUser(usuarioIntroducido, passwordIntroducida, tipoUsuario,
                new LoginCallback() {
            @Override
            public void onLoginSuccess(String uid, int userType) {
                startActivityWithTipoUsuario(uid);
            }

            @Override
            public void onLoginFailed(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityWithTipoUsuario(String uid){

        if (tipoUsuario != -1){
            Intent intent = null;

           /* if (tipoUsuario == 0){
                intent = new Intent(this, ListActivity.class);
            } else if (tipoUsuario == 1){
                intent = new Intent(this, PerfilProveedorActivity.class);
                intent.putExtra("uid", uid);
            } else {
                Log.d("LOGIN", Constantes.MSG_OPCION_INVALIDA);
            }*/

            if (tipoUsuario == 0){
                intent = new Intent(this, ListActivity.class);
            } else if (tipoUsuario == 1){
                intent = new Intent(this, PerfilProveedorActivity.class);
                intent.putExtra(Constantes.FIREBASE_USUARIOS_UID, uid);
            } else {
                Log.d(TAG, Constantes.MSG_OPCION_INVALIDA);
            }


            if (intent != null){
                Toast.makeText(getApplicationContext(),Constantes.MSG_REDIRIGIENDO,
                        Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        } else {
            Log.w(TAG, Constantes.MSG_OPCION_INVALIDA);
        }
    }
}
