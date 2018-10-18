package pe.edu.usat.penadiaz.control1.parcial_p3.Negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pe.edu.usat.penadiaz.control1.parcial_p3.datos.AccesoDatos;

/**
 * Created by USER on 06/10/2016.
 */

public class Carrera extends AccesoDatos {

    private int id_carrera;
    private int id_facultad;
    private String nombre;
    private Double pension_promedio;

    public static ArrayList<Carrera> listaPro = new ArrayList<Carrera>();

    public int getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(int id_carrera) {
        this.id_carrera = id_carrera;
    }

    public int getId_facultad() {
        return id_facultad;
    }

    public void setId_facultad(int id_facultad) {
        this.id_facultad = id_facultad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPension_promedio() {
        return pension_promedio;
    }

    public void setPension_promedio(Double pension_promedio) {
        this.pension_promedio = pension_promedio;
    }

    private void cargarDatosCarrera(int id_facultad){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from carrera where id_facultad ="+id_facultad+"";
        Cursor resultado = bd.rawQuery(sql,null);

        listaPro.clear();

        while(resultado.moveToNext()){
            Carrera objPro = new Carrera();
            objPro.setId_carrera(resultado.getInt(0));
            objPro.setId_facultad(resultado.getInt(1));
            objPro.setNombre(resultado.getString(2));
            objPro.setPension_promedio(resultado.getDouble(3));
            listaPro.add(objPro);
        }
    }

    public String[] listaCarrera(int id_facultad){
        cargarDatosCarrera(id_facultad);

        String listaNombresProvincia[] = new String[listaPro.size()];

        for (int i = 0; i < listaPro.size(); i++) {
            Carrera item = listaPro.get(i);
            listaNombresProvincia[i] = item.getNombre();
        }

        return listaNombresProvincia;
    }
}
