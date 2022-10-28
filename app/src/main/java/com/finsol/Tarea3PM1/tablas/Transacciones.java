package com.finsol.Tarea3PM1.tablas;

public class Transacciones {
    //Nombre de la Base de Datos
    public static final String NameDatabase = "PMT3DB";

    /*Creacion de la base de Datos*/
    public static final String TablaPersonas= "personas";

    /*Creación de la tabla contactos*/
    public static final String id = "id";
    public static final String nombre = "nombre";
    public static final String apellido = "apellido";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";

    //DDL
    public static final String createTablePersonas= "CREATE TABLE "+Transacciones.TablaPersonas+
            " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "apellido TEXT," +
            "edad INTEGER," +
            "correo TEXT," +
            "direccion TEXT)";

    public static final String GetPersonas= "SELECT * FROM "+Transacciones.TablaPersonas;

    public static final String DropTablePersonas= "DROP TABLE IF EXISTS personas";

    public static final String DeleteRegistro = "DELETE FROM "+TablaPersonas;

}

