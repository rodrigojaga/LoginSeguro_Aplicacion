package com.example.loginseguro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class listarVentasRecycler extends AppCompatActivity implements
        RecyclerItemTouchHelperVentas.RecyclerItemTouchHelperListenerVentas {

    static ArrayList<datosVenta> listarVentas = new ArrayList<>();
    private RecyclerView rvListarVentas;
    private Button regresar, nuevaVenta;
    LinearLayoutManager layout;
    private ventasDB VentasDB;
    private SQLiteDatabase database;

    adaptadorRecyclerListarVentas adaptadorVentas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_ventas_recycler);
        VentasDB = new ventasDB(this);
        database = VentasDB.getWritableDatabase();
        rvListarVentas = findViewById(R.id.rvListarVentasCv);
        try {
            regresar = findViewById(R.id.btnVolver);
            rvListarVentas.setHasFixedSize(true);
            layout = new LinearLayoutManager(getApplicationContext());
            rvListarVentas.setLayoutManager(layout);
            DBpeticiones dbp = new DBpeticiones();
            listarVentas.clear();
            for(datosVenta daV: dbp.readVentas(this)){
                listaVentas(daV);
            }
            adaptadorVentas = new adaptadorRecyclerListarVentas(listarVentas);
            rvListarVentas.setAdapter(adaptadorVentas);
            ItemTouchHelper.SimpleCallback simpleCallback =
                    new RecyclerItemTouchHelperVentas(0,ItemTouchHelper.LEFT,listarVentasRecycler.this);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvListarVentas);
        }catch(Exception Ignored){

        }
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHoldera, int direction, int position) {
        if(viewHoldera instanceof viewHolderVenta){
            adaptadorVentas.removeItem(viewHoldera.getAdapterPosition(),this);
        }
    }

    private void listaVentas(datosVenta dv){
        listarVentas.add(dv);
    }

    public void volverPrincipalVentas(View view){
        Intent volver = new Intent(this, principal.class);
        startActivity(volver);
    }

    public void nuevaVenta(View view){
        Intent volver = new Intent(this, nuevaVenta.class);
        startActivity(volver);
    }

}