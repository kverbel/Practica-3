package com.arteaga.kevin.miscarnesparrilla;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class ComidasActivity extends Fragment {

    //private ViewPager mViewPager;
    //ActionBar actionBar;
    //ActionBar.Tab tab;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View inflated =  inflater.inflate(R.layout.activity_comidas,container, false);
        tabLayout = (TabLayout) inflated.findViewById(R.id.tabs);
        viewPager = (ViewPager) inflated.findViewById(R.id.pager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_platos_tipicos_2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_platos_parrilla_2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_platos_hamburguesas_2));

        //Agregar el adapter
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));

        //Para evitar los listener, no se puede usar iconos solo texto
        //tabLayout.setupWithViewPager(viewPager);

        //Listeners para que se muevan las selecciones al tiempo si se hace swipe o si se selecciona el tab
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout)) ;
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        return inflated;

    }

/*

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    public ComidasActivity() {
        // Required empty public constructor
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comidas);


        PagerAdapter pageradapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(pageradapter);
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tab = actionBar.newTab().setIcon(R.drawable.ic_platos_tipicos).setTabListener(this);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setIcon(R.drawable.ic_platos_parrilla).setTabListener(this);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setIcon(R.drawable.ic_platos_hamburguesas).setTabListener(this);
        actionBar.addTab(tab);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        });
    }
    */

    public class PagerAdapter extends FragmentPagerAdapter{
        public PagerAdapter(FragmentManager fm){
            super (fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new TipicosFragment();
                case 1:
                    return new ParrillaFragment();
                case 2:
                    return new HamburguesaFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Típico";
                case 1 :
                    return "Parrilla";
                case 2 :
                    return "Hamburguesas";
            }
            return null;
        }

    }


}
