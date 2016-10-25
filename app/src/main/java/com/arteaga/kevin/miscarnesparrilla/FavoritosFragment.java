package com.arteaga.kevin.miscarnesparrilla;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder> {
    private List<Producto> items;

    public static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView precio;
        public TextView descripcion;

        public FavoritoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenPr);
            nombre = (TextView) v.findViewById(R.id.nombrePr);
            precio = (TextView) v.findViewById(R.id.precioPr);
            descripcion = (TextView) v.findViewById(R.id.descripcionPr);
        }
    }

    public FavoritoAdapter(List<Producto> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getItemNombre(int i){return items.get(i).getNombre();}

    @Override
    public FavoritoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.productos_card, viewGroup, false);
        return new FavoritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoritoViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen1());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.precio.setText(items.get(i).getPrecio());
        viewHolder.descripcion.setText(items.get(i).getDescricion());
    }
}

public class FavoritosFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    SharedPreferences MisCarnes;
    List<Producto> items;
    ProductosSQLiteHelper productos;
    SQLiteDatabase dbProductos;
    FavoritosSQLiteHelper favoritos;
    SQLiteDatabase dbFavoritos;



    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_favoritos, container, false);

        MisCarnes = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        productos = new ProductosSQLiteHelper(getContext(), "ProductosBD", null, 1);
        dbProductos = productos.getReadableDatabase();
        favoritos = new FavoritosSQLiteHelper(getContext(), "FavoritosBD", null, 1);
        dbFavoritos = favoritos.getWritableDatabase();

        items = new ArrayList();


        final Cursor c1 = dbFavoritos.rawQuery("select * from Favoritos where idUsuario='"+MisCarnes.getInt("IdUsuario",0 )+"'", null);
        if (c1.moveToFirst()){
            ArrayList<String> Ids = new ArrayList<String>();
            do{
                Ids.add(c1.getString(2));
            }while (c1.moveToNext());
            for(int i=0;i<Ids.size();i++) {
                final Cursor c2 = dbProductos.rawQuery("select * from Productos where idProducto like '"+Ids.get(i)+"'", null);
                if (c2.moveToFirst()) {
                    items.add(new Producto(c2.getString(0), c2.getInt(1), c2.getString(2), c2.getString(3), c2.getString(4)));
                }
            }
        }

        // Obtener el Recycler
        recycler = (RecyclerView) inflated.findViewById(R.id.recicladorFav);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new FavoritoAdapter(items);
        recycler.setAdapter(adapter);




        recycler.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, final int position){

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /*
                        Intent intent = new Intent().setClass(getActivity().getApplicationContext(), DesProductoActivity.class);
                        intent.putExtra("idProducto",items.get(position).getIdProducto());
                        startActivity(intent);
                        //getActivity().finish();
                        */
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

                Toast.makeText(getActivity().getApplicationContext(), "Favorito "+(position+1), Toast.LENGTH_SHORT).show();
            }
        }));

        return inflated;
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private FavoritosFragment.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final FavoritosFragment.ClickListener clicklistener){

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
