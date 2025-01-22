package com.example.milenioapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.milenioapp.database.dao.AmbienteDAO;
import com.example.milenioapp.database.dao.AmbienteGroupDAO;
import com.example.milenioapp.database.dao.CebaderoDAO;
import com.example.milenioapp.database.dao.CebaderoGroupDAO;
import com.example.milenioapp.database.dao.ClienteDAO;
import com.example.milenioapp.database.dao.MaterialDAO;
import com.example.milenioapp.database.dao.MaterialGroupDAO;
import com.example.milenioapp.database.dao.EmpleadoDAO;
import com.example.milenioapp.database.dao.ZonaGroupDAO;
import com.example.milenioapp.database.dao.HallazgoDAO;
import com.example.milenioapp.database.dao.HallazgoGroupDAO;
import com.example.milenioapp.database.dao.HigieneDAO;
import com.example.milenioapp.database.dao.HigieneGroupDAO;
import com.example.milenioapp.database.dao.InsectoDAO;
import com.example.milenioapp.database.dao.InsectoGroupDAO;
import com.example.milenioapp.database.dao.LamparaGroupDAO;
import com.example.milenioapp.database.dao.NroCebaderosDAO;
import com.example.milenioapp.database.dao.OrdenDAO;
import com.example.milenioapp.database.dao.TecnicaAplicacionDAO;
import com.example.milenioapp.database.dao.TipoClienteDAO;
import com.example.milenioapp.database.dao.TipoInsectoDAO;
import com.example.milenioapp.database.dao.UsuarioDAO;
import com.example.milenioapp.database.dao.ZonaDAO;
import com.example.milenioapp.database.entity.Ambiente;
import com.example.milenioapp.database.entity.AmbienteGroup;
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.CebaderoGroup;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Material;
import com.example.milenioapp.database.entity.MaterialGroup;
import com.example.milenioapp.database.entity.Empleado;
import com.example.milenioapp.database.entity.ZonaGroup;
import com.example.milenioapp.database.entity.Hallazgo;
import com.example.milenioapp.database.entity.HallazgoGrup;
import com.example.milenioapp.database.entity.Higiene;
import com.example.milenioapp.database.entity.HigieneGroup;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.InsectoGroup;
import com.example.milenioapp.database.entity.LamparaGroup;
import com.example.milenioapp.database.entity.NroCebaderos;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.TipoCliente;
import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.utilidades.Constants;
@Database(entities = {Usuario.class, Empleado.class, Insecto.class, Orden.class, TipoInsecto.class,
        Cliente.class, Zona.class, TecnicaAplicacion.class, Cebadero.class,
        Higiene.class, CebaderoGroup.class, HigieneGroup.class, ZonaGroup.class, InsectoGroup.class,
        Material.class, MaterialGroup.class, LamparaGroup.class, NroCebaderos.class,
        TipoCliente.class, Ambiente.class, AmbienteGroup.class, Hallazgo.class, HallazgoGrup.class},
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

    public abstract ZonaGroupDAO getGrupoZonaDAO();
    public abstract InsectoGroupDAO getInsectoGroupDAO();

    public abstract MaterialDAO getElementoUtilizadoDAO();

    public abstract MaterialGroupDAO getMaterialGroupDAO();
    public abstract LamparaGroupDAO getLamparoGroupDAO();
    public abstract NroCebaderosDAO getNroCebaderoDAO();

    public abstract AmbienteDAO getAmbienteDAO();
    public abstract TipoClienteDAO getTipoClienteDAO();

    public abstract AmbienteGroupDAO getAmbienteGroupDAO();

    public abstract HallazgoDAO getHallazgoDAO();

    public abstract HallazgoGroupDAO getHallazgoGroupDAO();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
