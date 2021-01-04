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


    public DataBaseHelper(@Nullable Context context) { //recibirá el Activity
        super(context, NOMBRE_TABLA, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);

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

        return resultado.equals(-1) ? false: true;
    }

    public Cursor getAllCafeteros(){
        SQLiteDatabase sqLiteDatabase;

        sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM Cafetero";

        Cursor cursor = sqLiteDatabase.rawQuery(query,null); // ojo estamos usando rawQuery() para el select

        return cursor;
    }

    public Boolean deleteCafeteroFromDB(Cafetero cafetero){
        Boolean borrarOno;

        String id = cafetero.getId().toString();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //DELETE FROM TABLE WHERE X = 'Y' AND Z ='W'
        String deleteQuery = "DELETE FROM " + NOMBRE_TABLA + " WHERE " +
                COL_0 + " = '" + id + "'";

        Log.d(TAG, "deleteCafetero: deleteQuery : " + deleteQuery);

        //sqLiteDatabase.execSQL(deleteQuery);

        //Este método evuelve el nº de registros afectados por el delete, lo cual es útil para informar al usuario
        Integer resultadoDelete = sqLiteDatabase.delete(NOMBRE_TABLA,"id = ?",new String[]{id});

        return (resultadoDelete > -1) ?true:false;


    }


    public void updateCafetero(Cafetero oldCafetero, Cafetero newCafetero){
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

        String updateQuery = "UPDATE " + NOMBRE_TABLA + " SET "         +
                COL_1 + "='" + newCafetero.getNombreCompleto() + "',"   +
                COL_2 + "='" + newCafetero.getMv()             + "',"   +
                COL_3 + "='" + newCafetero.getTipoCafe() +       "',"   +
                COL_4 + "='" + newCafetero.getNumCafe() +        "'" +
                " WHERE "    +
                COL_0 + "='"+ oldCafetero.getId().toString() + "'";

        Log.d(TAG, "updateCafetero: update query" + updateQuery);

        sqLiteDatabase.execSQL(updateQuery);




    }
}
