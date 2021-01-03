# Cafesito

Una app de android para reciprocar cafés.

## Ramas
  * main: persistencia de datos con SQLite
  * rama-room: persistencia de datos con Room + Arquitectura recomendada (Repository + LiveData) 

## Brainstorming antes de comenzar

<details>
  <summary>Mostrar borrador</summary>


Clase Cafetero
  - NombreCompleto (Nombre, apellidos, sobrenombre etc.)
  - Mv(puede server para hacer un Bizum, mandarle un whatsapp)
  - numCafe: Integer (nº de cafés que le debemos)
  - tipoCafe (descripcion de cómo le gusta el café)

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

  Colores marrones

  Desde 8.0 (Oreo)

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
    * Activity: startActivity(); getActivity(); getIntent(); finish();
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

    
  #### GUI Elements: atributo=valor
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

  #### View + Edit en el mismo activity

    Resumen: Decido diseñar la app de tal manera que la opción READ y la opción EDIT del CRUD se muestren en la misma activity.
    Lo que hago es guardar un estado del activity en la variable "modo". Cuando se pulsa el botón flotante los widgets cambian de
    color, algunos desaparecen de la vista (View.changevisibility()) para dar paso a EditText etc. Lo importante es que el usuario
    puede modificar los datos y, al volver a pulsar el botón flotante, el objeto se actualiza.

    Sin embargo aquí me estoy encontrando un problema y es que parece que cuando hacemos Intent.putExtra() no estamos pasando una
    referencia al objeto sino sólo la información que tiene el objeto, así cuando intento actualizar los datos del objeto para que los cambios sean permanentes, esto no ocurre.

    Así que de momento, los cambios que se hacen en el activity 1 no son permanentes. Veremos más adelante si lo puedo solucionar.
  
  ![demoCafesito1](https://media.giphy.com/media/eIXHsniyNp04pjPETN/giphy.gif)
  
  #### Implementación de ItemTouchHelper (para borrar ítem del recyclerview al desplazarlo a un lado)

    1. Instanciar una clase abstracta ItemTouchHelper.SimpleCallback pasandole como parámetros -> 0 y la dirección (mirar código)
    2. Sobreescribir el método onSwipe() ejecutando el código que queramos (borrado de un ítem + actualización del adaptador)

  #### Algunas notas sueltas

    * Para volver terminar un activity usamos finish()
    * Al añadir el atributo imeOptions y darle el valor flagNoExtractUi hace que no se tape la interfaz cuando estamos editando
      un EditText con la pantalla en horizontal.
  
  #### Persistencia de Datos con SQLite

    Resumen: Una vez hemos terminado la GUI y comprobado que funciona correctamente con datos estáticos, vamos implementar la persistencia de datos con SQLite.

    Nota: Cabe destacar que, debido a que en nuestra aplicación la VISTA y la EDICIÓN ocurren en el mismo activity, dicho activity
    tiene que:
      * Tener dos estados posibles: esto lo hicimos guardando en una variable estática el modo en el que se encuentra, además de 
      activando/desactivando el modo edición(mirar código)
      * Controlar si, al instanciarse CafeteroActivity estamos creando un nuevo Cafetero o estamos modificando uno existente. Esto lo hicimos discriminando el Intent, filtrando por el Extra (mirar código)
      * La primera columna es nº 0!: A diferencia de otras bases de datos como MySQL u Oracle, al usar cursores para obtener los registros de las tablas, la primera columna tiene un índice 0. Esto es muy importante.
    
    Implementación 
      1. Creamos una clase que herede de SQLiteOpenHelper
      2. Generamos un constructor que sólo recibirá Context como parámetro y al que le pasamos null como CursorFactory. 
      3. 

  
  
  

</details>



