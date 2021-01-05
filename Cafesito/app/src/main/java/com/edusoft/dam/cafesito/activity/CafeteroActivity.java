package com.edusoft.dam.cafesito.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.edusoft.dam.cafesito.R;
import com.edusoft.dam.cafesito.component.LinedEditText;
import com.edusoft.dam.cafesito.exception.IntentNoReconocidoException;
import com.edusoft.dam.cafesito.model.Cafetero;
import com.edusoft.dam.cafesito.persistence.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CafeteroActivity extends AppCompatActivity implements View.OnClickListener {

    //Base de datos
    DataBaseHelper dataBaseHelper;

    //Activacion/desactivacion del modo edicion
    private static final Integer MODO_EDICION_ACTIVADO = 1;
    private static final Integer MODO_EDICION_DESACTIVADO = 0;
    private Integer modo;

    // Funcionamento memoria
    private Boolean isNewCafetero; //recuerda si el usuario está creando un nuevo Cafetero

    //debugging
    private static final String TAG = "CafeteroActivity";

    //VIEW MODE
        //GUI
        TextView textViewNombreToolBar;
        TextView textViewMv;
        LinedEditText tipoCafeNonEditableLinedEditText;

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
        FloatingActionButton floatingActionButtonEdit;
        FloatingActionButton floatingActionButtonSave;
        ImageButton buttonBackArrow;

    //Variables Globales
    private Cafetero mCafeteroEditado;
    private Cafetero mCafeteroViejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafetero);

        //instanciaciones
        dataBaseHelper = new DataBaseHelper(this);


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
        floatingActionButtonEdit = findViewById(R.id.cafetero_fab);
        floatingActionButtonEdit.setOnClickListener(this);

        floatingActionButtonSave = findViewById(R.id.save_in_database_fab);
        floatingActionButtonSave.setOnClickListener(this);

        buttonNumCafeUp = findViewById(R.id.numCafe_imageButtonUp);
        buttonNumCafeDown = findViewById(R.id.numCafe_imageButtonDown);
        buttonBackArrow = findViewById(R.id.toolbar_back_arrow);
        buttonBackArrow.setOnClickListener(this);


        try {
            isNewCafetero = recogerIntent(); //recoge intent y alamcena, en un atributo de la clase, si estamos ante la creación de un nuevo cafetero o no
        } catch (IntentNoReconocidoException e) {
            e.printStackTrace();
        }

        if(isNewCafetero){
            enableEditMode();
        }else {
            mCafeteroViejo = getIntent().getParcelableExtra("com.edusoft.dam.cafesito.cafetero_old");
            populateGUI(mCafeteroViejo);
        }


    }


    /** Recoge el Intent que venga de otra Activity
     *  Si recibe un objeto de CafeteroListaActivity lo asigna a la variable global mCafetero.
     * @return si viene de la lista de cafeteros, devuelve true de lo contrario devuelve false
     */
    private Boolean recogerIntent() throws IntentNoReconocidoException {
        Log.d(TAG, "recogerIntent: Intent Recogido");

        if(getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_old")){
            return false;
        }else if (getIntent().hasExtra("com.edusoft.dam.cafesito.cafetero_new")){
            return true;
        }else{
            Log.d(TAG, "recogerIntent: " + "INTENT NO RECONOCIDO");
            throw new IntentNoReconocidoException("INTENT NO RECONOCIDO");

        }


    }

    /** Rellena la GUI (tanto modo edición como view) con los datos del cafeteroGlobal
     *
     */
    private void populateGUI(Cafetero cafetero) {

        //Obtiene los datos del objeto que recibe por parámetro
        String nombreCompleto = cafetero.getNombreCompleto();
        String numCafe = cafetero.getNumCafe().toString();
        String mv = cafetero.getMv();
        String tipoCafe = cafetero.getTipoCafe();


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

    /** Activa el modo edición del Activity
     *
     */
    private void enableEditMode(){
        modo = MODO_EDICION_ACTIVADO;

        floatingActionButtonEdit.setImageResource(R.drawable.ic_baseline_check_circle_24); //cambia el aspecto del botón

        //MODIFICA APARIENCIA Y APARICIÓN/DESPARICIÓN DE LOS WIDGETS PERTINENTES
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

    /** Desactiva el modo edición
     */
    private void disableEditMode(){

        //cada vez que de desactiva el modo edición
        updateView(); //se actualiza el VIEW con los datos que hubiese en el modo EDIT
        mCafeteroEditado = extractCafeteroFromScreen(); //se extraen los datos de la pantalla y se guardan en un objeto Cafetero Global
        Log.d(TAG, "disableEditMode: CAFETERO EXTRAÍDO de la pantalla : " + mCafeteroEditado);
        floatingActionButtonSave.setVisibility(View.VISIBLE); //aparece el boton guardar

        modo = MODO_EDICION_DESACTIVADO; //se guarda el estado de la clase

        floatingActionButtonEdit.setImageResource(R.drawable.ic_baseline_edit_24); //se cambia la apariencia del botón CHECK por el botón EDIT


        //CAMBIO DE ASPECTO Y APARICIÓN/DESPARICIÓN DE LOS WIDGETS PERTINENTES PARA ENTRAR EN MODO VIEW
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

    /** Guardará los cambios provocados en el modo edición de manera permanente
     *
     * Si el usuario está editando un Cafetero ya existente, se hará un update
     * Si el usuario está creando un nuevo Cafetero se hará un insert
     *
     */
    private void guardaPermanente() {
        //cada vez que se pulsa el botón guardar
        if(isNewCafetero){
            Log.d(TAG, "guardaPermanente: NUEVO CAFETERO");
            Log.d(TAG, "guardaPermanente: VAMOS A GUARDAR EN LA BASE DE DATOS A" + mCafeteroEditado.toString());

            Boolean resultadoInsercion = dataBaseHelper.addCafetero(mCafeteroEditado);
            if(resultadoInsercion){
                String infoUsuario = "";
                if(mCafeteroEditado.getNombreCompleto() != null){
                    infoUsuario = mCafeteroEditado.getNombreCompleto();
                }
                Toast.makeText(this, "Cafetero guardado  " + infoUsuario , Toast.LENGTH_LONG).show();
            }
            finish();
        }else{ //si hemos llegado aquí pulsando un cafetero que ya existía, cuando pulsamos el botón de guardado queremos que se modifique el registro de la base de datos
            Log.d(TAG, "guardaPermanente: VIEJO CAFETERO");
            Boolean updateResult = dataBaseHelper.updateCafetero(mCafeteroViejo, mCafeteroEditado);
            if(updateResult){
                Toast.makeText(this, "Cafetero actualizado! " , Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "No se ha podido actualizar al cafetero", Toast.LENGTH_LONG).show();
            }

            finish();//cuando termine se vuelve a la actividad inicial de lista
        }

        floatingActionButtonSave.setVisibility(View.GONE);//desaparece el botón
    }

    /** Cambia en una unidad el nº de cafés de cada Cafetero, en nigún caso bajará de cero.
     *
     * @param i (unidades positivas o negativas )
     */
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
     */
    private void cambiaModo() {

        if(modo.equals(MODO_EDICION_DESACTIVADO)){
            enableEditMode();

        }else{
            disableEditMode();
        }

    }

    /** Recoge los inputs del usuario y pasa los datos al VIEW
     *
     *  Aclaración: Algunos de los widget que se ven en modo EDIT no son los mismos que los widget
     *  en VIEW MODE. Este método se encarga de que el contenido de los widget en del VIEW mode se actualicen con
     *  los datos del EDIT MODE que acaba de introducir el usuario.
     *
     */
    private void updateView() {

        //se recoge input del usuario
        String cafeteroName = editTextNombreToolBar.getText().toString();
        String numCafe = editTextNumCafe.getText().toString();
        String mv = editTextMv.getText().toString();
        String tipoCafe = tipoCafeEditableLinedText.getText().toString();


        //se actualiza la vista a partir del objeto modificado
        textViewNombreToolBar.setText(cafeteroName);
        editTextNumCafe.setText(numCafe);
        textViewMv.setText(mv);
        tipoCafeNonEditableLinedEditText.setText(tipoCafe);

    }

    /** Se extrae los datos de la pantalla en modo VIEW
     *  Importante: sólo sirve cuando el modo edición está desactivado
     * @return Cafetero
     */
    private Cafetero extractCafeteroFromScreen(){

        //Se recogen los datos de la pantalla
        String cafeteroName = textViewNombreToolBar.getText().toString();
        String mv = textViewMv.getText().toString();
        String tipoCafe = tipoCafeEditableLinedText.getText().toString();
        Integer numCafe = Integer.parseInt(editTextNumCafe.getText().toString()) ;


        return new Cafetero(null,cafeteroName,mv,tipoCafe,numCafe);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cafetero_fab:{
                cambiaModo();
                break;
            }
            case R.id.save_in_database_fab:{
                guardaPermanente();
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


}