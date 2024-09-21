package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NrosCebaderos")
public class NroCebaderos {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nroRoedoresCapturados;
    private String nroCebaderosConsumo;
    private String nroCebaderosSucios;
    private String nroCebaderosInactivos;
    private long idOrden;

    public NroCebaderos(String nroRoedoresCapturados, String nroCebaderosConsumo,
                        String nroCebaderosSucios, String nroCebaderosInactivos, long idOrden) {
        this.nroRoedoresCapturados = nroRoedoresCapturados;
        this.nroCebaderosConsumo = nroCebaderosConsumo;
        this.nroCebaderosSucios = nroCebaderosSucios;
        this.nroCebaderosInactivos = nroCebaderosInactivos;
        this.idOrden = idOrden;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNroRoedoresCapturados() {
        return nroRoedoresCapturados;
    }

    public void setNroRoedoresCapturados(String nroRoedoresCapturados) {
        this.nroRoedoresCapturados = nroRoedoresCapturados;
    }

    public String getNroCebaderosConsumo() {
        return nroCebaderosConsumo;
    }

    public void setNroCebaderosConsumo(String nroCebaderosConsumo) {
        this.nroCebaderosConsumo = nroCebaderosConsumo;
    }

    public String getNroCebaderosSucios() {
        return nroCebaderosSucios;
    }

    public void setNroCebaderosSucios(String nroCebaderosSucios) {
        this.nroCebaderosSucios = nroCebaderosSucios;
    }

    public String getNroCebaderosInactivos() {
        return nroCebaderosInactivos;
    }

    public void setNroCebaderosInactivos(String nroCebaderosInactivos) {
        this.nroCebaderosInactivos = nroCebaderosInactivos;
    }
}
