package com.example.pacemaker.ui.word;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.pacemaker.R;
import com.like.LikeButton;
import com.like.OnLikeListener;
import org.json.JSONArray;
import org.json.JSONException;

public class WordFragment extends Fragment {

    private SharedPreferences pref;
    private String word;
    private String pronu;
    private String mean;
    private String gram;
    private JSONArray jsonArray;
    LikeButton starCheck;

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
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        word = getArguments().getString("word", "");
        pronu = getArguments().getString("pronu", "");
        gram = getArguments().getString("gram", "");
        mean = getArguments().getString("mean", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.fragment_word, container, false);
        final SharedPreferences.Editor editor = pref.edit();

        TextView wordTxt = view.findViewById(R.id.fgword1);
        TextView pronTxt = view.findViewById(R.id.fgword1_pro);
        TextView gramTxt = view.findViewById(R.id.fgword1_gram);
        TextView meanTxt = view.findViewById(R.id.fgword1_mean);

        wordTxt.setText(word);
        pronTxt.setText(pronu);
        gramTxt.setText(gram);
        meanTxt.setText(mean);

        final String arr = "{" +"\"word\":"+"\""+word+"\""+ "," + "\"proun\":"+"\""+pronu+"\"" + "," + "\"gram\":"+"\""+gram+"\"" + "," + "\"mean\":"+"\""+mean+"\"" + "}";

        starCheck = view.findViewById(R.id.like_check);
        starCheck.setCircleEndColorRes(R.color.colorPrimary);
        starCheck.setLiked(pref.getBoolean(word, false));

        starCheck.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite", null);
                    if(tmp != null) Log.d("favorite is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(arr);
                Log.d("json is = ", String.valueOf(jsonArray));
                editor.putString("favorite", String.valueOf(jsonArray));
                editor.putBoolean(word, true);
                editor.commit();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                try {
                    String tmp = pref.getString("favorite", null);
                    if(tmp != null)Log.d("favorite is =", tmp);
                    jsonArray = tmp != null ? new JSONArray(tmp) : new JSONArray() ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int i = 0;
                while(i < jsonArray.length()){
                    try {
                        if(String.valueOf(jsonArray.get(i)).contains(word)) break;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                jsonArray.remove(i);
                Log.d("remove json is = ", String.valueOf(jsonArray));
                editor.putString("favorite", String.valueOf(jsonArray));
                editor.putBoolean(word, false);
                editor.commit();

            }
        });

        return view;
    }
}