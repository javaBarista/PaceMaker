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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class MyMathFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private JSONArray jsonArray;
    private SharedPreferences pref;
    private ArrayList<CardForm> mList = new ArrayList<>();
    private CardListAdapter cardListAdapter;

    public static MyMathFragment newInstance() {
        MyMathFragment fragment = new MyMathFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        String tmp = pref.getString("favorite_math", null);

        try {
            jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i+=2) {
                try {
                    Log.d("mymath favorite is :", jsonArray.getString(i));
                    JsonElement jsonElement1 = new JsonParser().parse(jsonArray.getString(i));
                    JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                    JsonElement jsonElement2 = new JsonParser().parse(jsonArray.getString(i + 1));
                    JsonObject jsonObject2 = jsonElement2.getAsJsonObject();
                    CardForm cardForm = new CardForm(String.valueOf(jsonObject1.get("name")).replace("\"", ""), String.valueOf(jsonObject2.get("name")).replace("\"", ""));
                    mList.add(cardForm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(jsonArray.length() % 2 > 0){
                try {
                JsonElement jsonElement1 = new JsonParser().parse(jsonArray.getString(jsonArray.length() - 1));
                JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                    CardForm cardForm = new CardForm(String.valueOf(jsonObject1.get("name")).replace("\"", ""), "NO");
                    mList.add(cardForm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.layout.fragment_mymath, container, false);

        mRecyclerView = view.findViewById(R.id.myMath_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardListAdapter = new CardListAdapter(mList, getContext());
        mRecyclerView.setAdapter(cardListAdapter);

        return view;
    }
}
