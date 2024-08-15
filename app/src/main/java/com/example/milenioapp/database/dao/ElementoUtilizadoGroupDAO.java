package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.ElementoUtilizadoGroup;

import java.util.List;

@Dao
public interface ElementoUtilizadoGroupDAO {
    @Query("select * from elementosutilizadosgroup")
    List<ElementoUtilizadoGroupDAO> getAll();

    @Insert
    void insert(ElementoUtilizadoGroup elementoUtilizadoGroup);
    @Update
    void update(ElementoUtilizadoGroup elementoUtilizadoGroup);
    @Delete
    void delete(ElementoUtilizadoGroup elementoUtilizadoGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ElementoUtilizadoGroup> elementoUtilizadoGroups);
}
