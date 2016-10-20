package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 20/10/2016.
 */
public class FavoritosSQLiteHelper extends SQLiteOpenHelper {

    private int DATA_VERSION = 1;
    private String DATA_BASE_NAME = "FavoritosDB";

    String sqlCreate = "CREATE TABLE Favoritos (idFavorito INTEGER PRIMARY KEY AUTOINCREMENT, idUsuario INTEGER, idProducto TEXT)";

    public FavoritosSQLiteHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Favoritos");
        sqLiteDatabase.execSQL(sqlCreate);
    }
}
