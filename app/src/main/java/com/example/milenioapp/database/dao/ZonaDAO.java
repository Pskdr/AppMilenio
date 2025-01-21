package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.ObjetoAdapter;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.GrupoZonaMostrar;

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

    @Query("select 0 as id,id as idZona, -1 as idOrden, descripcion as nombre, '' as producto, '' as ingredienteActivo, '' as docificacion, '' as tecnicaAplicacion from zonas where idTipo = :idTipo")
    List<GrupoZonaMostrar> getByTypeDefault(long idTipo);

    @Query("select * from zonas where idTipo = :idTipo")
    List<Zona> getAll(long idTipo);


    @Query("select id, descripcion as nombre from zonas where idTipo = :idTipo")
    List<ItemMostrar> getByTypeDefaultAgregar(long idTipo);

    @Query("select * from zonas where id = :idZona")
    Zona getById(long idZona);
    @Query("select count(*) from zonas")
    long getZonaCount();

    @Query("select id as idPrincipal, 0 as idGroup, descripcion as nombre, 'N' as hallado from zonas")
    List<ObjetoAdapter> getZonaMostrar();
}
