package com.example.wjdck.hakerton;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static com.example.wjdck.hakerton.loginActivity.Uid;

public class PostItemAdapter extends BaseAdapter {
    private ArrayList<PostItem> listviewItemList = new ArrayList<PostItem>();
    private int option;

    public PostItemAdapter(int option){
        this.option = option;
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


        final PostItem listViewItem = listviewItemList.get(position);

        titleTextView.setText(listViewItem.getTitle());
        fieldTextView.setText(listViewItem.getCategory());
        startdateTextView.setText(mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate())));
        enddateTextView.setText(mSimpleDateFormat.format((Long.parseLong(listViewItem.getDate()))+(2592000000L)));

        ImageView cancle_btn = convertView.findViewById((R.id.item_delete));
        cancle_btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Agenda").child(listViewItem.getKey());
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(Uid);
                if(option == 1){
                    detailActivity.onBookmarkClicked(ref);
                    detailActivity.onBookmarkSave(userRef, listViewItem);
                }
                else if(option == 2){
                    detailActivity.onPushClicked(ref);
                    detailActivity.onPushSave(userRef, listViewItem);
                }
            }
        });

        return convertView;
    }

    public void addItem(PostItem item){listviewItemList.add(item);}

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
}