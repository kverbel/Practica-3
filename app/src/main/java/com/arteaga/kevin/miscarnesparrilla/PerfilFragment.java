package com.arteaga.kevin.miscarnesparrilla;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    SharedPreferences MisCarnes;
    TextView user,correo;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflated =inflater.inflate(R.layout.fragment_perfil, container, false);

        MisCarnes = this.getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        user=(TextView) inflated.findViewById(R.id.tUsuario);
        correo=(TextView)inflated.findViewById(R.id.tCorreo);
        user.setText(MisCarnes.getString("Usuario", ""));
        correo.setText(MisCarnes.getString("Correo", ""));

        return inflated;
    }

}
