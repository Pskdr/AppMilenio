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
    private String s; //S - N - NA

    public HigieneGroup( long idOrden, long idHigiene, String s) {
        this.idOrden = idOrden;
        this.idHigiene = idHigiene;
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
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

    public long getIdHigiene() {
        return idHigiene;
    }

    public void setIdHigiene(long idHigiene) {
        this.idHigiene = idHigiene;
    }
}
