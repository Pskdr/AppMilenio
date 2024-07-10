package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Higiene")
public class Higiene {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nombre;
    private String s; //S - N - NA
    private long idTipoOrden;

    public Higiene(long id, String nombre, String s, long idTipoOrden) {
        this.id = id;
        this.nombre = nombre;
        this.s = s;
        this.idTipoOrden = idTipoOrden;
    }

    public long getIdTipoOrden() {
        return idTipoOrden;
    }

    public void setIdTipoOrden(long idTipoOrden) {
        this.idTipoOrden = idTipoOrden;
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

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
