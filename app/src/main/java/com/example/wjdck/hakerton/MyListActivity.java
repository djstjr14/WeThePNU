package com.example.wjdck.hakerton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.wjdck.hakerton.loginActivity.Uid;


public class MyListActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference UserRef;
    ChildEventListener mChildEventListener;

    ListView listView;
    PostItemAdapter adapter;
    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        Intent intent = getIntent();
        int option = (int) intent.getSerializableExtra("OPTION");

        listView = findViewById(R.id.mylist_listview);
        toolbar = findViewById(R.id.mylist_toolbar);
        title = findViewById(R.id.mylist_title);

        //Toolbar 추가
        setSupportActionBar(toolbar);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);
        if(option == 2){
            title.setText("푸 쉬 알 림 목 록");
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        if(option == 1){
            UserRef = mFirebaseDatabase.getReference("User").child(Uid).child("bookmark");
        }
        else if(option == 2){
            UserRef = mFirebaseDatabase.getReference("User").child(Uid).child("pushalarm");
        }

        adapter = new PostItemAdapter(option);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(MyListActivity.this, detailActivity.class);
                PostItem post = adapter.getItem(position);
                ListViewItem item = new ListViewItem(post.getKey(), post.getTitle(), post.getText(), post.getCategory(), post.getRecommend(), post.getDate(), post.isAnswered());
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
