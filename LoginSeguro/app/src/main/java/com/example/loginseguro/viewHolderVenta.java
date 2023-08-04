package com.example.loginseguro;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolderVenta extends RecyclerView.ViewHolder {

    ImageView ivImagenDeLaVentaCardView;

    TextView tvIdVentaCardView, tvNombreVentaCardView, tvPrecioVentaCardView, tvStockVentaCardView,tvFechaVenta,tvUsuarioVendido;

    CardView cvTodoContenedorVenta;

    RelativeLayout layoutABorrar;

    Button borrarRelativeLayout, modificarVentaCv;


    public viewHolderVenta(@NonNull View itemView) {
        super(itemView);
        ivImagenDeLaVentaCardView = itemView.findViewById(R.id.imagenVentaCv);
        tvIdVentaCardView = itemView.findViewById(R.id.idVentaCv);
        tvNombreVentaCardView = itemView.findViewById(R.id.nombreVentaCv);
        tvPrecioVentaCardView = itemView.findViewById(R.id.precioVentaCv);
        tvStockVentaCardView = itemView.findViewById(R.id.stockVentaCv);
        tvFechaVenta = itemView.findViewById(R.id.tvFechaVentaCv);
        cvTodoContenedorVenta = itemView.findViewById(R.id.card_viewVenta);
        layoutABorrar = itemView.findViewById(R.id.layoutABorrar);
        modificarVentaCv = itemView.findViewById(R.id.btnModificarVenta);
        tvUsuarioVendido = itemView.findViewById(R.id.tvUsuarioVendido);

    }
}
