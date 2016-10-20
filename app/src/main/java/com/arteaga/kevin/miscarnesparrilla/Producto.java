package com.arteaga.kevin.miscarnesparrilla;

/**
 * Created by Usuario on 19/10/2016.
 */
public class Producto {
    private int imagen1;



    private String idProducto, nombre, precio, descricion;


    public Producto(String id, int imagen1, String nombre,  String descricion , String precio) {
        this.idProducto=id;
        this.imagen1 = imagen1;
        this.nombre = nombre;
        this.precio = precio;
        this.descricion = descricion;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public int getImagen1() {
        return imagen1;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDescricion() {
        return descricion;
    }
}
