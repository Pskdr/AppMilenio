package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(tableName = "Cebaderos")
public class Cebadero {
    @PrimaryKey
    @NonNull
    private long id;
    private String descripcion;
    private String tipoCebadero;

    public Cebadero(@NonNull long id, String descripcion, String tipoCebadero) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoCebadero = tipoCebadero;
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

    public String getTipoCebadero() {
        return tipoCebadero;
    }

    public void setTipoCebadero(String tipoCebadero) {
        this.tipoCebadero = tipoCebadero;
    }
}
