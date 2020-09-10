package com.example.ajuste_insulina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

public class Splash extends AppCompatActivity {

    private float resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        final Connect_SQlite admin = new Connect_SQlite(this, Connect_SQlite.DATABASE_NAME, null, Connect_SQlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                Cursor objetivo = bbdd2.rawQuery("SELECT objetivo FROM datosIN WHERE objetivo > 0 ",null);



                if (objetivo.moveToFirst() && objetivo != null){

                    objetivo.moveToFirst();

                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivityForResult(i,0);
                    finish();



                }else{

                    Intent i2 = new Intent(Splash.this, Introducir_Datos.class);
                    startActivityForResult(i2,0);
                    finish();



                }






            }
        },1000);











    }
}