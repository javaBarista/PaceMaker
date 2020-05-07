package com.example.pacemaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.pacemaker.ui.calender.CalenderFragment;
import com.example.pacemaker.ui.home.HomeFragment;
import com.example.pacemaker.ui.mypage.MyPageFragment;
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
    private MyPageFragment myPageFragment;
    String sfName = "dday_counter";
    String testName, testDay;

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
        testName = test;
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
        myPageFragment = new MyPageFragment();

        SharedPreferences sf = getSharedPreferences(sfName, 0);
        testName = sf.getString("test_sf", test);
        testDay = sf.getString("day_sf", String.valueOf(ddaycount));
        homeFragment.setNextTest(testName, testDay);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();

        myPageFragment.setBundle(bundle);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        /*
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.nav_host_fragment, homeFragment);

                        ft1.addToBackStack(null);
                        ft1.commit();
                         */
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();
                        return true;

                    case R.id.navigation_calender:
                        /*
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.nav_host_fragment, calenderFragment);

                        ft.addToBackStack(null);
                        ft.commit();

                         */
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, calenderFragment).commitAllowingStateLoss();
                        return true;

                    case R.id.navigation_myPage:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, myPageFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();

//        // SharedPreferences
//        SharedPreferences sf = getSharedPreferences(sfName, 0);
//        SharedPreferences.Editor editor = sf.edit();
//        String name = homeFragment.getNextTest_name();
//        String day = homeFragment.getNextTest_day();
//        editor.putString("test_sf", name);
//        editor.putString("day_sf", day);
//        editor.commit();
    }
}
