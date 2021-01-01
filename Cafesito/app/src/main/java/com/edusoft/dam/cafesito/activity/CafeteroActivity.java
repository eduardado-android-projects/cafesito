package com.edusoft.dam.cafesito.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        TextView textViewNumCafe;
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

        //VIEW MODE
        textViewNombreToolBar = findViewById(R.id.toolbar_textView);
        textViewNumCafe = findViewById(R.id.num_cafe_editText);
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


        //modo por defecto
        modo = MODO_EDICION_DESACTIVADO;


        //Discrimina si el Cafetero estaba en la lista o se ha creado pulsando el botón crear
        if(recogerIntent()){
            Log.d(TAG, "onCreate: VIEJO" + mCafetero.toString());
            setCafeteroProperties();
        }else{
            Log.d(TAG, "onCreate: NUEVO" + mCafetero.toString());
        }



    }


    /** Toma el objeto Cafetero Global y actualiza todos los campos de la clase tanto los EDIT MODE
     *  como los VIEW mode
     *
     */
    private void setCafeteroProperties() {
        String nombreCompleto = mCafetero.getNombreCompleto();
        String numCafe = mCafetero.getNumCafe().toString();
        String mv = mCafetero.getMv();
        String tipoCafe = mCafetero.getTipoCafe();


        //NO EDITABLES
        textViewNombreToolBar.setText(nombreCompleto);
        textViewNumCafe.setText(numCafe);
        textViewMv.setText(mv);
        tipoCafeNonEditableLinedEditText.setText(tipoCafe);

        //EDITABLES
        editTextNombreToolBar.setText(nombreCompleto);
        editTextNumCafe.setText(numCafe);
        editTextMv.setText(mv);
        tipoCafeEditableLinedText.setText(tipoCafe);

    }

    /** Recoge el Intent que venga de otra Activity
     * @return si viene de la lista de cafeteros, devuelve true de lo contrario devuelve false
     */
    private Boolean recogerIntent() {
        if(getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_old")){
             mCafetero = getIntent().getParcelableExtra("com.edusoft.dam.cafesito.cafetero_old");
            return true;
        }else{
            return false;
        }


    }

    private void enableEditMode(){
        textViewNombreToolBar.setVisibility(View.GONE);
        editTextNombreToolBar.setVisibility(View.VISIBLE);

        textViewNumCafe.setBackgroundColor(getResources().getColor(R.color.terciario));
        textViewNumCafe.setTextColor(getResources().getColor(R.color.secundario));

        textViewMv.setVisibility(View.GONE);
        editTextMv.setVisibility(View.VISIBLE);

        tipoCafeNonEditableLinedEditText.setVisibility(View.GONE);
        tipoCafeEditableLinedText.setVisibility(View.VISIBLE);

        buttonNumCafeDown.setOnClickListener(this);
        buttonNumCafeUp.setOnClickListener(this);

        buttonNumCafeDown.setVisibility(View.VISIBLE);
        buttonNumCafeUp.setVisibility(View.VISIBLE);

        modo = MODO_EDICION_ACTIVADO;
    }

    private void disableEditMode(){
        textViewNombreToolBar.setVisibility(View.VISIBLE);
        editTextNombreToolBar.setVisibility(View.GONE);

        textViewNumCafe.setBackgroundColor(getResources().getColor(R.color.primario));
        textViewNumCafe.setTextColor(getResources().getColor(R.color.white));

        textViewMv.setVisibility(View.VISIBLE);
        editTextMv.setVisibility(View.GONE);

        tipoCafeNonEditableLinedEditText.setVisibility(View.VISIBLE);
        tipoCafeEditableLinedText.setVisibility(View.GONE);

        buttonNumCafeDown.setOnClickListener(null);
        buttonNumCafeUp.setOnClickListener(null);

        buttonNumCafeDown.setVisibility(View.INVISIBLE);
        buttonNumCafeUp.setVisibility(View.INVISIBLE);

        modo = MODO_EDICION_DESACTIVADO;

    }


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
                Intent intent = new Intent(this,CafeteroListaActivity.class);
                startActivity(intent);
                break;
            }

        }




    }

    private void cambiaNumCafe(Integer i) {
        Integer numCafe = Integer.parseInt(textViewNumCafe.getText().toString());
        String numCafeString;

        if(numCafe + i >= 0){
            numCafe += i;
        }

        numCafeString = numCafe.toString();

        editTextNumCafe.setText(numCafeString);
        textViewNumCafe.setText(numCafeString);


    }

    private void cambiaModo() {
        if(modo == MODO_EDICION_DESACTIVADO){
            enableEditMode();
            floatingActionButton.setImageResource(R.drawable.ic_baseline_check_circle_24);
        }else{
            saveCafeteroProperties();
            disableEditMode();
            floatingActionButton.setImageResource(R.drawable.ic_baseline_edit_24);
        }
    }

    /** Refresca los datos de los campos VIEW MODE con los widget EDIT MODE
     *
     */
    private void saveCafeteroProperties() {
        //NO EDITABLES
        textViewNombreToolBar.setText(editTextNombreToolBar.getText());
        textViewNumCafe.setText(editTextNumCafe.getText());
        textViewMv.setText(editTextMv.getText());
        tipoCafeNonEditableLinedEditText.setText(tipoCafeEditableLinedText.getText());

    }
}