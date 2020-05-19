package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class EventModifyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EventListAdapter mAdapter;
    private EditText search;
    private String tmp[];
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_modify);
        mRecyclerView = findViewById(R.id.event_college_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Resources res = getResources();
        tmp = res.getStringArray(R.array.college);

        for(int i = 1; i < tmp.length; i++)
            mList.add(tmp[i]);

        mAdapter = new EventListAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);

        search = findViewById(R.id.event_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mList.clear();
                String target = search.getText().toString();
                if(target.length() != 0) {
                    for (int i = 1; i < tmp.length; i++)
                        if(tmp[i].contains(target)) mList.add(tmp[i]);
                }
                else {
                    for(int i = 1; i < tmp.length; i++)
                        mList.add(tmp[i]);
                }

                mAdapter = new EventListAdapter(mList, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
