package com.ngo.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Final extends AppCompatActivity {

    Button openFileButton,newInterviewButton,listviewButton;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        openFileButton = (Button) findViewById(R.id.openFileButton);
        newInterviewButton = (Button) findViewById(R.id.newInterviewButton);
        listviewButton = (Button) findViewById(R.id.listviewButton);
        filename = getIntent().getStringExtra("filename");
    }

    public void openFileClicked(View view){

        File file = new File(Environment.getExternalStorageDirectory() + "/Converted Text/" + filename +".txt");

        Intent i =new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.fromFile(file),filename + "/txt");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(i);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"File Doesn't Exist",Toast.LENGTH_LONG).show();
        }
    }

    public void listviewClicked(View view){

        Intent intent = new Intent(this,Listview.class);
        startActivity(intent);
    }

    public void newInterviewClicked(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
