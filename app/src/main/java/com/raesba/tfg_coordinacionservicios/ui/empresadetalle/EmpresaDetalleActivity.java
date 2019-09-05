package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.ui.empresaperfil.EmpresaPerfilActivity;
import com.raesba.tfg_coordinacionservicios.ui.proveedorperfil.ProveedorPerfilActivity;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class EmpresaDetalleActivity extends BaseActivity implements EmpresaDetalleContract.Activity {

    private EditText email;
    private EditText nombre;
    private EditText cif;
    private EditText direccion;
    private EditText poblacion;
    private EditText provincia;
    private EditText telefonoFijo;
    private EditText movil;

    private Empresa empresa;
    private EmpresaDetallePresenter presenter;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        cif = findViewById(R.id.cif);
        direccion = findViewById(R.id.direccion);
        poblacion = findViewById(R.id.poblacion);
        provincia = findViewById(R.id.provincia);
        telefonoFijo = findViewById(R.id.telefonoFijo);
        movil = findViewById(R.id.movil);

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new EmpresaDetallePresenter(databaseManager);

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
                uid = getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID);
                presenter.getDatosEmpresa(uid);
            }
        } else {
            Toast.makeText(this, Constantes.ERROR_LECTURA_BBDD, Toast.LENGTH_LONG).show();
            finish();
        }

        Button botonGuardar = findViewById(R.id.buttonGuardarEmpresaRegistro);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                empresa.setRazonSocial(nombre.getText().toString());
                empresa.setEmail(email.getText().toString());
                empresa.setCif(cif.getText().toString());
                empresa.setDireccion(direccion.getText().toString());
                empresa.setPoblacion(poblacion.getText().toString());
                empresa.setProvincia(provincia.getText().toString());
                empresa.setTelefonoFijo(telefonoFijo.getText().toString());
                empresa.setMovil(movil.getText().toString());

                presenter.updateEmpresa(empresa);

                Toast.makeText(getApplicationContext(), Constantes.MSG_GUARDADO, Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.vistaActiva(this);
    }

    @Override
    protected void onStop() {
        presenter.vistaInactiva();
        super.onStop();
    }

    @Override
    public void mostrarDatosEmpresa(Empresa empresa) {
        this.empresa = empresa;

        nombre.setText(empresa.getRazonSocial());
        email.setText(empresa.getEmail());
        cif.setText(empresa.getCif());
        direccion.setText(empresa.getDireccion());
        poblacion.setText(empresa.getPoblacion());
        provincia.setText(empresa.getProvincia());
        telefonoFijo.setText(empresa.getTelefonoFijo());
        movil.setText(empresa.getMovil());
    }

    @Override
    public void resultadoBaja() {
        setResult(EmpresaPerfilActivity.RESULT_CODE_BAJA);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dar_baja) {
            createDialogBaja();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialogBaja(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Constantes.PREGUNTA_BAJA)
                .setMessage(Constantes.MENSAJE_BAJA)
                .setPositiveButton(Constantes.DIALOGO_BORRAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        presenter.darDeBaja(uid);
                    }
                })
                .setNegativeButton(Constantes.DIALOGO_CANCELAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
