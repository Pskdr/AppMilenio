package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Factura;
@Dao
public interface FacturaDAO {

    @Insert
    long insert(Factura factura);
    @Update
    void update(Factura factura);
    @Delete
    void delete(Factura factura);
}
