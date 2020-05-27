package com.example.pacemaker.ui.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.TestResultActivity;

public class TestFinishFragment extends Fragment {
    private Bundle bundle;
    private SharedPreferences pref;

    public static TestFinishFragment newInstance(Bundle bundle) {
        TestFinishFragment fragment = new  TestFinishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("ResourceType") final View view = inflater.inflate(R.xml.test_finish_fragment, container, false);
        final SharedPreferences.Editor editor = pref.edit();

        Button button = view.findViewById(R.id.complete_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean(bundle.getString("year") + bundle.getString("college") + "complete", true);
                editor.commit();
                Intent intent = new Intent(getContext(), TestResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
