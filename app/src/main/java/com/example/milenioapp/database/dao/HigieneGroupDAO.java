package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.HigieneGroup;

import java.util.List;

@Dao
public interface HigieneGroupDAO {
    @Query("select * from higienesgroup")
    List<HigieneGroup> getAll();
    @Insert
    long insert(HigieneGroup higieneGroup);
    @Update
    void update(HigieneGroup higieneGroup);
    @Delete
    void delete(HigieneGroup higieneGroup);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HigieneGroup> higieneGroups);

    @Query("delete from higienesgroup where idOrden =:id")
    void deleteByOrden(long id);
}
