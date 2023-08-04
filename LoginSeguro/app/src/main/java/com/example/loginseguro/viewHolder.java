package com.example.loginseguro;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolder extends RecyclerView.ViewHolder {

    ImageView ivImagenDelProductoCardView;
    TextView tvIdProductoCardView, tvNombreProductoCardView, tvPrecioProductoCardView, tvStockProductoCardView;

    CardView cvTodoContenedor;

    RelativeLayout layoutABorrar;

    Button borrarRelativeLayout, modificarProductoCv;

    public viewHolder(@NonNull View itemView) {
        super(itemView);

        ivImagenDelProductoCardView = itemView.findViewById(R.id.imagenProductoCv);
        tvIdProductoCardView = itemView.findViewById(R.id.idProductoCv);
        tvNombreProductoCardView = itemView.findViewById(R.id.nombreProductoCv);
        tvPrecioProductoCardView = itemView.findViewById(R.id.precioProductoCv);
        tvStockProductoCardView = itemView.findViewById(R.id.stockProductoCv);
        cvTodoContenedor = itemView.findViewById(R.id.card_view);
        layoutABorrar = itemView.findViewById(R.id.layoutABorrar);
        modificarProductoCv = itemView.findViewById(R.id.btnModificarProducto);

    }
}
