package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


public class PerfilActivity extends Fragment {

    SharedPreferences MisCarnes;
    TextView user,correo;

    public PerfilActivity() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =inflater.inflate(R.layout.activity_perfil, container, false);

        MisCarnes = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        user=(TextView) inflated.findViewById(R.id.tUsuario);
        correo=(TextView)inflated.findViewById(R.id.tCorreo);
        user.setText(MisCarnes.getString("Usuario", ""));
        correo.setText(MisCarnes.getString("Correo", ""));

        return inflated ;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_perfil);

        MisCarnes= getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        user=(TextView)findViewById(R.id.tUsuario);
        correo=(TextView)findViewById(R.id.tCorreo);
        user.setText(MisCarnes.getString("Usuario", ""));
        correo.setText(MisCarnes.getString("Correo", ""));
        //user.setText(LogginActivity.getUser());
        //correo.setText(LogginActivity.getCorreo());
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }
    */

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_principal:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
}
