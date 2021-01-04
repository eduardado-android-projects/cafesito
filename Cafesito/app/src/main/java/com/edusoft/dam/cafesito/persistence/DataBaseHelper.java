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

    //debuggins
    private static final String TAG = "DataBaseHelper";


    private static final String NOMBRE_TABLA = "Cafetero";
    private static final Integer VERSION_DB = 2; //Es 2 porque cambiamos la tabla en una ocasión

    //Campos en PascalCase
    private  static final String COL_0 = "Id";
    private  static final String COL_1 = "NombreCompleto";
    private  static final String COL_2 = "Mv";
    private  static final String COL_3 = "TipoCafe";
    private  static final String COL_4 = "NumCafe"; //OJO INTEGER

    //El constructor sólo necesita recibir como parámetro el Activity
    public DataBaseHelper(@Nullable Context context) {
        super(context, NOMBRE_TABLA, null, VERSION_DB);
    }

    /** La primera vez que se instancia la clase, se creará la tabla
     *  Precaución: si se cambia la sentencia de creación de tabla hay que modificar la versión de la base de datos
     *  en el constructor
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

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

    /** Consulta la tabla y devuelve todos los registros
     *
     * @return cursor con todos los registros
     */
    public Cursor getAllCafeteros(){
        SQLiteDatabase sqLiteDatabase;
        Cursor cursor;

        sqLiteDatabase = getReadableDatabase(); //abrimos una instancia de la base de datos en modo lectura
        String query = "SELECT * FROM Cafetero";

        cursor = sqLiteDatabase.rawQuery(query,null); // ojo estamos usando rawQuery() para el select

        return cursor;
    }

    /** Borra un registro de la tabla
     *
     * @param cafetero
     * @return true si se elimina un registro, false en caso contrario
     */
    public Boolean deleteCafeteroFromDB(Cafetero cafetero){

        SQLiteDatabase sqLiteDatabase;
        String[] argumento;
        String whereClause;
        Integer resultadoDelete;

        sqLiteDatabase = getWritableDatabase();
        argumento = new String[]{cafetero.getId().toString()};
        whereClause = COL_0 + " = ?";

        resultadoDelete = sqLiteDatabase.delete(NOMBRE_TABLA,whereClause, argumento);

        return (resultadoDelete > -1) ?true:false;

    }


    /** Actualiza un registro de la tabla
     *
     * @param oldCafetero Objeto Cafetero con los datos anteriores
     * @param newCafetero Objeto Cafetero con los datos deseados
     * @return true si se actualiza un registro, false en caso contrario
     */
    public Boolean updateCafetero(Cafetero oldCafetero, Cafetero newCafetero){
        SQLiteDatabase sqLiteDatabase; //se obtiene una instancia de la base de datos en modo escritura

        ContentValues contentValues;
        String whereClause;
        String[] argumento;
        Integer updateResult;


        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();
        whereClause = COL_0 + " = ?";

        //cargamos el objeto ContentValues con los datos que queremos actualizar, que provienen de newCafetero
        contentValues.put(COL_1, newCafetero.getNombreCompleto());
        contentValues.put(COL_2, newCafetero.getMv());
        contentValues.put(COL_3, newCafetero.getTipoCafe());
        contentValues.put(COL_4, newCafetero.getNumCafe());

        //el id del registro a modificar
        argumento = new String[]{oldCafetero.getId().toString()};

        //ejecución
        updateResult = sqLiteDatabase.update(NOMBRE_TABLA, contentValues, whereClause, argumento);

        //si ha ido bien devolverá true
        return (updateResult > 0 ) ? true : false;
    }
}
