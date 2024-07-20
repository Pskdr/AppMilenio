package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.ui.ordenes.crearOrden.AgregarObjeto;
import com.example.milenioapp.ui.ordenes.crearOrden.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrden.insecto.InsectoGroupMostrar;

import java.util.List;

@Dao
public interface InsectoDAO {


    @Query("select * from insectos")
    List<Insecto> getAll();

    @Query("select insectosgroup.id as id,insectos.descripcion as nombre,insectos.id as idInsecto, insectosgroup.hallado as s " +
            "from insectosgroup inner join insectos on insectos.id = insectosgroup.idInsecto " +
            "where insectosgroup.idOrden = :idOrden")
    List<InsectoGroupMostrar> getInsectosGuardados(long idOrden);
    @Query("select * from insectos where idTipoInsecto = :idTipoInsecto")
    List<Insecto> getByTipo(long idTipoInsecto);

    @Query("select id,descripcion,idTipoInsecto as idTipo from insectos where idTipoInsecto = :idTipoInsecto")
    List<AgregarObjeto> getByTipoMostrar(long idTipoInsecto);


    @Query("select id, insectos.descripcion as nombre from insectos where idTipoInsecto = :idTipoInsecto")
    List<ItemMostrar> gettAllAgregar(long idTipoInsecto);
    @Insert
    void insert(Insecto insecto);
    @Update
    void update(Insecto insecto);
    @Delete
    void delete(Insecto insecto);

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Insecto> insectoList);

    @Query("select * from insectos where id = :idInsecto")
    Insecto getById(long idInsecto);
}
