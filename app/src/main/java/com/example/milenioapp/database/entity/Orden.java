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
    private long idUsuario;
    private String serial;
    private String operario;
    private long horaIngreso;
    private long horaSalida;
    private String observacionesTecnicas;
    private String correctivos;
    private String firmaOperario;
    private String firmaAyudante;
    private String tipoServicio;
    private String objetivoDelServicio;
    private String estadoEnvio;
    private String tipoOrden; //S (servicio) - I (Inspección)

    public Orden(long idEmpleado, long fechaInicio, long fechaUsuario, long idUsuario, String serial, String operario,
                 long horaIngreso, long horaSalida, String observacionesTecnicas, String correctivos, String firmaOperario,
                 String firmaAyudante, String tipoServicio,String objetivoDelServicio,String tipoOrden,String estadoEnvio) {
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaUsuario = fechaUsuario;
        this.idUsuario = idUsuario;
        this.serial = serial;
        this.operario = operario;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.observacionesTecnicas = observacionesTecnicas;
        this.correctivos = correctivos;
        this.firmaOperario = firmaOperario;
        this.firmaAyudante = firmaAyudante;
        this.tipoServicio = tipoServicio;
        this.objetivoDelServicio = objetivoDelServicio;
        this.tipoOrden = tipoOrden;
        this.estadoEnvio = estadoEnvio;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getObjetivoDelServicio() {
        return objetivoDelServicio;
    }

    public void setObjetivoDelServicio(String objetivoDelServicio) {
        this.objetivoDelServicio = objetivoDelServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public long getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(long horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public long getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(long horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getObservacionesTecnicas() {
        return observacionesTecnicas;
    }

    public void setObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
    }

    public String getCorrectivos() {
        return correctivos;
    }

    public void setCorrectivos(String correctivos) {
        this.correctivos = correctivos;
    }

    public String getFirmaOperario() {
        return firmaOperario;
    }

    public void setFirmaOperario(String firmaOperario) {
        this.firmaOperario = firmaOperario;
    }

    public String getFirmaAyudante() {
        return firmaAyudante;
    }

    public void setFirmaAyudante(String firmaAyudante) {
        this.firmaAyudante = firmaAyudante;
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

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
