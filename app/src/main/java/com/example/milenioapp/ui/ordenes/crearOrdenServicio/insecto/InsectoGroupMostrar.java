package com.example.milenioapp.ui.ordenes.crearOrdenServicio.insecto;

public class InsectoGroupMostrar {
    private long idInsectoGroup;
    private String nombre;
    private long idInsecto;
    private String s; //S - N - NA
    private String nivelInfestacion; //ALTO - MEDIO - BAJO

    public InsectoGroupMostrar(long idInsectoGroup, String nombre, long idInsecto, String s, String nivelInfestacion) {
        this.idInsectoGroup = idInsectoGroup;
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

    public long getIdInsectoGroup() {
        return idInsectoGroup;
    }

    public void setIdInsectoGroup(long idInsectoGroup) {
        this.idInsectoGroup = idInsectoGroup;
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
