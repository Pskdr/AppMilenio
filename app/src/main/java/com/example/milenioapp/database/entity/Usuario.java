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
    @NonNull
    private String password;

    public Usuario(long id, @NonNull String correo, @NonNull String password) {
        this.id = id;
        this.correo = correo;
        this.password = password;
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
