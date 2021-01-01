package com.edusoft.dam.cafesito.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.model.Cafetero;

import java.util.ArrayList;

/**
 *
 */
public class CafeteroRecyclerAdapter extends RecyclerView.Adapter<CafeteroRecyclerAdapter.ViewHolder> {

    private ArrayList<Cafetero> mCafeteros;
    private OnCafeteroListener mOnCafeteroListener;

    public CafeteroRecyclerAdapter(ArrayList<Cafetero> cafeteros, OnCafeteroListener onCafeteroListener) {
        mCafeteros = new ArrayList<>();
        mCafeteros = cafeteros;

        mOnCafeteroListener = onCafeteroListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //genera un ViewHolder por cada item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cafetero_list_item , parent, false);
        return new ViewHolder(view,mOnCafeteroListener); //recibe interfaz
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //rellena los datos de cada widget con el array
        holder.nombreCafetero.setText(mCafeteros.get(position).getNombreCompleto());
        holder.numCafe.setText(mCafeteros.get(position).getNumCafe().toString()); //DARÁ ALGUN PROBLEMA POR SER UN INTEGER?

    }

    @Override
    public int getItemCount() {
        return mCafeteros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nombreCafetero;
        private TextView numCafe;
        private OnCafeteroListener onCafeteroListener;

        public ViewHolder(@NonNull View itemView, OnCafeteroListener onCafeteroListener) {
            super(itemView);

            nombreCafetero = itemView.findViewById(R.id.cafeteros_nombre); //fuera de un Activity se necesita un objeto View para invocar findViewById
            numCafe = itemView.findViewById(R.id.cafeteros_numero_cafe);

            itemView.setOnClickListener(this);
            this.onCafeteroListener = onCafeteroListener;
        }

        @Override
        public void onClick(View v) {
            onCafeteroListener.onCafeteroClick(getAdapterPosition());
        }
    }

    public interface OnCafeteroListener{
        void onCafeteroClick(Integer position);
    }

}
