package com.example.pacemaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<String> mData;
    private SharedPreferences pref;
    private Context context;
    private int curPos = -1;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    EventListAdapter(ArrayList<String> list, Context context) {
        mData = list;
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.event_recycler_item, parent, false);
        EventListAdapter.ViewHolder vh = new EventListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final EventListAdapter.ViewHolder holder, final int position) {
        final SharedPreferences.Editor editor = pref.edit();
        final String item = mData.get(position);
        final boolean[] chk = new boolean[1];

        holder.title.setText(item);
        if(position < 19 && !item.contains("여자")) chk[0] = pref.getBoolean(item, true); //국숭세단 까지 여대 빼고
        else chk[0] = pref.getBoolean(item, false); // 그 이외

        holder.chkBox.setChecked(chk[0]);

        holder.chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                chk[0] = !chk[0];
                editor.putBoolean(item, chk[0]);
                holder.chkBox.setChecked(chk[0]);
                editor.commit();
            }
        });
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox chkBox;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.findViewById(R.id.event_college_name);
            chkBox = itemView.findViewById(R.id.event_chkBox);
        }
    }
}
