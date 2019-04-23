package com.raesba.tfg_coordinacionservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity implements ChildEventListener {

    private ArrayList<Proveedor> proveedores;
    private ProveedoresAdapter adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        proveedores = new ArrayList<>();

        boolean test = false;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("proveedores");

//        for (int i = 0; i<5; i++){
//            Proveedor proveedor = new Proveedor();
//            proveedor.setEmail("USUARIO" + (i+1) + "@gmail.com");
//            proveedor.setPassword("Pw000" + (i+1));
//            if (i == 0){proveedor.setNombre("José del Barrio Cobian");}
//                else if (i == 1){proveedor.setNombre("Marez");}
//                    else if (i == 2){proveedor.setNombre("Mava 2000");}
//                        else if (i == 3){proveedor.setNombre("BrickLayer");}
//                            else if (i == 4){proveedor.setNombre("3 Jotas");}
//            else {proveedor.setNombre("Nombre" + (i+1));}
//
//            proveedor.setDni("5000000" + (i+1));
//            proveedor.setDireccion("Direccion" + (i+1));
//            proveedor.setPoblacion("Poblacion" + (i+1));
//
//            if (i == 0){proveedor.setProvincia("Madrid");}
//                else if (i == 1){proveedor.setProvincia("Barcelona");}
//                    else if (i == 2){proveedor.setProvincia("Bilbao");}
//                        else if (i == 3){proveedor.setProvincia("Sevilla");}
//                            else if (i == 4){proveedor.setProvincia("Valladolid");}
//            else {proveedor.setProvincia("Provincia" + (i+1));}
//
//            proveedor.setTelefonoFijo("???????");
//            proveedor.setMovil("60660600" + (i+1));
//            if (i == 0){proveedor.setProfesion("Cerrajero");}
//            if (i == 1){proveedor.setProfesion("Fontanero");}
//            if (i == 2){proveedor.setProfesion("Albañil");}
//            if (i == 3){proveedor.setProfesion("Calefactor");}
//            if (i == 4){proveedor.setProfesion("Electricista");}
//            if (i == 0){proveedor.setPrecioHora(21);}
//            if (i == 1){proveedor.setPrecioHora(25);}
//            if (i == 2){proveedor.setPrecioHora(18);}
//            if (i == 3){proveedor.setPrecioHora(28);}
//            if (i == 4){proveedor.setPrecioHora(35);}
//            proveedor.setDescripcion("Descripcion " + (i+1));
//            proveedores.add(proveedor);
//        }

        adapter = new ProveedoresAdapter(proveedores);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View.OnClickListener mi_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProveedorRegistroActivity.class);
                startActivityForResult(intent, 0);

/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProveedorRegistroActivity.class);
                startActivityForResult(intent, 0);

*//*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });*/

        fab.setOnClickListener(mi_listener);

        getProveedores();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){

            if (resultCode == RESULT_OK){

                Proveedor proveedor = (Proveedor) data.getExtras().getSerializable("proveedor");
                adapter.addProveedor(proveedor);
            }
        }
    }

    private void getProveedores(){
        databaseReference.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        Proveedor proveedor = dataSnapshot.getValue(Proveedor.class);
        adapter.addProveedor(proveedor);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
