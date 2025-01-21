package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Ambiente;

import java.util.List;

@Dao
public interface AmbienteDAO {

    @Query("select * from ambientes")
    List<Ambiente> geatAll();

    @Insert
    void insert(Ambiente ambiente);

    @Delete
    void delete(Ambiente ambiente);

    @Update
    void update(Ambiente ambiente);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Ambiente> ambienteList);


}
