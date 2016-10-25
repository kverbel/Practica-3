package com.arteaga.kevin.miscarnesparrilla;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */

class HamburguesaAdapter extends RecyclerView.Adapter<HamburguesaAdapter.HamburguesaViewHolder> {
    private List<Producto> items;

    public static class HamburguesaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView precio;
        public TextView descripcion;

        public HamburguesaViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenPr);
            nombre = (TextView) v.findViewById(R.id.nombrePr);
            precio = (TextView) v.findViewById(R.id.precioPr);
            descripcion = (TextView) v.findViewById(R.id.descripcionPr);
        }
    }

    public HamburguesaAdapter(List<Producto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public HamburguesaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.productos_card, viewGroup, false);
        return new HamburguesaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HamburguesaViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen1());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.precio.setText(items.get(i).getPrecio());
        viewHolder.descripcion.setText(items.get(i).getDescricion());
    }
}

public class HamburguesaFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<Producto> items;
    SharedPreferences MisCarnes;
    ProductosSQLiteHelper productos;
    SQLiteDatabase dbProductos;


    public HamburguesaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_hamburguesa, container, false);

        MisCarnes = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        productos = new ProductosSQLiteHelper(getContext(), "ProductosBD", null, 1);
        dbProductos = productos.getReadableDatabase();

        items = new ArrayList();
        //final Cursor c = dbProductos.rawQuery("select idProducto,imagen,nombre,descripcion,precio  from Productos", null);
        final Cursor c = dbProductos.rawQuery("select * from Productos where idProducto like 'H%'", null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                items.add(new Producto(c.getString(0), c.getInt(1), c.getString(2), c.getString(3), c.getString(4)));
            } while (c.moveToNext());
        }

        // Obtener el Recycler
        recycler = (RecyclerView) inflated.findViewById(R.id.recicladorHam);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new HamburguesaAdapter(items);
        recycler.setAdapter(adapter);


        recycler.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, final int position){

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /*Intent intent = new Intent().setClass(getActivity().getApplicationContext(), DesProductoActivity.class);
                        intent.putExtra("idProducto",items.get(position).getIdProducto());
                        startActivity(intent);*/

                        SharedPreferences.Editor editor = MisCarnes.edit();
                        editor.putString("idProducto",items.get(position).getIdProducto());
                        editor.commit();

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framePrincipal, new DesProductoActivity())
                                .addToBackStack(null)   //addToBackStack Para regresar entre Fragments
                                .commit();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "Hamburguesa "+(position+1), Toast.LENGTH_SHORT).show();
            }
        }));

        return inflated;
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private HamburguesaFragment.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final HamburguesaFragment.ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
