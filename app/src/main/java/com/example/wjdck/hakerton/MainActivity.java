package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.agenda_listview);

        ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        listview.setAdapter(adapter);

        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
        adapter.addItem("웹하드 카르텔과 디지털 성범죄 산업에 대해 특별수사 해주세요.", "73,483 명", "18.07.19 ~ 18.08.16");
        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
        adapter.addItem("웹하드 카르텔과 디지털 성범죄 산업에 대해 특별수사 해주세요.", "73,483 명", "18.07.19 ~ 18.08.16");
        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);


            }
        });
    }
}
