package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Orden;
@Dao
public interface OrdenDAO {

    @Insert
    void insert(Orden orden);
    @Update
    void update(Orden orden);
    @Delete
    void delete(Orden orden);

    @Query("select * from ordenes where id = :idOrden")
    Orden getByid(long idOrden);
}
