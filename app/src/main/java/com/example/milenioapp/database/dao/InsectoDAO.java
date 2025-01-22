package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.ObjetoAdapter;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.AgregarObjeto;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.insecto.InsectoGroupMostrar;

import java.util.List;

@Dao
public interface InsectoDAO {


    @Query("select * from insectos")
    List<Insecto> getAll();

    @Query("select insectosgroup.id as idInsectoGroup,insectos.descripcion as nombre,insectos.id as idInsecto, insectosgroup.hallado as s, insectosgroup.nivelInfestacion as nivelInfestacion " +
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

    @Query("select id as idPrincipal, 0 as idGroup, descripcion as nombre, 'N' as hallado from insectos")
    List<ObjetoAdapter> getPlagaMostrar();

    @Query("select insectos.id as idPrincipal, insectosgroup.id as idGroup, insectos.descripcion as nombre, insectosgroup.hallado as hallado " +
            "from insectos inner join insectosgroup on insectos.id = insectosgroup.idInsecto " +
            "where insectosgroup.idOrden = :idOrden")
    List<ObjetoAdapter> getPlagaMostrar(long idOrden);
}
