package com.arteaga.kevin.miscarnesparrilla;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;

import java.util.ArrayList;

public class DescripcionActivity extends AppCompatActivity {

    ImageView image;
    TextView Des1;
    TextView Des2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        image = (ImageView) findViewById(R.id.iPromo);
        Des1 = (TextView) findViewById(R.id.tPromo1);
        Des2 = (TextView) findViewById(R.id.tPromo2);

        Intent i=getIntent();
        int Posicion = i.getExtras().getInt("Posicion");

        Promocion k = (Promocion) PromocionesActivity.items.get(Posicion);

        setTitle(k.getNombre());
        image.setImageResource(k.getImagen2());
        Des1.setText(k.getDescricion());
        Des2.setText(k.getPrecio());




        //image.setImageResource(R.id.imagen);
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
}
