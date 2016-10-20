package com.arteaga.kevin.miscarnesparrilla;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

public class DesProductoActivity extends AppCompatActivity {

    TextView Descripcion;
    TextView Precio;
    CheckBox Fav;
    String Id;
    ProductosSQLiteHelper productos;
    SQLiteDatabase dbProductos;
    FavoritosSQLiteHelper favoritos;
    SQLiteDatabase dbFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_producto);

        Descripcion = (TextView) findViewById(R.id.tProd1);
        Precio = (TextView) findViewById(R.id.tProd2);
        Fav = (CheckBox) findViewById(R.id.cProdF);
        productos = new ProductosSQLiteHelper(this, "ProductosBD", null, 1);
        dbProductos = productos.getReadableDatabase();
        favoritos = new FavoritosSQLiteHelper(this, "FavoritosBD", null, 1);
        dbFavoritos = favoritos.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i=getIntent();
        Id = i.getExtras().getString("idProducto");

        Cursor c = dbProductos.rawQuery("select * from Productos where idProducto='"+Id+"'",null);
        if (c.moveToFirst()){
            Descripcion.setText(c.getString(3));
            Precio.setText(c.getString(4));
        }
        c= dbFavoritos.rawQuery("select * from Favoritos where idProducto='"+Id+"'",null);
        if (c.moveToFirst()){
            Fav.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view){
        if(Fav.isChecked()){
            ContentValues dataDB = new ContentValues();

            dataDB.put("idUsuario",1);
            dataDB.put("idProducto",Id);

            dbFavoritos.insert("Favoritos", null, dataDB);
        }else{
            Cursor c = dbFavoritos.rawQuery("select * from Favoritos where idProducto='"+Id+"'",null);
            if (c.moveToFirst()){
                dbFavoritos.delete("Favoritos","idProducto='"+Id+"'",null);
            }


        }
    }

}
