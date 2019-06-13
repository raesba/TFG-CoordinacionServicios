package com.raesba.tfg_coordinacionservicios.ui.empresadetalle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Empresa;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;

public class EmpresaDetalleActivity extends BaseActivity implements EmpresaDetalleContract.Vista {

    private EditText email;
    private EditText nombre;
    private EditText cif;
    private EditText direccion;
    private EditText poblacion;
    private EditText provincia;
    private EditText telefonoFijo;
    private EditText movil;

    private Empresa empresa;
    private DatabaseManager databaseManager;
    private EmpresaDetallePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        cif = findViewById(R.id.cif);
        direccion = findViewById(R.id.direccion);
        poblacion = findViewById(R.id.poblacion);
        provincia = findViewById(R.id.provincia);
        telefonoFijo = findViewById(R.id.telefonoFijo);
        movil = findViewById(R.id.movil);

        databaseManager = DatabaseManager.getInstance();
        presenter = new EmpresaDetallePresenter(databaseManager);

        if (getIntent().getExtras() != null){
            if (getIntent().hasExtra(Constantes.EXTRA_EMPRESA_UID)){
                String uid = getIntent().getStringExtra(Constantes.EXTRA_EMPRESA_UID);
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

                databaseManager.updateEmpresa(empresa);

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
}
