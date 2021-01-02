package com.edusoft.dam.sqlitelistviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAdd;
    private Button btnView;
    private EditText editText;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btn_add);
        btnView = findViewById(R.id.btn_view);

        databaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(this);
        btnView.setOnClickListener(this);

    }

    public void addData(String newEntry){

        boolean insertData = databaseHelper.addData(newEntry); //false si no se inserta correctamente

        if(insertData){
            toastMessage("Data Successfuly Inserted");
        }else{
            toastMessage("Something went wrong");
        }

    }

    private void toastMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:{
                String newEntry = editText.getText().toString();
                if(!newEntry.isEmpty()){
                    addData(newEntry);//se añade a la base de datos
                    editText.setText(""); //se resetea
                }else{
                    toastMessage("Debes poner algo en la caja");
                }

                break;
            }

            case R.id.btn_view:{
                Intent intent = new Intent(this, ListDataActivity.class);
                startActivity(intent);
                break;
            }
        }

    }
}