package pe.edu.usat.penadiaz.control1.parcial_p3.Negocio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.usat.penadiaz.control1.parcial_p3.datos.AccesoDatos;

/**
 * Created by USER on 06/10/2016.
 */

public class Alumno extends AccesoDatos {

    private String codigo;
    private int id_carrera;
    private int id_facultad;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombre;
    private String sexo;
    private String anno_ingreso;

    //
    private String nombre_facultad;
    private String nombre_carrera;


    public static ArrayList<Alumno> listaCli = new ArrayList<Alumno>();

    public String getAnno_ingreso() {
        return anno_ingreso;
    }

    public void setAnno_ingreso(String anno_ingreso) {
        this.anno_ingreso = anno_ingreso;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNombre_carrera() {
        return nombre_carrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }

    public String getNombre_facultad() {
        return nombre_facultad;
    }

    public void setNombre_facultad(String nombre_facultad) {
        this.nombre_facultad = nombre_facultad;
    }

    public long agregar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("codigo",this.getCodigo());
        valores.put("id_carrera",this.getId_carrera());
        valores.put("id_facultad",this.getId_facultad());
        valores.put("apellido_paterno",this.getApellido_paterno());
        valores.put("apellido_materno",this.getApellido_materno());
        valores.put("nombre",this.getNombre());
        valores.put("sexo",this.getSexo());
        valores.put("anno_ingreso",this.getAnno_ingreso());

        long resultado = db.insert("alumno",null,valores);

        return resultado;

    }

    public void cargarLista(){
        SQLiteDatabase db = this.getReadableDatabase();
        //  Cursor resultado = db.rawQuery("select * from alumno", null);
        Cursor resultado = db.rawQuery("select a.codigo,a.id_carrera,a.id_facultad,a.apellido_paterno,a.apellido_materno,a.nombre,a.sexo,a.anno_ingreso,c.nombre,f.nombre from alumno a inner join carrera c on a.id_carrera=c.id_carrera and a.id_facultad=c.id_facultad inner join facultad f on c.id_facultad=f.id_facultad", null);
        listaCli.clear();
        while(resultado.moveToNext()){
            Alumno obj = new Alumno();
            obj.setCodigo(resultado.getString(0));
            obj.setId_carrera(resultado.getInt(1));
            obj.setId_facultad(resultado.getInt(2));
            obj.setApellido_paterno(resultado.getString(3));
            obj.setApellido_materno(resultado.getString(4));
            obj.setNombre(resultado.getString(5));
            obj.setSexo(resultado.getString(6));
            obj.setAnno_ingreso(resultado.getString(7));

            //
            obj.setNombre_carrera(resultado.getString(8));
            obj.setNombre_facultad(resultado.getString(9));
            listaCli.add(obj);
        }
    }

    public long eliminar(String dni){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("alumno", "codigo like '" + dni + "'", null);
    }

    //EDITAR EL LEER DATOS
    public Cursor leerDatos(String dni){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql ="select a.codigo,a.id_carrera,a.id_facultad,a.apellido_paterno,a.apellido_materno,a.nombre,a.sexo,a.anno_ingreso,c.nombre,f.nombre from alumno a inner join carrera c on a.id_carrera=c.id_carrera and a.id_facultad=c.id_facultad inner join facultad f on c.id_facultad=f.id_facultad where a.codigo like '"+dni+"'";

        Cursor resultado = db.rawQuery(sql, null);
        return resultado;
    }

    public long editar(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        //valores.put("dni",this.getDni());
        valores.put("id_carrera",this.getId_carrera());
        valores.put("id_facultad",this.getId_facultad());
        valores.put("apellido_paterno",this.getApellido_paterno());
        valores.put("apellido_materno",this.getApellido_materno());
        valores.put("nombre",this.getNombre());
        valores.put("sexo",this.getSexo());
        valores.put("anno_ingreso",this.getAnno_ingreso());

        String condicion = "codigo= '"+this.getCodigo()+"'";

        long resultado = db.update("alumno",valores,condicion,null);

        return resultado;
    }
}
