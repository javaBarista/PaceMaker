package com.example.pacemaker.ui.word;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;

public class WordFragment extends Fragment {

    private String word;
    private String pronu;
    private String mean;
    private String gram;

    public static WordFragment newInstance(String word, String pronu, String gram, String mean) {
        WordFragment fragment = new WordFragment();
        Bundle args = new Bundle();
        args.putString("word", word);
        args.putString("pronu", pronu);
        args.putString("gram", gram);
        args.putString("mean", mean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        word = getArguments().getString("word", "");
        pronu = getArguments().getString("pronu", "");
        gram = getArguments().getString("gram", "");
        mean = getArguments().getString("mean", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.fragment_word, container, false);
        TextView wordTxt = view.findViewById(R.id.fgword1);
        TextView pronTxt = view.findViewById(R.id.fgword1_pro);
        TextView gramTxt = view.findViewById(R.id.fgword1_gram);
        TextView meanTxt = view.findViewById(R.id.fgword1_mean);
        ImageView favorite = view.findViewById(R.id.favorite);
        wordTxt.setText(word);
        pronTxt.setText(pronu);
        gramTxt.setText(gram);
        meanTxt.setText(mean);

        return view;
    }
}
