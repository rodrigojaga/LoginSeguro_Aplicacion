package com.example.loginseguro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "login.db";

    public static final String TableName = "usuario";
    public static final String ColumnUsu = "usuarioNombre";
    public static final String ColumnUsuIV = "usuarioIV";
    public static final String ColumnPass = "usuarioContrasena";
    public static final String ColumnPassIV = "usuarioContrasenaIV";

    public DatabaseHelper(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table "+TableName+"("
                +ColumnUsu+" BLOB,"
                +ColumnUsuIV+" BLOB,"
                +ColumnPass+" BLOB,"
                +ColumnPassIV+" BLOB) ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
}
