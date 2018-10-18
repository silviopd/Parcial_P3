package pe.edu.usat.penadiaz.control1.parcial_p3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Alumno;

import static pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Alumno.listaCli;

/**
 * Created by USER on 06/10/2016.
 */

public class AlumnoAdaptador extends BaseAdapter {

    public static ArrayList<Alumno> lista;
    private LayoutInflater layoutInflater;

    public AlumnoAdaptador(Context context, ArrayList<Alumno> lista) {
        this.layoutInflater = LayoutInflater.from(context);
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        if (convertView == null) {  //solo se ve lo q se va scrolleando
            convertView = layoutInflater.inflate(R.layout.activity_alumno_item, null);
            holder = new Holder();
            holder.lblCodigo = (TextView) convertView.findViewById(R.id.lblCodigo);
            holder.lblAlumno = (TextView) convertView.findViewById(R.id.lblAlumno);
            holder.lblFacultad = (TextView) convertView.findViewById(R.id.lblFacultad);
            holder.lblCarrera = (TextView) convertView.findViewById(R.id.lblCarrera);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();      //solo lo lee sin recargar
        }

        Alumno item = Alumno.listaCli.get(position);
        holder.lblCodigo.setText(item.getCodigo());
        holder.lblAlumno.setText(item.getNombre()+" "+item.getApellido_paterno()+" "+item.getApellido_materno());
        holder.lblFacultad.setText(String.valueOf(item.getNombre_facultad()));
        holder.lblCarrera.setText(String.valueOf(item.getNombre_carrera()));

        return convertView;
    }

    private class Holder{   //para llamar a los controles
        TextView lblCodigo;
        TextView lblAlumno;
        TextView lblFacultad;
        TextView lblCarrera;
    }
}
