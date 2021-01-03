package com.edusoft.dam.cafesito.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.component.LinedEditText;
import com.edusoft.dam.cafesito.model.Cafetero;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CafeteroActivity extends AppCompatActivity implements View.OnClickListener {
    //Activacion/desactivacion del modo edicion
    private static final Integer MODO_EDICION_ACTIVADO = 1;
    private static final Integer MODO_EDICION_DESACTIVADO = 0;
    private Integer modo;

    //debug
    private static final String TAG = "CafeteroActivity";

    //VIEW MODE
        //GUI
        TextView textViewNombreToolBar;
        TextView textViewMv;
        LinedEditText tipoCafeNonEditableLinedEditText;
        //variables

    //EDIT MODE
        //GUI
        EditText editTextNombreToolBar;
        EditText editTextNumCafe;
        EditText editTextMv;
        LinedEditText tipoCafeEditableLinedText;
        //variables

    //BOTONES
        ImageButton buttonNumCafeUp;
        ImageButton buttonNumCafeDown;
        FloatingActionButton floatingActionButton;
        ImageButton buttonBackArrow;

    //Variables GLOBALes
    Cafetero mCafeteroElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetero);

        //modo por defecto
        modo = MODO_EDICION_DESACTIVADO;

        //VIEW MODE
        textViewNombreToolBar = findViewById(R.id.toolbar_textView);
        textViewMv = findViewById(R.id.mv_textView);
        tipoCafeNonEditableLinedEditText = findViewById(R.id.tipoCafe_LinedEditText);

        //EDIT MODE
        editTextNombreToolBar = findViewById(R.id.toolbar_editText);
        editTextNumCafe = findViewById(R.id.num_cafe_editText);
        editTextMv = findViewById(R.id.mv_editText);
        tipoCafeEditableLinedText = findViewById(R.id.tipoCafe_EditableLinedEditText);

        //BOTONES
        floatingActionButton = findViewById(R.id.cafetero_fab);
        floatingActionButton.setOnClickListener(this);
        buttonNumCafeUp = findViewById(R.id.numCafe_imageButtonUp);
        buttonNumCafeDown = findViewById(R.id.numCafe_imageButtonDown);
        buttonBackArrow = findViewById(R.id.toolbar_back_arrow);
        buttonBackArrow.setOnClickListener(this);



        //Discrimina si el Cafetero estaba en la lista o se ha creado pulsando el botón crear
        if(recogerIntent()){
            Log.d(TAG, "onCreate: VIEJO" + mCafeteroElegido.toString());
            setCafeteroValues();
        }else{
            Log.d(TAG, "onCreate: Nuevo cafetero");
        }



    }


    /**
     *
     */
    private void setCafeteroValues() {

        //Obtiene los datos del objeto
        String nombreCompleto = mCafeteroElegido.getNombreCompleto();
        String numCafe = mCafeteroElegido.getNumCafe().toString();
        String mv = mCafeteroElegido.getMv();
        String tipoCafe = mCafeteroElegido.getTipoCafe();


        //WIDGES VISIBLES EN MODO VIEW
        textViewNombreToolBar.setText(nombreCompleto);
        textViewMv.setText(mv);
        tipoCafeNonEditableLinedEditText.setText(tipoCafe);

        //WIDGETS VISIBLES EN MODO EDIT
        editTextNombreToolBar.setText(nombreCompleto);
        editTextMv.setText(mv);
        tipoCafeEditableLinedText.setText(tipoCafe);

        //WIDGET SIEMPRE VISIBLE
        editTextNumCafe.setText(numCafe);
    }

    /** Recoge el Intent que venga de otra Activity
     *  Asigna a la variable Global
     * @return si viene de la lista de cafeteros, devuelve true de lo contrario devuelve false
     */
    private Boolean recogerIntent() {

        if(getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_old")){
             mCafeteroElegido = getIntent().getParcelableExtra("com.edusoft.dam.cafesito.cafetero_old");
             // Aclaración: getParcelableExtra no es una referencia al objeto sino es un nuevo objeto con la información.
            return true;
        }else{
            mCafeteroElegido = new Cafetero(); //esto es necesario para que el método saveCafeteroProperties(); no haga un nullPointerException.. es necesario usar
            cambiaModo(); //cambia a modo edición
            return false;
        }


    }

    private void enableEditMode(){
        modo = MODO_EDICION_ACTIVADO;

        textViewNombreToolBar.setVisibility(View.GONE);
        editTextNombreToolBar.setVisibility(View.VISIBLE);

        textViewMv.setVisibility(View.GONE);
        editTextMv.setVisibility(View.VISIBLE);

        tipoCafeNonEditableLinedEditText.setVisibility(View.GONE);
        tipoCafeEditableLinedText.setVisibility(View.VISIBLE);

        buttonNumCafeDown.setOnClickListener(this);
        buttonNumCafeUp.setOnClickListener(this);

        buttonNumCafeDown.setVisibility(View.VISIBLE);
        buttonNumCafeUp.setVisibility(View.VISIBLE);

    }

    private void disableEditMode(){
        modo = MODO_EDICION_DESACTIVADO;

        textViewNombreToolBar.setVisibility(View.VISIBLE);
        editTextNombreToolBar.setVisibility(View.GONE);

        textViewMv.setVisibility(View.VISIBLE);
        editTextMv.setVisibility(View.GONE);

        tipoCafeNonEditableLinedEditText.setVisibility(View.VISIBLE);
        tipoCafeEditableLinedText.setVisibility(View.GONE);

        buttonNumCafeDown.setOnClickListener(null);
        buttonNumCafeUp.setOnClickListener(null);

        buttonNumCafeDown.setVisibility(View.INVISIBLE);
        buttonNumCafeUp.setVisibility(View.INVISIBLE);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cafetero_fab:{
                cambiaModo();
                break;
            }
            case R.id.numCafe_imageButtonUp:{
                cambiaNumCafe(1);
                break;
            }
            case R.id.numCafe_imageButtonDown:{
                cambiaNumCafe(-1);
                break;
            }
            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }

        }




    }

    private void cambiaNumCafe(Integer i) {
        Integer numCafe = Integer.parseInt(editTextNumCafe.getText().toString());
        String numCafeString;

        if(numCafe + i >= 0){
            numCafe += i;
        }

        numCafeString = numCafe.toString();

        editTextNumCafe.setText(numCafeString);


    }

    /** Cambia el Activity de modo VIEW a mode EDIT y viceversa
     *
     */
    private void cambiaModo() {
        if(modo.equals(MODO_EDICION_DESACTIVADO)){
            enableEditMode();
            floatingActionButton.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }else{
            saveCafeteroProperties(); //guardado de datos
            disableEditMode();
            floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24);
        }
    }

    /** Algunos de los widget en modo EDIT no son los mismos que los widget en VIEW MODE
     *  Este método se encarga de que el contenido de los widget en EDIT MODE actualice
     *  los widget que se ven en VIEW MODE
     *
     */
    private void saveCafeteroProperties() {

        //se recoge input del usuario
        String cafeteroName = editTextNombreToolBar.getText().toString();
        String numCafe = editTextNumCafe.getText().toString();
        String mv = editTextMv.getText().toString();
        String tipoCafe = tipoCafeEditableLinedText.getText().toString();

        Log.i(TAG, "saveCafeteroProperties: cafetero antes: " + mCafeteroElegido.toString());

        //se modifica el objeto global
        mCafeteroElegido.setNombreCompleto(cafeteroName);
        mCafeteroElegido.setNumCafe(Integer.parseInt(numCafe));
        mCafeteroElegido.setMv(mv);
        mCafeteroElegido.setTipoCafe(tipoCafe);

        Log.i(TAG, "saveCafeteroProperties: cafetero despues: " + mCafeteroElegido.toString());


        //se actualiza la vista a partir del objeto modificado
        textViewNombreToolBar.setText(mCafeteroElegido.getNombreCompleto());
        editTextNumCafe.setText(mCafeteroElegido.getNumCafe().toString());
        textViewMv.setText(mCafeteroElegido.getMv());
        tipoCafeNonEditableLinedEditText.setText(mCafeteroElegido.getTipoCafe());






    }




}