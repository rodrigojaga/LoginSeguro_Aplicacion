package com.example.loginseguro;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class adaptadorRecyclerListarProductos  extends RecyclerView.Adapter<viewHolder> {

    List<datosProducto> ListaObjeto;


    public adaptadorRecyclerListarProductos(List<datosProducto> listaObjeto){
        this.ListaObjeto = listaObjeto;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista;
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new viewHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        holder.ivImagenDelProductoCardView.setImageBitmap(ListaObjeto.get(position).getImagen());
        holder.tvNombreProductoCardView.setText(ListaObjeto.get(position).getNombreProducto());
        holder.tvIdProductoCardView.setText(ListaObjeto.get(position).getIdProducto());
        holder.tvPrecioProductoCardView.setText(ListaObjeto.get(position).getPrecio());
        holder.tvStockProductoCardView.setText(ListaObjeto.get(position).getCantidad());

        holder.modificarProductoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, nuevoProducto.class);
                String idProducto = holder.tvIdProductoCardView.getText().toString().trim();
                String nombreProducto = holder.tvNombreProductoCardView.getText().toString().trim();
                String stockProducto = holder.tvStockProductoCardView.getText().toString().trim();
                String precioProducto = holder.tvPrecioProductoCardView.getText().toString().trim();
                String imgProducto = bitmapToBase64(((BitmapDrawable) holder.ivImagenDelProductoCardView.getDrawable()).getBitmap());
                intent.putExtra("Message_Boolean_key",true);
                intent.putExtra("Message_idProducto_key",idProducto);
                intent.putExtra("Message_nombreProducto_key",nombreProducto);
                intent.putExtra("Message_stockProducto_key",stockProducto);
                intent.putExtra("Message_precioProducto_key",precioProducto);
                intent.putExtra("Message_imgProducto_key",imgProducto);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }



    public void removeItem(int position, Context context){
        String a = ListaObjeto.get(position).idProducto;
        ListaObjeto.remove(position);
        notifyItemRemoved(position);
        //agregar eliminacion de base de datosDA
        DBpeticiones dbp = new DBpeticiones();
        dbp.deleteProduct(Integer.parseInt(a),context);

        Log.d("idComprobacion",position+"");
        Log.d("idComprobacion",a);

    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }






}
