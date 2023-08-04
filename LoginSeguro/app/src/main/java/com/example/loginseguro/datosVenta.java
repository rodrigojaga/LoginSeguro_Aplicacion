package com.example.loginseguro;

import android.graphics.Bitmap;

public class datosVenta extends datosProducto{
    private String fecha;
    private String usuario;

    public datosVenta(String idProducto, String nombreProducto, String cantidad, String precio, Bitmap imagen, String fecha,String usuario) {
        super(idProducto, nombreProducto, cantidad, precio, imagen);
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
