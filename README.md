# multimedia-practicas

Repositorio de prácticas del segundo trimestre

## PRÁCTICAS

1. Persistencia de datos con SQLite


## Práctica 1: Persistencia de datos con SQLite
<details>
  <summary>Instrucciones</summary>
  <p>
    Tenéis que una aplicación para gestionar una lista de clientes de cualquier tipo con campos: Nombre y Apellidos, email, teléfono fijo y móvil.
1.- Se podrá visualizar todos los elementos de la lista en un ListView. Desde el ListView se podrá seleccionar un elemento para mostrarlo en detalle, modificarlo, o borrarlo.
2.- Se podrá insertar un elemento y borrar toda la lista.
3.- Los elementos se guardarán en una base de datos SQLite.
4.- Al salir se guardarán un dato cualquiera, la fecha y la hora en key-values, que se mostrarán al arrancar la aplicación en un Toast.

Sobre el punto 4, dentro de la persistencia de datos cómo habréis podido ver en el tercer enlace se encuentra el almacenamiento clave/valor de las preferencias de la aplicación. Haciendo uso de las preferencias de la aplicación y de los eventos de una actividad (https://multimedia.codeandcoke.com/_detail/apuntes:activity_lifecycle.png?id=apuntes%3Aandroid) debéis de guardar la fecha y hora al cerrar la actividad (onclose) y mostrar el toast al iniciar (oncreate). La primera vez que no tenga datos, podéis escribir o la fecha y hora actual o un mensaje de "Primera vez".
  </p>
  <p>https://developer.android.com/training/data-storage/sqlite</p>
  <p>http://www.hermosaprogramacion.com/2014/10/android-sqlite-bases-de-datos/</p>
  <p>http://developer.android.com/intl/es/training/basics/data-storage/index.html</p>
  <p>http://www.developandsys.es/shared-preferences/</p>
</details>

<details>
  <summary>Borrador CafesitoApp</summary>

### CafesitoApp

#### Objetivo
Una aplicación para que no olvidar devolver ese cafelito al que nos invitaron

#### Estructura Datos

Clase Cafetero
  - NombreCompleto (Nombre, apellidos, sobrenombre etc.)
  - Mv(puede server para hacer un Bizum, mandarle un whatsapp)
  - numCafe: Integer (nº de cafés que le debemos)
  - tipoCafe (descripcion de cómo le gusta el café)

#### Activities
Activities
1. Lista de cafeteros: Nombre-----NumCafe (tlf no aparece)->ListaCafeteros
    - Botón flotante: nuevo cafetero
    - Deslizas derecha: elimina un cafetero
    - Botón flotante: eliminar todos
2. Cafetero: Al pulsar sobre un cafetero me muestra una pantalla en grande con:
    - Nombre: Arriba centrado (banda ancha ocupa toda la pantalla)
    - Mv: debajo
    - numCafe que le debemos: en grande
    - tipo café: cómo le gusta el café: LinedTextView - descripción de cómo le gusta el café
    -MODO VISION: Muestra los datos
    -MODO EDICIÓN: Modo edición-> Al hacer doble click nos deja editar todos los campos
3. EliminarTodos: sale un botón rojo que, al darle, se eliminan todos los cafeteros

#### Aspecto
  Colores marrones

#### Compatibilidad
  Desde 8.0 (Oreo)

#### Idea
  Si desplazo a la izquierda disminuye
  Si desplazo a la derecha aumenta
  Si es cero y desplazo a la izquierda elimino ítem
  Si pantalla principal, en el toolbar hay un botón que lleva a actividad 3 (ElimintarTodos)
</details>

<details>
  <summary>Código usado</summary>

  #### Interfaces
    * View.OnClickListener
    * Parceable
    * OnCafeteroListener(custom): onCafeteroClick()
    
  #### Clase: métodos
    * Activity: startActivity(); getActivity(); getIntent();
    * Log: d(); i() ;
    * RecyclerView.ViewHolder: setText(); getAdapterPosition()
    * RecyclerView.Adapter<>: onCreateViewHolder(); onBindViewHolder(); getItemCount()
    * View: findViewById(); setOnClickListener(); getLineHeight(); getHeight(); onDraw()
    * ArrayList<>: get(); size()
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

    
  #### GUI Elements: atributo=valor
    * LinearLayout: orientation; weightSum; background; gravity
    * View
    * TextView: lines; textSize; padding; textColor; textSize; layout_margin_start; setText()
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

  
  #### Otros recursos usados
    * themes.xml
    * colors.xml
    * values.xml (acceso a través de ?attr) & (acceso a través de "@string/appbar_scrolling...)
    * AndroidManifest.xml

  
</details>

<details>
  <summary>Explicaciones</summary>

  #### Implementación del RecyclerView
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

  #### Separar los items con ItemDecorator
    1. Se crea una clase que herede de RecyclerView.ItemDecoration
    2. Se sobreescribe getItemOffset
    3. Se le pasa una instancia de este objeto al recyclerview

  #### Añadir un ActionBar personalizado con comportamiento
    1. Desactivar el ActionBar por defecto: en themes.xml sustituir DarkActionBar por NoActionBar
    2. Seguir la documentacion de https://material.io/components/app-bars-top/android#using-top-app-bars
  
  #### Implementar OnClickListener (buenas prácticas)
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

  Interfaz:
  ```java
  public interface OnCafeteroListener{
      void onCafeteroClick(Integer position);
  }
  ```
  Sobreescritura de onClick()

  ```java
  @Override
        public void onClick(View v) {
            onCafeteroListener.onCafeteroClick(getAdapterPosition());
        }
  ```

  #### Creación de activity nº 2 + mandar objetos entre activity1 y activity2(Cafetero Activity)

  Resumen: 
    - Este activity mostrará los detalles de cada ítem. 
    - Tendrá dos modos: Lectura y Escritura

    1. Crear Empty Activity usando generandor de código(genera layout y lo añade a androidManifest.xml )
    2. Hacer el objeto Parcelable (añadir métodos + implementación)
    3. Crear un Intent y adjuntarle un Bundle con el objeto deseado
    4. Recuperar el objeto en el activity nº2
    5. Con el método getIntent().hasExtra() podemo discriminar de qué activity viene el Itent

  #### Algunas notas sobre Layouts

    * weightSum: Si un elto padre tiene 100 como valor, podemos poner después el atributo layout_weight a los hijos para que ocupen un porcentaje del mismo, no sin antes poner layout_height/width a 0dp.
    * RelativeLayout: permite usar layout_centerInParent en los hijos. Es útil para manejar botones e imágenes centradas.
    * <include layout=""> permite anidar unos layouts dentro de otros y tener el código modularizado.
    * android:gravity es muy útil para posicionar texto
  
  #### Crear el componente AppCompatEditText

  Resumen: Crearemos un EditText personalizado, que tiene dibujadas líneas como si fuera papel de un cuaderno de notas.

    1. Crear una clase java que herede de AppCompatEditText
    2. Usar constructor que tenga el objeto AttributeSet como parámetro (importante)
    3. Sobreescribir onDraw() y, usando el objeto Rect + Paint dibujar líneas (ver código)
    4. Referenciar dicho componente en el layout

  #### Usar un drawable <shape> para redondear bordes
    1. Crear un drawable resource
    2. Usar <shape><stroke><corners> para definir el borde deseado
    3. Añadir al widget el atributo background referenciando el drawable.xml creado (mirar codigo)

  
  
  

</details>



