package com.example.loginseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class productosScanneadosDB extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "productoScanneado.db";

    public static final String TableName = "productosScanneados";
    public static final String ColumnIDScanneado = "id";
    public static final String ColumnProductoScanneado = "productoScanneadoNombre";
    public static final String ColumnPrecioProductoScanneado = "precioProductoScaneado";
    public static final String ColumnCantidadProductoScanneado = "cantidadProductoScanneado";
    public static final String ColumnCodugoDeBarrasProductoScanneado = "CodigoDeBarrasDelProducto";
    public static final String ColumnFechaScanneado = "fechaScanneo";

    public productosScanneadosDB(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table "+TableName+"("
                +ColumnIDScanneado+" INTEGER Primary Key Autoincrement,"
                +ColumnProductoScanneado+" TEXT,"
                +ColumnPrecioProductoScanneado+" TEXT,"
                +ColumnCodugoDeBarrasProductoScanneado+" TEXT,"
                +ColumnFechaScanneado+" TEXT,"
                +ColumnCantidadProductoScanneado+" TEXT) ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
}
