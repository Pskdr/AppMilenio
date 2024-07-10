package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CebaderosGroup")
public class CebaderoGroup {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private long idOrden;
    private long idCebadero;

    public CebaderoGroup(long id, long idOrden, long idCebadero) {
        this.id = id;
        this.idOrden = idOrden;
        this.idCebadero = idCebadero;
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

    public long getIdCebadero() {
        return idCebadero;
    }

    public void setIdCebadero(long idCebadero) {
        this.idCebadero = idCebadero;
    }
}
