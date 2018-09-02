package com.example.wjdck.hakerton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.example.wjdck.hakerton.loginActivity.Uid;

public class discussItemAdapter extends BaseAdapter {

    private ArrayList<discussItem> listviewItemList = new ArrayList<discussItem>();
    private final static int REC = 1;
    private final static int LAT = 2;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    private int sort = LAT;

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
            convertView = inflater.inflate(R.layout.item_discuss, parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.discuss_title);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.discuss_date);
        TextView histTextView = (TextView) convertView.findViewById((R.id.hits_num));
        TextView recommendsTextView = (TextView) convertView.findViewById((R.id.recommends_num));
        TextView commentsTextView = (TextView) convertView.findViewById(R.id.discuss_comments);

        discussItem listViewItem = listviewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        dateTextView.setText(mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate())));
        histTextView.setText(Long.toString(listViewItem.getHits()));
        recommendsTextView.setText(Long.toString(listViewItem.getRecommend()));
        commentsTextView.setText(Long.toString(listViewItem.getComments()));
        if(listViewItem.getClicked().containsKey(Uid)){
            clickedList(convertView);
        }else{
            unClickedList(convertView);
        }

        return convertView;
    }

    public void addItem(discussItem item){
        listviewItemList.add(item);
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
    public void clickedList(View view){
        LinearLayout relative = (LinearLayout) view.findViewById(R.id.clickedFlag_discuss);
        relative.setBackgroundResource(R.color.clicked);
    };
    public void  unClickedList(View view){
        LinearLayout relative = (LinearLayout) view.findViewById(R.id.clickedFlag_discuss);
        relative.setBackgroundResource(R.color.unclicked);
    }
    public void listSort(){
        Comparator<discussItem> noAsc = null;
        switch(sort) {
            case REC:noAsc = new Comparator<discussItem>() {
                @Override
                public int compare(discussItem item1, discussItem item2) {
                    return (int)(item2.getRecommend() - item1.getRecommend());
                }
            };
                break;
            case LAT:noAsc = new Comparator<discussItem>() {
                @Override
                public int compare(discussItem item1, discussItem item2) {
                    return (int)(Long.parseLong(item2.getDate()) - Long.parseLong(item1.getDate()));
                }
            };
                break;
        }
        Collections.sort(listviewItemList, noAsc);
    }

}
