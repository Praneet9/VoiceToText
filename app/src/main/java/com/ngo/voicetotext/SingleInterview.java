package com.ngo.voicetotext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SingleInterview extends AppCompatActivity {

    DBHandler myDb = new DBHandler(this);
    public String filename,name,age,contact,gender;
    public EditText nameEditText,ageEditText,contactEditText;
    public CheckBox genderMale,genderFemale;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_interview);

        next = (Button) findViewById(R.id.nextButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        contactEditText = (EditText) findViewById(R.id.contactEditText);
        genderMale = (CheckBox) findViewById(R.id.maleCheckBox);
        genderFemale = (CheckBox) findViewById(R.id.femaleCheckBox);
        next.setEnabled(false);
    }

    public void maleBoxClicked(View view){
        genderFemale.setChecked(false);
    }

    public void femaleBoxClicked(View view){
        genderMale.setChecked(false);
    }

    public void setDetailsClicked(View view){

        name = nameEditText.getText().toString();
        age = ageEditText.getText().toString();
        contact = contactEditText.getText().toString();
        if(genderMale.isChecked()){
            gender = "Male";
        }
        else if(genderFemale.isChecked()){
            gender = "Female";
        }
        else{
            Toast.makeText(getBaseContext(),"Enter Gender",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(name))
        {
            nameEditText.setError("Please Enter Your Full Name");
            nameEditText.requestFocus();
        }

        next.setEnabled(true);

        insertdata();
    }

    public void insertdata(){
        boolean isInserted=myDb.insertData(name,gender,age,contact);

        if(isInserted == true){
            Toast.makeText(getApplicationContext(),"Profile Saved Successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Could not Save Profile",Toast.LENGTH_LONG).show();
        }
    }

    public void refreshButtonClicked(View view){
        nameEditText.setText("");
        ageEditText.setText("");
        contactEditText.setText("");
        genderMale.setChecked(false);
        genderFemale.setChecked(false);
    }

    public void nextButtonClicked(View v){


        Intent intent = new Intent(this,Speech.class);
        intent.putExtra("filename",name);
        intent.putExtra("userDetails","Full Name : " + name + "\nGender : " + gender + "\nAge : " + age + "\nContact : " + contact);
        startActivity(intent);
    }
}
