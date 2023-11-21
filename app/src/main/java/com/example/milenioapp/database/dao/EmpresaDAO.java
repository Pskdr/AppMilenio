package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.ui.home.Empresa;

@Dao
public interface EmpresaDAO {

    @Insert
    void insert(Empresa empresa);
    @Delete
    void delete (Empresa empresa);
    @Update
    void update(Empresa empresa);
}
