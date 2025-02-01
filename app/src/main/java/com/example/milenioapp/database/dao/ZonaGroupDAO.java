package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.ZonaGroup;
import com.example.milenioapp.ui.ordenes.certificado.ItemZonasMostrar;
import com.example.milenioapp.ui.ordenes.crearOrdenServicio.zona.GrupoZonaMostrar;

import java.util.List;

@Dao
public interface ZonaGroupDAO {
    @Query("select ZonaGroup.id as id, ZonaGroup.idzona as idZona, :idOrden as idOrden,zonas.descripcion as nombre,ZonaGroup.producto as producto,ZonaGroup.ingredienteActivo as ingredienteActivo," +
            "ZonaGroup.dosificacion as docificacion, ZonaGroup.tecnicaAplicacion as tecnicaAplicacion,ZonaGroup.fechaVencimientoProducto as fechaVencimiento " +
            "from ZonaGroup inner join zonas on ZonaGroup.idZona = zonas.id " +
            "where ZonaGroup.idOrden = :idOrden")
    List<GrupoZonaMostrar> getZonasAgregadas(long idOrden);

    @Query("select  ZonaGroup.id, zonas.descripcion as nombre,ZonaGroup.tecnicaAplicacion as tecnica,ZonaGroup.ingredienteActivo as ingredienteActivo " +
            "from ZonaGroup inner join zonas on ZonaGroup.idZona = zonas.id " +
            "where ZonaGroup.idOrden = :idOrden")
    List<ItemZonasMostrar> getZonasAgregadasPdf(long idOrden);

    @Insert
    void insert(ZonaGroup zonaGroup);

    @Update
    void update(ZonaGroup zonaGroup);

    @Delete
    void delete(ZonaGroup zonaGroup);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ZonaGroup> zonaGroupInsert);

    @Query("delete from ZonaGroup where idOrden = :id")
    void deleteByOrden(long id);
}
