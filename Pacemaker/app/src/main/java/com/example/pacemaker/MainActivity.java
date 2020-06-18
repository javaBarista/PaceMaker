package com.example.pacemaker;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    protected Bundle bundle;
    protected Intent getIntent;
    private HomeFragment homeFragment;
    private CalenderFragment calenderFragment;
    private MyPageFragment myPageFragment;
    private BottomNavigationView navView;
    private static int target = 1;

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
        bundle = getIntent.getExtras();

        homeFragment = new HomeFragment();
        calenderFragment = new CalenderFragment();
        myPageFragment = new MyPageFragment();

        homeFragment.setNextTest(bundle);
        myPageFragment.setBundle(bundle);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, homeFragment).commitAllowingStateLoss();
                        //bundle.putString("refreshed_page", "homeFragment");
                        target = 1;
                        return true;

                    case R.id.navigation_calender:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, calenderFragment).commitAllowingStateLoss();
                        //bundle.putString("refreshed_page", "calenderFragment");
                        target = 2;
                        return true;

                    case R.id.navigation_myPage:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.nav_host_fragment, myPageFragment).commitAllowingStateLoss();
                        //bundle.putString("refreshed_page", "myPageFragment");
                        target = 3;
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
        switch(target) {
            case 1:
                ft.detach(homeFragment);
                ft.attach(homeFragment);
                break;
            case 2:
                ft.detach(calenderFragment);
                ft.attach(calenderFragment);
                break;
            case 3:
                ft.detach(myPageFragment);
                ft.attach(myPageFragment);
                break;
        }

        ft.commitAllowingStateLoss();
    }
}
