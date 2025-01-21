package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Hallazgo;

import java.util.List;

@Dao
public interface HallazgoDAO {

    @Query("select * from hallazgos")
    List<Hallazgo> getAll();

    @Insert
    void insert(Hallazgo hallazgo);

    @Delete
    void delete(Hallazgo hallazgo);

    @Update
    void update(Hallazgo hallazgo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Hallazgo> hallazgoList);
}
