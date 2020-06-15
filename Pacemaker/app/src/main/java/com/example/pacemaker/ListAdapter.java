package com.example.pacemaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dday_list_item, parent, false);
        }

        ImageView college_image = (ImageView) convertView.findViewById(R.id.college_image) ;
        TextView college_name = (TextView) convertView.findViewById(R.id.college_name) ;
        TextView college_dday = (TextView) convertView.findViewById(R.id.college_dday) ;

        ListViewItem listViewItem = listViewItemList.get(position);

        //college_image.setImageDrawable(listViewItem.getwmf());
        college_name.setText(listViewItem.getCollege_name());
        college_dday.setText(listViewItem.getCollege_dday());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String name, String dday, String date) {
        ListViewItem item = new ListViewItem();

        item.setCollege_name(name);
        item.setCollege_dday(dday);
        item.setCollege_date(date);

        listViewItemList.add(item);
    }
}
