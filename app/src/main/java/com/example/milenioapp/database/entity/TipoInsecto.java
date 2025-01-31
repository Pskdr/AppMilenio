package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TipoInsectos")
public class TipoInsecto {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String descripcion;
    private String abreviado;

    public TipoInsecto(long id, String descripcion, String abreviado) {
        this.id = id;
        this.descripcion = descripcion;
        this.abreviado = abreviado;
    }


    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
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
}
