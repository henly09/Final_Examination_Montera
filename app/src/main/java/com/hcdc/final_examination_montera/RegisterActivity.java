package com.hcdc.final_examination_montera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Author: John Henly A. Montera
// BSIT-3rd-Year  Offline
// Subject CC106 01527
// https://www.facebook.com/mhax.ter/

public class RegisterActivity extends AppCompatActivity {

    SQLiteDatabase myDB;
    EditText userregmain, passregmain, confpassregmain, namemain;
    Button createaccmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userregmain = findViewById(R.id.userreg);
        passregmain = findViewById(R.id.passreg);
        confpassregmain = findViewById(R.id.confpassreg);
        namemain = findViewById(R.id.name);
        createaccmain = findViewById(R.id.createacc);

        createaccmain.setOnClickListener(view -> {
            if (passregmain.getText().toString().equals(confpassregmain.getText().toString())){
                register(userregmain.getText().toString(),passregmain.getText().toString(),"0",namemain.getText().toString());
            }
            else{
                Toast.makeText(this, "Your Password and Confirm Password are not the same", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void register(String user, String pass, String amount, String name){

        try {
            myDB = openOrCreateDatabase("accounts.db", 0, null);
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("username", user);
            cv.put("password", pass);
            cv.put("amount", amount);
            myDB.insert("accountamount", null, cv);
            myDB.close();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(RegisterActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, "Error Occurred! Please try again later.", Toast.LENGTH_SHORT).show();
        }

    }
}