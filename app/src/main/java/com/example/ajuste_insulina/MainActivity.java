package com.example.ajuste_insulina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btn, btn3;
    private EditText edit1, edit2;
    private TextView textView,textView2, textView3;
    String carpetaFuente= "fonts/GeosansLight.ttf";

    public Float objetivos, ratios, sensibilidad;

    ArrayList<String>tramos;
    ArrayList<Introducir_Datos> tramos2;

    private String tramo;

    public MainActivity(String tramo) {

        this.tramo = tramo;

    }

    public MainActivity() {



}

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }



    String[] strTramo = {"Noche", "Desayuno" ,"Media Mañana", "Almuerzo", "Merienda", "Cena"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        btn = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView = findViewById(R.id.textView);

        Typeface fuente = Typeface.createFromAsset(getAssets(),carpetaFuente);
        edit2.setTypeface(fuente);
        edit1.setTypeface(fuente);
        btn.setTypeface(fuente);
        btn3.setTypeface(fuente);
        textView2.setTypeface(fuente);
        textView3.setTypeface(fuente);
        textView.setTypeface(fuente);


        final Connect_SQlite admin = new Connect_SQlite(this, Connect_SQlite.DATABASE_NAME, null, Connect_SQlite.DATABASE_VERSION);
        final SQLiteDatabase bbdd = admin.getWritableDatabase();
        final SQLiteDatabase bbdd2 = admin.getReadableDatabase();

        consultarListaTramos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tramos);


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity.this, Introducir_Datos.class);
                startActivityForResult(i, 0);



            }
        });


        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                switch (spinner.getSelectedItem().toString()){

                    case "Noche":

                        textView2.setText("00:00 - 06:30");

                        break;

                    case "Desayuno":


                        textView2.setText("6:30 – 12:30");


                        break;


                    case "Media Mañana":


                        textView2.setText("12:30 – 14:00");


                        break;

                    case "Almuerzo":

                        textView2.setText("14:00 - 17:00");

                        break;

                    case "Merienda":


                        textView2.setText("17:00 - 20:30");


                        break;

                    case "Cena":


                        textView2.setText("20:30 - 24:00");


                        break;




                }






    }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit1.getText().toString().equals("") || edit2.getText().toString().equals("")){


                    Toast.makeText(MainActivity.this, "Debes introducir todos los datos correctamente...", Toast.LENGTH_SHORT).show();


                }else {

                    double resultado = ((Float.parseFloat(edit1.getText().toString()) - objetivos) / sensibilidad) + (Float.parseFloat(edit2.getText().toString()) * ratios);

                    DecimalFormat df = new DecimalFormat("0.0");

                    textView3.setText(df.format(resultado));

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

