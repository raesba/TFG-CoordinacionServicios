package com.raesba.tfg_coordinacionservicios.ui.proveedorcalendario;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.managers.FirebaseManager;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ProveedorCalendarioActivity extends AppCompatActivity {

    private CalendarView calendario;
    private Button guardarCalendario;

    private static final long MAX_DATE_60_DIAS = 5184000000L;

    private HashMap<String, Boolean> disposiciones = new HashMap<>();

    private String disposicionActual;

    private DatabaseManager databaseManager;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_calendario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendario = findViewById(R.id.calendario);
        guardarCalendario = findViewById(R.id.guardar_calendario);
        guardarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCalendarioSelecciodo();
            }
        });

        if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
        }

        databaseManager = DatabaseManager.getInstance();

        long hoy = Utils.getToday();

        calendario.setMinDate(hoy);
        calendario.setMaxDate(hoy + MAX_DATE_60_DIAS);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                disposicionActual = String.valueOf(Utils.getDay(year, month, dayOfMonth));
                createDialog();
            }
        });
    }

    private void guardarCalendarioSelecciodo() {
        if (!uid.equals("")){
            databaseManager.pushDisposiciones(uid, disposiciones);
        }
    }

    private void imprimeDisposiciones(){
        for (Map.Entry<String, Boolean> entry: disposiciones.entrySet()){
            Log.d("DISPOSICION", entry.getKey() + " estado: " + entry.getValue());
        }
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Constantes.PREGUNTA_DISPONIBLE)
                .setPositiveButton(Constantes.DIALOGO_DISPONIBLE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        disposiciones.put(disposicionActual, true);
                        disposicionActual = "";
                        imprimeDisposiciones();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Constantes.DIALOGO_NO_DISPONIBLE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        disposiciones.put(disposicionActual, false);
                        imprimeDisposiciones();
                        disposicionActual = "";
                        dialog.dismiss();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        imprimeDisposiciones();
                        disposicionActual = "";
                    }
                })
                .create()
                .show();
    }


}
