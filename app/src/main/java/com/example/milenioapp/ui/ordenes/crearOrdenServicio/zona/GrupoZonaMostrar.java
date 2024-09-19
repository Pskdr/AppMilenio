package com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona;

public class GrupoZonaMostrar {
    private long id;
    private long idZona;
    private long idOrden;
    private String nombre;
    private String producto;
    private String ingredienteActivo;
    private String docificacion;
    private String tecnicaAplicacion;
    private String fechaVencimiento;


    public GrupoZonaMostrar(long id, long idZona, long idOrden, String nombre, String producto, String ingredienteActivo, String docificacion,
                            String tecnicaAplicacion, String fechaVencimiento) {
        this.id = id;
        this.idZona = idZona;
        this.idOrden = idOrden;
        this.nombre = nombre;
        this.producto = producto;
        this.ingredienteActivo = ingredienteActivo;
        this.docificacion = docificacion;
        this.tecnicaAplicacion = tecnicaAplicacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTecnicaAplicacion() {
        return tecnicaAplicacion;
    }

    public void setTecnicaAplicacion(String tecnicaAplicacion) {
        this.tecnicaAplicacion = tecnicaAplicacion;
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
