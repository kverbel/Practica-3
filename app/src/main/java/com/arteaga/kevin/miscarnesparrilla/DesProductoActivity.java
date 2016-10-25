package com.arteaga.kevin.miscarnesparrilla;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class DesProductoActivity extends Fragment {

    DrawerLayout drawerLayout;
    TextView Descripcion;
    TextView Precio;
    CheckBox Fav;
    String Id;
    int idUser;
    SharedPreferences MisCarnes;
    ProductosSQLiteHelper productos;
    SQLiteDatabase dbProductos;
    FavoritosSQLiteHelper favoritos;
    SQLiteDatabase dbFavoritos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_des_producto);

        View inflated =inflater.inflate(R.layout.activity_des_producto, container, false);

        MisCarnes= this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        Descripcion = (TextView) inflated.findViewById(R.id.tProd1);
        Precio = (TextView) inflated.findViewById(R.id.tProd2);
        Fav = (CheckBox) inflated.findViewById(R.id.cProdF);
        productos = new ProductosSQLiteHelper(getContext(), "ProductosBD", null, 1);
        dbProductos = productos.getReadableDatabase();
        favoritos = new FavoritosSQLiteHelper(getContext(), "FavoritosBD", null, 1);
        dbFavoritos = favoritos.getWritableDatabase();
        idUser=MisCarnes.getInt("IdUsuario",0 );
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        /*Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        //Intent i=getIntent();
        //Id = i.getExtras().getString("idProducto");
        Id = MisCarnes.getString("idProducto","");

        Cursor c = dbProductos.rawQuery("select * from Productos where idProducto='"+Id+"'",null);
        if (c.moveToFirst()){
            Descripcion.setText(c.getString(3));
            Precio.setText(c.getString(4));
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(c.getString(2));
        }
        c= dbFavoritos.rawQuery("select * from Favoritos where idProducto='"+Id+"' and idUsuario='"+idUser+"'",null);
        if (c.moveToFirst()){
            Fav.setChecked(true);
        }

        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(Fav.isChecked()){
                    ContentValues dataDB = new ContentValues();

                    dataDB.put("idUsuario",idUser);
                    dataDB.put("idProducto",Id);

                    dbFavoritos.insert("Favoritos", null, dataDB);
                }
                else{
                    dbFavoritos.delete("Favoritos","idProducto='"+Id+"'and idUsuario='"+idUser+"'",null);
                }
            }
        });

        return inflated;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent intent = new Intent().setClass(this, MainActivity.class);
                //startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*public void onClick(View view){
        if(Fav.isChecked()){
            ContentValues dataDB = new ContentValues();

            dataDB.put("idUsuario",idUser);
            dataDB.put("idProducto",Id);

            dbFavoritos.insert("Favoritos", null, dataDB);
        }else{
            Cursor c = dbFavoritos.rawQuery("select * from Favoritos where idProducto='"+Id+"'",null);
            if (c.moveToFirst()){
                dbFavoritos.delete("Favoritos","idProducto='"+Id+"'",null);
            }


        }
    }*/

}
