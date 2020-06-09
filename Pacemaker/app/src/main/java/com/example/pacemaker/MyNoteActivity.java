package com.example.pacemaker;

import android.os.Bundle;
import android.view.MenuItem;
import com.example.pacemaker.ui.mynote.MyMathFragment;
import com.example.pacemaker.ui.mynote.MyTestFragment;
import com.example.pacemaker.ui.mynote.MyWordFragment;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MyNoteActivity extends AppCompatActivity {

    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.myNote_view_pager);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
          switch(position){
              case 0:
                  return MyWordFragment.newInstance();
              case 1:
                  return MyMathFragment.newInstance();
              case 2:
                  return MyTestFragment.newInstance();
          }
          return  null;
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "My Word";
                case 1:
                    return "My Math";
                case 2:
                    return "My Test";
            }
            return null;
        }
    }
}