package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.ui.home.empresa.OrdenMostrar;

import java.util.List;

@Dao
public interface OrdenDAO {

    @Insert
    long insert(Orden orden);
    @Update
    void update(Orden orden);
    @Delete
    void delete(Orden orden);

    @Query("select * from ordenes where id = :idOrden")
    Orden getByid(long idOrden);
    @Query("select id, operario as nombre, horaIngreso as horaEntrada, horaSalida,estadoEnvio," +
            "fechaInicio, tipoOrden from ordenes")
    List<OrdenMostrar> getAllMostrar();

    @Query("select id, operario as nombre, horaIngreso as horaEntrada, horaSalida,estadoEnvio," +
            "fechaInicio, tipoOrden from ordenes where tipoOrden = 'S'  order by id DESC")
    List<OrdenMostrar> getAllByDate();

    @Query("select COUNT(id) from ordenes")
    int getNum();
}
