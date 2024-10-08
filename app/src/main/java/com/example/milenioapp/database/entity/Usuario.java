package com.example.milenioapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Usuarios")
public class Usuario {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @NonNull
    private String correo;
    private String nombre;
    @NonNull
    private String password;
    private String idDevice;
    private String aprobado;

    public Usuario(long id, @NonNull String correo,String nombre, @NonNull String password,
                   @NonNull String idDevice, String aprobado) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
        this.idDevice = idDevice;
        this.aprobado = aprobado;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(@NonNull String correo) {
        this.correo = correo;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
