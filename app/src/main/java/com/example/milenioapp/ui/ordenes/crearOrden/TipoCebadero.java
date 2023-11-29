package com.example.milenioapp.ui.ordenes.crearOrden;

public class TipoCebadero {
    private long id;
    private String descripcion;
    private String abreviado;

    public TipoCebadero(long id, String descripcion, String abreviado) {
        this.id = id;
        this.descripcion = descripcion;
        this.abreviado = abreviado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }
}
