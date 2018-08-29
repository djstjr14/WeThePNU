package com.example.wjdck.hakerton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class discussItemAdapter extends BaseAdapter {

    private ArrayList<discussItem> listviewItemList = new ArrayList<discussItem>();

    @Override
    public int getCount() {
        return listviewItemList.size();
    }

    @Override
    public discussItem getItem(int position) {
        return listviewItemList.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

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

        discussItem listViewItem = listviewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        recommendTextView.setText(Long.toString(listViewItem.getRecommend()) + " 명");
        dateTextView.setText(mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate())));

        return convertView;
    }

    public void addItem(discussItem item){
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

    public void replaceItem(discussItem newItem) {
        int index = findItem(newItem.getKey());
        listviewItemList.remove(index);
        listviewItemList.add(index, newItem);
    }

}
