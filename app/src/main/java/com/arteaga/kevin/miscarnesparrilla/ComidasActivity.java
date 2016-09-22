package com.arteaga.kevin.miscarnesparrilla;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class ComidasActivity extends AppCompatActivity implements ActionBar.TabListener {

    private ViewPager mViewPager;
    ActionBar actionBar;
    ActionBar.Tab tab;

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

    }


}
