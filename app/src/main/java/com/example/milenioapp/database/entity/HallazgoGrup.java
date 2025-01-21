package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HallazgosGroup")
public class HallazgoGrup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idOrden;
    private long idHllazgo;
    private String s; //S - N

    public HallazgoGrup(long idOrden, long idHllazgo, String s) {
        this.idOrden = idOrden;
        this.idHllazgo = idHllazgo;
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

    public long getIdHllazgo() {
        return idHllazgo;
    }

    public void setIdHllazgo(long idHllazgo) {
        this.idHllazgo = idHllazgo;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
