package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ZonaGroup")
public class ZonaGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idZona;
    private long idOrden;
    private String producto;
    private String ingredienteActivo;
    private String dosificacion;
    private String tecnicaAplicacion;
    private String fechaVencimientoProducto;

    public ZonaGroup(long idZona, long idOrden, String producto, String ingredienteActivo, String dosificacion,
                     String tecnicaAplicacion, String fechaVencimientoProducto) {
        this.idZona = idZona;
        this.idOrden = idOrden;
        this.producto = producto;
        this.ingredienteActivo = ingredienteActivo;
        this.dosificacion = dosificacion;
        this.tecnicaAplicacion=tecnicaAplicacion;
        this.fechaVencimientoProducto = fechaVencimientoProducto;
    }

    public String getFechaVencimientoProducto() {
        return fechaVencimientoProducto;
    }

    public void setFechaVencimientoProducto(String fechaVencimientoProducto) {
        this.fechaVencimientoProducto = fechaVencimientoProducto;
    }

    public String getTecnicaAplicacion() {
        return tecnicaAplicacion;
    }

    public void setTecnicaAplicacion(String tecnicaAplicacion) {
        this.tecnicaAplicacion = tecnicaAplicacion;
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

    public String getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(String dosificacion) {
        this.dosificacion = dosificacion;
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
