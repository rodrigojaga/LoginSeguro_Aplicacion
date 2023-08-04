package com.example.loginseguro;

public class datosProductosScanneados {
    private String idProductoScanneado;
    private String nombreProductoScanneado;
    private String cantidadScanneado;
    private String precioScanneado;
    private String codigoScanneado;
    private String Fecha;



    public datosProductosScanneados(String idProductoScanneado, String nombreProductoScanneado, String cantidadScanneado, String precioScanneado, String codigoScanneado, String fecha) {
        this.idProductoScanneado = idProductoScanneado;
        this.nombreProductoScanneado = nombreProductoScanneado;
        this.cantidadScanneado = cantidadScanneado;
        this.precioScanneado = precioScanneado;
        this.codigoScanneado = codigoScanneado;
        this.Fecha = fecha;
    }

    public String getIdProductoScanneado() {
        return idProductoScanneado;
    }

    public void setIdProductoScanneado(String idProductoScanneado) {
        this.idProductoScanneado = idProductoScanneado;
    }

    public String getNombreProductoScanneado() {
        return nombreProductoScanneado;
    }

    public void setNombreProductoScanneado(String nombreProductoScanneado) {
        this.nombreProductoScanneado = nombreProductoScanneado;
    }

    public String getCantidadScanneado() {
        return cantidadScanneado;
    }

    public void setCantidadScanneado(String cantidadScanneado) {
        this.cantidadScanneado = cantidadScanneado;
    }

    public String getPrecioScanneado() {
        return precioScanneado;
    }

    public void setPrecioScanneado(String precioScanneado) {
        this.precioScanneado = precioScanneado;
    }

    public String getCodigoScanneado() {
        return codigoScanneado;
    }

    public void setCodigoScanneado(String codigoScanneado) {
        this.codigoScanneado = codigoScanneado;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
