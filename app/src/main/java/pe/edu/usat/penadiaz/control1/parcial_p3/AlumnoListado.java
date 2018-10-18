package pe.edu.usat.penadiaz.control1.parcial_p3;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.usat.penadiaz.control1.parcial_p3.Negocio.Alumno;
import pe.edu.usat.penadiaz.control1.parcial_p3.datos.AccesoDatos;
import pe.edu.usat.penadiaz.control1.parcial_p3.util.Funciones;

public class AlumnoListado extends AppCompatActivity implements View.OnClickListener{

    FloatingActionButton fab;
    ListView lvLista;

    ArrayList<Alumno> lista;
    AlumnoAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_listado);

        AccesoDatos.aplicacion = this;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        lvLista = (ListView) findViewById(R.id.lvListado);

        registerForContextMenu(lvLista);

        listar();
    }

    private void listar() {
        new Alumno().cargarLista();
        lista = new ArrayList<Alumno>();
        lista = Alumno.listaCli;
        adaptador = new AlumnoAdaptador(this, lista);
        lvLista.setAdapter(adaptador);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        listar();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvListado) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            String nombre = Alumno.listaCli.get(info.position).getNombre()+" "+Alumno.listaCli.get(info.position).getApellido_paterno()+" "+Alumno.listaCli.get(info.position).getApellido_materno();

            menu.setHeaderTitle(nombre);
            String[] menuItems = getResources().getStringArray(R.array.menu);    //menu del strings.xml
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();

        switch (menuItemIndex) {
            case 0:  //editar
                Intent i = new Intent(this,MainActivity.class);
                Bundle p = new Bundle();
                p.putInt("position",info.position);
                i.putExtras(p);
                startActivity(i);

                break;
            case 1:  //eliminar

                boolean r = Funciones.mensajeConfirmacion(this, "Confirme", "Desea eliminar");
                if (r == false) {
                    return false;
                }

                String dni = Alumno.listaCli.get(info.position).getCodigo();
                long resultado = new Alumno().eliminar(dni);
                if (resultado > 0) {
                    Toast.makeText(this, "Registro eliminado", Toast.LENGTH_LONG).show();
                    listar();
                }
                break;
        }
        return true;
    }
}
