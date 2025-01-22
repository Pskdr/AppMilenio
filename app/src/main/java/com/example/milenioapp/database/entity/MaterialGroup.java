package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MaterialesGroup")
public class MaterialGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private long idElemento;
    @NonNull
    private long idOrden;
    private String hallado;

    public MaterialGroup(long idElemento, long idOrden, String hallado) {
        this.idElemento = idElemento;
        this.idOrden = idOrden;
        this.hallado = hallado;
    }

    public String getHallado() {
        return hallado;
    }

    public void setHallado(String hallado) {
        this.hallado = hallado;
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

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }
}
