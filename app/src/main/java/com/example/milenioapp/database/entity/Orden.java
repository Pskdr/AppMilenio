package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Ordenes")
public class Orden {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long idEmpleado;
    private long fechaInicio;
    private long fechaUsuario;
    private long idCebadero;
    private long idFactura;
    private long idUsuario;
    private String seria;

    public Orden( long idEmpleado, long fechaInicio, long fechaUsuario, long idCebadero, long idFactura, long idUsuario, String seria) {
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaUsuario = fechaUsuario;
        this.idCebadero = idCebadero;
        this.idFactura = idFactura;
        this.idUsuario = idUsuario;
        this.seria = seria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getFechaUsuario() {
        return fechaUsuario;
    }

    public void setFechaUsuario(long fechaUsuario) {
        this.fechaUsuario = fechaUsuario;
    }

    public long getIdCebadero() {
        return idCebadero;
    }

    public void setIdCebadero(long idCebadero) {
        this.idCebadero = idCebadero;
    }

    public long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(long idFactura) {
        this.idFactura = idFactura;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }
}
