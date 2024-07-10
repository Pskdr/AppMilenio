package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GrupoZonas")
public class GrupoZona {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idZona;
    private long idOrden;
    private String producto;
    private String ingredienteActivo;
    private String docificacion;

    public GrupoZona(long idZona, long idOrden, String producto, String ingredienteActivo, String docificacion) {
        this.idZona = idZona;
        this.idOrden = idOrden;
        this.producto = producto;
        this.ingredienteActivo = ingredienteActivo;
        this.docificacion = docificacion;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getIngredienteActivo() {
        return ingredienteActivo;
    }

    public void setIngredienteActivo(String ingredienteActivo) {
        this.ingredienteActivo = ingredienteActivo;
    }

    public String getDocificacion() {
        return docificacion;
    }

    public void setDocificacion(String docificacion) {
        this.docificacion = docificacion;
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
}
