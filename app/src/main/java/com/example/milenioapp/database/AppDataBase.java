package com.example.milenioapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.milenioapp.database.dao.CebaderoDAO;
import com.example.milenioapp.database.dao.CebaderoGroupDAO;
import com.example.milenioapp.database.dao.ClienteDAO;
import com.example.milenioapp.database.dao.ElementoUtilizadoDAO;
import com.example.milenioapp.database.dao.ElementoUtilizadoGroupDAO;
import com.example.milenioapp.database.dao.EmpleadoDAO;
import com.example.milenioapp.database.dao.GrupoZonaDAO;
import com.example.milenioapp.database.dao.HigieneDAO;
import com.example.milenioapp.database.dao.HigieneGroupDAO;
import com.example.milenioapp.database.dao.InsectoDAO;
import com.example.milenioapp.database.dao.InsectoGroupDAO;
import com.example.milenioapp.database.dao.LamparaGroupDAO;
import com.example.milenioapp.database.dao.NroCebaderosDAO;
import com.example.milenioapp.database.dao.OrdenDAO;
import com.example.milenioapp.database.dao.TecnicaAplicacionDAO;
import com.example.milenioapp.database.dao.TipoInsectoDAO;
import com.example.milenioapp.database.dao.UsuarioDAO;
import com.example.milenioapp.database.dao.ZonaDAO;
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.ElementoUtilizado;
import com.example.milenioapp.database.entity.ElementoUtilizadoGroup;
import com.example.milenioapp.database.entity.Empleado;
import com.example.milenioapp.database.entity.GrupoZona;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.HigieneGroup;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.InsectoGroup;
import com.example.milenioapp.database.entity.LamparaGroup;
import com.example.milenioapp.database.entity.NroCebaderos;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.utilidades.Constants;
@Database(entities = {Usuario.class, Empleado.class, Insecto.class, Orden.class, TipoInsecto.class,
        Cliente.class, Zona.class, TecnicaAplicacion.class, Cebadero.class,
        Higiene.class, CebaderoGroup.class, HigieneGroup.class, GrupoZona.class, InsectoGroup.class,
        ElementoUtilizado.class, ElementoUtilizadoGroup.class, LamparaGroup.class, NroCebaderos.class},
        version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDataBase extends RoomDatabase {


    public static AppDataBase INSTANCE;

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract EmpleadoDAO getEmpleadoDAO();
    public abstract InsectoDAO getInsectoDAO();
    public abstract OrdenDAO getOrdenDAO();
    public abstract TipoInsectoDAO getTipoInsectoDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract ZonaDAO getZonaDAO();
    public abstract TecnicaAplicacionDAO getTecnicaAplicacionDAO();
    public abstract CebaderoDAO getCebaderoDAO();
    public abstract HigieneDAO getHigieneDAO();
    public abstract CebaderoGroupDAO getCebaderoGroupDAO();
    public abstract HigieneGroupDAO getHigieneGroupDAO();
    public abstract GrupoZonaDAO getGrupoZonaDAO();
    public abstract InsectoGroupDAO getInsectoGroupDAO();
    public abstract ElementoUtilizadoDAO getElementoUtilizadoDAO();
    public abstract ElementoUtilizadoGroupDAO getElementoUtilizadoGroupDAO();
    public abstract LamparaGroupDAO getLamparoGroupDAO();
    public abstract NroCebaderosDAO getNroCebaderoDAO();


    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
