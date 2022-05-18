package com.hcdc.final_examination_montera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Author: John Henly A. Montera
// BSIT-3rd-Year  Offline
// Subject CC106 01527
// https://www.facebook.com/mhax.ter/

public class DashboardActivity extends AppCompatActivity {

    SQLiteDatabase myDB;
    EditText inputamountmain;
    TextView currentamountviewmain,setaccviewmain,setuserviewmain;
    Button updatemain;
    int amountindex;
    String amount,id,user,name,amountforview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        inputamountmain = findViewById(R.id.inputamount);
        currentamountviewmain = findViewById(R.id.currentamountview);
        setaccviewmain = findViewById(R.id.setaccview);
        setuserviewmain = findViewById(R.id.setuserview);
        updatemain = findViewById(R.id.update);

        Intent intent = getIntent();
         id = intent.getStringExtra("id");
         user = intent.getStringExtra("user");
         name = intent.getStringExtra("name");
        amountforview = intent.getStringExtra("amount");

        currentamountviewmain.setText(amountforview);
        setaccviewmain.setText(name);
        setuserviewmain.setText(user);

        updatemain.setOnClickListener(view -> {

                update(inputamountmain.getText().toString());

        });

    }

    public void update(String inputtedamount){
        myDB = openOrCreateDatabase("accounts.db", 0, null);
        // getting amount
        Cursor getamount = myDB.rawQuery("SELECT amount FROM accountamount where accountamount.accountid = ?", new String[] {id});
        while (getamount.moveToNext()){
            amountindex = getamount.getColumnIndex("amount");
            amount = getamount.getString(amountindex);
        }
        Double newamount = Double.parseDouble(amount) + Double.parseDouble(inputtedamount);
        ContentValues cv = new ContentValues();
        cv.put("amount", newamount);
        myDB.update("accountamount", cv, "accountid = "+id, null);
        myDB.close();
        refreshamount();
    }

    public void refreshamount(){
        myDB = openOrCreateDatabase("accounts.db", 0, null);
        Cursor getamount = myDB.rawQuery("SELECT amount FROM accountamount where accountamount.accountid = ?", new String[] {id});
        while (getamount.moveToNext()){
            amountindex = getamount.getColumnIndex("amount");
            amount = getamount.getString(amountindex);
        }
        currentamountviewmain.setText(amount);
        myDB.close();
    }


}