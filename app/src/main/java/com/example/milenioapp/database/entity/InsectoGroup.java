package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InsectosGroup")
public class InsectoGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idOrden;
    private long idInsecto;
    private String hallado;

    public InsectoGroup(long idOrden, long idInsecto, String hallado) {
        this.idOrden = idOrden;
        this.idInsecto = idInsecto;
        this.hallado = hallado;
    }

    public String getHallado() {
        return hallado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }

    public long getIdInsecto() {
        return idInsecto;
    }

    public void setIdInsecto(long idInsecto) {
        this.idInsecto = idInsecto;
    }

    public String isHallado() {
        return hallado;
    }

    public void setHallado(String hallado) {
        this.hallado = hallado;
    }
}
