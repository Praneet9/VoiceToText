package com.ngo.voicetotext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button singleInterview, groupInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleInterview = (Button) findViewById(R.id.singleButton);
        groupInterview = (Button) findViewById(R.id.groupButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void singleButtonClicked(View view){
        Intent i = new Intent(this,SingleInterview.class);
        startActivity(i);
    }

    public void groupButtonClicked(View view){
        Intent i = new Intent(this,GroupInterview.class);
        startActivity(i);
    }


}
