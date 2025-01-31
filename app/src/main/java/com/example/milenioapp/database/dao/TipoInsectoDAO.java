package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.TipoInsectosAgregar;

import java.util.ArrayList;
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

    @Query("select id, descripcion as nombre from tipoinsectos")
    List<TipoInsectosAgregar> getTipoAgregar();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TipoInsecto> tipoInsectoList);
}
