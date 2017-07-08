package com.example.giovanni_alexander.finaldemo01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CrearActivity extends AppCompatActivity {
    private EditText etNombre, etApellido, etDocumento, etEdad;
    private Button btnGuardar;
    private String idPersona = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        //Crear los items
        etNombre = (EditText) findViewById(R.id.etNombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etDocumento = (EditText) findViewById(R.id.etDocumento);
        etEdad = (EditText) findViewById(R.id.etEdad);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        // Traer los datos
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            idPersona = (String) bundle.get("idPersona");
            Persona p = new PersonaDAO(CrearActivity.this).obtenerPersona(Integer.parseInt(idPersona));
            etNombre.setText(p.getNombre());
            etApellido.setText(p.getApellido());
            etDocumento.setText(p.getDocumento());
            etEdad.setText(String.valueOf(p.getEdad()));
            btnGuardar.setText("ACTUALIZAR");
        }

        // Asignar eventos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    Persona p = new Persona();
                    p.setNombre(etNombre.getText().toString());
                    p.setApellido(etApellido.getText().toString());
                    p.setDocumento(etDocumento.getText().toString());
                    p.setEdad(Integer.parseInt(etEdad.getText().toString()));

                    if (btnGuardar.getText().toString().toUpperCase().equals("ACTUALIZAR")) {
                        new PersonaDAO(CrearActivity.this).actualizarPersona(p);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        new PersonaDAO(CrearActivity.this).insertarPersona(p);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

    }

    private boolean validar() {
        boolean resultado = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearActivity.this);
        builder.setTitle("ADVERTENCIA !!!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        if (etNombre.getText().toString().equals("") || etNombre.getText().toString() == null) {
            mostrarAlert(builder, "Ingresar el nombre");
            resultado = false;
        } else if (etApellido.getText().toString().equals("") || etApellido.getText().toString() == null) {
            mostrarAlert(builder, "Ingresar el apellido");
            resultado = false;
        } else if (etDocumento.getText().toString().equals("") || etDocumento.getText().toString() == null) {
            mostrarAlert(builder, "Ingresar el nro de documento");
            resultado = false;
        } else if (etEdad.getText().toString().equals("") || etEdad.getText().toString() == null) {
            mostrarAlert(builder, "Ingresar la edad");
            resultado = false;
        }
        return resultado;
    }

    private void mostrarAlert(AlertDialog.Builder builder, String msg) {
        builder.setMessage(msg);
        builder.show();
    }
}
