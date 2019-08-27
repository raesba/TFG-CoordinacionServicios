package com.raesba.tfg_coordinacionservicios.ui.proveedorlista;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.raesba.tfg_coordinacionservicios.R;
import com.raesba.tfg_coordinacionservicios.base.BaseActivity;
import com.raesba.tfg_coordinacionservicios.data.managers.DatabaseManager;
import com.raesba.tfg_coordinacionservicios.data.modelo.user.Proveedor;
import com.raesba.tfg_coordinacionservicios.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

public class ProveedorListaActivity extends BaseActivity implements ProveedorListaContract.Activity, DatePickerDialog.OnDateSetListener {

    private ProveedoresAdapter adapter;

    private Spinner filtroProfessiones;
    private TextView emptyResults;
    private TextView filtroDisposiciones;
    private RecyclerView listaProveedores;

    private ProveedorListaPresenter presenter;
    private ArrayList<String> profesiones;
    private long diaFiltrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBotonBack();

        // Recycler view es una lista pero mejor ListView
        listaProveedores = findViewById(R.id.lista_proveedores);
        emptyResults = findViewById(R.id.empty_results);
        filtroProfessiones = findViewById(R.id.filtro_profesiones);
        filtroDisposiciones = findViewById(R.id.filtro_disposiciones);
        filtroProfessiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    adapter.clear();
                    presenter.getProveedores(null);
                } else {
                    String profesionSeleccionada = profesiones.get(position);
                    adapter.clear();
                    presenter.getProveedores(profesionSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapter.clear();
                presenter.getProveedores(null);
            }
        });

        filtroDisposiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarView();
            }
        });

        DatabaseManager databaseManager = DatabaseManager.getInstance();
        presenter = new ProveedorListaPresenter(databaseManager);

        adapter = new ProveedoresAdapter();
        listaProveedores.setAdapter(adapter);
        listaProveedores.setLayoutManager(new LinearLayoutManager(this));

        presenter.getProfesiones();
    }

    private void showCalendarView() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
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
    public void mostrarProveedores(ArrayList<Proveedor> proveedores) {
        if (proveedores.size() == 0) {
            emptyResults.setVisibility(View.VISIBLE);
            listaProveedores.setVisibility(View.GONE);
        } else {
            emptyResults.setVisibility(View.GONE);
            listaProveedores.setVisibility(View.VISIBLE);
            adapter.addProveedores(proveedores);
        }
    }

    @Override
    public void mostrarProfesiones(ArrayList<String> profesiones) {
        profesiones.add(0, "Sin filtro");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_adapter_spinner, profesiones);
        filtroProfessiones.setAdapter(adapter);
        this.profesiones = profesiones;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("HOLA", "YEAR:" + year + " " + "MONTH:" + month + " " + "DAY:" + dayOfMonth);

        diaFiltrado = Utils.getDay(year, month, dayOfMonth);
        Log.d("HOLA", "DIA MODIFICADO: " + diaFiltrado);

        filtroDisposiciones.setText(dayOfMonth + "/" + (month+1) + "/" + year);

        adapter.filtroPorDia(diaFiltrado);
    }
}
