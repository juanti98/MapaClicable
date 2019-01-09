package com.example.juan.mapaclicable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ConexionBBDD {
    private SQLiteOpenHelper sq;
    private Context c;

    public ConexionBBDD(Context c) {
        this.c = c;
        conexionConBBDD();
    }

    private void conexionConBBDD() {
        sq = new SQLiteOpenHelper(c, "Puntos", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE puntos (informacion varchar(500), lat varchar(100), lon varchar(100));";
                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    public void insercionBBDD(String info, String lat, String lon) {
        SQLiteDatabase bd = sq.getWritableDatabase();
        String insercion = "INSERT INTO puntos VALUES('" + info + "','" + lat + "','" + lon + "');";
        bd.execSQL(insercion);
    }

    public ArrayList<Puntos> getPuntos() {
        ArrayList<Puntos> pts = new ArrayList<>();
        String select = "SELECT * FROM puntos;";
        SQLiteDatabase bd = sq.getWritableDatabase();
        Cursor c = bd.rawQuery(select, null);

        while (c.moveToNext()){
            pts.add(new Puntos(c.getString(0),c.getString(1),c.getString(2)));
        }
        return pts;
    }
}
