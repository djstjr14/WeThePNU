package com.example.wjdck.hakerton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.wjdck.hakerton.loginActivity.Uid;


public class BookmarkActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference UserRef;
    ChildEventListener mChildEventListener;

    ListView listView;
    PostItemAdapter adapter;
    Toolbar toolbar;
    Button btn_rec;
    Button btn_latest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        listView = findViewById(R.id.bookmark_listview);
        toolbar = findViewById(R.id.bookmark_toolbar);

        //Toolbar 추가
        setSupportActionBar(toolbar);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);

        adapter = new PostItemAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(BookmarkActivity.this, detailActivity.class);
                PostItem post = adapter.getItem(position);
                ListViewItem item = new ListViewItem(post.getKey(), post.getTitle(), post.getText(), post.getCategory(), post.getRecommend(), post.getDate());
                if(post.isBookmark()){
                    item.bookmark.put(Uid, true);
                }
                if(post.isPush()){
                    item.pushalarm.put(Uid, true);
                }
                intent.putExtra("ITEM", item);
                startActivity(intent);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        UserRef = mFirebaseDatabase.getReference("User").child(Uid).child("bookmark");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostItem post = dataSnapshot.getValue(PostItem.class);
                adapter.addItem(post);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                PostItem post = dataSnapshot.getValue(PostItem.class);
                adapter.replaceItem(post);
                adapter.notifyDataSetChanged();
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
        UserRef.addChildEventListener(mChildEventListener);
    }
}
