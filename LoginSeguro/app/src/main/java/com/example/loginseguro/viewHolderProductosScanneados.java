package com.example.loginseguro;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewHolderProductosScanneados extends RecyclerView.ViewHolder {

    TextView tvIdProductoScanneadoCardView,  tvNombrePorductoScanneadoCardView, tvCantidadProductoScanneadoCardView, tvPrecioProductoScanneadoCardView, tvFechaScanneoCardView,tvCodigoScanneadoCardView;
    Button btnModificarProductoScanneadoCardView;
    CardView cardViewContenedor;
    RelativeLayout layoutABorrarScanneado;

    public viewHolderProductosScanneados(@NonNull View itemView) {
        super(itemView);
        tvIdProductoScanneadoCardView = itemView.findViewById(R.id.idScanneadoCv);
        tvNombrePorductoScanneadoCardView = itemView.findViewById(R.id.nombreScanneadoCv);
        tvCantidadProductoScanneadoCardView = itemView.findViewById(R.id.stockScanneadoCv);
        tvPrecioProductoScanneadoCardView = itemView.findViewById(R.id.precioProductoScanneadoCv);
        tvFechaScanneoCardView = itemView.findViewById(R.id.tvFechaScanneadoCv);
        tvCodigoScanneadoCardView = itemView.findViewById(R.id.CodigoScanneado);
        btnModificarProductoScanneadoCardView = itemView.findViewById(R.id.btnModificarScanneado);
        cardViewContenedor = itemView.findViewById(R.id.card_viewProductoScanneado);
        layoutABorrarScanneado = itemView.findViewById(R.id.layoutABorrarScanneado);
    }
}
