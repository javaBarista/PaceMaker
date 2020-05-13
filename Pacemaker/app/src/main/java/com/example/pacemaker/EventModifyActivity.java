package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;

public class EventModifyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EventListAdapter mAdapter;
    private String tmp[];

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

        String mList[] = new String[tmp.length - 1];
        for(int i = 1; i < tmp.length; i++)
            mList[i - 1] = tmp[i];

        mAdapter = new EventListAdapter(mList, this);
        mRecyclerView.setAdapter(mAdapter);
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
