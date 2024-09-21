package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.NroCebaderos;

@Dao
public interface NroCebaderosDAO {
    @Query("select * from nroscebaderos where idOrden = :idOrden")
    NroCebaderos getByIdOrden(long idOrden);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NroCebaderos nroCebaderos);
    @Update
    void update(NroCebaderos nroCebaderos);
    @Delete
    void delete(NroCebaderos nroCebaderos);
}
