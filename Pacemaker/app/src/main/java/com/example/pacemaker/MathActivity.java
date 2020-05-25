package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.pacemaker.R.drawable.formula;
import static com.example.pacemaker.R.drawable.ic_add_circle_outline_black_24dp;
import static com.example.pacemaker.R.drawable.ic_remove_circle_outline_black_24dp;

public class MathActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List expandableListTitle;
    private HashMap expandableListDetail;
    private HashMap<Integer, Boolean> hmap = new HashMap<>();
    private EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        setTitle("Math");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hmap.put(0, true);
        hmap.put(1, true);
        hmap.put(2, true);
        hmap.put(3, true);
        hmap.put(4, true);
        hmap.put(5, true);
        hmap.put(6, true);
        hmap.put(7, true);
        hmap.put(8, true);
        hmap.put(9, true);
        hmap.put(10, true);

        expandableListView = (ExpandableListView) findViewById(R.id.math_extendable_list);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                ImageView fap_btn = v.findViewById(R.id.mathfap);
                if (hmap.get(groupPosition)) {
                    fap_btn.setImageResource(ic_remove_circle_outline_black_24dp);
                    hmap.put(groupPosition, false);
                } else {
                    fap_btn.setImageResource(ic_add_circle_outline_black_24dp);
                    hmap.put(groupPosition, true);
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        editSearch = (EditText) findViewById(R.id.search_category);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for (int n=0; n<expandableListAdapter.getGroupCount(); n++) {
                    expandableListView.expandGroup(i);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editSearch.getText().toString().length() == 0) {
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                } else {
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    HashMap<String, List<String>> ListDetail = new HashMap<>();

                    for (int i = 0; i < 11; i++) {
                        List<String> search = new ArrayList<>();

                        List<String> state = ExpandableListDataPump.getData().get(expandableListTitle.get(i).toString());
                        for (int j = 0; j < state.size(); j++) {
                            if (state.get(j).toString().contains(editSearch.getText().toString())) {
                                search.add(state.get(j));
                            }
                        }
                        if (search.size() != 0) {
                            ListDetail.put(expandableListTitle.get(i).toString(), search);
                        }
                    }
                    Log.d("List Search Result", ListDetail.toString());
                    expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, ListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    for (int i=0; i<expandableListAdapter.getGroupCount(); i++) {
                        expandableListView.expandGroup(i);
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO : process the click event for action_search item.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
