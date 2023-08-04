package com.example.loginseguro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adaptadorRecyclerListarVentas extends RecyclerView.Adapter<viewHolderVenta> {

    List<datosVenta> listaObjetoVentas;

    public adaptadorRecyclerListarVentas(List<datosVenta> listaObjetoVentas) {
        this.listaObjetoVentas = listaObjetoVentas;
    }

    @NonNull
    @Override
    public viewHolderVenta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_ventas, parent, false);
        return new viewHolderVenta(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderVenta holder, int position) {
        holder.ivImagenDeLaVentaCardView.setImageBitmap(listaObjetoVentas.get(position).getImagen());
        holder.tvIdVentaCardView.setText(listaObjetoVentas.get(position).getIdProducto());
        holder.tvNombreVentaCardView.setText(listaObjetoVentas.get(position).getNombreProducto());
        holder.tvPrecioVentaCardView.setText(listaObjetoVentas.get(position).getPrecio());
        holder.tvStockVentaCardView.setText(listaObjetoVentas.get(position).getCantidad());
        holder.tvFechaVenta.setText(listaObjetoVentas.get(position).getFecha());
        holder.tvUsuarioVendido.setText(listaObjetoVentas.get(position).getUsuario());

        holder.modificarVentaCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,nuevaVenta.class);

                String idVenta = holder.tvIdVentaCardView.getText().toString().trim();
                String nombreVenta = holder.tvNombreVentaCardView.getText().toString().trim();
                String[] cadenaSeparada = separarCadenas(nombreVenta);
                String totalVenta = holder.tvPrecioVentaCardView.getText().toString().trim();
                String cantidadTotal = holder.tvStockVentaCardView.getText().toString().trim();
                String fecha = holder.tvFechaVenta.getText().toString().trim();
                String usuario = holder.tvUsuarioVendido.getText().toString().trim();
                String imagen = bitmapToBase64(((BitmapDrawable) holder.ivImagenDeLaVentaCardView.getDrawable()).getBitmap());
                intent.putExtra("Booleano_llave",true);
                intent.putExtra("idVenta_Key",idVenta);
                intent.putExtra("idProductoVendido_key",cadenaSeparada[0]);
                intent.putExtra("nombreProductoVendido_key",cadenaSeparada[1]);
                intent.putExtra("totalVenta_key",totalVenta);
                intent.putExtra("cantidadTotal_key",cantidadTotal);
                intent.putExtra("fecha_key",fecha);
                intent.putExtra("usuario_key",usuario);
                intent.putExtra("imagenHD_key",imagen);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return  listaObjetoVentas.size();
    }

    public void removeItem(int position, Context context){
        String a = listaObjetoVentas.get(position).idProducto;
        listaObjetoVentas.remove(position);
        notifyItemRemoved(position);
        //agregar eliminacion de base de datosDA
        DBpeticiones dbp = new DBpeticiones();
        dbp.deleteVentas(Integer.parseInt(a),context);


    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private String[] separarCadenas(String cadena) {
        if (cadena.contains("-")) {
            String[] cadenasSeparadas = cadena.split("-");
            cadenasSeparadas[0] = cadenasSeparadas[0].trim();
            cadenasSeparadas[1] = cadenasSeparadas[1].trim();
            return cadenasSeparadas;
        } else {
            return null;
        }
    }
}
