package com.example.pacemaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.pacemaker.ui.calender.CalenderFragment;
import com.example.pacemaker.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    protected Bundle bundle;
    protected Intent getIntent;
    private HomeFragment homeFragment;
    private CalenderFragment calenderFragment;

    //@SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calender, R.id.navigation_home, R.id.navigation_myPage)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getIntent = getIntent();
        String test = getIntent.getStringExtra("test");
        String date = getIntent.getStringExtra("date");
        bundle = getIntent.getExtras();

        Calendar today = Calendar.getInstance();
        Calendar testday = Calendar.getInstance();

        int year = Integer.parseInt(date.substring(0, date.indexOf("/")));
        int month =  Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
        int day = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));

        testday.set(year, month - 1, day);

        long lToday = today.getTimeInMillis()/(24*60*60*1000);
        long lTestday = testday.getTimeInMillis()/(24*60*60*1000);

        long ddaycount = lTestday - lToday;

        homeFragment = new HomeFragment();
        calenderFragment = new CalenderFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();

        homeFragment.setNextTest(test, String.valueOf(ddaycount));

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.nav_host_fragment, homeFragment);

                        ft1.addToBackStack(null);
                        ft1.commit();
                        //getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();
                        return true;

                    case R.id.navigation_calender:
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.nav_host_fragment, calenderFragment);

                        ft.addToBackStack(null);
                        ft.commit();
                       //getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, calenderFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });


    }

}
