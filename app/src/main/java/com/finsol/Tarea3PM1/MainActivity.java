package com.finsol.Tarea3PM1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finsol.Tarea3PM1.Configuracion.SQLiteConexion;
import com.finsol.Tarea3PM1.tablas.Personas;
import com.finsol.Tarea3PM1.tablas.Transacciones;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables
    Button btnIngresar, btnVerLista;
    EditText nombres, apellidos, edad, correo, direccion;
    String id = "";
    Boolean extras = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Config();

        //Verificando si enviaron datos
        Bundle datos = getIntent().getExtras();
        if (datos == null) {
            extras = false;
            Log.i("Extras: ", "No hay datos a actualizar");

        } else {
            extras = true;
            Log.i("Extras: ", "Sí hay datos a actualizar");

            id = ""+datos.getString("id");
            Log.i("Extras: ", "ID "+id);
            if (id!=null){
                btnIngresar.setText("Actualizar Persona");
                llenarDatos(id); //Llena la pantalla si trae el id a actualizar
            }else
            {
                btnIngresar.setText(R.string.btnSalvar);
            }

        }

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaciones()){
                    if (!extras){
                        agregarPersona();
                    }else{
                        actualizarRegistro(id);
                    }
                }

            }
        });

        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityList.class);
                startActivity(intent);
            }
        });
    }

    private void Config()
    {
        btnIngresar = findViewById(R.id.btnIngresar);
        btnVerLista = findViewById(R.id.btnVerLista);
        nombres = findViewById(R.id.ai_nombre);
        apellidos = findViewById(R.id.ai_apellidos);
        edad = findViewById(R.id.ai_edad);
        correo = findViewById(R.id.ai_correo);
        direccion = findViewById(R.id.txtDireccion);
    }

    public void agregarPersona()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transacciones.nombre,nombres.getText().toString());
        values.put(Transacciones.apellido,apellidos.getText().toString());
        values.put(Transacciones.edad,edad.getText().toString());
        values.put(Transacciones.correo,correo.getText().toString());
        values.put(Transacciones.direccion,direccion.getText().toString());

        try {
            Long resultado = db.insert(Transacciones.TablaPersonas, Transacciones.id, values);
            Toast.makeText(getApplicationContext(), "Registo Ingresado. "+resultado.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al insertar. "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        db.close();
        ClearScreem();
    }

    private void ClearScreem() {

        nombres.setText("");
        apellidos.setText("");
        edad.setText("");
        correo.setText("");
        direccion.setText("");
        nombres.requestFocus();
    }

    private void llenarDatos(String id) {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        Cursor cursor = null;
        SQLiteDatabase db = conexion.getReadableDatabase(); //Base de Datos en modo lectura
        Personas listPersonas;
        String [] args = {id};
        Log.i("args",args[0]);

        try{
            cursor = db.rawQuery( Transacciones.GetPersonas+" Where id = ?",args);
            while (cursor.moveToNext()){
                nombres.setText(cursor.getString(1));
                apellidos.setText(cursor.getString(2));
                edad.setText(cursor.getString(3));
                correo.setText(cursor.getString(4));
                direccion.setText(cursor.getString(5));

            }
            cursor.close();
        }catch (Exception e){
            Log.i("Error al mostrar datos ", e.getMessage());
        }
    }

    private boolean validaciones() {
        Boolean validacion = true;
        if (nombres.length() <= 0) {
            mostrarAlerta("Debe escribir un nombre");
            validacion = false;
        }else if (apellidos.length() <= 0) {
            mostrarAlerta("Debe escribir un apellido");
            validacion = false;
        }else if (edad.length() <= 0) {
            mostrarAlerta("Debe escribir una edad");
            validacion = false;
        }else if (correo.length() <= 0) {
            mostrarAlerta("Debe agregar un correo");
            validacion = false;
        }else if (direccion.length() <= 0) {
            mostrarAlerta("Debe agregar una direccion");
            validacion = false;
        }
        return validacion;
    }

    private void mostrarAlerta(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

    private void actualizarRegistro(String id) {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Transacciones.nombre,nombres.getText().toString());
        values.put(Transacciones.apellido,apellidos.getText().toString());
        values.put(Transacciones.edad,edad.getText().toString());
        values.put(Transacciones.correo,correo.getText().toString());
        values.put(Transacciones.direccion,direccion.getText().toString());

        try {
            db.update(Transacciones.TablaPersonas, values, "id=?", new String[]{id});

            Toast.makeText(getApplicationContext(), "Persona Actualizado Correctamente. ", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al actualizar persona. "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        db.close();

        ClearScreem();

        ActivityList c = new ActivityList();
        c.finalizarActivity(); //cierra la activity anterior
        this.finish(); //cierra la activity actual
        btnIngresar.setText(R.string.btnSalvar);
    }


}