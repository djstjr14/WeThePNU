package com.example.wjdck.hakerton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CommentViewAdapter extends BaseAdapter{


    private ArrayList<ListViewItem> listviewItemList = new ArrayList<ListViewItem>();

    public CommentViewAdapter(){

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
            convertView = inflater.inflate(R.layout.comment_item, parent,false);
        }

        TextView useridTextView = (TextView) convertView.findViewById(R.id.comment_userid);
        TextView bodyTextView = (TextView) convertView.findViewById(R.id.comment_body);
        TextView datetimeTextView = (TextView) convertView.findViewById(R.id.comment_datetime);

        ListViewItem listViewItem = listviewItemList.get(position);

        useridTextView.setText(listViewItem.getTitle());
        bodyTextView.setText(listViewItem.getRecommend());
        datetimeTextView.setText(listViewItem.getDate());


        return convertView;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public Object getItem(int position){ return listviewItemList.get(position);}

    public void addItem(String userid, String body, String datetime){


        ListViewItem item = new ListViewItem();

        item.setTitle(userid);
        item.setRecommend(body);
        item.setDate(datetime);

        listviewItemList.add(item);
    }
}
