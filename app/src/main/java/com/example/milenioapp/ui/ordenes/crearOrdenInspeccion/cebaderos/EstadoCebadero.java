package com.example.milenioapp.ui.ordenes.crearOrdenInspeccion.cebaderos;

public class EstadoCebadero {
    private String abreviado;
    private String text;

    public EstadoCebadero(String abreviado, String text) {
        this.abreviado = abreviado;
        this.text = text;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
