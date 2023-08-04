package com.example.loginseguro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class productosScanneadosRecycler extends AppCompatActivity implements
        RecyclerItemTouchHelperProductoScanneado.RecyclerItemTouchHelperListenerProductosScanneados {

    adaptadorRecyclerProductosScanneados AdaptadorRecyclerProductosScanneados;

    static ArrayList<datosProductosScanneados> listarEscaneados = new ArrayList<>();
    private RecyclerView rvListarScanneados;

    private Button volver,agregar;

    private TextView textViewEncabezadoEscaneados;
    LinearLayoutManager layout;
    private productosScanneadosDB ProductosScanneadosDB;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_scanneados_recycler);
        ProductosScanneadosDB = new productosScanneadosDB(this);
        database = ProductosScanneadosDB.getWritableDatabase();
        rvListarScanneados = findViewById(R.id.rvListarEscaneadosCv);
        try {
            volver = findViewById(R.id.btnVolverEscaneado);
            rvListarScanneados.setHasFixedSize(true);
            layout = new LinearLayoutManager(getApplicationContext());
            rvListarScanneados.setLayoutManager(layout);
            DBpeticiones dbp = new DBpeticiones();
            listarEscaneados.clear();
            for(datosProductosScanneados dps: dbp.readProductoScanneado(this)){
                listaEscaneados(dps);
            }
            AdaptadorRecyclerProductosScanneados = new adaptadorRecyclerProductosScanneados(listarEscaneados);
            rvListarScanneados.setAdapter(AdaptadorRecyclerProductosScanneados);
            ItemTouchHelper.SimpleCallback simpleCallback =
                    new RecyclerItemTouchHelperProductoScanneado(0,ItemTouchHelper.LEFT,productosScanneadosRecycler.this);
            new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvListarScanneados);
        }catch(Exception Ignored){

        }
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHoldera, int direction, int position) {
        if(viewHoldera instanceof viewHolderProductosScanneados){
            AdaptadorRecyclerProductosScanneados.removeItem(viewHoldera.getAdapterPosition(),this);
        }
    }



    private void listaEscaneados(datosProductosScanneados dps){
        listarEscaneados.add(dps);
    }

    public void volverPrincipalEscaneados(View view){
        Intent volver = new Intent(this, principal.class);
        startActivity(volver);
    }

    public void irCrear(View view){
        Intent volver = new Intent(this, lectorDeCodigoDeBarrasYQR.class);
        startActivity(volver);
    }

}