package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Clientes")
public class Cliente {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String nombre;
    private String NIT;
    private String direccion;
    private String telefono;
    private String nombreContacto;
    private String sede;
    private String email;

    public Cliente( String nombre, String NIT, String direccion, String telefono, String nombreContacto, String sede, String email) {
        this.nombre = nombre;
        this.NIT = NIT;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombreContacto = nombreContacto;
        this.sede = sede;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
