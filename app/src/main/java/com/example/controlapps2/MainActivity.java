package com.example.controlapps2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    SwitchCompat switchCompat;
    private Fragment fragmentA;
    private Fragment fragmentB;
    private Fragment fragmentC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.idnavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        switchCompat = findViewById(R.id.r1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frameFragment, new HomeFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                Fragment fragmentA = new HomeFragment();
                Fragment fragmentB = new RecordFragment();
                Fragment fragmentC = new ControlsFragment();

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            if (fragmentA.isAdded()) {
                                ft.show(fragmentA);
                            }
                            if (fragmentB.isAdded()) {
                                ft.hide(fragmentB);
                            }
                            // Sembunyikan fragment  C
                            if (fragmentC.isAdded()) {
                                ft.hide(fragmentC);
                            } else {
                                getSupportFragmentManager().beginTransaction().add(R.id.frameFragment, new HomeFragment()).commit();
                            }
                            ft.commit();

                            break;
                        case R.id.navigation_dashboard:
                            if (fragmentB.isAdded()) {
                                ft.show(fragmentB);
                            }
                            if (fragmentA.isAdded()) {
                                ft.hide(fragmentA);
                            }
                            // Sembunyikan fragment  C
                            if (fragmentC.isAdded()) {
                                ft.hide(fragmentC);
                            } else {
                                getSupportFragmentManager().beginTransaction().add(R.id.frameFragment, new RecordFragment()).commit();
                            }
                            ft.commit();
                            break;
                        case R.id.navigation_control:
                            if (fragmentC.isAdded()) {
                                ft.show(fragmentC);
                            }
                            if (fragmentB.isAdded()) {
                                ft.hide(fragmentB);
                            }
                            // Sembunyikan fragment  C
                            if (fragmentA.isAdded()) {
                                ft.hide(fragmentA);
                            } else {
                                getSupportFragmentManager().beginTransaction().add(R.id.frameFragment, new ControlsFragment()).commit();
                            }
                            ft.commit();
                            break;
                    }
                    return true;
                }
            };


}