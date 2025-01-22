package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.Material;
import com.example.milenioapp.ui.ordenes.crearOrdenLocativos.adapterscheck.ObjetoAdapter;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.CustomDIalogAgregar.ItemMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.elementosUtilizados.ElementoUtilizadoMostrar;

import java.util.List;

@Dao
public interface MaterialDAO {
    @Query("select * from Materiales")
    List<Material> getAll();

    @Insert
    void insert(Material material);
    @Update
    void update(Material material);
    @Delete
    void delete(Material material);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Material> materials);

    @Query("select MaterialesGroup.id as id, Materiales.id as idElemento, Materiales.descripcion as descripcion from MaterialesGroup inner join Materiales on materialesgroup.idElemento = Materiales.id" +
            " where materialesgroup.idOrden = :id")
    List<ElementoUtilizadoMostrar> getElementosGuardados(long id);

    @Query("select Materiales.id as id, Materiales.descripcion as nombre from Materiales")
    List<ItemMostrar> gettAllAgregar();

    @Query("select * from Materiales where id = :idElemento")
    Material getById(long idElemento);

    @Query("select Materiales.id as idPrincipal, 0 as idGroup, Materiales.descripcion as nombre, 'N' as hallado from Materiales")
    List<ObjetoAdapter> getAgregar();

    @Query("select Materiales.id as idPrincipal, materialesgroup.id as idGroup, Materiales.descripcion as nombre, materialesgroup.hallado as hallado from Materiales " +
            "inner join materialesgroup on Materiales.id = materialesgroup.idElemento and materialesgroup.idOrden = :idOrden")
    List<ObjetoAdapter> getAgregar(long idOrden);
}
