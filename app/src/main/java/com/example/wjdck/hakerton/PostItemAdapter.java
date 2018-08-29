package com.example.wjdck.hakerton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class PostItemAdapter extends BaseAdapter implements View.OnClickListener{
    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    private ListBtnClickListener listBtnClickListener;

    private ArrayList<PostItem> listviewItemList = new ArrayList<PostItem>();

    public PostItemAdapter(){}

    public PostItemAdapter(ListBtnClickListener clickListener){
        this.listBtnClickListener = clickListener;
    }

    @Override
    public int getCount() {
        return listviewItemList.size();
    }

    @Override
    public PostItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_bookmark, parent,false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.item_bookmark_title);
        TextView fieldTextView = (TextView) convertView.findViewById(R.id.item_bookmark_field);
        TextView startdateTextView = (TextView) convertView.findViewById(R.id.item_bookmark_startdate);
        TextView enddateTextView = (TextView) convertView.findViewById(R.id.item_bookmark_enddate);


        PostItem listViewItem = listviewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        fieldTextView.setText(listViewItem.getCategory());
        startdateTextView.setText(mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate())));
        enddateTextView.setText(mSimpleDateFormat.format((Long.parseLong(listViewItem.getDate()))+(2592000000L)));

        ImageButton button = convertView.findViewById(R.id.item_delete);
        button.setTag(position);
        button.setOnClickListener(this);

        return convertView;
    }

    public void addItem(PostItem item){
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

    public void replaceItem(PostItem newItem) {
        int index = findItem(newItem.getKey());
        listviewItemList.remove(index);
        listviewItemList.add(index, newItem);
    }

    public void onClick(View v){
        if(this.listBtnClickListener != null){
            this.listBtnClickListener.onListBtnClick((int)v.getTag());
        }
    }
}
