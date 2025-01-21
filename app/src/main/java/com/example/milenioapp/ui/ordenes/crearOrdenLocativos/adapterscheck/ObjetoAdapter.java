package com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck;


public class ObjetoAdapter {
    private long idPrincipal;
    private long idGroup;
    private String nombre;
    private String hallado;

    public ObjetoAdapter(long idPrincipal, long idGroup, String nombre, String hallado) {
        this.idPrincipal = idPrincipal;
        this.idGroup = idGroup;
        this.nombre = nombre;
        this.hallado = hallado;
    }

    public long getIdPrincipal() {
        return idPrincipal;
    }

    public void setIdPrincipal(long idPrincipal) {
        this.idPrincipal = idPrincipal;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHallado() {
        return hallado;
    }

    public void setHallado(String hallado) {
        this.hallado = hallado;
    }
}
