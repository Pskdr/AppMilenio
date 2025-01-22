package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.HallazgoGrup;

import java.util.List;

@Dao
public interface HallazgoGroupDAO {

    @Query("select * from hallazgosgroup")
    List<HallazgoGrup> getAll();

    @Insert
    void insert(HallazgoGrup hallazgoGrup);

    @Update
    void update(HallazgoGrup hallazgoGrup);

    @Delete
    void delete(HallazgoGrup hallazgoGrup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HallazgoGrup> hallazgoGrups);
}
