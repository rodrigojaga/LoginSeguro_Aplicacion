package com.example.loginseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ventasDB extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "venta.db";

    public static final String TableName = "ventas";
    public static final String ColumnIDVenta = "id";
    public static final String ColumnVentaProductoConcatID = "productoVendido";
    public static final String ColumnVentaTotal = "totalVenta";
    public static final String ColumnVentaCantidadProducto = "cantidadVendido";
    public static final String ColumnVentaFecha = "fechaDeLaVenta";
    public static final String ColumnVentaImagen = "imagenVenta";
    public static final String ColumnIDProductoVendido = "IDProductoVendido";
    public static final String ColumnUsuarioVenta = "UsuarioAlQueSeLeVendio";

    public ventasDB(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table "+TableName+"("
                +ColumnIDVenta+" INTEGER Primary Key Autoincrement,"
                +ColumnVentaProductoConcatID+" TEXT,"
                +ColumnVentaTotal+" TEXT,"
                +ColumnVentaCantidadProducto+" TEXT,"
                +ColumnVentaFecha+" TEXT,"
                +ColumnIDProductoVendido+" TEXT,"
                +ColumnUsuarioVenta+" TEXT,"
                +ColumnVentaImagen+" TEXT) ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }

}
