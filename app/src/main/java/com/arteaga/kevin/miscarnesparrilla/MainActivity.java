package com.arteaga.kevin.miscarnesparrilla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    SharedPreferences MisCarnes;
    TextView UserHeader, CorreoHeader;
    View Header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        //Toolbar con Library Desing Widget
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MisCarnes = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);

        //Manipular Nav_Header
        Header = navView.getHeaderView(0); //Variable tipo View
        UserHeader = (TextView) Header.findViewById(R.id.hIdUser);
        CorreoHeader = (TextView) Header.findViewById(R.id.hIdCorreo);
        UserHeader.setText(MisCarnes.getString("Usuario",""));
        CorreoHeader.setText(MisCarnes.getString("Correo",""));

        //Principal arranca checkeado
        getSupportFragmentManager().beginTransaction().replace(R.id.framePrincipal, new InicioFragment()).commit();
        navView.setCheckedItem(R.id.option_principal);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.option_principal:
                                fragment = new InicioFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.option_promociones:
                                //Intent promo = new Intent(getApplicationContext(),PromocionesActivity.class);
                                //startActivity(promo);
                                fragment = new PromocionesActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.option_perfil:
                                fragment = new PerfilActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.option_comidas:
                                fragment = new ComidasActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.option_salir:
                                Intent loggin = new Intent(getApplicationContext(),LogginActivity.class);
                                startActivity(loggin);
                                SharedPreferences.Editor editor = MisCarnes.edit();
                                editor.putBoolean("FlagLoggin", false);
                                editor.commit();
                                finish();
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.framePrincipal, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });


    }



    // Menu Overflow
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            /*case R.id.action_perfil:
                intent = new Intent(this,PerfilActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_comidas:
                intent = new Intent(this,ComidasActivity.class);
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
