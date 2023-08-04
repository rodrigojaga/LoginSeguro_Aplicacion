package com.example.loginseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class productosDB extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "producto.db";

    public static final String TableName = "productos";
    public static final String ColumnID = "id";
    public static final String ColumnProducto = "productoNombre";
    public static final String ColumnPrecioProducto = "precioProducto";
    public static final String ColumnCantidadProducto = "cantidad";
    public static final String ColumnImagenProducto = "imagen";

    public productosDB(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table "+TableName+"("
                +ColumnID+" INTEGER Primary Key Autoincrement,"
                +ColumnProducto+" TEXT,"
                +ColumnPrecioProducto+" TEXT,"
                +ColumnImagenProducto+" TEXT,"
                +ColumnCantidadProducto+" TEXT) ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
}
