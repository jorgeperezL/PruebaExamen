package com.example.pruebaexamen;

public class Usuario {
    String nombre,apellido,correo,id;

    public Usuario(String nombre, String apellido, String correo, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.id = id;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
