package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.LamparaGroup;

import java.util.List;

@Dao
public interface LamparaGroupDAO {
    @Query("select * from lamparasgroups")
    List<LamparaGroup> getAll();

    @Insert
    void insert(LamparaGroup lamparaGroup);
    @Update
    void update(LamparaGroup lamparaGroup);
    @Delete
    void delete(LamparaGroup lamparaGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LamparaGroup> lamparaGroupList);

    @Query("select * from lamparasgroups where idOrden = :id")
    List<LamparaGroup> getById(long id);
}
