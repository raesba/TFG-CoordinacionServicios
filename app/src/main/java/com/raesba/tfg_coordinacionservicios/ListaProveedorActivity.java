package com.raesba.tfg_coordinacionservicios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaProveedorActivity extends AppCompatActivity implements ChildEventListener {

    private ArrayList<Proveedor> proveedores;
    private ProveedoresAdapter adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proveedor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.lista_proveedores);
        // Recycler view es una lista pero mejor ListView

        proveedores = new ArrayList<>();

        boolean test = false;

        /*databaseReference = FirebaseDatabase.getInstance().getReference().
                child("proveedores");*/

        databaseReference = FirebaseDatabase.getInstance().getReference().
                child(Constantes.FIREBASE_PROVEEDORES_KEY);

        adapter = new ProveedoresAdapter(proveedores);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*View.OnClickListener mi_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ProveedorDetalleActivity.class);
                startActivityForResult(intent, 0);

*//*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        };*/

        getProveedores();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){

            if (resultCode == RESULT_OK){

                Proveedor proveedor = (Proveedor) data.getExtras().getSerializable(Constantes.EXTRA_PROVEEDOR);
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
        Proveedor proveedor = dataSnapshot.getValue(Proveedor.class);
        adapter.updateProveedor(proveedor);
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
