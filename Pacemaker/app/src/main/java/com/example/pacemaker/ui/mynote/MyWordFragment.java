package com.example.pacemaker.ui.mynote;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.pacemaker.R;
import com.example.pacemaker.ui.word.WordForm;
import com.example.pacemaker.ui.word.WordFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MyWordFragment extends Fragment {

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private CircleIndicator circleIndicator;
    private JSONArray jsonArray;
    private SharedPreferences pref;
    private ArrayList<WordForm> mList = new ArrayList<>();

    public static MyWordFragment newInstance() {
        MyWordFragment fragment = new MyWordFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        String tmp = pref.getString("favorite", null);

        try {
            jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    Log.d("myword favorite is :", jsonArray.getString(i));
                    JsonElement jsonElement = new JsonParser().parse(jsonArray.getString(i));
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    WordForm temp = new WordForm(String.valueOf(i), String.valueOf(jsonObject.get("word")).replace("\"", ""), String.valueOf(jsonObject.get("proun")).replace("\"", ""), String.valueOf(jsonObject.get("gram")).replace("\"", ""), String.valueOf(jsonObject.get("mean")).replace("\"", ""));
                    mList.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.layout.fragment_myword, container, false);

        viewPager = view.findViewById(R.id.mywordPager);
        circleIndicator = view.findViewById(R.id.myword_indicator);
        myPagerAdapter = new MyWordFragment.MyPagerAdapter(getChildFragmentManager(), mList);
        viewPager.setAdapter(myPagerAdapter);
        circleIndicator.setViewPager(viewPager);
        return view;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS ;
        private ArrayList<WordForm> mList= new ArrayList<>();
        // private CircleIndicator indicator;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<WordForm> mList) {
            super(fragmentManager);
            this.mList = mList;
            this.NUM_ITEMS = mList.size();
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return WordFragment.newInstance(mList.get(position).getWord(), mList.get(position).getPron(),mList.get(position).getGram(), mList.get(position).getMean());
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
