package com.example.loginseguro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBpeticiones {

    private productosDB ProductosDB;
    private ventasDB VentasDB;
    private productosScanneadosDB ProductosScanneadosDB;
    private SQLiteDatabase database;

    List<datosProducto> productos;


    public void insertProductos(String nombreProducto, String precioProducto,String cantidad, String imagenProducto,Context context){

        ProductosDB = new productosDB(context);
        database = ProductosDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(productosDB.ColumnProducto,nombreProducto);
        values.put(productosDB.ColumnPrecioProducto, precioProducto);
        values.put(productosDB.ColumnImagenProducto, imagenProducto);
        values.put(productosDB.ColumnCantidadProducto,cantidad);

        database.insert(productosDB.TableName,null,values);
        Toast.makeText(context,"Nuevo producto ingresado",Toast.LENGTH_SHORT).show();

    }

    public ArrayList<datosProducto> readProducto(Context context){
        ProductosDB = new productosDB(context);
        database = ProductosDB.getWritableDatabase();
        ArrayList<datosProducto> productos= new ArrayList<datosProducto>();
        String[] projection = {productosDB.ColumnID,productosDB.ColumnProducto,productosDB.ColumnImagenProducto,productosDB.ColumnPrecioProducto,productosDB.ColumnCantidadProducto};

        Cursor cursor = database.query(productosDB.TableName, projection,null,null,null,null,null);
        datosProducto dp;

        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(productosDB.ColumnID));
                String nombreProducto = cursor.getString(cursor.getColumnIndex(productosDB.ColumnProducto));
                String imagen = cursor.getString(cursor.getColumnIndex(productosDB.ColumnImagenProducto));
                String precio = cursor.getString(cursor.getColumnIndex(productosDB.ColumnPrecioProducto));
                String stock = cursor.getString(cursor.getColumnIndex(productosDB.ColumnCantidadProducto));
                Bitmap envio = base64ToBitmap(imagen);
                dp = new datosProducto(id,nombreProducto,stock,precio,envio);
                productos.add(dp);


            }while(cursor.moveToNext());
            return productos;
        }

        cursor.close();
        return null;
    }

    public void deleteProduct(int id,Context context){
        ProductosDB = new productosDB(context);
        database = ProductosDB.getWritableDatabase();

        String selection =  productosDB.ColumnID +" = ?";
        String[] selectionArguments = {String.valueOf(id)};
        database.delete(productosDB.TableName,selection,selectionArguments);
    }

    public void UpdateTask(String idProducto,String nombreProducto, String precioProducto,String cantidad, String imagenProducto,Context context){
        ProductosDB = new productosDB(context);
        database = ProductosDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(productosDB.ColumnProducto,nombreProducto);
        values.put(productosDB.ColumnPrecioProducto, precioProducto);
        values.put(productosDB.ColumnImagenProducto, imagenProducto);
        values.put(productosDB.ColumnCantidadProducto,cantidad);
        String selection = productosDB.ColumnID+"=?";
        String[] selectionArgs = {idProducto};
        database.update(ProductosDB.TableName, values, selection,selectionArgs);
        Toast.makeText(context,"Producto actualizado",Toast.LENGTH_SHORT).show();
    }


    //------------------------------------------------Ventas-------------------------------------
    public ArrayList<datosVenta> readVentas(Context context){
        VentasDB = new ventasDB(context);
        database = VentasDB.getWritableDatabase();
        ArrayList<datosVenta> ventas= new ArrayList<datosVenta>();
        String[] projection = {ventasDB.ColumnIDVenta,ventasDB.ColumnVentaProductoConcatID,
                ventasDB.ColumnVentaTotal,ventasDB.ColumnVentaCantidadProducto,
                ventasDB.ColumnVentaFecha,ventasDB.ColumnIDProductoVendido,
                ventasDB.ColumnUsuarioVenta,ventasDB.ColumnVentaImagen
        };
        Cursor cursor = database.query(ventasDB.TableName, projection,null,null,null,null,null);
        datosVenta dv;
        if(cursor.moveToFirst()){
            do{
                String idVenta = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnIDVenta));
                String ProductoNombre = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnVentaProductoConcatID));
                String productoID = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnIDProductoVendido));
                String imagen = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnVentaImagen));
                String total = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnVentaTotal));
                String catidadArticulos = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnVentaCantidadProducto));
                String fecha = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnVentaFecha));
                String usuario = cursor.getString(cursor.getColumnIndex(ventasDB.ColumnUsuarioVenta));
                Bitmap envio = base64ToBitmap(imagen);
                String concatenacion = productoID+" - "+ProductoNombre;
                dv = new datosVenta(idVenta,concatenacion,catidadArticulos,total,envio,fecha,usuario);
                ventas.add(dv);
            }while(cursor.moveToNext());
            return ventas;
        }

        cursor.close();
        return null;
    }

    public void insertVentas(Context context,String imagen, String Nombreventa,String total,String cantProducto,String fecha,String usuario,String idProducto){

        VentasDB = new ventasDB(context);
        database = VentasDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ventasDB.ColumnVentaImagen,imagen);
        values.put(ventasDB.ColumnVentaProductoConcatID, Nombreventa);
        values.put(ventasDB.ColumnIDProductoVendido, idProducto);
        values.put(ventasDB.ColumnVentaTotal,total);
        values.put(ventasDB.ColumnVentaCantidadProducto,cantProducto);
        values.put(ventasDB.ColumnVentaFecha,fecha);
        values.put(ventasDB.ColumnUsuarioVenta,usuario);

        database.insert(ventasDB.TableName,null,values);
        Toast.makeText(context,"Nueva venta ingresada",Toast.LENGTH_SHORT).show();

    }

    public void UpdateVenta(String idVenta,Context context,String imagen, String Nombreventa,String total,String cantProducto,String fecha,String usuario,String idProducto){
        VentasDB = new ventasDB(context);
        database = VentasDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ventasDB.ColumnVentaImagen,imagen);
        values.put(ventasDB.ColumnVentaProductoConcatID, Nombreventa);
        values.put(ventasDB.ColumnIDProductoVendido, idProducto);
        values.put(ventasDB.ColumnVentaTotal,total);
        values.put(ventasDB.ColumnVentaCantidadProducto,cantProducto);
        values.put(ventasDB.ColumnVentaFecha,fecha);
        values.put(ventasDB.ColumnUsuarioVenta,usuario);
        String selection = ventasDB.ColumnIDVenta+"=?";
        String[] selectionArgs = {idVenta};
        database.update(VentasDB.TableName, values, selection,selectionArgs);
        Toast.makeText(context,"Venta actualizada",Toast.LENGTH_SHORT).show();
    }

    public void deleteVentas(int id,Context context){
        VentasDB = new ventasDB(context);
        database = VentasDB.getWritableDatabase();

        String selection =  ventasDB.ColumnIDVenta +" = ?";
        String[] selectionArguments = {String.valueOf(id)};
        database.delete(ventasDB.TableName,selection,selectionArguments);
    }

    //------------------------------------------------Productos Scanneados-------------------------------------

    public void insertProductosScanneados(String nombreProductoScanneado, String precioProductoScanneado,String cantidadScanneado, String codigoProductoScanneado,String fechaScanneo,Context context){

        ProductosScanneadosDB = new productosScanneadosDB(context);
        database = ProductosScanneadosDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(productosScanneadosDB.ColumnProductoScanneado,nombreProductoScanneado);
        values.put(productosScanneadosDB.ColumnPrecioProductoScanneado, precioProductoScanneado);
        values.put(productosScanneadosDB.ColumnCodugoDeBarrasProductoScanneado, codigoProductoScanneado);
        values.put(productosScanneadosDB.ColumnFechaScanneado, fechaScanneo);
        values.put(productosScanneadosDB.ColumnCantidadProductoScanneado,cantidadScanneado);

        database.insert(productosScanneadosDB.TableName,null,values);
        Toast.makeText(context,"Nuevo producto Scanneado",Toast.LENGTH_SHORT).show();

    }

    public ArrayList<datosProductosScanneados> readProductoScanneado(Context context){
        ProductosScanneadosDB = new productosScanneadosDB(context);
        database = ProductosScanneadosDB.getWritableDatabase();
        ArrayList<datosProductosScanneados> productosScanneados= new ArrayList<datosProductosScanneados>();
        String[] projection = {productosScanneadosDB.ColumnIDScanneado,productosScanneadosDB.ColumnProductoScanneado,
                productosScanneadosDB.ColumnCodugoDeBarrasProductoScanneado,productosScanneadosDB.ColumnCantidadProductoScanneado,
                productosScanneadosDB.ColumnPrecioProductoScanneado,productosScanneadosDB.ColumnFechaScanneado};
        Cursor cursor = database.query(productosScanneadosDB.TableName, projection,null,null,null,null,null);
        datosProductosScanneados dpS;

        if(cursor.moveToFirst()){
            do{
                String idScanneado = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnIDScanneado));
                String nombreProductoScanneado = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnProductoScanneado));
                String codigoScanneado = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnCodugoDeBarrasProductoScanneado));
                String precioScanneado = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnPrecioProductoScanneado));
                String stockScanneado = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnCantidadProductoScanneado));
                String fechaScanneo = cursor.getString(cursor.getColumnIndex(productosScanneadosDB.ColumnFechaScanneado));
                dpS = new datosProductosScanneados(idScanneado,nombreProductoScanneado,stockScanneado,precioScanneado,codigoScanneado,fechaScanneo);
                productosScanneados.add(dpS);


            }while(cursor.moveToNext());
            return productosScanneados;
        }

        cursor.close();
        return null;
    }

    public void UpdateProductoScanneado(String idProductoScanneado,String nombreProductoScanneado, String precioProductoScanneado,String cantidadScanneado, String codigoProductoScanneado,String fechaScanneado,Context context){
        ProductosScanneadosDB = new productosScanneadosDB(context);
        database = ProductosScanneadosDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(productosScanneadosDB.ColumnProductoScanneado,nombreProductoScanneado);
        values.put(productosScanneadosDB.ColumnPrecioProductoScanneado, precioProductoScanneado);
        values.put(productosScanneadosDB.ColumnCodugoDeBarrasProductoScanneado, codigoProductoScanneado);
        values.put(productosScanneadosDB.ColumnCantidadProductoScanneado,cantidadScanneado);
        values.put(productosScanneadosDB.ColumnFechaScanneado,fechaScanneado);
        String selection = productosScanneadosDB.ColumnIDScanneado+"=?";
        String[] selectionArgs = {idProductoScanneado};
        database.update(ProductosScanneadosDB.TableName, values, selection,selectionArgs);
        Toast.makeText(context,"Producto actualizado",Toast.LENGTH_SHORT).show();
    }

    public void deleteProductoScanneado(int id,Context context){
        ProductosScanneadosDB = new productosScanneadosDB(context);
        database = ProductosScanneadosDB.getWritableDatabase();

        String selection =  productosScanneadosDB.ColumnIDScanneado +" = ?";
        String[] selectionArguments = {String.valueOf(id)};
        database.delete(productosScanneadosDB.TableName,selection,selectionArguments);
    }


    private Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }



}
