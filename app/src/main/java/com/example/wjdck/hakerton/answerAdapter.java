package com.example.wjdck.hakerton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class answerAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public answerAdapter() {}

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        int cnt = 0;
        int size = listViewItemList.size();
        for(int i = 0; i < size; i++){
            if(listViewItemList.get(i).getAnswerNum() != 0){
                cnt++;
            }
        }
        return cnt;
    }

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_answer, parent, false);
        }

        TextView answernumTextView = (TextView) convertView.findViewById(R.id.answer_number);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.answer_title) ;
        TextView startdateTextView = (TextView) convertView.findViewById(R.id.answer_startdate) ;
        TextView enddateTextView = (TextView) convertView.findViewById(R.id.answer_enddate) ;
        TextView peoplenumTextView = (TextView) convertView.findViewById(R.id.answer_people_number) ;
        TextView answeringdateTextView = (TextView) convertView.findViewById(R.id.answering_date) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        if(listViewItem.getAnswerNum() != 0){
            answernumTextView.setText(Integer.toString(listViewItem.getAnswerNum()));
            titleTextView.setText(listViewItem.getTitle());
            startdateTextView.setText("["+mSimpleDateFormat.format(Long.parseLong(listViewItem.getDate()))+"]");
            enddateTextView.setText("["+mSimpleDateFormat.format((Long.parseLong(listViewItem.getDate())) + (2592000000L))+"]");
            peoplenumTextView.setText(Long.toString(listViewItem.getRecommend()) + "명");
            //answeringdateTextView.setText(listViewItem.getAnswerDate());
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(ListViewItem item) {listViewItemList.add(item); }

    public int findItem(String key) {
        for(int i=0; i<listViewItemList.size(); i++) {
            if(listViewItemList.get(i).getKey().equals(key)){
                return i;
            }
        }
        return -1;
    }
    public void removeItem(int position) {
        listViewItemList.remove(position);
    }

    public void replaceItem(ListViewItem newItem) {
        int index = findItem(newItem.getKey());
        listViewItemList.remove(index);
        listViewItemList.add(index, newItem);
    }
}

