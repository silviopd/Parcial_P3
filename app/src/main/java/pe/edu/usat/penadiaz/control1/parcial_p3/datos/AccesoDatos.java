package pe.edu.usat.penadiaz.control1.parcial_p3.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 06/10/2016.
 */

public class AccesoDatos extends SQLiteOpenHelper {

    private static String nombreBD = "bdAlumnos";
    private static int versionBD = 1;

    public static Context aplicacion;

    /*
    facultad
    id_facultad
    nombre

    carrera
    id_carrera
    id_facultad
    nombre
    pension_promedio

    alumno
    codigo
    id_carrera
    id_facultad
    apellido_paterno
    apellido_materno
    nombre
    sexo
    anno_ingreso
    */

    private static String tablaFacultad = "create table facultad(id_facultad integer,nombre varchar(100),PRIMARY KEY (id_facultad))";
    private static String tablaFacultadDatos[] = {
            "insert into facultad values(1,'Derecho')",
            "insert into facultad values(2,'Ingeniería')",
            "insert into facultad values(3,'Medicina')"
    };

    private static String tablaCarrera = "create table carrera(id_carrera integer,id_facultad integer,nombre varchar(100),pension_promedio numeric,PRIMARY KEY(id_carrera,id_facultad),FOREIGN KEY(id_facultad) references facultad (id_facultad))";
    private static String tablaCarreraDatos[] = {
            "insert into carrera values(1,1,'Derecho',500)",

            "insert into carrera values(1,2,'Arquitectura',700)",
            "insert into carrera values(2,2,'Ing. Civil Ambiental',500)",
            "insert into carrera values(3,2,'Ing. Sistemas y Computación',500)",
            "insert into carrera values(4,2,'Ing. Industrial',500)",
            "insert into carrera values(5,2,'Ing. Mecanica Electrica',600)",

            "insert into carrera values(1,3,'Medicina',800)",
            "insert into carrera values(2,3,'Psicología',700)",
            "insert into carrera values(3,3,'Odontología',600)",
            "insert into carrera values(4,3,'Enfermería',700)"
    };

    private static String tablaAlumno = "create table alumno(codigo char(10),id_carrera integer,id_facultad integer,apellido_paterno varchar(100),apellido_materno varchar(100),nombre varchar(100),sexo char(1),anno_ingreso varchar(10),PRIMARY KEY(codigo,id_carrera,id_facultad),FOREIGN KEY(id_facultad,id_carrera) references carrera (id_facultad,id_carrera))";
    private static String tablaAlumnoDatos[] = {
            "insert into alumno values('141TE51191',3,2,'PEÑA','DIAZ','SILVIO','M','2014 - II')",
            "insert into alumno values('151KD51191',1,1,'SALAZAR','VARGAS','AUGUSTO','M','2015 - I')",
            "insert into alumno values('161FT51191',3,3,'TAPIA','PIEROLA','MARIA','M','2016 - I')"
    };


    public AccesoDatos() {
        super(aplicacion, nombreBD, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaFacultad);
        db.execSQL(tablaCarrera);
        db.execSQL(tablaAlumno);

        for (int i = 0; i < tablaFacultadDatos.length; i++) {
            db.execSQL(tablaFacultadDatos[i]);
        }

        for (int i = 0; i < tablaCarreraDatos.length; i++) {
            db.execSQL(tablaCarreraDatos[i]);
        }

        for (int i = 0; i < tablaAlumnoDatos.length; i++) {
            db.execSQL(tablaAlumnoDatos[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
