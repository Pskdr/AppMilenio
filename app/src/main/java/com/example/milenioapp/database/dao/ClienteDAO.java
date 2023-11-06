package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ClienteDAO {

    @Insert
    void insert(Cliente cliente);
    @Update
    void update(Cliente cliente);
    @Delete
    void delete(Cliente cliente);

    @Query("select * from clientes")
    List<Cliente> getAll();

    @Query("select * from clientes where id = :id")
    Cliente getById(long id);
}
