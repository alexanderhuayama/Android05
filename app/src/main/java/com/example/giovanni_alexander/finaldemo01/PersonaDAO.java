package com.example.giovanni_alexander.finaldemo01;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giovanni_Alexander on 8/07/2017.
 */

public class PersonaDAO {
    // 1. Declarar las los campos de la tabla y el nombre de la tabla
    private final String TABLA = "TB_PERSONA";
    private final String COL_ID = "ID";
    private final String COL_NOMBRE = "NOMBRE";
    private final String COL_APELLIDO = "APELLIDO";
    private final String COL_DOCUMENTO = "DOCUMENTO";
    private final String COL_EDAD = "EDAD";
    private Context context;

    // Crear un constructor y pasarle un contexto
    public PersonaDAO(Context context) {
        this.context = context;
    }

    public List<Persona> listarPersonas() {
        List<Persona> personaList = new ArrayList<Persona>();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.openDataBase();
            // Declarar in cursor
            Cursor cursor = sqLiteDatabase.query(TABLA, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    personaList.add(convertirCursorAPersona(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personaList;
    }

    public List<Persona> listarPersonasPorEdad(String edad) {
        List<Persona> personaList = new ArrayList<Persona>();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.openDataBase();
            // Declarar in cursor
            Cursor cursor = sqLiteDatabase.query(TABLA, null, COL_EDAD+"<=?", new String[]{edad}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    personaList.add(convertirCursorAPersona(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personaList;
    }

    public Persona obtenerPersona(int id) {
        Persona persona = null;
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.openDataBase();
            Cursor cursor = sqLiteDatabase.query(TABLA, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                persona = convertirCursorAPersona(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persona;
    }


    public void insertarPersona(Persona p) {
        // Declarar un contenvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, p.getNombre());
        contentValues.put(COL_APELLIDO, p.getApellido());
        contentValues.put(COL_DOCUMENTO, p.getDocumento());
        contentValues.put(COL_EDAD, p.getEdad());
        new DataBaseHelper(context).openDataBase().insert(TABLA, null, contentValues);
    }

    public void actualizarPersona(Persona p) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, p.getNombre());
        contentValues.put(COL_APELLIDO, p.getApellido());
        contentValues.put(COL_DOCUMENTO, p.getDocumento());
        contentValues.put(COL_EDAD, p.getEdad());
        new DataBaseHelper(context).openDataBase().update(TABLA, contentValues, COL_ID + "=?", new String[]{String.valueOf(p.getId())});
    }

    public void eliminarPersona(Persona p) {
        new DataBaseHelper(context).openDataBase().delete(TABLA, COL_ID + "=?", new String[]{String.valueOf(p.getId())});
    }


    // Crear el metodo para convertir el cursor
    public Persona convertirCursorAPersona(Cursor cursor) {
        Persona p = new Persona();
        p.setId(cursor.isNull(cursor.getColumnIndex(COL_ID)) ? 0 : cursor.getInt(cursor.getColumnIndex((COL_ID))));
        p.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE)) ? "" : cursor.getString(cursor.getColumnIndex((COL_NOMBRE))));
        p.setApellido(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO)));
        p.setDocumento(cursor.isNull(cursor.getColumnIndex(COL_DOCUMENTO)) ? "" : cursor.getString(cursor.getColumnIndex(COL_DOCUMENTO)));
        p.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD)) ? 0 : cursor.getInt(cursor.getColumnIndex(COL_EDAD)));
        return p;
    }
}
