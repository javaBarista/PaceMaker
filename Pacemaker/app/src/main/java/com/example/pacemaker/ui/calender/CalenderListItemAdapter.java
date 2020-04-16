package com.example.pacemaker.ui.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;

import java.util.ArrayList;

public class CalenderListItemAdapter extends RecyclerView.Adapter<CalenderListItemAdapter.ViewHolder> {
    private ArrayList<CalenderListItem> mData;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public CalenderListItemAdapter(ArrayList<CalenderListItem> list) { mData = list; }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public CalenderListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        @SuppressLint("ResourceType") View view = inflater.inflate(R.xml.calender_recycler_item, parent, false);
        CalenderListItemAdapter.ViewHolder vh = new CalenderListItemAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(final  CalenderListItemAdapter.ViewHolder holder, final int position) {
        CalenderListItem item = mData.get(position);

                holder.college.setText(item.getCollege());
                holder.todo.setText(item.getTodo());
                holder.startDate.setText(item.getStartDate());
                holder.endDate.setText(item.getEndDate());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView college;
        TextView todo;
        TextView startDate;
        TextView endDate;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        CalenderListItem item = mData.get(pos);
                    }
                }
            });

            // 뷰 객체에 대한 참조. (hold strong reference)
            college = itemView.findViewById(R.id.college);
            todo = itemView.findViewById(R.id.todo);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
        }
    }

}
