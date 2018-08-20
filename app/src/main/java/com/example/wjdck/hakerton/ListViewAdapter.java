package com.example.wjdck.hakerton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListViewAdapter extends BaseAdapter{


    private ArrayList<ListViewItem> listviewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter(){

    }

    @Override
    public int getCount(){
        return listviewItemList.size();
    }

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
        recommendTextView.setText(listViewItem.getRecommend());
        dateTextView.setText(listViewItem.getDate());


        return convertView;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public ListViewItem getItem(int position){ return listviewItemList.get(position);}

    public void addItem(String key, String title, String text, String recommend, String date){

        ListViewItem item = new ListViewItem();

        recommend = recommend + " 명";

        item.setKey(key);
        item.setTitle(title);
        item.setText(text);
        item.setRecommend(recommend);
        item.setDate(date);
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
}
