package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QandAActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private QandAListAdapter mAdapter;
    private EditText search;
    private GetQuestion getQuestion = new GetQuestion();
    private ArrayList<QandAForm> mList = new ArrayList<>();
    private ArrayList<QandAForm> tmpList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qand_a);

        mRecyclerView = findViewById(R.id.qanda_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getQuestion.execute();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                QandAForm item = mList.get(position);
                Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
                intent.putExtra("id", item.getUserID());
                intent.putExtra("num", item.getNum());
                intent.putExtra("year", item.getYear());
                intent.putExtra("name", item.getName());
                intent.putExtra("testNum", item.getTestNum());
                intent.putExtra("date", item.getUploadDate());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        search = findViewById(R.id.qanda_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mList.clear();
                String target = search.getText().toString();
                if(target.length() > 1) {
                    for (int i = 0; i < tmpList.size(); i++) {
                        String sds = tmpList.get(i).getYear() + " " + tmpList.get(i).getName() + " " + tmpList.get(i).getTestNum() + "번";
                        if (sds.contains(target))mList.add(tmpList.get(i));
                    }
                }
                else mList.addAll(tmpList);

                mAdapter = new QandAListAdapter(mList, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    private class GetQuestion extends AsyncTask<String, Void, QandAForm[]> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected QandAForm[] doInBackground(String... URL) {

            String url = "http://nobles1030.cafe24.com/request_QuestionBoard.php";
            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream());

                QandAForm[] posts = gson.fromJson(rootObject, QandAForm[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(QandAForm[] result) {
            if(result == null) return;
            if(result.length > 0){
                for (QandAForm post: result){
                    mList.add(post);
                    tmpList.add(post);
                }
                //Adapter setting
                mAdapter = new QandAListAdapter(mList, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }
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

    @Override
    public void onStop() {
        super.onStop();
        getQuestion.cancel(true);
    }
}
