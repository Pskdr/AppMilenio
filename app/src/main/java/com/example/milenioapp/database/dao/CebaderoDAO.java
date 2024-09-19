package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.AgregarObjeto;

import java.util.List;

@Dao
public interface CebaderoDAO {

    @Query("select * from cebaderos")
    List<Cebadero> getAll();


    @Query("select id,descripcion,-1 as idTipo from cebaderos")
    List<AgregarObjeto> getAllMostrar();

    @Insert
    void insert(Cebadero cebadero);
    @Delete
    void delete(Cebadero cebadero);
    @Update
    void update(Cebadero cebadero);
}
