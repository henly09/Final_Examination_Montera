package com.hcdc.final_examination_montera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Author: John Henly A. Montera
// BSIT-3rd-Year  Offline
// Subject CC106 01527
// https://www.facebook.com/mhax.ter/

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase myDB;
    EditText usernamemain,passwordmain;
    Button regmain, logmain;
    String countacc,name,id,amount,user;
    int countindex,nameindex,idindex,amountindex,userindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernamemain = findViewById(R.id.username);
        passwordmain = findViewById(R.id.password);
        regmain = findViewById(R.id.register);
        logmain = findViewById(R.id.login);

/*        myDB = openOrCreateDatabase("accounts.db", 0, null);
        myDB.execSQL("DROP TABLE accountamount");
        myDB.close();*/

        myDB = openOrCreateDatabase("accounts.db", 0, null);
        myDB.execSQL("create table if not exists accountamount (" +
                "accountid integer primary key autoincrement," +
                "name varchar(255)," +
                "amount Double," +
                "username varchar(16)," +
                "password varchar(16)" +
                ")");
        myDB.close();

        logmain.setOnClickListener(view -> {
            try {
                myDB = openOrCreateDatabase("accounts.db", 0, null);
                Cursor acc_count = myDB.rawQuery("SELECT COUNT(*) AS count FROM accountamount where accountamount.username = ? AND accountamount.password = ?;", new String[] {usernamemain.getText().toString(),passwordmain.getText().toString()});
                // getting validation count
                while (acc_count.moveToNext()){
                    countindex = acc_count.getColumnIndex("count");
                    countacc = acc_count.getString(countindex);
                }
                // getting name
                Cursor getname = myDB.rawQuery("SELECT name FROM accountamount where accountamount.username = ? AND accountamount.password = ?", new String[] {usernamemain.getText().toString(),passwordmain.getText().toString()});
                while (getname.moveToNext()){
                    nameindex = getname.getColumnIndex("name");
                    name = getname.getString(nameindex);
                }
                // getting ID
                Cursor getid = myDB.rawQuery("SELECT accountid FROM accountamount where accountamount.username = ? AND accountamount.password = ?", new String[] {usernamemain.getText().toString(),passwordmain.getText().toString()});
                while (getid.moveToNext()){
                    idindex = getid.getColumnIndex("accountid");
                    id = getid.getString(idindex);
                }
                // getting amount
                Cursor getamount = myDB.rawQuery("SELECT amount FROM accountamount where accountamount.username = ? AND accountamount.password = ?", new String[] {usernamemain.getText().toString(),passwordmain.getText().toString()});
                while (getamount.moveToNext()){
                    amountindex = getamount.getColumnIndex("amount");
                    amount = getamount.getString(amountindex);
                }
                // getting user
                Cursor getuser = myDB.rawQuery("SELECT username FROM accountamount where accountamount.username = ? AND accountamount.password = ?", new String[] {usernamemain.getText().toString(),passwordmain.getText().toString()});
                while (getuser.moveToNext()){
                    userindex = getuser.getColumnIndex("username");
                    user = getuser.getString(userindex);
                }
                myDB.close();
                if (Integer.parseInt(countacc) != 0){
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("user", user);
                    intent.putExtra("name", name);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials! Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, "Error Occurred! Please Try Again Later...", Toast.LENGTH_SHORT).show();
            }

        });

        regmain.setOnClickListener(view -> {
            try{
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            } catch (Exception e){
                Toast.makeText(this, "Error Occurred! Please Try Again Later ", Toast.LENGTH_SHORT).show();
            }

        });

    }

}