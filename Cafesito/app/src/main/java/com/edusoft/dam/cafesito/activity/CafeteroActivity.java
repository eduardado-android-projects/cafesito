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

    // Funcionamento memoria
    private Boolean isNewCafetero; //si el usuario está creando un nuevo Cafetero

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
    Cafetero mCafetero;

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



        isNewCafetero = recogerIntent(); //recoge intent

        if(isNewCafetero){
            insertGlobalCafetero();
        }else{
            updateCafeteroWithGlobalCafetero();
        }





    }

    private void insertGlobalCafetero() {
        Log.d(TAG, "insertGlobalCafetero: Insertando" + mCafetero.toString());
    }

    private void updateCafeteroWithGlobalCafetero() {
        Log.d(TAG, "updateCafeteroWithGlobalCafetero: actualizando" + mCafetero.toString());
    }


    /** Rellena la GUI (tanto modo edición como view) con los datos del cafeteroGlobal
     *
     */
    private void populateGUIwithGlobalCafetero() {

        //Obtiene los datos del objeto
        String nombreCompleto = mCafetero.getNombreCompleto();
        String numCafe = mCafetero.getNumCafe().toString();
        String mv = mCafetero.getMv();
        String tipoCafe = mCafetero.getTipoCafe();


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
     *  Si recibe un objeto de CafeteroListaActivity lo asigna a la variable global mCafetero.
     * @return si viene de la lista de cafeteros, devuelve true de lo contrario devuelve false
     */
    private Boolean recogerIntent() {
        Log.d(TAG, "recogerIntent: Intent Recogido");


        if(getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_old")){
            mCafetero = getIntent().getParcelableExtra("com.edusoft.dam.cafesito.cafetero_old");
            populateGUIwithGlobalCafetero();
            return false;
        }else if (getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_new")){
            mCafetero = new Cafetero();
            enableEditMode();
            return true;
        }else{
            return null;
        }

    }

    private void enableEditMode(){
        modo = MODO_EDICION_ACTIVADO;

        floatingActionButton.setImageResource(R.drawable.ic_baseline_check_circle_24);

        editTextNumCafe.setBackgroundColor(getResources().getColor(R.color.terciario));

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

        updateView();


        modo = MODO_EDICION_DESACTIVADO;

        floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24);

        editTextNumCafe.setBackgroundColor(getResources().getColor(R.color.primario));

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

        }else{
            disableEditMode();
        }
    }

    /** Algunos de los widget que se ven en modo EDIT no son los mismos que los widget en VIEW MODE
     *  Este método se encarga de que el contenido de los widget en del VIEW mode se actualicen con
     *  los datos del EDIT MODE que acaba de introducir el usuario.
     *
     */
    private void updateView() {

        //se recoge input del usuario
        String cafeteroName = editTextNombreToolBar.getText().toString();
        String numCafe = editTextNumCafe.getText().toString();
        String mv = editTextMv.getText().toString();
        String tipoCafe = tipoCafeEditableLinedText.getText().toString();

        //se modifica el objeto Cafetero global
        mCafetero.setNombreCompleto(cafeteroName);
        mCafetero.setNumCafe(Integer.parseInt(numCafe));
        mCafetero.setMv(mv);
        mCafetero.setTipoCafe(tipoCafe);

        //se actualiza la vista a partir del objeto modificado
        textViewNombreToolBar.setText(mCafetero.getNombreCompleto());
        editTextNumCafe.setText(mCafetero.getNumCafe().toString());
        textViewMv.setText(mCafetero.getMv());
        tipoCafeNonEditableLinedEditText.setText(mCafetero.getTipoCafe());


    }





}