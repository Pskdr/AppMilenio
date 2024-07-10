package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HigienesGroup")
public class HigieneGroup {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long idOrden;
    private long idHigiene;

    public HigieneGroup(long id, long idOrden, long idHigiene) {
        this.id = id;
        this.idOrden = idOrden;
        this.idHigiene = idHigiene;
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

    public long getIdHigiene() {
        return idHigiene;
    }

    public void setIdHigiene(long idHigiene) {
        this.idHigiene = idHigiene;
    }
}
