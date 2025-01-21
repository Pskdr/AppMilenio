package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.milenioapp.database.entity.AmbienteGroup;

import java.util.List;

@Dao
public interface AmbienteGroupDAO {

    @Insert
    void insert(AmbienteGroup ambienteGroup);

    @Delete
    void delete(AmbienteGroup ambienteGroup);

    @Update
    void update(AmbienteGroup ambienteGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AmbienteGroup> ambienteGroups);
}
