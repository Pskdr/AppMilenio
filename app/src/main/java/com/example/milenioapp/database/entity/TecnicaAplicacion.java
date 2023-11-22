package com.example.milenioapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.checkerframework.checker.nullness.qual.NonNull;

@Entity(tableName = "TecnicasAplicacion")
public class TecnicaAplicacion {
    @PrimaryKey
    @NonNull
    private long id;
    private String tecnica;
    private String ingrediente;
    private long idZona;

    public TecnicaAplicacion(long id, String tecnica, String ingrediente, long idZona) {
        this.id = id;
        this.tecnica = tecnica;
        this.ingrediente = ingrediente;
        this.idZona = idZona;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public long getIdZona() {
        return idZona;
    }

    public void setIdZona(long idZona) {
        this.idZona = idZona;
    }
}
