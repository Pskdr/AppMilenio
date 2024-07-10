package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrden.AgregarObjeto;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.GrupoZonaMostrar;

import java.util.List;

@Dao
public interface ZonaDAO {

    @Query("select * from zonas where idTipo = :idTipo")
    List<Zona> getByType(long idTipo);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Zona> zonas);

    @Delete
    void delete(Zona zona);
    @Insert
    void insert(Zona zona);
    @Update
    void update(Zona zona);

    @Query("select id, -1 as idOrden, descripcion as nombre, '' as producto, '' as ingredienteActivo, '' as docificacion from zonas where idTipo = :idTipo and defalt = 'S'")
    List<GrupoZonaMostrar> getByTypeDefault(long idTipo);

    @Query("select * from zonas where idTipo = :idTipo")
    List<Zona> getAll(long idTipo);


}
