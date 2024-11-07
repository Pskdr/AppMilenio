package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Usuario;

@Dao
public interface UsuarioDAO {

    @Query("select * from usuarios")
    Usuario getUser();

    @Insert
    void insert(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Update
    void update(Usuario usuario);


}
