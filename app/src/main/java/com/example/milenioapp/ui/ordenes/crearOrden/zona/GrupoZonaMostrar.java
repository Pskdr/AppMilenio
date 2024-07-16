package com.example.milenioapp.ui.ordenes.crearOrden.zona;

public class GrupoZonaMostrar {
    private long id;
    private long idZona;
    private long idOrden;
    private String nombre;
    private String producto;
    private String ingredienteActivo;
    private String docificacion;


    public GrupoZonaMostrar(long id, long idZona, long idOrden, String nombre, String producto, String ingredienteActivo, String docificacion) {
        this.id = id;
        this.idZona = idZona;
        this.idOrden = idOrden;
        this.nombre = nombre;
        this.producto = producto;
        this.ingredienteActivo = ingredienteActivo;
        this.docificacion = docificacion;
    }

    public long getIdZona() {
        return idZona;
    }

    public void setIdZona(long idZona) {
        this.idZona = idZona;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
