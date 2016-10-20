package com.arteaga.kevin.miscarnesparrilla;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 19/10/2016.
 */
public class ProductosSQLiteHelper extends SQLiteOpenHelper {

    private int DATA_VERSION = 1;
    private String DATA_BASE_NAME = "ProductosDB";

    String sqlCreate = "CREATE TABLE Productos (idProducto TEXT, imagen INTEGER, nombre TEXT, descripcion TEXT, precio TEXT)";

    public ProductosSQLiteHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreate);
        List<Producto> items= new ArrayList();

        items.add(new Producto("T0",R.drawable.platos_tipicos, "Bandeja Paisa","Frijol, arroz,aguacate, patacón, morcilla, chorizo, carne molida, huevo y arepa.","$26.900"));
        items.add(new Producto("T1",R.drawable.platos_tipicos, "Chuleta Valluna","Lomo fino de cerdo apanado, acompañado de arroz de la finca y papas a la francesa.","$25.900"));
        items.add(new Producto("T2",R.drawable.platos_tipicos, "Patacón Berraco","Chicharrón, morcilla, chorizo, costilla, papa criolla, guacamole y hogao.","$23.900"));
        items.add(new Producto("T3",R.drawable.platos_tipicos, "Patacón con Carne","Carne desmechada, guacamole y hogao.","$22.900"));
        items.add(new Producto("T4",R.drawable.platos_tipicos, "Calentado Paisa","Con mezcla de arroz y frijol, carne de res, chicharrón, arepa, tajada de plátano y huevo frito.","$25.900"));
        items.add(new Producto("T5",R.drawable.platos_tipicos, "Solomito","Medallones de lomo envueltos en tocineta, papa sour.","$30.900"));
        items.add(new Producto("T6",R.drawable.platos_tipicos, "Frijolada","Frijoles con chicarrón, guacamole y hogao.","$25.900"));
        items.add(new Producto("T7",R.drawable.platos_tipicos, "Bistec a Caballo","Carne de res bañada en salsa grille con huevo frito, acompañada con papas criollas y arroz.","$30.900"));
        items.add(new Producto("H0",R.drawable.platos_hamburguesas, "Pollo BBQ","1/4 de libra de pechuga de pollo, bañada en salsa BBQ tomate y queso mozzarella.","$18.900"));
        items.add(new Producto("H1",R.drawable.platos_hamburguesas, "Doble Carne","1/4 de libra de carne, tomate y queso mozzarella.","$23.900"));
        items.add(new Producto("H2",R.drawable.platos_hamburguesas, "La Nuestra","1/4 de libra de carne, tomate, queso cheddar y tocineta; con papas y gaseosa.","$18.900"));
        items.add(new Producto("H3",R.drawable.platos_hamburguesas, "Colombiana","1/4 de libra de Carne, tomate, queso mozzarella, huevo frito, tocineta y salsa grille. Acompañada con papas criollas y gaseosa 16 oz.","$18.900"));
        items.add(new Producto("H4",R.drawable.platos_hamburguesas, "Ranchera","1/4 de libra de Carne, tomate, queso mozzarella y chorizo.","$18.900"));
        items.add(new Producto("H5",R.drawable.platos_hamburguesas, "Mis Carnes","1/4 de libra de carne angus, tomate, cebolla, queso cheddar, tocineta frita y lechuga con papas a la francesa y gaseosa 16 oz.","$24.900"));
        items.add(new Producto("H6",R.drawable.platos_hamburguesas, "BBQ Mis Carnes","250gr de carne, salsa BBQ de la casa, queso cheddar, tomate, lechuga; acompañada con papa francesa y bebida 16 oz","$21.900"));
        items.add(new Producto("P0",R.drawable.platos_parrilla, "Parrillada","Carne de res, pechuga, costilla de cerdo, morcilla, chorizo, arepa y papas criollas.","$29.900"));
        items.add(new Producto("P1",R.drawable.platos_parrilla, "Churrasco","Acompañado de arepa, papa francesa y ensalada.","$29.900"));
        items.add(new Producto("P2",R.drawable.platos_parrilla, "Baby Beef","Lomo de res, arepa, papa francesa y ensalada.","$31.900"));
        items.add(new Producto("P3",R.drawable.platos_parrilla, "Punta de Anca","Acompañada de arepa, chorizo, papa criolla y ensalada.","$30.900"));
        items.add(new Producto("P4",R.drawable.platos_parrilla, "Bife de Chatas","Bañado en reducción de vino, acompañado de papas rizadas.","$34.900"));


        ContentValues dataDB = new ContentValues();

        for(int i=0;i<items.size();i++) {
            //System.out.println(al.get(x));
            dataDB.put("idProducto",items.get(i).getIdProducto());
            dataDB.put("imagen",items.get(i).getImagen1());
            dataDB.put("nombre",items.get(i).getNombre());
            dataDB.put("descripcion",items.get(i).getDescricion());
            dataDB.put("precio", items.get(i).getPrecio());

            sqLiteDatabase.insert("Productos", null, dataDB);
            //sqLiteDatabase.execSQL("INSERT INTO Productos VALUES('"+items.get(i).getIdProducto()+"','"+items.get(i).getImagen1()+"','"+items.get(i).getNombre()+"', '"+items.get(i).getDescricion()+"','"+items.get(i).getPrecio()+"')");
        }





    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Productos");
        sqLiteDatabase.execSQL(sqlCreate);
    }
}
