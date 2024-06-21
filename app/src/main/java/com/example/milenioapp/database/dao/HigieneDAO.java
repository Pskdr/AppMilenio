package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Factura;
import com.example.milenioapp.database.entity.Higiene;

import java.util.List;

@Dao
public interface HigieneDAO {

    @Query("select * from higiene")
    List<Higiene> getAll();
    @Insert
    long insert(Higiene higiene);
    @Update
    void update(Higiene higiene);
    @Delete
    void delete(Higiene higiene);
}
