package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class BookmarkActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        Toolbar toolbar_bookmark;

        toolbar_bookmark = (Toolbar) findViewById(R.id.bookmark_toolbar);


        //Toolbar 추가
        setSupportActionBar(toolbar_bookmark);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);
    }

}
