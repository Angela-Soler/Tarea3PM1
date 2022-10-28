package com.finsol.Tarea3PM1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.finsol.Tarea3PM1.Configuracion.SQLiteConexion;
import com.finsol.Tarea3PM1.tablas.Personas;
import com.finsol.Tarea3PM1.tablas.Transacciones;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listPerson;
    ArrayList<Personas> lista;
    ArrayList<String> listaConcatenada;
    Button btnEliminar, btnActualizar, btnVerRegistro;
    String id = "";
    public static Activity actList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listPerson = (ListView) findViewById(R.id.listPerson);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVerRegistro = findViewById(R.id.btnVerRegistro);

        GetListPerson();

        //Para cerrar actividad desde otra activity
        actList=this;

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaConcatenada);
        listPerson.setAdapter(adp);

        listPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ActivityList.this, listaConcatenada.get(i), Toast.LENGTH_SHORT).show();

                Log.i("Seleccion id", listaConcatenada.get(i));
                id = lista.get(i).getId().toString();
                //Boton Eliminar Registro
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        id = lista.get(i).getId().toString();
                        mostrarAlerta("¿Desea eliminar el registro de " + lista.get(i).getNombre()+"?", id, "D");
                        Log.i("Eliminar id", "" + lista.get(i).getId().toString());
                    }
                });

                //Boton Actualizar Registro
                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        id = lista.get(i).getId().toString();
                        mostrarAlerta("¿Desea actualizar el registro " + lista.get(i).getNombre() +"?", id, "A");
                        Log.i("Actualizar id", "" + lista.get(i).getId().toString());


                    }
                });

                btnVerRegistro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            mostrarAlertaOK("Nombre: " + lista.get(i).getNombre() +
                                    "\nApellido: " + lista.get(i).getapellido() +
                                    "\nEdad: " + lista.get(i).getedad() +
                                    "\nCorreo: " + lista.get(i).getcorreo() +
                                    "\nDireccion: " + lista.get(i).getdireccion());

                        }
                });
            }
        });
    }

    private void GetListPerson() {
        SQLiteDatabase db = conexion.getReadableDatabase(); //Base de Datos en modo lectura
        Personas listPersonas;

        lista = new ArrayList<>(); //lista de objetos del tipo Personas
        Cursor cursor = db.rawQuery(Transacciones.GetPersonas,null);

        while (cursor.moveToNext()){
            listPersonas = new Personas();
            listPersonas.setId(cursor.getInt(0));
            listPersonas.setNombre(cursor.getString(1));
            listPersonas.setapellido(cursor.getString(2));
            listPersonas.setedad(cursor.getInt(3));
            listPersonas.setcorreo(cursor.getString(4));
            listPersonas.setdireccion(cursor.getString(5));

            lista.add(listPersonas);
        }
        cursor.close();

        llenarLista();
    }

    private void llenarLista() {

        listaConcatenada = new ArrayList<>();

        for (int i=0; i < lista.size(); i++){
            listaConcatenada.add("ID: "+lista.get(i).getId()+"\nNombre: "+lista.get(i).getNombre()+" \nApellidos: "+
                    lista.get(i).getapellido()+" \nEdad: "+
                    lista.get(i).getedad()+" \nCorreo: "+
                    lista.get(i).getcorreo()+" \nDireccion: "+
                    lista.get(i).getdireccion());
        }
    }

    private void mostrarAlerta(String mensaje, String id, String tipo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityList.this);
        builder.setMessage(mensaje)
                .setTitle("Confirmación")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (tipo.equalsIgnoreCase("D")){ //Delete
                                    eliminarRegistro(id);
                                }
                                if (tipo.equalsIgnoreCase("A")){ //Actualizar
                                    actualizarRegistro(id);
                                }
                            }
                        }
                ).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        }
                );

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actualizarRegistro(String id) {
        /*try {
            onBackPressed();
        } catch (Throwable e) {
            e.printStackTrace();
        }*/
        Intent intent = new Intent(ActivityList.this, MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }

    private void eliminarRegistro(String id) {

        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            db.delete(Transacciones.TablaPersonas, "id="+id, null);
            Toast.makeText(getApplicationContext(), "Registro Eliminado. ", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al eliminar. "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error al eliminar",e.toString());
        }

        db.close();
        try {
            onBackPressed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //Volver a Activity principal
        Intent intent = new Intent(ActivityList.this, MainActivity.class);
        startActivity(intent);
    }

    public void verificaSeleccion(View view){

        if (id.toString().equalsIgnoreCase("")){
            mostrarAlertaOK("Debe seleccionar un registro y luego presionar el boton");
        }
    }

    private void mostrarAlertaOK(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityList.this);
        builder.setMessage(mensaje)
                .setTitle("Informacion")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void finalizarActivity(){
        actList.finish();
    }
}