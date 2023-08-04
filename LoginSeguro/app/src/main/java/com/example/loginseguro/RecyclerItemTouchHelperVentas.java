package com.example.loginseguro;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerItemTouchHelperVentas extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListenerVentas listener;

    public RecyclerItemTouchHelperVentas(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListenerVentas listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHoldera, int actionState) {
        if(viewHoldera != null){
            View fondo = ((viewHolderVenta) viewHoldera).layoutABorrar;
            getDefaultUIUtil().onSelected(fondo);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHoldera, float dX, float dY, int actionState,
                                boolean isCurrentlyActive) {
        View fondo = ((viewHolderVenta) viewHoldera).layoutABorrar;
        getDefaultUIUtil().onDrawOver(c,recyclerView,fondo,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHoldera) {
        View fondo = ((viewHolderVenta) viewHoldera).layoutABorrar;
        getDefaultUIUtil().clearView(fondo);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHoldera, float dX, float dY, int actionState,
                            boolean isCurrentlyActive) {
        View fondo = ((viewHolderVenta) viewHoldera).layoutABorrar;
        getDefaultUIUtil().onDraw(c,recyclerView,fondo,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListenerVentas{
        void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
