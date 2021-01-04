package com.edusoft.dam.cafesito.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.adapter.CafeteroRecyclerAdapter;
import com.edusoft.dam.cafesito.model.Cafetero;
import com.edusoft.dam.cafesito.persistence.DataBaseHelper;
import com.edusoft.dam.cafesito.util.SeparaVerticalItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CafeteroListaActivity extends AppCompatActivity implements CafeteroRecyclerAdapter.OnCafeteroListener, View.OnClickListener {

    private static final String TAG = "CafeteroListaActivity";

    //base de datos
    DataBaseHelper dataBaseHelper;


    //Estética
    private ItemTouchHelper.SimpleCallback simpleCallback;

    //GUI
    private RecyclerView mRecyclerView;
    private Toolbar mListaCafeterosToolbar;
    private FloatingActionButton floatingActionButton;

    //variables
    private ArrayList<Cafetero> mCafeteros;
    private CafeteroRecyclerAdapter mCafeteroRecyclerAdapter;

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: HE VUELTO A LA PANTALLA!");

        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetero_list);



        //GUI, referencia
        mRecyclerView = findViewById(R.id.recyclerView); //dentro de un Activity hay una referencia implícita a View. Puedo usar findViewById() directamente
        mListaCafeterosToolbar = findViewById(R.id.lista_cafeteros_toolbar);
        floatingActionButton = findViewById(R.id.cafetero_list_fab);
        floatingActionButton.setOnClickListener(this);

        //variables, instanciación
        mCafeteros = new ArrayList<>();

        //base de datos
        dataBaseHelper = new DataBaseHelper(this); // TODO el problema está aquí



        //implementaciones ordenadas
        initRecyclerView(); //configura el recyclerivew con el adaptador personalizado
        separaItems(); //para separar los items
        initToolBar(); //para añadir un ToolBar personalizado
        initItemTouchHelper(); //para poder desplazar un ítem y borrarlo

        //DATOS
        cargaArrayConBaseDeDatos();
        //insertaDemoCafeteros(); //para cargar datos falsos y probar la GUI, no inserta nada en la base de datos, sólo en el array del adapter
        //precargaDemoBaseDatos(); // para cargar demo en la base de datos


    }



    private void initItemTouchHelper() {
        //instancia una clase abstracta y con ella hay que sobreescribir un par de métodos
         ItemTouchHelper.SimpleCallback  simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) { //hay que pasarle 0 y la direccion que queremos implementar
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

        mCafeteroRecyclerAdapter = new CafeteroRecyclerAdapter(mCafeteros,this); //ojo, recibe la interfaz que hace posible clickar en cada item
        mRecyclerView.setLayoutManager(linearLayoutManager); //se lo pasamos al recyclerview
        mRecyclerView.setAdapter(mCafeteroRecyclerAdapter); //le pasamos el adaptador al recyclerview
    }

    @Override
    public void onCafeteroClick(Integer position) { //Manda Cafeteros de la lista al pulsar algún item del RecyclerView
        Log.d(TAG, "onCafeteroClick: Hicisteclick en el cafetero de la POSICION: " + position);
        Log.d(TAG, "onCafeteroClick: clicaste en el cafetero: " + mCafeteros.get(position).toString());
        Intent intent = new Intent(this,CafeteroActivity.class);
        //Los objetos tienen que implementar la interfaz Parcelable para poder mandarse entre Activities
        intent.putExtra("com.edusoft.dam.cafesito.cafetero_old", mCafeteros.get(position)); //usamos el paquete único de la app + nombre del objeto (buenas prácticas por si recibimos mismo objeto de otra app)
        startActivity(intent);
    }

    @Override
    public void onClick(View v) { // Sólo lo estoy usando con el botón flotante
        Intent intent = new Intent(this, CafeteroActivity.class);
        intent.putExtra("com.edusoft.dam.cafesito.cafetero_new", CafeteroActivity.class);
        startActivity(intent);
    }


    // BASE DE DATOS
    private void addCafeteroToDataBase(Cafetero cafetero){
        Boolean insertResult;

        insertResult = dataBaseHelper.addCafetero(cafetero);

        if(insertResult){
            Toast.makeText(this, "Cafetero Guardado con éxito", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Hubo un problema al guardar el cafetero", Toast.LENGTH_SHORT).show();
        }

    }

    /** Borra un item de la lista de cafeteros
     *
     * @param cafetero
     */
    private void borrarCafetero(Cafetero cafetero) {

        dataBaseHelper.deleteCafeteroFromDB(cafetero); //primero se borra de la base de datos

        mCafeteros.remove(cafetero); //después se borra del array

        mCafeteroRecyclerAdapter.notifyDataSetChanged(); //actualiza el adaptador con nuevos datos
    }

    private void addCafetero(Cafetero cafetero){

        dataBaseHelper.addCafetero(cafetero); //añadimos el cafetero a la base de datos

        mCafeteros.add(cafetero);//lo añadimos al array

        mCafeteroRecyclerAdapter.notifyDataSetChanged();

        //mCafeteroRecyclerAdapter.notifyItemInserted(); //TODO probar también
    }

    /** Carga la base de datos con 20 Cafeteros
     *  Ideal correr este método la primera vez que se instala la aplicación solamente
     */
    private void precargaDemoBaseDatos(){

        Log.d(TAG, "precargaDemoBaseDatos: PRECARGANDO UNOS CUANTOS DATOS FALSOS EN LA BASE DE DATOS");
        for(int i = 0; i < 5; i++){
            Cafetero cafetero = new Cafetero();
            cafetero.setNombreCompleto("nombre" + i);
            cafetero.setMv("+34 " + i);
            cafetero.setNumCafe(i);
            cafetero.setTipoCafe("cafe con " + i);

            dataBaseHelper.addCafetero(cafetero);
        }

        mCafeteroRecyclerAdapter.notifyDataSetChanged();
    }

    /** Carga el array con objetos Cafetero que se encuentran en la base de datos y refresca el adaptador del recyclerview
     *
     */
    private void cargaArrayConBaseDeDatos(){

        //todo NO ESTOY OBTENIENDO TODOS LOS REGISTROS EN EL CURSOR
        Cursor cursor = dataBaseHelper.getAllCafeteros(); //obtiene todos los registros de la base de datos
        Log.d(TAG, "cargaArrayConBaseDeDatos: QUE PASA AQUI");

        //O NO ESTÁ FUNCIONANDO getAllCafeteros o el cursor no se actualiza

        Integer eltosBD = cursor.getCount();

        Log.d(TAG, "cargaArrayConBaseDeDatos: El cursor dice que hay " + eltosBD);

        while(cursor.moveToNext()){ //extrae, de todos los registros, los campos que nos interesan

            Cafetero cafeteroDB = new Cafetero(); //creo un cafetero

            cafeteroDB.setId(cursor.getInt(0)); //importante en SQLite sí hay columna 0 a dif
            cafeteroDB.setNombreCompleto(cursor.getString(1)); //meto en el todos los datos del primer registro (el id no lo guardo)
            cafeteroDB.setMv(cursor.getString(2));
            cafeteroDB.setTipoCafe(cursor.getString(3));
            cafeteroDB.setNumCafe(cursor.getInt(4));

            //Log.d(TAG, "cargaArrayConBaseDeDatos: CAFETERO ANTES DE AÑADIR AL ARRAY" + cafeteroDB);

            mCafeteros.add(cafeteroDB); //los va añadiendo al array global de cafeteros (que luego se cargará en el adapter
        }

        mCafeteroRecyclerAdapter.notifyDataSetChanged();// avisa al adapter que los datos han cambiado para que refresque la lista

    }






}