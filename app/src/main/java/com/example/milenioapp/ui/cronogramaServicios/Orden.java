package com.example.milenioapp.ui.cronogramaServicios;

public class Orden {
    private long id;
    private String lugar;
    private String encargado;
    private String hora;

    public Orden(long id, String lugar, String encargado, String hora) {
        this.id = id;
        this.lugar = lugar;
        this.encargado = encargado;
        this.hora = hora;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
