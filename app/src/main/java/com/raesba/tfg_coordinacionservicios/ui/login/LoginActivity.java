package com.raesba.tfg_coordinacionservicios.ui.login;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.data.callbacks.LoginCallback;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.ui.empresaperfil.EmpresaPerfilActivity;
import com.raesba.tfg_coordinacionservicios.ui.lopd.LOPDActivity;
import com.raesba.tfg_coordinacionservicios.ui.proveedorperfil.ProveedorPerfilActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity: ";

    private EditText email;
    private EditText password;
    private Button entrar;
    private Button registrarse;
    private TextView lopd;

    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progress_bar);
        lopd = findViewById(R.id.lopd);

        lopd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LOPDActivity.class);
                startActivity(intent);
            }
        });

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

                progressBar.setVisibility(View.VISIBLE);
                databaseManager.checkLogin(usuarioIntroducido, passwordIntroducida,
                        new LoginCallback() {
                    @Override
                    public void onLoginSuccess(String uid, int userType) {
                        tipoUsuario = userType;
                        progressBar.setVisibility(View.GONE);
                        startActivityWithTipoUsuario(uid);
                    }

                    @Override
                    public void onLoginFailed(String error) {
                        progressBar.setVisibility(View.GONE);
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
                .setMessage(Constantes.DIALOGO_LOPD)
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
        progressBar.setVisibility(View.VISIBLE);
        databaseManager.createUser(usuarioIntroducido, passwordIntroducida, tipoUsuario,
                new LoginCallback() {
            @Override
            public void onLoginSuccess(String uid, int userType) {
                progressBar.setVisibility(View.GONE);
                startActivityWithTipoUsuario(uid);
            }

            @Override
            public void onLoginFailed(String error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityWithTipoUsuario(String uid){

        if (tipoUsuario != -1){
            Intent intent = null;

           /* if (tipoUsuario == 0){
                intent = new Intent(this, ProveedorListaActivity.class);
            } else if (tipoUsuario == 1){
                intent = new Intent(this, ProveedorPerfilActivity.class);
                intent.putExtra("uid", uid);
            } else {
                Log.d("LOGIN", Constantes.MSG_OPCION_INVALIDA);
            }*/

            if (tipoUsuario == 0){
                intent = new Intent(this, EmpresaPerfilActivity.class);
                intent.putExtra(Constantes.EXTRA_EMPRESA_UID, uid);
            } else if (tipoUsuario == 1){
                intent = new Intent(this, ProveedorPerfilActivity.class);
                intent.putExtra(Constantes.EXTRA_PROVEEDOR_UID, uid);
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
