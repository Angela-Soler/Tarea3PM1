package com.finsol.Tarea3PM1.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finsol.Tarea3PM1.tablas.Transacciones;


public class SQLiteConexion extends SQLiteOpenHelper{

    //Constructor SQLite
    public SQLiteConexion(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version){

        super(context, dbname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creacion de Objetos de la BD
        sqLiteDatabase.execSQL(Transacciones.createTablePersonas);//Creando tabla de contactos
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Eliminamos la data y las tablas de la aplicacion
        sqLiteDatabase.execSQL(Transacciones.DropTablePersonas);
        onCreate(sqLiteDatabase);
    }

}
