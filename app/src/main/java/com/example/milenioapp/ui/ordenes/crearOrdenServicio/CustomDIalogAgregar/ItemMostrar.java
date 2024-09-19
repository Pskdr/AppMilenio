package com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar;

public class ItemMostrar {
    private long id;
    private String nombre;

    public ItemMostrar(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
