package com.example.milenioapp.ui.ordenes.crearOrdenServicio.elementosUtilizados;

public class ElementoUtilizadoMostrar {
    private long id;
    private long idElemento;
    private String descripcion;

    public ElementoUtilizadoMostrar(long id, long idElemento, String descripcion) {
        this.id = id;
        this.idElemento = idElemento;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(long idElemento) {
        this.idElemento = idElemento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
