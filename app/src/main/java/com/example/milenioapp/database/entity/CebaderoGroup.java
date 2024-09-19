package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CebaderosGroup")
public class CebaderoGroup {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private long idOrden;
    private String zona;
    private String nro;
    private String estado;
    private String observaciones;

    public CebaderoGroup(long idOrden, String zona, String nro, String estado, String observaciones) {
        this.idOrden = idOrden;
        this.zona = zona;
        this.nro = nro;
        this.estado = estado;
        this.observaciones = observaciones;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
