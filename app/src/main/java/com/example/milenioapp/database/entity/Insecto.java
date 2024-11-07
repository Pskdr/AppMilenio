package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Insectos")
public class Insecto {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String descripcion;
    private long idTipoInsecto;

    public Insecto( String descripcion, long idTipoInsecto) {
        this.descripcion = descripcion;
        this.idTipoInsecto = idTipoInsecto;
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

    public long getIdTipoInsecto() {
        return idTipoInsecto;
    }

    public void setIdTipoInsecto(long idTipoInsecto) {
        this.idTipoInsecto = idTipoInsecto;
    }
}
