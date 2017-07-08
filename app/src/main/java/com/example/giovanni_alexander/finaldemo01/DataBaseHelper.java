package com.example.giovanni_alexander.finaldemo01;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Giovanni_Alexander on 8/07/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_RUTA = "/data/data/com.example.giovanni_alexander.finaldemo01/";
    private static String DB_NOMBRE = "ExamenFinal.sqlite";
    private SQLiteDatabase sqLiteDatabase;
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, 1);
        this.context = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
        }else{
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error al copiar la base de datos");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_RUTA + DB_NOMBRE;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NOMBRE);
        String outFileName = DB_RUTA + DB_NOMBRE;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {
        if(sqLiteDatabase ==null|| !sqLiteDatabase.isOpen()) {
            //Open the database
            String myPath = DB_RUTA + DB_NOMBRE;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return sqLiteDatabase;
    }

    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
