package com.example.wjdck.hakerton;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.wjdck.hakerton.loginActivity.Uid;

public class AnswerActivity extends AppCompatActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference UserRef;
    ChildEventListener mChildEventListener;

    ListView listView;
    answerAdapter adapter;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        int option = (int) intent.getSerializableExtra("OPTION");

        listView = findViewById(R.id.answer_listview);
        toolbar = findViewById(R.id.answer_toolbar);
        title = findViewById(R.id.answer_tooltitle);

        //Toolbar 추가
        setSupportActionBar(toolbar);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        adapter = new answerAdapter();
        listView.setAdapter(adapter);



    }

}
