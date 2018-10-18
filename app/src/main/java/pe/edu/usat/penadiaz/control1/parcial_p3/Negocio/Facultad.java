package pe.edu.usat.penadiaz.control1.parcial_p3.Negocio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pe.edu.usat.penadiaz.control1.parcial_p3.datos.AccesoDatos;

import static android.R.attr.id;

/**
 * Created by USER on 06/10/2016.
 */

public class Facultad extends AccesoDatos {

    private int id_facultad;
    private String nombre;


    public static ArrayList<Facultad> listaDep = new ArrayList<Facultad>();

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

    private void cargarDatosFacultad(){
        SQLiteDatabase bd = this.getReadableDatabase();
        String sql = "select * from facultad";
        Cursor resultado = bd.rawQuery(sql,null);

        listaDep.clear();

        while(resultado.moveToNext()){
            Facultad objDep = new Facultad();
            objDep.setId_facultad(resultado.getInt(0));
            objDep.setNombre(resultado.getString(1));
            listaDep.add(objDep);
        }
    }

    public String[] listaFacultad(){
        cargarDatosFacultad();

        String listaNombresDepartamentos[] = new String[listaDep.size()];

        for (int i = 0; i < listaDep.size(); i++) {
            Facultad item = listaDep.get(i);
            listaNombresDepartamentos[i] = item.getNombre();
        }

        return listaNombresDepartamentos;
    }
}
