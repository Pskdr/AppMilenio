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

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Higiene> Higienes);
}
