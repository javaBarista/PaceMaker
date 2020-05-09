package com.example.pacemaker.ui.calender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.DateEvent;
import com.example.pacemaker.EventModifyActivity;
import com.example.pacemaker.ListViewItem;
import com.example.pacemaker.R;
import com.example.pacemaker.TranslateActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CalenderFragment extends Fragment {

    private MaterialCalendarView materialCalendarView;
    private RecyclerView mRecyclerView;
    private CalenderListItemAdapter mAdapter;
    private SharedPreferences pref;
    private LinearLayout pageHide;
    private ArrayList<CalenderListItem> mList = new ArrayList<>();
    private ArrayList<DateEvent> schedule = new ArrayList<>();
    private Map<String, Integer> count = new HashMap<>();
    private HashMap<String, String> sList = new HashMap<>();
    private static boolean trigger = true;
    public ArrayList<CalenderListItem> calenderListItems = new ArrayList<>();
    public ArrayList<ListViewItem> items = new ArrayList<>();
    private ListViewItem item;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_calender, container, false);
        materialCalendarView = root.findViewById(R.id.calendarView);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        pageHide = root.findViewById(R.id.pageHide);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        pageHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(year, 5, 1))
                .setMaximumDate(CalendarDay.from(year, 6, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        materialCalendarView.addDecorators(
                new CalenderFragment.SundayDecorator(),
                new CalenderFragment.SaturdayDecorator(),
                new CalenderFragment.OneDayDecorator());

        mRecyclerView = root.findViewById(R.id.calenderlist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        new CalenderFragment.getScheduleList().execute(materialCalendarView.getCurrentDate().getMonth() + 1);

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                trigger = false;
                new CalenderFragment.getScheduleList().execute(materialCalendarView.getCurrentDate().getMonth() + 1);
            }
        });

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(root.getContext(), sList.get(date.toString()), Toast.LENGTH_SHORT).show();
            }
        } );

        return root;
    }

    public void onStop() {
        super.onStop();
        count.clear();
        sList.clear();
        mList.clear();
        schedule.clear();
    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }

    public class EventDecorator implements DayViewDecorator {

        private final int count;
        private final HashSet<CalendarDay> dates;


        public EventDecorator(Collection<CalendarDay> dates, int count) {
            //this.color = color;
            this.dates = new HashSet<>(dates);

            this.count = count;

        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {

            view.addSpan((new CalenderFragment.MultiDotSpan(5, count)));
        }


    }

    public class MultiDotSpan implements LineBackgroundSpan {

        private float radius;
        private int count;

        public MultiDotSpan(float radius, int count) {
            this.radius = radius;
            this.count = count;
        }

        @Override
        public void drawBackground(@NonNull Canvas canvas, @NonNull Paint paint, int left, int right, int top, int baseline, int bottom, @NonNull CharSequence text, int start, int end, int lineNumber) {
            int total = count > 2 ? 3 : count;
            int loop = count % 3 != 0 ? (count / 3) + 1 : (count / 3);
            int leftMost = (total - 1) * -12;

            for(int k = 0; k < loop; k++) {
                if(k == 1 && loop == 2) total = count % 3;

                if(k == 2 && count > 6){
                    float oldSIze = paint.getTextSize();
                    paint.setTextSize(17f);
                    canvas.drawText("+" + String.valueOf(count-6), 86, 100, paint);
                    paint.setTextSize(oldSIze);
                    break;
                }
                for (int i = 0; i < total; i++) {
                    int oldColor = paint.getColor();
                    paint.setColor(Color.DKGRAY);

                    if (i % 3 == 0) {
                        bottom += 13;
                        leftMost = (total - 1) * -12;
                    }

                    canvas.drawCircle((float) ((left + right) / 2 - leftMost), bottom + radius, radius, paint);
                    //else if(dote == 7)    canvas.drawText("+" + String.valueOf(total - dote),left, bottom + radius, paint);
                    //canvas.drawLine(left,bottom + leftMost, right, bottom + leftMost, paint);

                    paint.setColor(oldColor);
                    leftMost += 24;
                }
            }
        }
    }


    public class getScheduleList extends AsyncTask<Integer, Void, CalenderListItem[]> {

        OkHttpClient client = new OkHttpClient();
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        int thisMonth = calendar.get(calendar.MONTH) + 1;
        int today = calendar.get(calendar.DAY_OF_MONTH);
        int curMonth = 0;

        @Override
        protected CalenderListItem[] doInBackground(Integer... pos) {
            String url = "https://nobles1030.cafe24.com/scheduleRequest.php";

            this.curMonth = pos[0];

            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();

                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();

                JsonElement rootObject = parser.parse(response.body().charStream());

                CalenderListItem[] posts = gson.fromJson(rootObject, CalenderListItem[].class);

                return posts;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(CalenderListItem[] result) {
            mList.clear();
            if(getContext() == null) return;

            //Calendar eventday = Calendar.getInstance();
            if (result.length > 0) {
                for (CalenderListItem post : result) {
                    if(pref.getBoolean(post.getCollege(), isInCollege(post.getCollege()))) continue;

                    int smonth = post.getMonth()[1];
                    int emonth = post.getMonth()[0];
                    if (post.getDue() > (thisMonth == 1 ? 13 : thisMonth) * 100 + today) {
                        if (smonth == curMonth || emonth == curMonth) {
                            mList.add(post);
                        }
                        if (trigger) {
                            post.setMap();
                            DateEvent dda = new DateEvent(post.getMap("sYear"), post.getMap("sMonth") - 1, post.getMap("sDay"));
                            schedule.add(dda);
                            count.put(dda.getKey(), count.get(dda.getKey()) == null ? 1 : count.get(dda.getKey()) + 1);
                            sList.put(dda.getKey(), sList.get(dda.getKey()) ==  null ? post.getCollege() + post.getTodo() +"-시작\n" : sList.get(dda.getKey()) + post.getCollege() + post.getTodo() + "-시작\n");
                            DateEvent dea = new DateEvent(post.getMap("eYear"), post.getMap("eMonth") - 1, post.getMap("eDay"));
                            schedule.add(dea);
                            count.put(dea.getKey(), count.get(dea.getKey()) == null ? 1 : count.get(dea.getKey()) + 1);
                            if(post.getTodo().contains("시험")){
                                sList.put(dea.getKey(), sList.get(dea.getKey()) == null ? post.getCollege() + post.getTodo() +"\n" : sList.get(dea.getKey()) + post.getCollege() + post.getTodo() +"\n");
                            }
                            else{
                                sList.put(dea.getKey(), sList.get(dea.getKey()) == null ? post.getCollege() + post.getTodo() + "-마감\n" : sList.get(dea.getKey()) + post.getCollege() + post.getTodo() + "-마감\n");
                            }
                        }
                    }
                }
            }

            if (trigger) {
                for (int i = 0; i < schedule.size(); i++) {
                    Calendar eventday = Calendar.getInstance();
                    eventday.set(schedule.get(i).getDate()[0], schedule.get(i).getDate()[1], schedule.get(i).getDate()[2]);
                    Log.d("date aaaaaaaaaa = ", schedule.get(i).getKey());
                    CalendarDay calendarDay = CalendarDay.from(eventday);
                    materialCalendarView.addDecorator(new CalenderFragment.EventDecorator(Collections.singleton(calendarDay), count.get(schedule.get(i).getKey())));
                }
            }

            //Adapter setting
            mAdapter = new CalenderListItemAdapter(mList);
            mRecyclerView.setAdapter(mAdapter);
        }

        private boolean isInCollege(String name){
            Resources res = getResources();
            String tmp[] = res.getStringArray(R.array.college);

            if(name.contains("여자")) return false;

            int i = 1;
            while(i < tmp.length){
                if(tmp[i].equals(name)) break;
                i++;
            }

            return i < 19 ? true :false;
        }
    }

}