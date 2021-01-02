package com.edusoft.dam.sqlitelistviewdemo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "ListDataActivity";

    DatabaseHelper databaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        listView = findViewById(R.id.list_view);
        databaseHelper = new DatabaseHelper(this);
        listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Mostrando datos en el ListView ");


        Cursor cursor = databaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while (cursor.moveToNext()) {
            listData.add(cursor.getString(1));
        }

        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(listAdapter);


    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        Log.d(TAG, "onItemClick:  Hiciste click en " + name);

        Cursor cursor = databaseHelper.getItemId(name);
        Integer itemId = -1;
        while (cursor.moveToNext()) {
            itemId = cursor.getInt(0);
        }

        if (itemId > -1) {
            Log.d(TAG, "onItemClick: The ID es: " + itemId);
            Intent editScreenIntent = new Intent(this, EditDataActivity.class);
            editScreenIntent.putExtra("id", itemId);
            editScreenIntent.putExtra("name",name);
            startActivity(editScreenIntent);

        } else {
            toastMsg("No hay ninguna ID asociada a ese nombre");
        }


    }
}
