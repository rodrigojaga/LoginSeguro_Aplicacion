package com.example.loginseguro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorRecyclerProductosScanneados extends RecyclerView.Adapter<viewHolderProductosScanneados> {

    List<datosProductosScanneados> listaObjetoProductosScanneados;

    public adaptadorRecyclerProductosScanneados(List<datosProductosScanneados> listaObjetoProductosScanneados) {
        this.listaObjetoProductosScanneados = listaObjetoProductosScanneados;
    }

    @NonNull
    @Override
    public viewHolderProductosScanneados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_producto_scanneado, parent, false);
        return new viewHolderProductosScanneados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderProductosScanneados holder, int position) {
        holder.tvIdProductoScanneadoCardView.setText(listaObjetoProductosScanneados.get(position).getIdProductoScanneado());
        holder.tvNombrePorductoScanneadoCardView.setText(listaObjetoProductosScanneados.get(position).getNombreProductoScanneado());
        holder.tvCantidadProductoScanneadoCardView.setText(listaObjetoProductosScanneados.get(position).getCantidadScanneado());
        holder.tvPrecioProductoScanneadoCardView.setText(listaObjetoProductosScanneados.get(position).getPrecioScanneado());
        holder.tvCodigoScanneadoCardView.setText(listaObjetoProductosScanneados.get(position).getCodigoScanneado());
        holder.tvFechaScanneoCardView.setText(listaObjetoProductosScanneados.get(position).getFecha());

        holder.btnModificarProductoScanneadoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, lectorDeCodigoDeBarrasYQR.class);
                String idProductoScanneado = holder.tvIdProductoScanneadoCardView.getText().toString().trim();
                String nombreProductoScanneado = holder.tvNombrePorductoScanneadoCardView.getText().toString().trim();
                String cantidadProductoScanneado = holder.tvCantidadProductoScanneadoCardView.getText().toString().trim();
                String precioProductoScanneado = holder.tvPrecioProductoScanneadoCardView.getText().toString().trim();
                String codigoProductoScanneado = holder.tvCodigoScanneadoCardView.getText().toString().trim();
                intent.putExtra("BooleanKeyScaneado",true);
                intent.putExtra("isScaneadoKey",idProductoScanneado);
                intent.putExtra("nombreScaneadoKey",nombreProductoScanneado);
                intent.putExtra("cantidadScaneadoKey",cantidadProductoScanneado);
                intent.putExtra("precioScaneadoKey",precioProductoScanneado);
                intent.putExtra("codigoScaneadoKey",codigoProductoScanneado);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaObjetoProductosScanneados.size();
    }

    public void removeItem(int position, Context context){
        String a = listaObjetoProductosScanneados.get(position).getIdProductoScanneado();
        listaObjetoProductosScanneados.remove(position);
        notifyItemRemoved(position);
        //agregar eliminacion de base de datosDA
        DBpeticiones dbp = new DBpeticiones();
        dbp.deleteProductoScanneado(Integer.parseInt(a),context);


    }
}
