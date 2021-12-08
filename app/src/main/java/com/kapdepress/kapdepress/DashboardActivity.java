package com.kapdepress.kapdepress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardActivity extends AppCompatActivity {
private ChipNavigationBar chipNavigationBar;
private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initalization();
       if(savedInstanceState==null)
        {
            chipNavigationBar.setItemSelected(R.id.homeTab,true);
            fragmentManager=getSupportFragmentManager();
            HomeFragment homeFragment=new HomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,homeFragment)
                    .commit();

        }


        loadFragments();
    }

    private void initalization()
    {
        chipNavigationBar=findViewById(R.id.chipNavigationBar);
    }

    private void loadFragments()
    {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment=null;
                switch (id)
                {
                    case R.id.homeTab:
                        fragment=new HomeFragment();
                        break;
                    case R.id.orderTab:
                        fragment=new OrdersFragment();
                        break;
                    case R.id.cartTab:
                        fragment=new CartFragment();
                        break;
                    case R.id.walletTab:
                        fragment=new WalletFragment();
                        break;
                }
                if(fragment!=null)
                {
                    fragmentManager=getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .commit();
                }
                else
                {

                }
            }
        });
    }

}