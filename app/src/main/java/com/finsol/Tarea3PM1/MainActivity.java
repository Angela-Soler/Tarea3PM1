package com.finsol.Tarea3PM1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.finsol.Tarea3PM1.Configuracion.SQLiteConexion;
import com.finsol.Tarea3PM1.tablas.Transacciones;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables
    Button btnIngresar;
    EditText nombres, apellidos, edad, correo, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Config();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPersona();
            }
        });
    }

    private void Config()
    {
        btnIngresar = findViewById(R.id.btnIngresar);
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
}