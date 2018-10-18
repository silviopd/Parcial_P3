package pe.edu.usat.penadiaz.control1.parcial_p3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Alumno;
import pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Carrera;
import pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Facultad;
import pe.edu.usat.penadiaz.control1.parcial_p3.datos.AccesoDatos;
import pe.edu.usat.penadiaz.control1.parcial_p3.util.Funciones;

import static pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Facultad.listaDep;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,View.OnTouchListener{

    EditText txtCodigo,txtApellido_Paterno,txtApellido_Materno,txtNombre;
    RadioButton rbMasculino,rbFemenino;
    Spinner spAnno_Ingreso,spFacultad,spCarrera;
    Button btnGrabar;

    boolean touch;

    Object annoIngreso[] = new Object[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        txtApellido_Paterno = (EditText) findViewById(R.id.txtApellido_Paterno);
        txtApellido_Materno = (EditText) findViewById(R.id.txtApellido_Materno);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFemenino = (RadioButton) findViewById(R.id.rbFemenino);
        spAnno_Ingreso = (Spinner) findViewById(R.id.spAnnoIngreso);
        spFacultad = (Spinner) findViewById(R.id.spFacultad);
        spCarrera = (Spinner) findViewById(R.id.spCarrera);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);

        AccesoDatos.aplicacion = this;

        cargarDatosSpinnerFacultad();
        cargarSpinnerAnnoIngreso();

        btnGrabar.setOnClickListener(this);

        spFacultad.setOnItemSelectedListener(this);
        spFacultad.setOnTouchListener(this);

        Bundle p = this.getIntent().getExtras();
        if (p != null){//Esta llegando un parametro, significa que debo leer los datos
            int position = p.getInt("position");
            Alumno item = Alumno.listaCli.get(position);
            this.leerDatos(item.getCodigo());
            System.out.println("medio");
        }

        //this.touch = true;
    }

    private void leerDatos(String dni){
        Cursor cursor = new Alumno().leerDatos(dni);
        if (cursor.moveToNext()){
            txtCodigo.setText(cursor.getString(0));
            txtApellido_Paterno.setText(cursor.getString(3));
            txtApellido_Materno.setText(cursor.getString(4));
            txtNombre.setText(cursor.getString(5));

            if (cursor.getString(6).equalsIgnoreCase("M")){
                rbMasculino.setChecked(true);
            }else{
                rbFemenino.setChecked(true);
            }

            Toast.makeText(this,cursor.getInt(1)+" "+cursor.getInt(2)+" "+cursor.getString(9)+" "+cursor.getString(8),Toast.LENGTH_LONG).show();

            this.cargarDatosSpinnerCarrera(cursor.getInt(2));

            Funciones.selectedItemSpinner(spFacultad,cursor.getString(9));
            Funciones.selectedItemSpinner(spCarrera,cursor.getString(8));


            Funciones.selectedItemSpinner(spAnno_Ingreso,cursor.getString(7));

            txtCodigo.setEnabled(false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (this.touch){
            switch (parent.getId()){
                case R.id.spFacultad:
                    int dep = Facultad.listaDep.get(position).getId_facultad();
                    cargarDatosSpinnerCarrera(dep);
                    break;
            }

            this.touch = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void cargarDatosSpinnerFacultad(){
        String listaDepartamento[] = new Facultad().listaFacultad();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listaDepartamento);
        spFacultad.setAdapter(adapter);
    }

    private void cargarDatosSpinnerCarrera(int codigoDepartamento){
        String listaProvincia[] = new Carrera().listaCarrera(codigoDepartamento);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listaProvincia);
        spCarrera.setAdapter(adapter);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.touch = true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGrabar:

                String codigo = txtCodigo.getText().toString();
                String apellido_paterno = txtApellido_Paterno.getText().toString();
                String apellido_materno = txtApellido_Materno.getText().toString();
                String nombre = txtNombre.getText().toString();
                String sexo="";

                if (rbFemenino.isChecked()){
                    sexo = "F";
                }else{
                    sexo = "M";
                }

                String anno_ingreso = spAnno_Ingreso.getSelectedItem().toString();

                int id_facultad = Carrera.listaPro.get(spCarrera.getSelectedItemPosition()).getId_facultad();
                int id_carrera= Carrera.listaPro.get(spCarrera.getSelectedItemPosition()).getId_carrera();


                Alumno obj = new Alumno();
                obj.setCodigo(codigo);
                obj.setApellido_paterno(apellido_paterno);
                obj.setApellido_materno(apellido_materno);
                obj.setNombre(nombre);
                obj.setSexo(sexo);
                obj.setAnno_ingreso(anno_ingreso);
                obj.setId_facultad(id_facultad);
                obj.setId_carrera(id_carrera);

                long resultado = -1;
                if (txtCodigo.isEnabled()){
                    resultado = obj.agregar();
                }else{
                    resultado = obj.editar();
                }

                System.out.println("Resultado: " + resultado);

                if (resultado != -1) {
                    Toast.makeText(this, "Grabado Ok!", Toast.LENGTH_LONG).show();
                    this.finish();
                }

                break;
        }
    }

    public void cargarSpinnerAnnoIngreso(){
        annoIngreso[0] = "2012 - I";
        annoIngreso[1] = "2012 - II";
        annoIngreso[2] = "2013 - I";
        annoIngreso[3] = "2013 - II";
        annoIngreso[4] = "2014 - I";
        annoIngreso[5] = "2014 - II";
        annoIngreso[6] = "2015 - I";
        annoIngreso[7] = "2015 - II";
        annoIngreso[8] = "2016 - I";
        annoIngreso[9] = "2016 - II";

        String articuloSpinner[] = new String [annoIngreso.length];
        for (int i=0;i<annoIngreso.length;i++){
            articuloSpinner[i] = String.valueOf(annoIngreso[i]);
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,articuloSpinner);
        spAnno_Ingreso.setAdapter(adapter);
    }
}
