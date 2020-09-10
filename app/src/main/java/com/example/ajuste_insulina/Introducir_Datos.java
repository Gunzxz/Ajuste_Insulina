package com.example.ajuste_insulina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Introducir_Datos extends AppCompatActivity {

    private Spinner spinner;
    private Button btn, btn4;
    private EditText edit1, edit2, edit3;
    String carpetaFuente= "fonts/GeosansLight.ttf";


    private float objetivo_desayuno,ratio_desayuno,sensi_desayuno, objetivo_media,ratio_media,sensi_media , objetivo_almuerzo,ratio_almuerzo,sensi_almuerzo ,objetivo_merienda,ratio_merienda,sensi_merienda, objetivo_cena,ratio_cena,sensi_cena;
    private float objetivos, ratios, sensibilidad;

    ArrayList<String>tramos;
    ArrayList<Introducir_Datos> tramos2;


    private String tramo;


    public Introducir_Datos(String tramo) {

        this.tramo = tramo;

    }

    public Introducir_Datos() {



    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir__datos);


        spinner = findViewById(R.id.spinner);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        btn = findViewById(R.id.btn);
        btn4 = findViewById(R.id.btn);

        Typeface fuente = Typeface.createFromAsset(getAssets(),carpetaFuente);
        edit2.setTypeface(fuente);
        edit1.setTypeface(fuente);
        edit3.setTypeface(fuente);
        btn.setTypeface(fuente);
        btn4.setTypeface(fuente);



        final Connect_SQlite admin = new Connect_SQlite(this, Connect_SQlite.DATABASE_NAME, null, Connect_SQlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        consultarListaTramos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tramos);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Cursor objetivo = bbdd2.rawQuery("SELECT objetivo FROM datosIN WHERE tramo = '"+ spinner.getSelectedItem().toString() +"' ",null);

                Cursor ratio = bbdd2.rawQuery("SELECT ratio FROM datosIN WHERE tramo = '"+ spinner.getSelectedItem().toString() +"' ",null);

                Cursor sensi = bbdd2.rawQuery("SELECT sensibilidad FROM datosIN WHERE tramo = '"+ spinner.getSelectedItem().toString() +"' ",null);


                if (objetivo.moveToFirst() && objetivo != null){

                    objetivo.moveToFirst();

                    objetivos = objetivo.getFloat(0);

                }

                if (ratio.moveToFirst()  && ratio != null){

                    ratio.moveToFirst();

                    ratios = ratio.getFloat(0);

                }

                if (sensi.moveToFirst() && sensi != null){

                    sensi.moveToFirst();

                     sensibilidad = sensi.getFloat(0);


                }

                edit1.setText(Float.toString(objetivos));
                edit2.setText(Float.toString(ratios));
                edit3.setText(Float.toString(sensibilidad));




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


       btn4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
               startActivityForResult(i,0);


           }
       });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                switch (spinner.getSelectedItem().toString()){

                    case "Noche":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {

                            objetivos = Float.parseFloat(edit1.getText().toString());
                            ratios = Float.parseFloat(edit2.getText().toString());
                            sensibilidad = Float.parseFloat(edit3.getText().toString());

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivos + "', ratio = '" + ratios + "', sensibilidad = '" + sensibilidad + "' WHERE tramo = 'Noche'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();


                        }

                        break;

                    case "Desayuno":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {


                            objetivo_desayuno = Float.parseFloat(edit1.getText().toString());
                            ratio_desayuno = Float.parseFloat(edit2.getText().toString());
                            sensi_desayuno = Float.parseFloat(edit3.getText().toString());

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivo_desayuno + "', ratio = '" + ratio_desayuno + "', sensibilidad = '" + sensi_desayuno + "' WHERE tramo = 'Desayuno'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();

                        }

                        break;


                    case "Media Mañana":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {

                            objetivo_media = Float.parseFloat(edit1.getText().toString());
                            ratio_media = Float.parseFloat(edit2.getText().toString());
                            sensi_media = Float.parseFloat(edit3.getText().toString());

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivo_media + "', ratio = '" + ratio_media + "', sensibilidad = '" + sensi_media + "' WHERE tramo = 'Media Mañana'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();


                        }

                        break;

                    case "Almuerzo":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {

                            objetivo_almuerzo = Float.parseFloat(edit1.getText().toString());
                            ratio_almuerzo = Float.parseFloat(edit2.getText().toString());
                            sensi_almuerzo = Float.parseFloat(edit3.getText().toString());
                            ;

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivo_almuerzo + "', ratio = '" + ratio_almuerzo + "', sensibilidad = '" + sensi_almuerzo + "' WHERE tramo = 'Almuerzo'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();



                        }

                        break;

                    case "Merienda":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {

                            objetivo_merienda = Float.parseFloat(edit1.getText().toString());
                            ratio_merienda = Float.parseFloat(edit2.getText().toString());
                            sensi_merienda = Float.parseFloat(edit3.getText().toString());

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivo_merienda + "', ratio = '" + ratio_merienda + "', sensibilidad = '" + sensi_merienda + "' WHERE tramo = 'Merienda'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();

                        }

                        break;

                    case "Cena":

                        if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("") || edit3.getText().toString().equals("") ){

                            Toast.makeText(Introducir_Datos.this, "Debes introducir todos los datos", Toast.LENGTH_SHORT).show();

                        }else {

                            objetivo_cena = Float.parseFloat(edit1.getText().toString());
                            ratio_cena = Float.parseFloat(edit2.getText().toString());
                            sensi_cena = Float.parseFloat(edit3.getText().toString());

                            bbdd.execSQL("UPDATE datosIN SET objetivo = '" + objetivo_cena + "', ratio = '" + ratio_cena + "', sensibilidad = '" + sensi_cena + "' WHERE tramo = 'Cena'");

                            Intent i = new Intent(Introducir_Datos.this, MainActivity.class);
                            startActivityForResult(i,0);
                            finish();

                        }

                        break;




                }





            }
        });









    }






    public void consultarListaTramos(){

        final Connect_SQlite admin = new Connect_SQlite(this, Connect_SQlite.DATABASE_NAME, null, Connect_SQlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        Introducir_Datos tramo = new Introducir_Datos();
        tramos2 = new ArrayList<Introducir_Datos>();

        //Select

        Cursor cursor = bbdd2.rawQuery("SELECT tramo FROM datosIN",null);

        if(cursor.moveToFirst()) {


            do {

                tramo = new Introducir_Datos();
                tramo.setTramo(cursor.getString(0));





                tramos2.add(tramo);

            }

            while (cursor.moveToNext());

        }

        obtenerListaTramos();

    }



    public void obtenerListaTramos(){

        final Connect_SQlite admin = new Connect_SQlite(this, Connect_SQlite.DATABASE_NAME, null, Connect_SQlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();
        final Cursor consulta = bbdd2.rawQuery("SELECT tramo FROM datosIN ", null);

        tramos = new ArrayList<String>();


        for (int i = 0; i < tramos2.size(); i++){

            tramos.add(tramos2.get(i).getTramo());



        }



    }




}