package com.example.loginseguro;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.ByteArrayOutputStream;

public class nuevaVenta extends AppCompatActivity {

    //Variables para el cambio de modo de creacion al modo de Modificacion de producto
    private boolean cambioAModificacion;
    private String strIdVenta, strIdProductoVendido, strNombreProductoVendido,strTotalVenta,strCantidadVenta, strFecha,strUsuario,strImagenHD;

    private EditText idProducto, nombreProducto, cantidadProductos, totalProductos, nombreComprador;
    private ImageView FotoVenta;
    private Button cancelar, agregarVenta,tomarFoto;
    private TextView tvEncabezadoVenta;

    //constantes necesarias para el funcionamiento de permisos de la camara del dispositivo
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int TAKE_PICTURE = 101;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 200;

    //Bitmap que sirve en la toma de fotografias
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_venta);
        cancelar = findViewById(R.id.cancelarVolver);
        agregarVenta = findViewById(R.id.agregarVentaN);
        idProducto = findViewById(R.id.idProductoVen);
        nombreProducto = findViewById(R.id.etNombreProductoVen);
        cantidadProductos = findViewById(R.id.etCantidadProductoVen);
        totalProductos = findViewById(R.id.etPrecioProductoVen);
        nombreComprador = findViewById(R.id.usuarioVentaCampo);
        FotoVenta = findViewById(R.id.imagenVentaN);
        tomarFoto = findViewById(R.id.btnFotoTomarVenta);
        tvEncabezadoVenta = findViewById(R.id.tvEncabezadoVenta);
        cambioAModificacion = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cambioAModificacion = bundle.getBoolean("Booleano_llave");
            strIdVenta = bundle.getString("idVenta_Key");
            strIdProductoVendido = bundle.getString("idProductoVendido_key");
            strNombreProductoVendido = bundle.getString("nombreProductoVendido_key");
            strTotalVenta = bundle.getString("totalVenta_key");
            strCantidadVenta = bundle.getString("cantidadTotal_key");
            strFecha = bundle.getString("fecha_key");
            strUsuario = bundle.getString("usuario_key");
            strImagenHD = bundle.getString("imagenHD_key");

        }

        if (cambioAModificacion) {

            agregarVenta.setText("Actualizar");
            tvEncabezadoVenta.setText("Actualizar Venta");
            flashPoint();
        }
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionCamera();
            }
        });

        agregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatosVenta();
            }
        });
    }

    //Inicio procesos relacionados con la camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == TAKE_PICTURE){
            if(resultCode == Activity.RESULT_OK && data != null){

                bitmap = (Bitmap) data.getExtras().get("data");

                FotoVenta.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA_PERMISSION){
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePicture();
            }
        }else if(requestCode == REQUEST_PERMISSION_WRITE_STORAGE){
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermissionCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                takePicture();
            }else{
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION
                );
            }
        }else{
            takePicture();
        }
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,TAKE_PICTURE);
    }
//Fin de procesos relacionados con la camara

    //Obtener datos de los campos
    private void obtenerDatosVenta(){
        Date fechaHoraActual = new Date();
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        String idProductoVendido = idProducto.getText().toString().trim();
        String nombreProductoVendido = nombreProducto.getText().toString().trim();
        String cantidadProductosVendidos = cantidadProductos.getText().toString().trim();
        String montoTotal = totalProductos.getText().toString().trim();
        String nombreCliente = nombreComprador.getText().toString().trim();
        String fechaHoraActualString = formatoFechaHora.format(fechaHoraActual);

        Intent intent = new Intent(this, listarVentasRecycler.class);
        if (!nombreCliente.isEmpty() && !nombreProductoVendido.isEmpty() && !montoTotal.isEmpty()) {
            Drawable drawable1 = FotoVenta.getDrawable();
            Bitmap bm = ((BitmapDrawable) drawable1).getBitmap();
            String imgN = bitmapToBase64(bm);
            DBpeticiones dBpeticiones = new DBpeticiones();
            if(cambioAModificacion){
                cambioAModificacion = false;
                dBpeticiones.UpdateVenta(strIdVenta,this,imgN,nombreProductoVendido,montoTotal,cantidadProductosVendidos,fechaHoraActualString,nombreCliente,idProductoVendido);
                startActivity(intent);
            }else{
                dBpeticiones.insertVentas(this,imgN,nombreProductoVendido,montoTotal,cantidadProductosVendidos,fechaHoraActualString,nombreCliente,idProductoVendido);
                startActivity(intent);
            }
        } else {
            Toast.makeText(nuevaVenta.this, "No se ingreso", Toast.LENGTH_SHORT).show();
        }


    }

    private void flashPoint(){
        idProducto.setText(strIdProductoVendido);
        nombreProducto.setText(strNombreProductoVendido);
        cantidadProductos.setText(strCantidadVenta);
        totalProductos.setText(strTotalVenta);
        nombreComprador.setText(strUsuario);
        FotoVenta.setImageBitmap(base64ToBitmap(strImagenHD));
    }




    //Convertir un Bitmap a String
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //Convertir String a Bitmap
    private Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }



}