package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

    TabLayout tabLayout;
    ViewPager viewPager;

    public PerfilActivity() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =inflater.inflate(R.layout.activity_perfil, container, false);
        tabLayout = (TabLayout) inflated.findViewById(R.id.tabsPerfil);
        viewPager = (ViewPager) inflated.findViewById(R.id.pagerPerfil);

        //Agregar el adapter
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        //Para evitar los listener, no se puede usar iconos solo texto
        tabLayout.setupWithViewPager(viewPager);

        return inflated ;
    }

    public class PagerAdapter extends FragmentPagerAdapter{
        public PagerAdapter(FragmentManager fm){
            super (fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new PerfilFragment();
                case 1:
                    return new FavoritosFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Perfil";
                case 1 :
                    return "Favoritos";
            }
            return null;
        }

    }

}
