# Cafesito

An Android app to keep track of those talks we want to have with friends over a coffee and have many coffees we think it will take.

## Demo 

<details>
  <summary>(v1.0)Basic functionality</summary>

![Democafesito](https://media.giphy.com/media/Zx9ZcMmvuyMi4Zelk4/giphy.gif)
</details>


<details>
  <summary>(v1.1) Extending functionality</summary>

![preferences](https://media.giphy.com/media/8LyxgapqBqkhpQSZ7S/giphy.gif)
</details>

## Codebase Explained (for Android Developers)
<details>
  <summary>View Codebase explanations</summary>

  ### Interfaces
    * View.OnClickListener
    * Parceable
    * OnCafeteroListener(custom): onCafeteroClick()
    
  ### Classes: methods
    * Activity: startActivity(); getActivity(); getIntent(); finish(); getSharedPreferences()
    * Log: d(); i() ;
    * RecyclerView.ViewHolder: setText(); getAdapterPosition()
    * RecyclerView.Adapter<>: onCreateViewHolder(); onBindViewHolder(); getItemCount()
    * View: findViewById(); setOnClickListener(); getLineHeight(); getHeight(); onDraw(); setVisibility();setBackgroundColor();    setTextColor();
    * ArrayList<>: get(); size(); remove();
    * LayoutInflater: from(); inflate()
    * ViewGroup: getContext();
    * LinearLayoutManager
    * RecyclerView: setLayoutManager(); setAdapter(); addItemDecoration()
    * RecyclerView.ItemDecoration: getItemOffSets()
    * Rect
    * AppCompatActivity: setSupportActionBar(); setTitle()
    * Intent: putExtra(); getParceableExtra(); hasExtra()
    * Rect
    * Paint: setStyle(); setSTrokeWidth(); setColor()
    * Canvas: drawLine()
    * TextView: getLineBounds()
    * ItemTouchHelper.SimpleCallback: onSwiped()
    * ItemTouchHelper: attachToRecyclerView();
    * SQLiteOpenHelper: onCreate(); onUpgrade();
    * SQLiteDatabase: getWritableDatabase(); insert(); rawQuery(); delete(); update()
    * ContentValues: put()
    * Cursor:
    * SharedPreferences:getString(); edit()
    * SharedPreferences.Editor: putString(); apply()
    * AlertDialog.Builder: setMessage(); create()
    * AlertDialog.show()

    
  ### GUI Elements: attribute=value
    * LinearLayout: orientation; weightSum; background; gravity
    * View
    * TextView: lines; textSize; padding; textColor; textSize; layout_margin_start; setText(); imeOptions="flagNoExtractUi"
    * RecyclerView: orientation; padding; layout_behaviour="@string/appbar_scrolling_view_behaviour"
    * CoordinatorLayout
    * AppBarLayout
    * ToolBar: layout_height="?attr/actionBarSize"; layout_scrollFlags="scroll"
    * include
    * LinearLayout
    * ConstraintLayout
    * RelativeLayout: visibility="visible/gone";
    * ImageButton: layout_centerInParent; background ="?attr/selectableItemBackGround"
    * LinedEditText(custom)
    * shape: shape="rectangle"
      * stroke: width; color
      * corner: radius
      * solid: color;
    

  
  ### Other resources used
    * themes.xml
    * colors.xml
    * values.xml (acceso a través de ?attr) & (acceso a través de "@string/appbar_scrolling...)
    * AndroidManifest.xml

  
</details>

## Explanations (for Android Developers)
<details>
  <summary>View Explanations</summary>

  ### RecyclerView Implementation
    1. Crear layout para un solo item (layout_cafetero_list_item.xml)
    2. Crear el adaptador
      1. Creamos clase java
      2. Anidamos una clase que hereda de RecyclerVew.ViewHolder. En ella añadimos atributos y referenciamos los widgets.
      3. En la clase adaptadora añadimos herencia de RecyclerView.Adapter. Le pasamos como tipo la clase anidada.
      4. Añadimos un ArrayList a la clase adaptadora
      5. Sobreescribimos
        * onCreateViewHolder() 
          1. `View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nombreLayout, viewGroup, false );`
          2. return new ViewHolder(view)
        * onBindViewHolder()
          1. viewHolder.atributo.setText(array.get(pos)).getNombreAtributo()) -> por cada widget
    3. Implementar el widget
      1. Añadimos un RecyclerView al layout que muestra la lista de ítems
      2. En Java
        * Referenciamos: Array, adaptador y recyclerview
        * Pasamos al recyclerview una instancia del adaptador y una instancia de LinearLayoutManager

  ### Splitting items with ItemDecorator
    1. Se crea una clase que herede de RecyclerView.ItemDecoration
    2. Se sobreescribe getItemOffset
    3. Se le pasa una instancia de este objeto al recyclerview

  ### ActionBar with custom behaviour
    1. Desactivar el ActionBar por defecto: en themes.xml sustituir DarkActionBar por NoActionBar
    2. Seguir la documentacion de https://material.io/components/app-bars-top/android#using-top-app-bars
  
  ### OnItemListener: Good practice implementation
    Resumen: Definimos una interfaz, que será ejecutada por cada ítem cuando se click sobre él.

    Aclaración: Cada ítem del recyclerview es un ViewHolder

    Para entender mejor la implementación pensemos en lo que queremos que ocurra desde el punto de vista de la ejecución.

    Ejecución:
      1. El Activity implementa una interfaz
      2. El Activity, al instanciar el adaptador, le pasa dicha interfaz.
      3. Dentro de la clase adaptadora, ésta instancia la interfaz y se la pasa a ViewHolder
      4. Dentro de ViewHolder, ésta clase implementa la interfaz OnClickListener
      5. ViewHolder, cada vez que crea un ítem, le aplica OnClickListener (ahora cada ítem notará clicks)
      6. ViewHolder sobreescribe onClick(), donde usa la interfaz, invoca el método onCafeteroClick pasándole la posición del ítem
      7. ¿Cómo le pasa la posición del ítem? Usando el método getAdapterPosition()

    Implementación:

    1. En clase Adaptadora: Definimos, instanciamos y pasamos a la clase anidada la Interfaz:
    2. En clase anidada ViewHolder: 
      * Implementa OnClickListener
      * Aplica OnClickListener a cada ítem
      * Sobreescribe onClick: usa la interfaz y le pasa la posición del ítem como parámetro gracias a getAdapterPosition()
    3. Sobreescribimos onClick() 

  Interface:
  ```java
  public interface OnCafeteroListener{
      void onCafeteroClick(Integer position);
  }
  ```
  Overriding onClick()

  ```java
  @Override
        public void onClick(View v) {
            onCafeteroListener.onCafeteroClick(getAdapterPosition());
        }
  ```
### Activity creation and passing objects between Activity1 and Activity2

    - Resumen: 
      - Este activity mostrará los detalles de cada ítem. 
      - Tendrá dos modos: Lectura y Escritura

    1. Crear Empty Activity usando generandor de código(genera layout y lo añade a androidManifest.xml )
    2. Hacer el objeto Parcelable (añadir métodos + implementación)
    3. Crear un Intent y adjuntarle un Bundle con el objeto deseado
    4. Recuperar el objeto en el activity nº2
    5. Con el método getIntent().hasExtra() podemo discriminar de qué activity viene el Itent

  ### Layouts: Some notes

    * weightSum: Si un elto padre tiene 100 como valor, podemos poner después el atributo layout_weight a los hijos para que ocupen un porcentaje del mismo, no sin antes poner layout_height/width a 0dp.
    * RelativeLayout: permite usar layout_centerInParent en los hijos. Es útil para manejar botones e imágenes centradas.
    * <include layout=""> permite anidar unos layouts dentro de otros y tener el código modularizado.
    * android:gravity es muy útil para posicionar texto
  
  ### AppCompatEditText: creating the component

    Resumen: Crearemos un EditText personalizado, que tiene dibujadas líneas como si fuera papel de un cuaderno de notas.

    1. Crear una clase java que herede de AppCompatEditText
    2. Usar constructor que tenga el objeto AttributeSet como parámetro (importante)
    3. Sobreescribir onDraw() y, usando el objeto Rect + Paint dibujar líneas (ver código)
    4. Referenciar dicho componente en el layout

  ### Softening the borders with drawable <shape>
    1. Crear un drawable resource
    2. Usar <shape><stroke><corners> para definir el borde deseado
    3. Añadir al widget el atributo background referenciando el drawable.xml creado (mirar codigo)

  ### View + Edit in the same Activity: Design decision

    Resumen: Decido diseñar la app de tal manera que la opción READ y la opción EDIT del CRUD se muestren en la misma activity.
    Lo que hago es guardar un estado del activity en la variable "modo". Cuando se pulsa el botón flotante los widgets cambian de
    color, algunos desaparecen de la vista (View.changevisibility()) para dar paso a EditText etc. Lo importante es que el usuario
    puede modificar los datos y, al volver a pulsar el botón flotante, el objeto se actualiza.

  
  ### ItemTouchHelper implementation (deleting an item from the recyclerview swaping left or right)

    1. Instanciar una clase abstracta ItemTouchHelper.SimpleCallback pasandole como parámetros -> 0 y la dirección (mirar código)
    2. Sobreescribir el método onSwipe() ejecutando el código que queramos (borrado de un ítem + actualización del adaptador)

  ### Some random notes

    * Para volver terminar un activity usamos finish()
    * Al añadir el atributo imeOptions y darle el valor flagNoExtractUi hace que no se tape la interfaz cuando estamos editando
      un EditText con la pantalla en horizontal.
  
  ### Data Persistence with SQLite

    Resumen: Una vez hemos terminado la GUI y comprobado que funciona correctamente con datos estáticos, vamos implementar la persistencia de datos con SQLite.

    Nota: Cabe destacar que, debido a que en nuestra aplicación la VISTA y la EDICIÓN ocurren en el mismo activity, dicho activity
    tiene que:
      * Tener dos estados posibles: esto lo hicimos guardando en una variable estática el modo en el que se encuentra, además de 
      activando/desactivando el modo edición(mirar código)
      * Controlar si, al instanciarse CafeteroActivity estamos creando un nuevo Cafetero o estamos modificando uno existente. Esto lo hicimos discriminando el Intent, filtrando por el Extra (mirar código)
      * La primera columna es nº 0!: A diferencia de otras bases de datos como MySQL u Oracle, al usar cursores para obtener los registros de las tablas, la primera columna tiene un índice 0. Esto es muy importante.
      * Muy importante, después de que se ejecute onCreate() de la clase SQLOpenHelper, se crearán tablas cuya estructura no puede cambiar. Dicha base
      de datos se crea en la memoria del emulador del teléfono móvil en el que corre la aplicación. Si queremos cambiar la estructura de la base datos
      modificando una tabla, tendremos que cambiar la versión en el constructor de la clase o dará errores.

    
    Implementación 
      1. Creamos una clase que herede de SQLiteOpenHelper
      2. Generamos un constructor que sólo recibirá Context como parámetro y al que le pasamos null como CursorFactory. 
      3. Creamos los métodos para add, delete, update y select que necesita nuestra aplicación (mirar código comentado)

#### SharedPreferences

[Documentacion](https://developer.android.com/training/data-storage/shared-preferences)

##### Where to find preferences file

<pre>
  1. En View>Tool Windows>Device File Explorer; Podemos ver la memoria del teléfono (emulado o no) que seleccionemos en el desplegable.
  2. En el directorio <b>data/data/nombreApp/shared_prefs</b> se encuentran los archivos xml donde se guardan las preferencias
  Nota: Si hemos usado getPreferences() para cargar el objeto SharedPreferences, se guardarán los datos en un archivo llamado: NombreActividad.xml
  Nota2: Si hemos usado getSharedPreferences() se generará un archivo en el mismo directorio pero con el nombre que le hayamos pasado como parámetro a dicho método.
</pre>

##### Use example

  ```java

    Date fechaActual = Calendar.getInstance().getTime();
    String fechaString = fechaActual.toString();
    String fechaKey = "fechaInicioSesion";
    String fechaValue = fechaString;

    //GUARDAR PREFERENCIAS DE LA ACTIVIDAD
    //escribirá en las preferencias de la actividad porque getPreferences() se ejecuta desde el contexto, que es la actividad
    //el nombre del archivo será paquete.Actividad.xml
    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(fechaKey ,fechaValue);
    editor.apply();

    //AHORA CON PREFERENCIAS DEL USUARIO DE LA APLICACIÓN
    //Al usar getSharedPreferences(), se genera un archivo (lo tenemos referenciado en el recurso strings.xml), se llama CafesitoLogFile
    SharedPreferences sharedPreferencesGlobal = getSharedPreferences(getString(R.string.com_edusoft_dam_cafesito_CAFESITO_LOG), MODE_PRIVATE); //El objeto sharedPreferencesGlobal apunta al fichero CafesitoLogFile.xml
    SharedPreferences.Editor globalEditor = sharedPreferencesGlobal.edit(); //cargamos el editor del las preferencias globales
    globalEditor.putString(fechaKey,fechaValue); //guardamos pares clave:valor en el editor
    globalEditor.apply(); //el editor aplica los cambios

    //PARA LEER DEL ARCHIVO DE PREFERENCIAS GLOBAL
    SharedPreferences sharedPreferencesLectura = getSharedPreferences(getString(R.string.com_edusoft_dam_cafesito_CAFESITO_LOG),MODE_PRIVATE);
    String lecturaFecha = sharedPreferencesLectura.getString(fechaKey,"error"); //obtiene el valor del elto con key "fechaInicioSesion", devuelve "error" si no encuetra el key
    Toast.makeText(this, lecturaFecha, Toast.LENGTH_SHORT).show(); //mostrará por pantalla el valor recuperado
  ```
![Imgur](https://i.imgur.com/iIzshlp.png)


</details>

## Improvements for the future

<details>
  <summary>Ver Mejoras pendientes:</summary>

- rama-room: persistencia de datos con Room + Arquitectura recomendada (Repository + LiveData)
- Añadir foto/icono
- 
</details>



