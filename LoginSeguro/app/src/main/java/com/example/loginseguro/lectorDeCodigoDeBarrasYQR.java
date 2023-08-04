package com.example.loginseguro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class lectorDeCodigoDeBarrasYQR extends AppCompatActivity {

    private Button btnScanner, volver, ok,copiar;
    private EditText scnaeo, nombreProductoScaneado,cantidadProductoScanneado,precioProductoScaneado,etResultadoScaneo;
    private boolean cambioAModificacion;
    private String strIdProductoEscaneado, strNombreProductoEscaneado, strCantidadProductoScanneado,strPrecioProductoVendido,strCodigoProductoScaneado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_de_codigo_de_barras_yqr);

        btnScanner = findViewById(R.id.Scannear);
        scnaeo = findViewById(R.id.etResultadoScaneo);
        nombreProductoScaneado = findViewById(R.id.nombreProductoScaneado);
        cantidadProductoScanneado = findViewById(R.id.cantidadProductoScanneado);
        precioProductoScaneado = findViewById(R.id.precioProductoScaneado);
        etResultadoScaneo = findViewById(R.id.etResultadoScaneo);
        volver = findViewById(R.id.volverPrincipal);
        ok = findViewById(R.id.okPrincipal);
        copiar = findViewById(R.id.copiar);
        cambioAModificacion = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cambioAModificacion = bundle.getBoolean("BooleanKeyScaneado");
            strIdProductoEscaneado = bundle.getString("isScaneadoKey");
            strNombreProductoEscaneado = bundle.getString("nombreScaneadoKey");
            strCantidadProductoScanneado = bundle.getString("cantidadScaneadoKey");
            strPrecioProductoVendido = bundle.getString("precioScaneadoKey");
            strCodigoProductoScaneado = bundle.getString("codigoScaneadoKey");
            ok.setText("ACTUALIZAR");
            flashPoint();

        }
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(lectorDeCodigoDeBarrasYQR.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector - CDP");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

                copiarPortapapeles();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"Scanner cancelado",Toast.LENGTH_SHORT).show();
            }else{
                scnaeo.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void copiarPortapapeles(){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("EditText",scnaeo.getText().toString().trim());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this,"Codigo Copiado",Toast.LENGTH_SHORT).show();
    }

    public void volverPrincipalScanner(View view){
        Intent volver = new Intent(this, principal.class);
        startActivity(volver);
    }

    private void obtenerDatos(){
        Date fechaHoraActual = new Date();
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String nombre = nombreProductoScaneado.getText().toString().trim();
        String cantidad = cantidadProductoScanneado.getText().toString().trim();
        String precio = precioProductoScaneado.getText().toString().trim();
        String escaneo = etResultadoScaneo.getText().toString().trim();
        String fecha = formatoFechaHora.format(fechaHoraActual);
        Intent intent = new Intent(this, principal.class);

        if (!nombre.isEmpty() && !precio.isEmpty() && !cantidad.isEmpty()) {

            DBpeticiones dBpeticiones = new DBpeticiones();
            if(cambioAModificacion){
                cambioAModificacion = false;
                dBpeticiones.UpdateProductoScanneado(strIdProductoEscaneado,nombre,precio,cantidad,escaneo,fecha,this);
                startActivity(intent);
            }else{
                dBpeticiones.insertProductosScanneados(nombre,precio,cantidad,escaneo,fecha,this);
                startActivity(intent);
            }
        } else {
            Toast.makeText(lectorDeCodigoDeBarrasYQR.this, "No se ingreso", Toast.LENGTH_SHORT).show();
        }
    }

    private void flashPoint(){
        nombreProductoScaneado.setText(strNombreProductoEscaneado);
        cantidadProductoScanneado.setText(strCantidadProductoScanneado);
        precioProductoScaneado.setText(strPrecioProductoVendido);
        etResultadoScaneo.setText(strCodigoProductoScaneado);
    }
}