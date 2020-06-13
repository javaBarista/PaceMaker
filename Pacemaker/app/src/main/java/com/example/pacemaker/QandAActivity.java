package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class QandAActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private QandAListAdapter mAdapter;
    private EditText search;
    private ArrayList<QandAForm> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand_a);

        mRecyclerView = findViewById(R.id.qanda_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search = findViewById(R.id.qanda_search);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
