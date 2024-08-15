package com.example.milenioapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.milenioapp.database.entity.GrupoZona;
import com.example.milenioapp.ui.ordenes.crearOrden.certificado.ItemZonasMostrar;
import com.example.milenioapp.ui.ordenes.crearOrden.zona.GrupoZonaMostrar;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GrupoZonaDAO {
    @Query("select GrupoZonas.id as id, GrupoZonas.idzona as idZona, :idOrden as idOrden,zonas.descripcion as nombre,GrupoZonas.producto as producto,GrupoZonas.ingredienteActivo as ingredienteActivo," +
            "GrupoZonas.docificacion as docificacion, grupozonas.tecnicaAplicacion as tecnicaAplicacion,grupozonas.fechaVencimientoProducto as fechaVencimiento " +
            "from GrupoZonas inner join zonas on GrupoZonas.idZona = zonas.id " +
            "where GrupoZonas.idOrden = :idOrden")
    List<GrupoZonaMostrar> getZonasAgregadas(long idOrden);

    @Query("select  GrupoZonas.id, zonas.descripcion as nombre,GrupoZonas.tecnicaAplicacion as tecnica,GrupoZonas.ingredienteActivo as ingredienteActivo " +
            "from GrupoZonas inner join zonas on GrupoZonas.idZona = zonas.id " +
            "where GrupoZonas.idOrden = :idOrden")
    List<ItemZonasMostrar> getZonasAgregadasPdf(long idOrden);
    @Insert
    void insert(GrupoZona grupoZona);
    @Update
    void update(GrupoZona grupoZona);
    @Delete
    void delete(GrupoZona grupoZona);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<GrupoZona> grupoZonaInsert);
}
