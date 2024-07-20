package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Factura;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.ui.ordenes.crearOrden.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrden.hallazgos.HygieneItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface HigieneDAO {

    @Query("select * from higiene")
    List<Higiene> getAll();

    @Query("select higienesgroup.id as id,higiene.id as idHigiene,higiene.nombre as itemName,higienesgroup.s as s " +
            "from higienesgroup inner join higiene on higiene.id = higienesgroup.idHigiene " +
            "where idOrden = :idOrden")
    List<HygieneItem> getAgregados(long idOrden);
    @Insert
    long insert(Higiene higiene);
    @Update
    void update(Higiene higiene);
    @Delete
    void delete(Higiene higiene);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Higiene> Higienes);
    @Query("select id, higiene.nombre as nombre from higiene")
    List<ItemMostrar> getAllAgregar();

    @Query("select * from higiene where id = :idHigiene")
    Higiene getId(long idHigiene);
}
