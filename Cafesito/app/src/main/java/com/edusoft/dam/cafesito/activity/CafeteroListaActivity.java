package com.edusoft.dam.cafesito.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.adapter.CafeteroRecyclerAdapter;
import com.edusoft.dam.cafesito.model.Cafetero;
import com.edusoft.dam.cafesito.util.SeparaVerticalItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CafeteroListaActivity extends AppCompatActivity implements CafeteroRecyclerAdapter.OnCafeteroListener, View.OnClickListener {

    private static final String TAG = "CafeteroListaActivity";

    //experimental
    private ItemTouchHelper.SimpleCallback simpleCallback;

    //GUI
    private RecyclerView mRecyclerView;
    private Toolbar mListaCafeterosToolbar;
    private FloatingActionButton floatingActionButton;

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
        floatingActionButton = findViewById(R.id.cafetero_list_fab);
        floatingActionButton.setOnClickListener(this);

        //variables, instanciación
        mCafeteros = new ArrayList<>();
        mCafeteroRecyclerAdapter = new CafeteroRecyclerAdapter(mCafeteros,this); //ojo, recibe la interfaz que hace posible clickar en cada item

        //implementaciones ordenadas
        initRecyclerView(); //configura el recyclerivew con el adaptador personalizado
        insertaDemoCafeteros(); //para cargar datos falsos y probar la GUI
        separaItems(); //para separar los items
        initToolBar(); //para añadir un ToolBar personalizado
        initItemTouchHelper(); //para poder desplazar un ítem y borrarlo


    }

    private void initItemTouchHelper() {
        //instancia una clase abstracta y con ella hay que sobreescribir un par de métodos
         ItemTouchHelper.SimpleCallback  simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) { //hay que pasarle 0 y la direccion que queremos implementar
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                borrarCafetero(mCafeteros.get(viewHolder.getAdapterPosition())); //le pasa al método que borra un elto del array el objeto que estamos desplazando a la derecha
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);
    }

    /** Borra un item de la lista de cafeteros
     *
     * @param cafetero
     */
    private void borrarCafetero(Cafetero cafetero) {
        mCafeteros.remove(cafetero);
        mCafeteroRecyclerAdapter.notifyDataSetChanged(); //actualiza el adaptador con nuevos datos
    }

    @Override
    protected void onResume() { //TODO no funciona como quiero que funcione
        Log.i(TAG, "onResume: HE VUELTO");
        super.onResume();
        Cafetero cafeteroPrueba = mCafeteros.get(0);
        Log.i(TAG, "onResume: " + cafeteroPrueba.toString());
        mCafeteroRecyclerAdapter.notifyDataSetChanged();
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
        linearLayoutManager = new LinearLayoutManager(this); //instanciamos un Layout Manager

        mRecyclerView.setLayoutManager(linearLayoutManager); //se lo pasamos al recyclerview
        mRecyclerView.setAdapter(mCafeteroRecyclerAdapter); //le pasamos el adaptador al recyclerview
    }

    @Override
    public void onCafeteroClick(Integer position) { //Manda Cafeteros de la lista
        Log.d(TAG, "onCafeteroClick: clicaste en el cafetero: " + mCafeteros.get(position).toString());
        Intent intent = new Intent(this,CafeteroActivity.class);
        intent.putExtra("com.edusoft.dam.cafesito.cafetero_old", mCafeteros.get(position)); //usamos el paquete único de la app + nombre del objeto (buenas prácticas por si recibimos mismo objeto de otra app)
        startActivity(intent);
    }

    @Override
    public void onClick(View v) { // lanza una nueva actividad
        Intent intent = new Intent(this, CafeteroActivity.class);
        startActivity(intent);
    }




}