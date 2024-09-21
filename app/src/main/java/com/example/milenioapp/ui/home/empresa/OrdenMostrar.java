package com.example.milenioapp.ui.home.empresa;

public class OrdenMostrar {
    private long id;
    private String nombre;
    private long horaEntrada;
    private long horaSalida;
    private String estadoEnvio;
    private long fechaInicio;
    private String tipoOrden;

    public OrdenMostrar(long id, String nombre, long horaEntrada, long horaSalida, String estadoEnvio, long fechaInicio,String tipoOrden) {
        this.id = id;
        this.nombre = nombre;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.estadoEnvio = estadoEnvio;
        this.fechaInicio = fechaInicio;
        this.tipoOrden = tipoOrden;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
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

    public long getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(long horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public long getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(long horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
