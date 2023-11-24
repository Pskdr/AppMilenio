package com.example.milenioapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.milenioapp.database.dao.CebaderoDAO;
import com.example.milenioapp.database.dao.ClienteDAO;
import com.example.milenioapp.database.dao.EmpleadoDAO;
import com.example.milenioapp.database.dao.EstadoCebaderoDAO;
import com.example.milenioapp.database.dao.FacturaDAO;
import com.example.milenioapp.database.dao.InsectoDAO;
import com.example.milenioapp.database.dao.OrdenDAO;
import com.example.milenioapp.database.dao.TecnicaAplicacionDAO;
import com.example.milenioapp.database.dao.TipoInsectoDAO;
import com.example.milenioapp.database.dao.UsuarioDAO;
import com.example.milenioapp.database.dao.ZonaDAO;
import com.example.milenioapp.database.entity.Cebadero;
import com.example.milenioapp.database.entity.Cliente;
import com.example.milenioapp.database.entity.Empleado;
import com.example.milenioapp.database.entity.EstadoCebadero;
import com.example.milenioapp.database.entity.Factura;
import com.example.milenioapp.database.entity.Insecto;
import com.example.milenioapp.database.entity.Orden;
import com.example.milenioapp.database.entity.TecnicaAplicacion;
import com.example.milenioapp.database.entity.TipoInsecto;
import com.example.milenioapp.database.entity.Usuario;
import com.example.milenioapp.database.entity.Zona;
import com.example.milenioapp.utilidades.Constants;
@Database(entities = {Usuario.class, Empleado.class, Insecto.class, Orden.class, TipoInsecto.class,
        EstadoCebadero.class, Factura.class, Cliente.class, Zona.class, TecnicaAplicacion.class, Cebadero.class},
        version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDataBase extends RoomDatabase {


    public static AppDataBase INSTANCE;

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract EmpleadoDAO getEmpleadoDAO();
    public abstract InsectoDAO getInsectoDAO();
    public abstract OrdenDAO getOrdenDAO();
    public abstract TipoInsectoDAO getTipoInsectoDAO();
    public abstract EstadoCebaderoDAO getEstadoCebaderoDAO();
    public abstract FacturaDAO getFacturaDAO();
    public abstract ClienteDAO getClienteDAO();
    public abstract ZonaDAO getZonaDAO();
    public abstract TecnicaAplicacionDAO getTecnicaAplicacionDAO();
    public abstract CebaderoDAO getCebaderoDAO();


    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDataBase.class, Constants.DATABASE_NAME).build();
        }
        return INSTANCE;
    }
}
