package com.example.milenioapp.ui.ordenes.crearOrden.insecto;

public class InsectoGroupMostrar {
    private long id;
    private String nombre;
    private long idInsecto;
    private String s; //S - N - NA
    private String nivelInfestacion; //ALTO - MEDIO - BAJO

    public InsectoGroupMostrar(long id, String nombre, long idInsecto, String s, String nivelInfestacion) {
        this.id = id;
        this.nombre = nombre;
        this.idInsecto = idInsecto;
        this.s = s;
        this.nivelInfestacion = nivelInfestacion;

    }

    public String getNivelInfestacion() {
        return nivelInfestacion;
    }

    public void setNivelInfestacion(String nivelInfestacion) {
        this.nivelInfestacion = nivelInfestacion;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
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

    public long getIdInsecto() {
        return idInsecto;
    }

    public void setIdInsecto(long idInsecto) {
        this.idInsecto = idInsecto;
    }
}
