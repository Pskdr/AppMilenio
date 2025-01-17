package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.TipoCliente;

import java.util.List;

@Dao
public interface TipoClienteDAO {

    @Insert
    void insert(TipoCliente tipoCliente);

    @Update
    void update(TipoCliente tipoCliente);

    @Delete
    void delete(TipoCliente tipoCliente);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TipoCliente> tipoClientes);

    @Query("select * from tiposcliente")
    List<TipoCliente> getAll();
}
