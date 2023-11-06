package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Facturas")
public class Factura {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idEmpleado;
    private long fechaInicio;
    private long fechaUsuario;
    private long idCebadero;
    private long idFactura;
    private long idUsuario;
    private String serial;

    public Factura( long idEmpleado, long fechaInicio, long fechaUsuario, long idCebadero, long idFactura, long idUsuario, String serial) {
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaUsuario = fechaUsuario;
        this.idCebadero = idCebadero;
        this.idFactura = idFactura;
        this.idUsuario = idUsuario;
        this.serial = serial;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
