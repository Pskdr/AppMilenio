package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.ui.ordenes.certificado.ItemZonasMostrar;

import java.util.List;

@Dao
public interface CebaderoGroupDAO {
    @Query("select * from CebaderosGroup")
    List<CebaderoGroup> getAll();
    @Insert
    long insert(CebaderoGroup cebaderoGroup);
    @Update
    void update(CebaderoGroup cebaderoGroup);
    @Delete
    void delete(CebaderoGroup cebaderoGroup);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CebaderoGroup> cebaderoGroups);

    @Query("select * from cebaderosgroup where idOrden =:id")
    List<CebaderoGroup> getByOrdenId(long id);

    @Query("SELECT  cebaderosgroup.id as id, insectos.descripcion AS nombre, cebaderosgroup.zona as tecnica, '' as ingredienteActivo  " +
            "            FROM insectosgroup " +
            "            INNER JOIN insectos ON insectosgroup.idInsecto = insectos.id " +
            "            INNER JOIN cebaderosgroup ON insectosgroup.idOrden = cebaderosgroup.idOrden " +
            "            WHERE insectosgroup.idOrden = :idOrden AND cebaderosgroup.idOrden = :idOrden")
    List<ItemZonasMostrar> getItemsMostrar(long idOrden);
}
