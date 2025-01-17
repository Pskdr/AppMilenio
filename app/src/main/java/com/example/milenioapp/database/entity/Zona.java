package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(tableName = "Zonas")
public class Zona {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String descripcion;
    private long idTipo;

    public Zona(String descripcion, long idTipo) {
        this.descripcion = descripcion;
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

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }
}
