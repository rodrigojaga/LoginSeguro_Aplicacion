package com.example.loginseguro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MainActivity extends AppCompatActivity {

    EditText usuario,contrasena;
    Button entrar,creacion;


    private static final String KEY_ALIAS ="my_key_alias12";

    private FirebaseAnalytics analytics;
    static byte[] contrasenaEnByteCom, usuarioEnByteCom;
    static byte[] usuarioDB, contrasenaDB;
    String datosEncrip,usuarioEncrip;

    static Key aesKey;

    String combinado;
    EncriptacionResult erCon,erUsu,erUsuario,erContrasena;

    static int contador;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        entrar = findViewById(R.id.button);
        creacion = findViewById(R.id.creacion);
        analytics = FirebaseAnalytics.getInstance(this);
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        try {
            aesKey = generarLave();
            Log.d("llave",aesKey.toString());
        }catch (Exception e){

        }

        creacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = contrasena.getText().toString().trim();
                String val1 = usuario.getText().toString().trim();
                User user = new User();
                boolean validada = user.validarContrsena(val);
                if(validada){
                    contrasena.setText("");
                    usuario.setText("");
                    Bundle bundle = new Bundle();
                    //lo que se vera en Firebase -
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"creacion");
                    //nombre
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"Creacion de usuario");
                    //tipo de contenido del elemento seleccionado
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"text");
                    analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                    try {

                        datosEncrip = sha256(val);
                        usuarioEncrip = sha256(val1);

                        byte[] datosEncriptarBytes = datosEncrip.getBytes(StandardCharsets.UTF_8);
                        byte[] usuEncriptarBytes = usuarioEncrip.getBytes(StandardCharsets.UTF_8);

                        erCon = encriptarDatos(aesKey,datosEncriptarBytes);
                        erUsu = encriptarDatos(aesKey,usuEncriptarBytes);

                        inserttask(erUsu.getDatosEncriptados(), erUsu.getIv(), erCon.getDatosEncriptados(), erCon.getIv());

                        Toast.makeText(MainActivity.this,"Info encriptada",Toast.LENGTH_SHORT).show();
                    } catch (Exception ignored) {
                        Log.d("cosa",ignored.toString());
                    }

                }else{
                    Toast.makeText(MainActivity.this,"La contraseña debe contener Mayusculas, minusculas, Numeros, Caracteres especiales",Toast.LENGTH_SHORT).show();

                }
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {

                //buscar los datos mediante emparejado
                combinado = usuario.getText().toString().trim() + contrasena.getText().toString().trim();
                readUser();



                String aux = sha256(String.valueOf(usuario.getText()));
                String aux1 = sha256(String.valueOf(contrasena.getText()));
                String a = "";
                String b = "";
                String d = "";
                String c = "";
                try {
                    usuarioDB = desencriptarDatos(aesKey,erUsuario);
                    contrasenaDB = desencriptarDatos(aesKey,erContrasena);
                    usuarioEnByteCom = aux.getBytes(StandardCharsets.UTF_8);
                    contrasenaEnByteCom = aux1.getBytes(StandardCharsets.UTF_8);
                    a = new String(contrasenaDB, java.nio.charset.StandardCharsets.UTF_8);
                    b = new String(contrasenaEnByteCom, java.nio.charset.StandardCharsets.UTF_8);
                    c = new String(usuarioDB, java.nio.charset.StandardCharsets.UTF_8);
                    d = new String(usuarioEnByteCom, java.nio.charset.StandardCharsets.UTF_8);



                } catch (Exception e) {
                    Log.d("descrip", e.toString());
                }

                if (b.equals(a) && d.equals(c)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"BotonEntrada");
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"Entrada de usuario");
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"boolean");
                    analytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                    Toast.makeText(MainActivity.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                    siguiente(v);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    contador++;
                    if (contador == 4) {
                        Toast.makeText(MainActivity.this, "Fin de la sesion", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }catch (Exception e){
                Log.d("defini",e.toString());
            }
            }
        });



    }



    public Key generarLave(){
        try{
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if(!keyStore.containsAlias(KEY_ALIAS)){
                KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT|KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setRandomizedEncryptionRequired(true)
                        .build();

                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
                keyGenerator.init(keyGenParameterSpec);
                Toast.makeText(MainActivity.this,"La llave se genero",Toast.LENGTH_SHORT).show();
                return keyGenerator.generateKey();
            }else{



                return keyStore.getKey(KEY_ALIAS,null);


            }
        }catch(Exception e){
            Log.d("mandarLLave",e.toString());
        }
        return null;
    }


    // Encriptar
    public EncriptacionResult encriptarDatos(Key clave, byte[] datos) throws Exception{

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);


            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            SecretKey secretKey = secretKeyEntry.getSecretKey();


            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] datosEncriptados = cipher.doFinal(datos);

            byte[] iv = cipher.getIV();

            return new EncriptacionResult(iv, datosEncriptados);

    }




    // Desencriptar
    public byte[] desencriptarDatos(Key clave, EncriptacionResult encriptacionResult) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(encriptacionResult.getIv());
            cipher.init(Cipher.DECRYPT_MODE, clave, ivParameterSpec);
            return cipher.doFinal(encriptacionResult.getDatosEncriptados());
        }catch (Exception e){
            Log.d("desen",e.toString());
        }
        return null;
    }

    public String sha256(String contrasena){
        try{
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] digest = sha.digest(contrasena.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b : digest){
                hexString.append(String.format("%02x",b));
            }
            return hexString.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException();
        }
    }

    private void inserttask(byte[] usuarioE,byte[] usuarioIV, byte[] contrasena,byte[] contrasenaIV){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ColumnUsu,usuarioE);
        values.put(DatabaseHelper.ColumnUsuIV,usuarioIV);
        values.put(DatabaseHelper.ColumnPass,contrasena);
        values.put(DatabaseHelper.ColumnPassIV,contrasenaIV);
        database.insert(DatabaseHelper.TableName,null,values);
    }

    private void readUser(){
        try {
            String[] projection = {DatabaseHelper.ColumnUsu, DatabaseHelper.ColumnUsuIV, DatabaseHelper.ColumnPass, DatabaseHelper.ColumnPassIV};

            Cursor cursor = database.query(DatabaseHelper.TableName, projection, null, null, null, null, null);

            String name = "";
            if (cursor.moveToFirst()) {
                do {
                    byte[] usuarioDB = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.ColumnUsu));
                    byte[] usuarioIVDB = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.ColumnUsuIV));
                    byte[] contrasenaDB = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.ColumnPass));
                    byte[] contrasenaIVDB = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.ColumnPassIV));

                    erUsuario = new EncriptacionResult(usuarioIVDB, usuarioDB);
                    erContrasena = new EncriptacionResult(contrasenaIVDB, contrasenaDB);


                } while (cursor.moveToNext());

            }

            cursor.close();
        }catch(Exception e){
            Log.d("DB", e.toString());
        }
    }

    public void siguiente(View view){
        Intent siguiente = new Intent(this, principal.class);
        startActivity(siguiente);
    }

}