package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AmbientesGroup")
public class AmbienteGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idOrden;
    private long idAmbiente;
    private String s;//S - N

    public AmbienteGroup(long idOrden, long idAmbiente, String s) {
        this.idOrden = idOrden;
        this.idAmbiente = idAmbiente;
        this.s = s;
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

    public long getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(long idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
