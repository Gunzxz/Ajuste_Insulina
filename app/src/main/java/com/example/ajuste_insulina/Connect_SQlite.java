package com.example.ajuste_insulina;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Connect_SQlite extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "Ajuste_Insulina.db";
        public static final int DATABASE_VERSION = 1;

    public Connect_SQlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE IF NOT EXISTS datosIN (glucemia float(5), tramo varchar(20), raciones float(5), horario varchar(30), objetivo float(5), ratio float(5), sensibilidad float(5), insulina float (5))");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Noche', '00:00 - 06:30') ");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Desayuno', '06:30 - 12:30') ");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Media Mañana', '12:30 - 14:00') ");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Almuerzo', '14:00 - 17:00') ");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Merienda', '17:00 - 20:30') ");
            db.execSQL("INSERT INTO datosIN (tramo,horario) VALUES ('Cena', '20:30 - 24:00') ");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {




        }


}
