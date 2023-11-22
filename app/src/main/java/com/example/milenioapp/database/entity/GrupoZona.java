package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GrupoZonas")
public class GrupoZona {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idZona;
    private long idOrden;
    private long idTecnica;

    public GrupoZona(long idZona, long idOrden, long idTecnica) {
        this.idZona = idZona;
        this.idOrden = idOrden;
        this.idTecnica = idTecnica;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdZona() {
        return idZona;
    }

    public void setIdZona(long idZona) {
        this.idZona = idZona;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }

    public long getIdTecnica() {
        return idTecnica;
    }

    public void setIdTecnica(long idTecnica) {
        this.idTecnica = idTecnica;
    }
}
