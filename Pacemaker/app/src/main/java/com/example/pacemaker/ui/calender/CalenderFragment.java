package com.example.pacemaker.ui.calender;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pacemaker.DateEvent;
import com.example.pacemaker.ListViewItem;
import com.example.pacemaker.R;
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

       new GetScheduleList().execute(materialCalendarView.getCurrentDate().getMonth() + 1);

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                trigger = false;
                new GetScheduleList().execute(materialCalendarView.getCurrentDate().getMonth() + 1);
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

        private int test_cnt;
        private int appli_cnt;
        private int docu_cnt;
        private final HashSet<CalendarDay> dates;


        public EventDecorator(Collection<CalendarDay> dates, int test_cnt, int appli_cnt, int docu_cnt) {
            //this.color = color;
            this.dates = new HashSet<>(dates);
            this.test_cnt = test_cnt;
            this.appli_cnt = appli_cnt;
            this.docu_cnt = docu_cnt;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan((new CalenderFragment.MultiDotSpan(5, test_cnt, appli_cnt, docu_cnt)));
        }
    }

    public class MultiDotSpan implements LineBackgroundSpan {

        private float radius;
        private int test_cnt;
        private int appli_cnt;
        private int docu_cnt;

        public MultiDotSpan(float radius, int test_cnt, int appli_cnt, int docu_cnt) {
            this.radius = radius;
            this.test_cnt = test_cnt;
            this.appli_cnt = appli_cnt;
            this.docu_cnt = docu_cnt;
        }

        @Override
        public void drawBackground(@NonNull Canvas canvas, @NonNull Paint paint, int left, int right, int top, int baseline, int bottom, @NonNull CharSequence text, int start, int end, int lineNumber) {
            float oldSIze = paint.getTextSize();
            int oldColor = paint.getColor();
            int interval = 60;
            paint.setTextSize(18f);

                if(test_cnt > 0) {
                    paint.setColor(Color.parseColor("#FF7B68EE"));
                    canvas.drawText("시험: " + String.valueOf(test_cnt) + "개", 33, interval, paint);
                    interval += 19;
                }
                if(appli_cnt > 0) {
                    paint.setColor(Color.parseColor("#FFFFA500"));
                    canvas.drawText("원서: " + String.valueOf(appli_cnt) + "개", 33, interval, paint);
                    interval += 19;
                }
            if(docu_cnt > 0) {
                paint.setColor(Color.parseColor("#FF2E8B57"));
                canvas.drawText("서류: " + String.valueOf(docu_cnt) + "개", 33, interval, paint);
            }

            paint.setTextSize(oldSIze);
            paint.setColor(oldColor);

        }
    }


    public class GetScheduleList extends AsyncTask<Integer, Void, CalenderListItem[]> {

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
                    if(!pref.getBoolean(post.getCollege(), isInCollege(post.getCollege()))) continue;

                    int smonth = post.getMonth()[1];
                    int emonth = post.getMonth()[0];

                    String todo = "";
                    if(post.getTodo().contains("시험")) todo = "시험";
                    else if(post.getTodo().contains("원서")) todo = "원서";
                    else  todo = "서류";

                    if (post.getDue() > (thisMonth == 1 ? 13 : thisMonth) * 100 + today) {
                        if (smonth == curMonth || emonth == curMonth) {
                            mList.add(post);
                        }
                        if (trigger) {
                            post.setMap();
                            DateEvent dda = new DateEvent(post.getMap("sYear"), post.getMap("sMonth") - 1, post.getMap("sDay"));
                            schedule.add(dda);
                            count.put(dda.getKey() + todo, count.get(dda.getKey() + todo) == null ? 1 : count.get(dda.getKey() + todo) + 1);
                            sList.put(dda.getKey(), sList.get(dda.getKey()) ==  null ? post.getCollege() + post.getTodo() +"-시작\n" : sList.get(dda.getKey()) + post.getCollege() + post.getTodo() + "-시작\n");
                            DateEvent dea = new DateEvent(post.getMap("eYear"), post.getMap("eMonth") - 1, post.getMap("eDay"));
                            schedule.add(dea);
                            count.put(dea.getKey() + todo, count.get(dea.getKey() + todo) == null ? 1 : count.get(dea.getKey() + todo) + 1);
                            if(todo.equals("시험")){
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
                    int test = count.get(schedule.get(i).getKey() + "시험") == null ? 0 : count.get(schedule.get(i).getKey() + "시험");
                    int appli = count.get(schedule.get(i).getKey() + "원서") == null ? 0 :  count.get(schedule.get(i).getKey() + "원서");
                    int docu = count.get(schedule.get(i).getKey() + "서류") == null ? 0 : count.get(schedule.get(i).getKey() + "서류");

                    Calendar eventday = Calendar.getInstance();
                    eventday.set(schedule.get(i).getDate()[0], schedule.get(i).getDate()[1], schedule.get(i).getDate()[2]);

                    CalendarDay calendarDay = CalendarDay.from(eventday);
                    materialCalendarView.addDecorator(new CalenderFragment.EventDecorator(Collections.singleton(calendarDay), test, appli, docu));
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