package com.example.milenioapp.ui.ordenes.crearOrdenServicio.certificado;

public class ItemZonasMostrar {
    private long id;
    private String nombre;
    private String tecnica;
    private String ingredienteActivo;

    public ItemZonasMostrar(long id, String nombre, String tecnica, String ingredienteActivo) {
        this.id = id;
        this.nombre = nombre;
        this.tecnica = tecnica;
        this.ingredienteActivo = ingredienteActivo;
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

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getIngredienteActivo() {
        return ingredienteActivo;
    }

    public void setIngredienteActivo(String ingredienteActivo) {
        this.ingredienteActivo = ingredienteActivo;
    }
}
