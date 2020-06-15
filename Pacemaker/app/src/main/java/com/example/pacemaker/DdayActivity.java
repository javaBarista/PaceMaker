package com.example.pacemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacemaker.ui.calender.CalenderFragment;
import com.example.pacemaker.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class DdayActivity extends AppCompatActivity  {
    public ArrayList<ListViewItem> itemArrayList = new ArrayList<>();
    private ListView listView;
    private ListViewItem listViewItem;
    private ListAdapter listAdapter;
    private FragmentRefreshListener fragmentRefreshListener;
    private GetData task = new GetData();
    String sfName = "dday_counter";

    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="dday_college";
    private static final String TAG_ID = "college";
    private static final String TAG_NAME = "endDate";

    String mJsonString;

    String url = "https://nobles1030.cafe24.com/scheduleRequest2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);

        setTitle("D-day");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewItem = new ListViewItem();
        listView = (ListView) findViewById(R.id.college_list_view);
        listAdapter = new ListAdapter();
        task.execute(url);

        // listview 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String collegeName = item.getCollege_name();
                String collegeDday = item.getCollege_date();

                if(getFragmentRefreshListener()!= null){
                    getFragmentRefreshListener().onRefresh();
                }

                Intent intent = new Intent();
                intent.putExtra("new_nextTest", collegeName);
                intent.putExtra("new_dday", collegeDday);
                setResult(RESULT_OK, intent);
                //Toast.makeText(getApplicationContext(), collegeName+" D-"+collegeDday, Toast.LENGTH_LONG).show();

                // SharedPreferences
                SharedPreferences sf = getSharedPreferences(sfName, 0);
                SharedPreferences.Editor editor = sf.edit();
                String name = collegeName;
                String day = collegeDday;
                editor.putString("test_sf", name);
                editor.putString("day_sf", day);
                editor.commit();

                finish();
            }
        });
    }

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }
    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }
    public interface FragmentRefreshListener{
        void onRefresh();
    }


    private class GetData extends AsyncTask<String, Void, String>{
        private ProgressDialog pd;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DdayActivity.this);
            pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            pd.setContentView(R.layout.custom_progress_bar);

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pd.dismiss();
            Log.d(TAG, "response  - " + result);
            if (result == null){ }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);

                String college_name = item.getString(TAG_ID);
                String college_date = item.getString(TAG_NAME);
                listAdapter.addItem(college_name, countDday(college_date), college_date);
            }
            listView.setAdapter(listAdapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

    // 디데이 계산
    public String countDday(String date) {
        Calendar today = Calendar.getInstance();
        Calendar testday = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0, date.indexOf("/")));
        int month =  Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/")));
        int day = Integer.parseInt(date.substring(date.lastIndexOf("/") + 1, date.length()));

        testday.set(year, month - 1, day);

        long lToday = today.getTimeInMillis()/(24*60*60*1000);
        long lTestday = testday.getTimeInMillis()/(24*60*60*1000);
        long ddaycount = lTestday - lToday;
        String dday = String.valueOf(ddaycount);
        return dday;
    }

    @Override
    public void onStop() {
        super.onStop();
        task.cancel(true);
    }
}
