package com.example.loginseguro;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.ByteArrayOutputStream;

public class nuevoProducto extends AppCompatActivity{
    //Variables para el cambio de modo de creacion al modo de Modificacion de producto
    private boolean cambioAModificacion;
    private String strIdProducto, strNombreProducto, strStockProducto,strPrecioProducto,strImagenProducto;

    //Declaracion de botones de la interfaz de usuario en la creacion y modificacion de productos
    private Button cancelar,agregar,tomarFoto;
    private EditText nombreProducto,cantidadProducto,precioProducto;
    private ImageView imagenProducto;
    private TextView tvEncabezado;

    //constantes necesarias para el funcionamiento de permisos de la camara del dispositivo
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int TAKE_PICTURE = 101;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 200;

    //Bitmap que sirve en la toma de fotografias
    private Bitmap bitmap;
    //FIREBASE
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);
        cancelar = findViewById(R.id.cancelarVolver);
        agregar = findViewById(R.id.agregarProducto);
        tomarFoto = findViewById(R.id.btnFotoTomar);
        nombreProducto = findViewById(R.id.etNombreProductoVen);
        cantidadProducto = findViewById(R.id.etCantidadProductoVen);
        precioProducto = findViewById(R.id.etPrecioProducto);
        imagenProducto = findViewById(R.id.imagenProducto);
        tvEncabezado = findViewById(R.id.tvEncabezado);
        FirebaseApp.initializeApp(this);
        analytics = FirebaseAnalytics.getInstance(this);
        cambioAModificacion = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cambioAModificacion = bundle.getBoolean("Message_Boolean_key");
            strIdProducto = bundle.getString("Message_idProducto_key");
            strNombreProducto = bundle.getString("Message_nombreProducto_key");
            strStockProducto = bundle.getString("Message_stockProducto_key");
            strPrecioProducto = bundle.getString("Message_precioProducto_key");
            strImagenProducto = bundle.getString("Message_imgProducto_key");
        }

        if (cambioAModificacion) {

            agregar.setText("Actualizar");
            tvEncabezado.setText("Actualizar Tarea");
            showPast();
        }

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionCamera();
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();
            }
        });

    }
//Inicio procesos relacionados con la camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == TAKE_PICTURE){
            if(resultCode == Activity.RESULT_OK && data != null){

                bitmap = (Bitmap) data.getExtras().get("data");

                imagenProducto.setImageBitmap(bitmap);
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


//convertir un String a Bitmap
    private Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

//Convertir un Bitmap a String
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


//Obtener datos de los campos
    public void obtenerDatos(){

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"btnAgregar");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"Actualizacion o creacion de producto");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"Objeto Producto");
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);

        String nombreProductoString = nombreProducto.getText().toString().trim();
        String cantidadProductoString = cantidadProducto.getText().toString().trim();
        String precioProductoString = precioProducto.getText().toString().trim();
        Intent intent = new Intent(this, principal.class);

        if (!nombreProductoString.isEmpty() && !cantidadProductoString.isEmpty() && !precioProductoString.isEmpty()) {
            Drawable drawable1 = imagenProducto.getDrawable();
            Bitmap bm = ((BitmapDrawable) drawable1).getBitmap();
            String imgN = bitmapToBase64(bm);
            DBpeticiones dBpeticiones = new DBpeticiones();
            if(cambioAModificacion){
                cambioAModificacion = false;
                dBpeticiones.UpdateTask(strIdProducto,nombreProductoString,precioProductoString,cantidadProductoString,imgN,this);
                startActivity(intent);
            }else{
                dBpeticiones.insertProductos(nombreProductoString,precioProductoString,cantidadProductoString,imgN,nuevoProducto.this);
                startActivity(intent);
            }
        } else {
            Toast.makeText(nuevoProducto.this, "No se ingreso", Toast.LENGTH_SHORT).show();
        }


    }

    private void showPast(){

        nombreProducto.setText(strNombreProducto);
        cantidadProducto.setText(strStockProducto);
        precioProducto.setText(strPrecioProducto);
        Bitmap imgTemp = base64ToBitmap(strImagenProducto);
        imagenProducto.setImageBitmap(imgTemp);
        //imgGeneral = imgTemp;

    }
}