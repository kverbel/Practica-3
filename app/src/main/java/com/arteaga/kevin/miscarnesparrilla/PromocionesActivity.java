package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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

class Promocion {

    private int imagen1, imagen2;
    private String nombre, precio, descricion;


    public Promocion(int imagen1, int imagen2, String nombre, String precio, String descricion) {
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.nombre = nombre;
        this.precio = precio;
        this.descricion = descricion;
    }

    public int getImagen1() {
        return imagen1;
    }

    public int getImagen2() {
        return imagen2;
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

class PromocionAdapter extends RecyclerView.Adapter<PromocionAdapter.PromocionViewHolder> {
    private List<Promocion> items;

    public static class PromocionViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView precio;


        public PromocionViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            precio = (TextView) v.findViewById(R.id.precio);
        }

    }



    public PromocionAdapter(List<Promocion> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PromocionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.promocion_card, viewGroup, false);
        return new PromocionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PromocionViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen1());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.precio.setText(items.get(i).getPrecio());
        //viewHolder.precio.setText("Precio:"+String.valueOf(items.get(i).getPrecio()));
    }
}

public class PromocionesActivity extends Fragment {

    private RecyclerView recycler;
    private Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    static List items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.activity_promociones, container, false);

        items = new ArrayList();

        items.add(new Promocion(R.drawable.prom1,R.drawable.cupon1, "Combo Hamburguesas", "$10.900","Combo Hamburguesa con Papas y Gaseosa 12 Oz. Disponible Martes & Jueves."));
        items.add(new Promocion(R.drawable.prom2,R.drawable.cupon2, "Happy Hour", "Desde las 5:00 P.M","2x1 en Vinos y Cócteles"));
        items.add(new Promocion(R.drawable.prom3,R.drawable.cupon3, "Mis del Mes", "$14.900","Plato Montañero. Pide tu combo con Carne de Res, Cerdo o Pechuga."));
        //items.add(new Anime(R.drawable.nhk, "Welcome to the NHK", 645));
        //items.add(new Anime(R.drawable.suzumiya, "Suzumiya Haruhi", 459));

        // Obtener el Recycler
        recycler = (RecyclerView) inflated.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new PromocionAdapter(items);
        recycler.setAdapter(adapter);


        recycler.addOnItemTouchListener(new RecyclerTouchListener(this.getActivity(), recycler, new ClickListener() {
            @Override
            public void onClick(View view, final int position){

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent().setClass(getActivity().getApplicationContext(), DescripcionActivity.class);
                        intent.putExtra("Posicion",position );
                        startActivity(intent);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "Promoción "+(position+1), Toast.LENGTH_LONG).show();
            }
        }));

        return inflated;
    }

  /**  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);

        List items = new ArrayList();

        items.add(new Promocion(R.drawable.prom1, "Martes & Jueves de Hamburguesas", 10900));
        items.add(new Promocion(R.drawable.prom2, "Happy Hour", 456));
        items.add(new Promocion(R.drawable.prom3, "Mis del Mes", 14900));
        //items.add(new Anime(R.drawable.nhk, "Welcome to the NHK", 645));
        //items.add(new Anime(R.drawable.suzumiya, "Suzumiya Haruhi", 459));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new PromocionAdapter(items);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerTouchListener(this, recycler, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(PromocionesActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(PromocionesActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }*/

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private PromocionesActivity.ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final PromocionesActivity.ClickListener clicklistener){

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





