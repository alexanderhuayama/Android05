package com.example.giovanni_alexander.finaldemo01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Definiendo codigo de respuestas
    private static final int REQ_COD_AGREGAR = 1;
    private static final int REQ_COD_ACTUALIZAR = 2;

    // Declarando vistas
    private ListView lvPersonas;
    private Button btnCrear;
    private Spinner spEdades;

    // Adaptadores
    private AdaptadorLV adaptadorLV;
    private AdaptadorSpinner adaptadorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPersonas = (ListView) findViewById(R.id.lstPersonas);
        btnCrear = (Button) findViewById(R.id.btnAgregar);
        spEdades = (Spinner) findViewById(R.id.spEdades);

        // Asignar el adaptador a la lista
        adaptadorLV = new AdaptadorLV(MainActivity.this);
        lvPersonas.setAdapter(adaptadorLV);

        // Asignar el adaptador al spinner
        adaptadorSpinner = new AdaptadorSpinner(MainActivity.this);
        spEdades.setAdapter(adaptadorSpinner);

        // Asignacion de eventos
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrearActivity.class);
                startActivityForResult(intent, REQ_COD_AGREGAR);
            }
        });

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
            cargarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lvPersonas.setOnItemClickListener(adaptadorLVOnItemClickListener);
        spEdades.setOnItemSelectedListener(spEdadesOnItemSelectedListener);

        // Llenar el spinner
        for (int i = 0; i <= 80; i += 10) {
            adaptadorSpinner.add(String.valueOf(i));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_COD_AGREGAR) {
            if (resultCode == RESULT_OK) {
                cargarLista();
            }
        }
        if (requestCode == REQ_COD_ACTUALIZAR) {
            if (resultCode == RESULT_OK) {
                cargarLista();
            }
        }
    }

    private void cargarLista() {
        adaptadorLV.clear();
        adaptadorLV.addAll(new PersonaDAO(MainActivity.this).listarPersonas());
    }

    private AdapterView.OnItemClickListener adaptadorLVOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TextView tvId = (TextView) view.findViewById(R.id.tvId);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("MANTENIMIENTO");
            builder.setMessage("Elegir una operacion");
            builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, CrearActivity.class);
                    intent.putExtra("idPersona", tvId.getText().toString());
                    startActivityForResult(intent, REQ_COD_ACTUALIZAR);
                }
            });
            builder.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Persona p = new Persona();
                    p.setId(Integer.parseInt(tvId.getText().toString()));
                    new PersonaDAO(MainActivity.this).eliminarPersona(p);
                    cargarLista();
                }
            });
            builder.show();
        }
    };

    private AdapterView.OnItemSelectedListener spEdadesOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String edad = (String) parent.getSelectedItem();
            //Toast.makeText(MainActivity.this, edad, Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(edad) == 0) {
                cargarLista();
            } else {
                adaptadorLV.clear();
                adaptadorLV.addAll(new PersonaDAO(MainActivity.this).listarPersonasPorEdad(edad));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
