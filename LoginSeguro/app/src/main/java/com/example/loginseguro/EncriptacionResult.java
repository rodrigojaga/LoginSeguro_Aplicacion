package com.example.loginseguro;

public class EncriptacionResult {

    private byte[] iv;
    private byte[] datosEncriptados;

    public EncriptacionResult(byte[] iv, byte[] datosEncriptados) {
        this.iv = iv;
        this.datosEncriptados = datosEncriptados;
    }

    public byte[] getIv() {
        return iv;
    }

    public byte[] getDatosEncriptados() {
        return datosEncriptados;
    }

}
