package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class detailAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ListView listview = (ListView) findViewById(R.id.comment);

        CommentViewAdapter adapter;
        adapter= new CommentViewAdapter();

        listview.setAdapter(adapter);

        adapter.addItem("jwj6258","댓글을 내가 한번써보는데 진짜 한번도 안써본건데이렇게 내가 댓글을 쓰네 참 나 ㅎ","13:00");
        adapter.addItem("aaa123","asejpfijpseijfpiajsepifjapsiejfpaiesfpaisenpfianspeinfpaisnepfianspeifn","13:00");
        adapter.addItem("bbasef","ㅁ레댠메ㅑㅞ댜ㅜㅍ메ㅑㅞㅑㅜ메걈프게ㅑㅜㅁ","13:00");
        adapter.addItem("1awff3","12312473098127409815704981752094861709486120948629018401293875192805","13:00");
        adapter.addItem("aaagr124","빵상!빵 빵상!","13:00");


    }

}
