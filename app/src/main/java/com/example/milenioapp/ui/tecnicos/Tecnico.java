package com.example.milenioapp.ui.tecnicos;

public class Tecnico {
    private long id;
    private String nombre;
    private String foto;

    public Tecnico(long id, String nombre,String foto) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
