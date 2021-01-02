package com.edusoft.dam.sqlitelistviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditDataActivity";

    private Button btnSave;
    private Button btnDelete;
    private EditText editableText;

    DatabaseHelper databaseHelper;

    private String selectedName;
    private Integer selectedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_data);

        btnSave = findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(this);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        editableText = findViewById(R.id.editable_text);

        databaseHelper = new DatabaseHelper(this);

        //recibe intent de ListaDataActivity
        Intent receivedIntent = getIntent();

        selectedId = receivedIntent.getIntExtra("id", -1);

        selectedName = receivedIntent.getStringExtra("name");

        editableText.setText(selectedName);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Save: {
                String item = editableText.getText().toString();
                if (!item.equals("")) {
                    toastMsg("Estás guardando " + item);
                    databaseHelper.updateName(item, selectedId, selectedName);
                } else {
                    toastMsg("tienes que poner un nombre");
                }
                break;
            }

            case R.id.btn_delete: {
                databaseHelper.deleName(selectedId, selectedName);
                editableText.setText("");
                toastMsg("removed from the database");
            }
        }
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
