package com.sj.astin.multimanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.os.Bundle;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.provider.Settings;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import com.sj.astin.multimanager.R;
import com.sj.astin.multimanager.Fragment.FragmentPageAdapter;


public class AddOnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addon);
        getSupportActionBar().setTitle("Adding AddOn");
        final Context ctx = getApplicationContext();
        
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager(), ctx));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
