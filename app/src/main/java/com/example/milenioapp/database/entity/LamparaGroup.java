package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LamparasGroups")
public class LamparaGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String tipoDeInsecto;
    private String lamparaN;
    private String ubicacionLampara;
    private int cantadidadEncontrada;
    private String observaciones;
    private long idOrden;

    public LamparaGroup(String tipoDeInsecto, String lamparaN, String ubicacionLampara, int cantadidadEncontrada, String observaciones, long idOrden) {
        this.tipoDeInsecto = tipoDeInsecto;
        this.lamparaN = lamparaN;
        this.ubicacionLampara = ubicacionLampara;
        this.cantadidadEncontrada = cantadidadEncontrada;
        this.observaciones = observaciones;
        this.idOrden = idOrden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoDeInsecto() {
        return tipoDeInsecto;
    }

    public void setTipoDeInsecto(String tipoDeInsecto) {
        this.tipoDeInsecto = tipoDeInsecto;
    }

    public String getLamparaN() {
        return lamparaN;
    }

    public void setLamparaN(String lamparaN) {
        this.lamparaN = lamparaN;
    }

    public String getUbicacionLampara() {
        return ubicacionLampara;
    }

    public void setUbicacionLampara(String ubicacionLampara) {
        this.ubicacionLampara = ubicacionLampara;
    }

    public int getCantadidadEncontrada() {
        return cantadidadEncontrada;
    }

    public void setCantadidadEncontrada(int cantadidadEncontrada) {
        this.cantadidadEncontrada = cantadidadEncontrada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }
}
