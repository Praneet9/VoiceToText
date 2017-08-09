package com.ngo.voicetotext;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listview extends AppCompatActivity {

    DBHandler myDb;
    int j=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView listView = (ListView) findViewById(R.id.listview);
        myDb = new DBHandler(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDb.getAllData();

        if(data.getCount() == 0)
            Toast.makeText(getApplicationContext(),"Database is Empty",Toast.LENGTH_LONG).show();
        else
            while (data.moveToNext()){
                theList.add("ID                  :   " + j + "\nFull Name   :   " + data.getString(1) + "\nGender        :   " + data.getString(2) + "\nAge              :   " + data.getString(3) + "\nContact       :   " + data.getString(4));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList);
                listView.setAdapter(listAdapter);
                j++;
            }
    }
}
