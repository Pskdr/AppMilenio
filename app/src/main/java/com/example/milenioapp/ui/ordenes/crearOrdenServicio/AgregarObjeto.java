package com.example.milenioapp.ui.ordenes.crearOrdenServicio;

public class AgregarObjeto {

    private long id;
    private String description;
    private long idTipo;


    public AgregarObjeto(long id, String description, long idTipo) {
        this.id = id;
        this.description = description;
        this.idTipo = idTipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }
}
