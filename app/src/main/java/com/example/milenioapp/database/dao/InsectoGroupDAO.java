package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.InsectoGroup;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface InsectoGroupDAO {

    @Query("select * from insectosgroup")
    List<InsectoGroup> getAll();

    @Insert
    void insert(InsectoGroup insectoGroup);
    @Delete
    void delete(InsectoGroup insectoGroup);
    @Update
    void update(InsectoGroup insectoGroup);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<InsectoGroup> insectoGroupsInsert);

    @Query("delete from insectosgroup where idOrden = :id")
    void deleteByOrden(long id);
}
