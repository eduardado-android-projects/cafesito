package com.edusoft.dam.cafesito.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.edusoft.dam.cafesito.model.Cafetero;

public class DataBaseHelper extends SQLiteOpenHelper {

    /*
    private String nombreCompleto;
    private String mv;
    private String tipoCafe;
    private Integer numCafe;
     */

    private static final String TAG = "DataBaseHelper";

    private static final String NOMBRE_TABLA = "Cafetero";
    private static final Integer VERSION_DB = 1;

    private  static final String COL_0 = "Id";

    private  static final String COL_1 = "NombreCompleto";
    private  static final String COL_2 = "Mv";
    private  static final String COL_3 = "TipoCafe";
    private  static final String COL_4 = "NumCafe"; //OJO INTEGER


    public DataBaseHelper(@Nullable Context context) {
        super(context, NOMBRE_TABLA, null, VERSION_DB);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        /*
            CREATE TABLE Cafetero (Id INTEGER PRIMARY KEY AUTOINCREMENT,
                                   NombreCompleto TEXT,
                                   Mv TEXT,
                                   TipoCafe TEXT,
                                   NumCafe INTEGER)

         */
        String createTable = "CREATE TABLE " + NOMBRE_TABLA +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NombreCompleto TEXT, " +
                " Mv TEXT, " +
                " TipoCafe TEXT, " +
                " NumCafe INTEGER)" ;

        Log.d(TAG, "onCreate: Creando tabla query:" + createTable);

        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //este método se ejecuta cuando se cambia la versión de la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        onCreate(db);

    }

    /** Añade un Cafetero a la base de datos
     *
     * @param cafetero
     * @return true si se ha insertado correctamente, false en caso contrario
     */
    public Boolean addCafetero(Cafetero cafetero){

        SQLiteDatabase sqLiteDatabase;
        ContentValues contentValues;
        Long resultado;

        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();


        contentValues.put(COL_1,cafetero.getNombreCompleto());
        contentValues.put(COL_2, cafetero.getMv());
        contentValues.put(COL_3, cafetero.getTipoCafe());
        contentValues.put(COL_4, cafetero.getNumCafe());

        resultado = sqLiteDatabase.insert(NOMBRE_TABLA,null,contentValues);

        return resultado.equals(-1) ? true: false;
    }

    public Cursor getAllCafeteros(){
        SQLiteDatabase sqLiteDatabase;

        sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + NOMBRE_TABLA;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        return cursor;
    }

    public void deleteCafetero(){

    }

    public void updateCafetero(){
        /*
        private  static final String COL_1 = "NombreCompleto";
    private  static final String COL_2 = "Mv";
    private  static final String COL_3 = "TipoCafe";
    private  static final String COL_4 = "NumCafe"; //OJO INTEGER
         */

        /*
            UPDATE Cafetero
            SET COL_1 = 'Chayan',
                 COL_2 = '667622551',
                 COL_3 = 'Café con aceitunas',
                 COL_4 = '5'
            WHERE
                COL_0 = ID
         */

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

    }
}
