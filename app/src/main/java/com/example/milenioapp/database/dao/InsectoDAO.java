package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.ui.ordenes.crearOrden.AgregarObjeto;

import java.util.List;

@Dao
public interface InsectoDAO {


    @Query("select * from insectos")
    List<Insecto> getAll();

    @Query("select * from insectos where idTipoInsecto = :idTipoInsecto")
    List<Insecto> getByTipo(long idTipoInsecto);

    @Query("select id,descripcion,idTipoInsecto from insectos where idTipoInsecto = :idTipoInsecto")
    List<AgregarObjeto> getByTipoMostrar(long idTipoInsecto);
    @Insert
    void insert(Insecto insecto);
    @Update
    void update(Insecto insecto);
    @Delete
    void delete(Insecto insecto);
}
