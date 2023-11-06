package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Empleado;
import com.example.milenioapp.ui.tecnicos.Tecnico;

import java.util.List;

@Dao
public interface EmpleadoDAO {

    @Query("select * from empleados")
    List<Empleado> getAll();

    @Insert
    void insert(Empleado empleado);

    @Update
    void update(Empleado empleado);

    @Delete
    void delete(Empleado empleado);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Empleado> empleados);

    @Query("select id,nombre,foto from empleados")
    List<Tecnico> getTecnicos();

    @Query("select * from empleados where id = :id")
    Empleado getById(long id);
}
