package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.ObjetoAdapter;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.elementosUtilizados.ElementoUtilizadoMostrar;

import java.util.List;

@Dao
public interface ElementoUtilizadoDAO {
    @Query("select * from elementosutilizados")
    List<ElementoUtilizado> getAll();

    @Insert
    void insert(ElementoUtilizado elementoUtilizado);
    @Update
    void update(ElementoUtilizado elementoUtilizado);
    @Delete
    void delete(ElementoUtilizado elementoUtilizado);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ElementoUtilizado> elementoUtilizados);
    @Query("select elementosutilizadosgroup.id as id, elementosutilizados.id as idElemento, elementosutilizados.descripcion as descripcion from elementosutilizadosgroup inner join elementosutilizados on elementosutilizadosgroup.idElemento = elementosutilizados.id" +
            " where elementosutilizadosgroup.idOrden = :id")
    List<ElementoUtilizadoMostrar> getElementosGuardados(long id);
    @Query("select elementosutilizados.id as id, elementosutilizados.descripcion as nombre from elementosutilizados")
    List<ItemMostrar> gettAllAgregar();

    @Query("select elementosutilizados.id as idPrincipal, 0 as idGroup, elementosutilizados.descripcion as nombre, 'N' as hallado from elementosutilizados")
    List<ObjetoAdapter> getAgregar();

    @Query("select * from elementosutilizados where id = :idElemento")
    ElementoUtilizado getById(long idElemento);
}
