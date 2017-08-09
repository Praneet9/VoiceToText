package com.ngo.voicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Speech extends AppCompatActivity {

    String convertedspeech,filename,userDetails;
    int j=1,n=1,language=1,flag=0;
    public EditText timeduration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        timeduration = (EditText) findViewById(R.id.timedurationEditText);
        filename = getIntent().getStringExtra("filename");
        userDetails = getIntent().getStringExtra("userDetails");

        final Switch englishSwitch = (Switch) findViewById(R.id.switchEnglish);
        final Switch hindiSwitch = (Switch) findViewById(R.id.switchHindi);

        englishSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    language = 1;
                    hindiSwitch.setChecked(false);
                }
            }
        });

        hindiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    language = 2;
                    englishSwitch.setChecked(false);
                }
            }
        });
    }
/*
    public void englishClicked(View view){
        language = 1;
    }

    public void hindiClicked(View view){
        language = 2;
    }

*/
    public void speakButtonClicked(View view) {

            /*String n=name.getText().toString();
            if(TextUtils.isEmpty(n)){
                name.setError("Please Enter Your Name");
                name.requestFocus();
                return;
            }*/
        String time = timeduration.getText().toString();
        if (TextUtils.isEmpty(time)){
            timeduration.setError("Please Enter Time Duration");
            timeduration.requestFocus();
        }
        j = Integer.parseInt(time);
        j = j*2;
        promptSpeechInput();

    }

    public void nextButtonClicked(View view){

        Intent finalIntent = new Intent(this,Final.class);
        finalIntent.putExtra("filename",filename);
        startActivity(finalIntent);
    }

    public void promptSpeechInput() {

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (language == 1)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en_IN");
        else if (language == 2)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi_IN");
        else
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"en_IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,40000);
        i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,3000 );
        try {
            while(n<=j){
                startActivityForResult(i, 100);
                n++;
            }
        } catch (ActivityNotFoundException a) {
            Toast.makeText(Speech.this, "Sorry! Your Device doesn't support speech language", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onActivityResult(int request_code,int result_code, Intent i){
        super.onActivityResult(request_code,result_code,i);


        switch (request_code){
            case 100 : if (result_code == RESULT_OK && i != null) {
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                convertedspeech = result.get(0);
                writeToFile();

            }
                break;
        }
    }

    public void writeToFile() {


        try {

            final File dir = new File("/sdcard/Converted Text/");

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Toast.makeText(getBaseContext(), "Unable to Create Required Directory", Toast.LENGTH_LONG).show();
                }
            }
            File externalStorageDir = Environment.getExternalStorageDirectory();


            File myFile = new File(externalStorageDir + "/Converted Text/", filename + ".txt");

            if (myFile.exists() == true) {
                FileOutputStream fOut = new FileOutputStream(myFile, true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.write("\n" + convertedspeech + ".");
                myOutWriter.close();
                fOut.close();
            } else {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(userDetails + "\n" + convertedspeech + ".");
                myOutWriter.close();
                fOut.close();
            }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    }
