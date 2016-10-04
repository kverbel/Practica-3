package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    EditText usuario,contraseña,contraseña2,correo;
    SharedPreferences MisCarnes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        MisCarnes = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        usuario=(EditText)findViewById(R.id.eUsuarioR);
        contraseña=(EditText)findViewById(R.id.eContraseñaR);
        contraseña2=(EditText)findViewById(R.id.eContraseñaR2);
        correo=(EditText)findViewById(R.id.eCorreo);



    }

    public void OnClickAceptar(View v){
        if(usuario.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty() || contraseña2.getText().toString().isEmpty() || correo.getText().toString().isEmpty()){
            Toast.makeText(this,"No debe dejar campos vacios",Toast.LENGTH_SHORT).show();
        }
        else if(!contraseña.getText().toString().equals(contraseña2.getText().toString())){
            Toast.makeText(this,"Las contraseñas deben coincidir",Toast.LENGTH_SHORT).show();
        }
        else {
            String User=usuario.getText().toString();
            String Pass=contraseña.getText().toString();
            String Email=correo.getText().toString();

            SharedPreferences.Editor editor = MisCarnes.edit();
            editor.putString("Usuario", User);
            editor.putString("Contraseña", Pass);
            editor.putString("Correo", Email);
            editor.commit();

            //Intent i=getIntent();
            //i.putExtra("Usuario",User);
            //i.putExtra("Contraseña",Pass);
            //i.putExtra("Correo",Email);
            //setResult(RESULT_OK,i);
            setResult(RESULT_OK);
            finish();
        }

    }

    public void OnClickCancelar(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}
