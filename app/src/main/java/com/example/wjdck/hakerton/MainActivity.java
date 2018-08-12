package com.example.wjdck.hakerton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mChildEventListener;

    ListView listView;
    ListViewAdapter adapter;

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.agenda_listview);

        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
//
//        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
//        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
//        adapter.addItem("웹하드 카르텔과 디지털 성범죄 산업에 대해 특별수사 해주세요.", "73,483 명", "18.07.19 ~ 18.08.16");
//        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
//        adapter.addItem("슬리핑 차일드 체크 제도를 도입해주세요.", "102,350 명", "18.07.17 ~ 18.08.16");
//        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
//        adapter.addItem("웹하드 카르텔과 디지털 성범죄 산업에 대해 특별수사 해주세요.", "73,483 명", "18.07.19 ~ 18.08.16");
//        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
//        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");
//        adapter.addItem("희귀 난치병 보험 혜택 절실합니다..", "107,328 명", "18.07.19 ~ 18.08.19");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, adapter.getItem(position).getKey(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), detailActivity.class);
                intent.putExtra("ITEM", adapter.getItem(position));
                startActivity(intent);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("agenda");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Agenda item = dataSnapshot.getValue(Agenda.class);
                adapter.addItem(dataSnapshot.getKey(), item.title, item.agenda, Long.toString(item.recommend), mSimpleDateFormat.format(item.date));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int position = adapter.findItem(dataSnapshot.getKey());
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }
}
