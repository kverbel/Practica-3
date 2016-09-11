package com.arteaga.kevin.miscarnesparrilla;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LogginActivity extends AppCompatActivity {

    private final static int ObtenerDatos = 0;
    EditText usuario,contraseña ;
    static String User, Pass,Correo;

    public static String getCorreo() {
        return Correo;
    }

    public static String getPass() {
        return Pass;
    }

    public static String getUser() {
        return User;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_loggin);
        usuario = (EditText)findViewById(R.id.eUsuario);
        contraseña = (EditText)findViewById(R.id.eContraseña);
    }

    public void OnClickIngresar(View v){
        if (!usuario.getText().toString().equals(User) || !contraseña.getText().toString().equals(Pass)){
            Toast.makeText(this,"Usuario/Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(v.getContext(),MainActivity.class);
            startActivity(intent);
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
            User = data.getExtras().getString("Usuario");
            Pass = data.getExtras().getString("Contraseña");
            Correo = data.getExtras().getString("Correo");

            //switch (requestCode) {
                //case ObtenerDatos:
                    //usuario.setText(User);
                    //contraseña.setText(Pass);
                    //break;
            //}
        }
    }
}
