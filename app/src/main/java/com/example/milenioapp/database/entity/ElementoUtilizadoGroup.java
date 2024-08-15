package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ElementosUtilizadosGroup")
public class ElementoUtilizadoGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private long idElemento;
    @NonNull
    private long idOrden;

    public ElementoUtilizadoGroup(long idElemento, long idOrden) {
        this.idElemento = idElemento;
        this.idOrden = idOrden;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(long idElemento) {
        this.idElemento = idElemento;
    }

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }
}
