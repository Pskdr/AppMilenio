package com.example.milenioapp.ui.ordenes.crearOrden.zona;

public class ProductoMostrar {

    private String producto;
    private String ingredienteActivo;

    public ProductoMostrar(String producto, String ingredienteActivo) {
        this.producto = producto;
        this.ingredienteActivo = ingredienteActivo;
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
}
