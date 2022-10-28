package com.finsol.Tarea3PM1.tablas;

public class Personas {
    public Integer id;
    public String nombre;
    public String apellido;
    public Integer edad;
    public String correo;
    public String direccion;

    //Constructor de la clase
    public Personas(){
        //Todo
    }

    public Personas(Integer id, String nombre, Integer edad, String apellido, String correo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.apellido = apellido;
        this.correo = correo;
        this.direccion = direccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getapellido() {
        return apellido;
    }

    public void setapellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getedad() {
        return edad;
    }

    public void setedad(Integer edad) {
        this.edad = edad;
    }

    public String getcorreo() {
        return correo;
    }

    public void setcorreo(String correo) {
        this.correo = correo;
    }

    public String getdireccion() {
        return direccion;
    }

    public void setdireccion(String direccion) {
        this.direccion = direccion;
    }

}
