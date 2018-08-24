package com.example.wjdck.hakerton;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter{


    private ArrayList<ListViewItem> listviewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter(){}

    @Override
    public int getCount(){
        return listviewItemList.size();
    }

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.agenda_title);
        TextView recommendTextView = (TextView) convertView.findViewById(R.id.agenda_num);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.agenda_date);

        ListViewItem listViewItem = listviewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        recommendTextView.setText(Long.toString(listViewItem.getRecommend()) + " 명");
        dateTextView.setText(mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate())));

        return convertView;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public ListViewItem getItem(int position){ return listviewItemList.get(position);}

    public void clickedList(View view){
        RelativeLayout relative = (RelativeLayout) view.findViewById(R.id.clickedFlag);
        relative.setBackgroundResource(R.color.clicked);
    };

    public void addItem(ListViewItem item){
        Collections.reverse(listviewItemList);
        listviewItemList.add(item);
        Collections.reverse(listviewItemList);
    }
    public int findItem(String key) {
        for(int i=0; i<listviewItemList.size(); i++) {
            if(listviewItemList.get(i).getKey().equals(key)){
                return i;
            }
        }
        return -1;
    }
    public void removeItem(int position) {
        listviewItemList.remove(position);
    }

    public void replaceItem(ListViewItem newItem) {
        int index = findItem(newItem.getKey());
        listviewItemList.remove(index);
        listviewItemList.add(index, newItem);
    }
}
