package com.arteaga.kevin.miscarnesparrilla;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogginActivity extends AppCompatActivity {

    private final static int ObtenerDatos = 0;
    SharedPreferences MisCarnes;
    EditText usuario,contraseña ;
    String User, Pass, Email;
    int Id;
    UsuariosSQLiteHelper usuarios;
    SQLiteDatabase dbUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_loggin);

        MisCarnes= getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        usuario = (EditText)findViewById(R.id.eUsuario);
        contraseña = (EditText)findViewById(R.id.eContraseña);
        usuarios = new UsuariosSQLiteHelper(this, "UsuariosBD", null, 1);
        dbUsuarios = usuarios.getReadableDatabase();
        //User=usuario.getText().toString();


        /*
        if (MisCarnes.contains("Usuario")& MisCarnes.contains("Contraseña")) {
            User=MisCarnes.getString("Usuario", "");
            Pass=MisCarnes.getString("Usuario", "");
        }*/
    }

    public void OnClickIngresar(View v){

        Cursor c = dbUsuarios.rawQuery("select * from Usuarios where nombre='"+usuario.getText().toString()+"'",null);

        if (c.moveToFirst()){
            Id=c.getInt(0);
            User=c.getString(1);
            Pass=c.getString(2);
            Email=c.getString(3);
        }

        if (!usuario.getText().toString().equals(User) || !contraseña.getText().toString().equals(Pass)){
            Toast.makeText(this,"Usuario/Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(v.getContext(),MainActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = MisCarnes.edit();
            editor.putInt("IdUsuario",Id);
            editor.putString("Usuario", User);
            editor.putString("Correo", Email);
            editor.putBoolean("FlagLoggin", true);
            editor.commit();
            finish();
        }
    }

    public void OnClickText(View v){
        Intent intent=new Intent(v.getContext(),RegistroActivity.class);
        startActivityForResult(intent,ObtenerDatos);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this,"Registro cancelado", Toast.LENGTH_SHORT).show();
        }
        else {

            //User = MisCarnes.getString("Usuario", "");
            //Pass = MisCarnes.getString("Contraseña", "");

            //User = data.getExtras().getString("Usuario");
            //Pass = data.getExtras().getString("Contraseña");
            //Correo = data.getExtras().getString("Correo");

            //Para varios intent que devuelven resultados se pone un switch en e que se pasa el codigo del intent
            //switch (requestCode) {
                //case ObtenerDatos:
                    //usuario.setText(User);
                    //contraseña.setText(Pass);
                    //break;
            //}
        }
    }
}
