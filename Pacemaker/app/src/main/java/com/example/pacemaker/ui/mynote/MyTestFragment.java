package com.example.pacemaker.ui.mynote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.pacemaker.R;

public class MyTestFragment extends Fragment {
    public static MyTestFragment newInstance() {
        MyTestFragment fragment = new MyTestFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.layout.fragment_mytest, container, false);

        return view;
    }
}
