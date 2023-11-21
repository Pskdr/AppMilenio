package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.Zona;

import java.util.List;

@Dao
public interface TecnicaAplicacionDAO {
    @Query("select * from tecnicasaplicacion where idZona = :idZona")
    List<TecnicaAplicacion> getAllByZona(long idZona);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TecnicaAplicacion> tecnicaAplicacions);

    @Update
    void uodate(TecnicaAplicacion tecnicaAplicacion);
    @Delete
    void delete(TecnicaAplicacion tecnicaAplicacion);
    @Insert
    void insert(TecnicaAplicacion tecnicaAplicacion);

}
