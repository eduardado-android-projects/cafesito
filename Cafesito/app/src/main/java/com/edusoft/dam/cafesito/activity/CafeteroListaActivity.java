package com.edusoft.dam.cafesito.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.adapter.CafeteroRecyclerAdapter;
import com.edusoft.dam.cafesito.model.Cafetero;
import com.edusoft.dam.cafesito.util.SeparaVerticalItemDecorator;

import java.util.ArrayList;

public class CafeteroListaActivity extends AppCompatActivity implements CafeteroRecyclerAdapter.OnCafeteroListener {

    private static final String TAG = "CafeteroListaActivity";

    //GUI
    private RecyclerView mRecyclerView;
    private Toolbar mListaCafeterosToolbar;

    //variables
    private ArrayList<Cafetero> mCafeteros;
    private CafeteroRecyclerAdapter mCafeteroRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetero_list);
        Log.d(TAG, "onCreate: todo correcto");

        //GUI, referencia
        mRecyclerView = findViewById(R.id.recyclerView); //dentro de un Activity hay una referencia implícita a View. Puedo usar findViewById() directamente
        mListaCafeterosToolbar = findViewById(R.id.lista_cafeteros_toolbar);

        //variables, instanciación
        mCafeteros = new ArrayList<>();
        mCafeteroRecyclerAdapter = new CafeteroRecyclerAdapter(mCafeteros,this); //ojo, recibe la interfaz que hace posible clickar en cada item

        //--
        initRecyclerView();
        insertaDemoCafeteros();
        separaItems();
        initToolBar();
    }

    private void initToolBar() {
        setSupportActionBar(mListaCafeterosToolbar);
        setTitle(R.string.titulo_toolbar_cafeteros);
    }

    private void separaItems() {
        RecyclerView.ItemDecoration itemDecoration = new SeparaVerticalItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void insertaDemoCafeteros(){
        for(int i = 0; i < 20; i++){
            Cafetero cafetero = new Cafetero();
            cafetero.setNombreCompleto("nombre" + i);
            cafetero.setMv("+34 " + i);
            cafetero.setNumCafe(i);
            cafetero.setTipoCafe("cafe con " + i);
            mCafeteros.add(cafetero);
        }
        mCafeteroRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager;

        linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mCafeteroRecyclerAdapter);
    }

    @Override
    public void onCafeteroClick(Integer position) { //AQUÍ SOLO MANDA NUEVOS CAFETEROS
        Log.d(TAG, "onCafeteroClick: clicaste en" + position);
        Intent intent = new Intent(this,CafeteroActivity.class);
        intent.putExtra("com.edusoft.dam.cafesito.cafetero_old", mCafeteros.get(position)); //usamos el paquete único de la app + nombre del objeto (buenas prácticas por si recibimos mismo objeto de otra app)
        startActivity(intent);
    }
}