package com.example.milenioapp.database.entity;

import androidx.room.Entity;

@Entity(tableName = "Zonas")
public class Zona {
    private long id;
    private String descripcion;
    private String defalt;
    private long idTipo;

    public Zona(long id, String descripcion, String defalt, long idTipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.defalt = defalt;
        this.idTipo = idTipo;
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

    public String getDefalt() {
        return defalt;
    }

    public void setDefalt(String defalt) {
        this.defalt = defalt;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }
}
