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

import java.util.ArrayList;

public class answerAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<answerItem> listViewItemList = new ArrayList<answerItem>() ;

    // ListViewAdapter의 생성자
    public answerAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

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

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView answernumTextView = (TextView) convertView.findViewById(R.id.answer_number);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.answer_title) ;
        ImageView starticonImageView = (ImageView) convertView.findViewById(R.id.answer_start_icon) ;
        TextView startTextView = (TextView) convertView.findViewById(R.id.answer_start_text) ;
        TextView startdateTextView = (TextView) convertView.findViewById(R.id.answer_startdate) ;
        ImageView arrow1ImageView = (ImageView) convertView.findViewById(R.id.arrow1) ;

        ImageView endiconImageView = (ImageView) convertView.findViewById(R.id.answer_end_icon) ;
        TextView endTextView = (TextView) convertView.findViewById(R.id.answer_end_text) ;
        TextView enddateTextView = (TextView) convertView.findViewById(R.id.answer_enddate) ;
        ImageView arrow2ImageView = (ImageView) convertView.findViewById(R.id.arrow2) ;

        ImageView peopleiconImageView = (ImageView) convertView.findViewById(R.id.answer_people_icon) ;
        TextView peopleTextView = (TextView) convertView.findViewById(R.id.answer_people_text) ;
        TextView peoplenumTextView = (TextView) convertView.findViewById(R.id.answer_people_number) ;
        ImageView arrow3ImageView = (ImageView) convertView.findViewById(R.id.arrow3) ;

        ImageView answeringImageView = (ImageView) convertView.findViewById(R.id.answering_icon) ;
        TextView answeringTextView = (TextView) convertView.findViewById(R.id.answering_text) ;
        TextView answeringdateTextView = (TextView) convertView.findViewById(R.id.answering_date) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        answerItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        answernumTextView.setText(listViewItem.getAnswer_number());
        titleTextView.setText(listViewItem.getTitle());
        startdateTextView.setText(listViewItem.getStartdate());
        enddateTextView.setText(listViewItem.getEnddate());
        peoplenumTextView.setText(Long.toString(listViewItem.getAnswer_people()));
        answeringdateTextView.setText(listViewItem.getAnswering_date());
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(answerItem item) {listViewItemList.add(item); }
}

