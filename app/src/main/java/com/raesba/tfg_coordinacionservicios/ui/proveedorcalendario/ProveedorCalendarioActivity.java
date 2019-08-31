package com.raesba.tfg_coordinacionservicios.ui.proveedorcalendario;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.utils.Constantes;
import com.raesba.tfg_coordinacionservicios.utils.EventDecorator;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ProveedorCalendarioActivity extends BaseActivity implements ProveedorCalendarioContract.Activity {

    private MaterialCalendarView calendario;
    private Button guardarCalendario;

    private static final long MAX_DATE_60_DIAS = 5184000000L;

    private HashMap<String, Boolean> disposiciones = new HashMap<>();

    private String disposicionActual;
    private CalendarDay calendarDayActual;

    private String uid;

    private ProveedorCalendarioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_calendario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setBotonBack();

        calendario = findViewById(R.id.calendario);
        guardarCalendario = findViewById(R.id.guardar_calendario);
        guardarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCalendarioSelecciodo();
            }
        });


        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new ProveedorCalendarioPresenter(databaseManager);

        if (getIntent().hasExtra(Constantes.EXTRA_PROVEEDOR_UID)){
            uid = getIntent().getStringExtra(Constantes.EXTRA_PROVEEDOR_UID);
        }

        long hoy = Utils.getToday();

        CalendarDay today = CalendarDay.today();
        calendario.state().edit()
                .setMinimumDate(today)
                .setMaximumDate(Utils.getCalendarDay(hoy + MAX_DATE_60_DIAS))
                .commit();

        calendario.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                disposicionActual = String.valueOf(Utils.getDay(date.getYear(), date.getMonth()-1, date.getDay()));
                calendarDayActual = date;
                createDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.vistaActiva(this);
        presenter.getDisposiciones(uid);
    }

    @Override
    protected void onStop() {
        presenter.vistaInactiva();
        super.onStop();
    }

    private void guardarCalendarioSelecciodo() {
        if (!uid.equals("")){
            presenter.pushDisposiciones(uid, disposiciones);
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
                        calendario.addDecorator(new EventDecorator(Color.GREEN, calendarDayActual));
                        disposicionActual = "";
                        calendarDayActual = null;

                        imprimeDisposiciones();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(Constantes.DIALOGO_NO_DISPONIBLE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        disposiciones.put(disposicionActual, false);
                        calendario.addDecorator(new EventDecorator(Color.RED, calendarDayActual));
                        imprimeDisposiciones();
                        disposicionActual = "";
                        calendarDayActual = null;
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

    @Override
    public void mostrarDisposiciones(HashMap<String, Boolean> disposiciones, boolean guardadas) {

        if (disposiciones != null){
            for (Map.Entry<String, Boolean> entry: disposiciones.entrySet()){
                CalendarDay day = Utils.getCalendarDay(Long.parseLong(entry.getKey()));

                if (entry.getValue()){
                    calendario.addDecorator(new EventDecorator(Color.GREEN, day));
                } else {
                    calendario.addDecorator(new EventDecorator(Color.RED, day));
                }
            }
        }

        if (guardadas){
            mostrarToast("Disposiciones guardadas");
            finish();
        }
    }
}
