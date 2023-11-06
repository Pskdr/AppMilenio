package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.milenioapp.database.entity.EstadoCebadero;

@Dao
public interface EstadoCebaderoDAO {

    @Insert
    void insert(EstadoCebadero estadoCebadero);
    @Update
    void update(EstadoCebadero estadoCebadero);
    @Delete
    void delete(EstadoCebadero estadoCebadero);

}
