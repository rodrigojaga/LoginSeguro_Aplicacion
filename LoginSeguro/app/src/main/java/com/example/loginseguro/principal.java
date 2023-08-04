package com.example.loginseguro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void siguienteCrearProducto(View view){
        Intent volver = new Intent(this, nuevoProducto.class);
        startActivity(volver);
    }

    public void siguienteListarProductos(View view){
        Intent volver = new Intent(this, listarProductosRecycler.class);
        startActivity(volver);
    }

    public void siguienteHistorialDeVentas(View view){
        Intent volver = new Intent(this, listarVentasRecycler.class);
        startActivity(volver);
    }

    public void siguienteScanner(View view){
        Intent volver = new Intent(this, lectorDeCodigoDeBarrasYQR.class);
        startActivity(volver);
    }

    public void siguienteScannerListar(View view){
        Intent volver = new Intent(this, productosScanneadosRecycler.class);
        startActivity(volver);
    }
}