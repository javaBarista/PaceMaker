package com.example.pacemaker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.pacemaker.ui.calender.CalenderFragment;
import com.example.pacemaker.ui.home.HomeFragment;
import com.example.pacemaker.ui.mypage.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
    private BottomNavigationView navView;
    String sfName = "dday_counter";
    String testName, testDay;

    //@SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
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

        myPageFragment.setBundle(bundle);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();
                        bundle.putString("refreshed_page", "homeFragment");
                        return true;

                    case R.id.navigation_calender:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, calenderFragment).commitAllowingStateLoss();
                        bundle.putString("refreshed_page", "calenderFragment");
                        return true;

                    case R.id.navigation_myPage:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, myPageFragment).commitAllowingStateLoss();
                        bundle.putString("refreshed_page", "myPageFragment");
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_menu, menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_event:
                Intent eventIntent = new Intent(getApplicationContext(), EventModifyActivity.class);
                eventIntent.putExtras(bundle);
                startActivity(eventIntent);
               // finish();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Log.d("this state: ", "onRestart");

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(bundle.getString("refreshed_page", "homeFragment")) {
            case "homeFragment":
                ft.detach(homeFragment);
                ft.attach(homeFragment);
                break;
            case "calenderFragment":
                ft.detach(calenderFragment);
                ft.attach(calenderFragment);
                break;
            case "myPageFragment":
                ft.detach(myPageFragment);
                ft.attach(myPageFragment);
                break;
        }
        ft.commitAllowingStateLoss();
    }
}
