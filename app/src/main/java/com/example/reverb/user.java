package com.example.reverb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class user extends AppCompatActivity{

    TextView uiname ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        uiname=findViewById(R.id.uiname);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.NameUser);
        final ViewPager viewPager=findViewById(R.id.viewpager2);
        TabLayout tabLayout=findViewById(R.id.tablayout2);
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Media"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final adapter adapter= new adapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.NameUser:
                        return true;

                    case R.id.musicitem:
                        startActivity(new Intent(getApplicationContext()
                                ,AudioPlayer.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomePage.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Video:
                        startActivity(new Intent(getApplicationContext()
                                ,VideoPlayer.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    default:
                        bottomNavigationView.setSelectedItemId(R.id.home);
                }


                return false;


            }
        });
    }

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        int selectedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.home != selectedItemId) {
            setHomeItem(HomePage.class);

        } else {
            //super.onBackPressed();
            System.exit(0);
        }


    }

    public void setHomeItem(Class<HomePage> activity) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }



}