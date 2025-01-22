package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.MaterialGroup;

import java.util.List;

@Dao
public interface MaterialGroupDAO {

    @Query("SELECT * FROM MaterialesGroup")
    List<MaterialGroup> getAll();

    @Insert
    void insert(MaterialGroup materialGroup);

    @Update
    void update(MaterialGroup materialGroup);

    @Delete
    void delete(MaterialGroup materialGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MaterialGroup> materialGroups);

    @Query("delete from MaterialesGroup where idOrden = :id")
    void deleteByOrden(long id);
}
