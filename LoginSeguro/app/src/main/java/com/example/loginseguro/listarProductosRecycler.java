package com.example.loginseguro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class listarProductosRecycler extends AppCompatActivity implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    static ArrayList<datosProducto> listarProductos = new ArrayList<>();

    private RecyclerView rvListarProductos;
    private Button regresar,realizado;

    adaptadorRecyclerListarProductos adaptadorRecyclerView;

    LinearLayoutManager layout;

    DBpeticiones peticiones;

    private productosDB ProductosDB;
    private SQLiteDatabase database;

    adaptadorRecyclerListarProductos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_recycler);

        ProductosDB = new productosDB(this);
        database = ProductosDB.getWritableDatabase();
        rvListarProductos = findViewById(R.id.rvListarProductos);
        try {
            regresar = findViewById(R.id.btnVolver);
            rvListarProductos.setHasFixedSize(true);
            layout = new LinearLayoutManager(getApplicationContext());
            rvListarProductos.setLayoutManager(layout);
            DBpeticiones dbp = new DBpeticiones();
            listarProductos.clear();
            for(datosProducto dap: dbp.readProducto(this)){
                lista(dap);
            }
            adaptador = new adaptadorRecyclerListarProductos(listarProductos);
            rvListarProductos.setAdapter(adaptador);
            ItemTouchHelper.SimpleCallback simpleCallback =
                    new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,listarProductosRecycler.this);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvListarProductos);
        }catch(Exception e){
            Log.d("listarProducto",e.toString());
        }

    }


    public void lista(datosProducto dp){
        listarProductos.add(dp);
    }



    public void volverPrincipal(View view){
        Intent volver = new Intent(this, principal.class);
        startActivity(volver);
    }

    public void irCrearHD(View view){
        Intent volver = new Intent(this, nuevoProducto.class);
        startActivity(volver);
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHoldera, int direction, int position) {

        if(viewHoldera instanceof viewHolder){
            adaptador.removeItem(viewHoldera.getAdapterPosition(),this);
        }
    }
}