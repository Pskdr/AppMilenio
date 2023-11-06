package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.TipoInsecto;

import java.util.List;

@Dao
public interface TipoInsectoDAO {

    @Query("select * from tipoinsectos")
    List<TipoInsecto> getAll();
    @Insert
    void insert(TipoInsecto tipoInsecto);
    @Update
    void update(TipoInsecto tipoInsecto);
    @Delete
    void delete(TipoInsecto tipoInsecto);
}
