package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.CebaderoGroup;

import java.util.List;

@Dao
public interface CebaderoGroupDAO {
    @Query("select * from CebaderosGroup")
    List<CebaderoGroup> getAll();
    @Insert
    long insert(CebaderoGroup cebaderoGroup);
    @Update
    void update(CebaderoGroup cebaderoGroup);
    @Delete
    void delete(CebaderoGroup cebaderoGroup);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CebaderoGroup> cebaderoGroups);
}
