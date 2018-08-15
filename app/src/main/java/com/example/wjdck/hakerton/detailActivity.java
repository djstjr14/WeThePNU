package com.example.wjdck.hakerton;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class detailActivity extends AppCompatActivity {

    TextView Title;
    TextView Body;
    Button btn_agree;
    EditText edit_agree;
    Toolbar toolbar_detail;
    DrawerLayout drawer;
    NavigationView navigation;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mChildEventListener;
    private DatabaseReference ref;

    RecyclerView recyclerView;
    CommentViewAdapter adapter;
    ArrayList<CommentItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final ListViewItem item = (ListViewItem) intent.getSerializableExtra("ITEM");

        btn_agree = findViewById(R.id.agree_btn);
        edit_agree = findViewById(R.id.agree_edit);
        Title = findViewById(R.id.detail_title);
        toolbar_detail = findViewById(R.id.detail_toolbar);
        drawer= findViewById(R.id.drawer);
        navigation= findViewById(R.id.navigation);

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.comment);
        adapter = new CommentViewAdapter(this, items, item.getTitle(), item.getText());
        recyclerView.setAdapter(adapter);
        Title.setText("참여인원 : ["+Long.toString(item.getRecommend())+"명]");

        //Toolbar 추가
        setSupportActionBar(toolbar_detail);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_black_18dp);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_item1:
                        break;

                    case R.id.navigation_item2:
                        break;

                    case R.id.navigation_item3:
                        break;
                }

                return true;
            }
        });

        initFirebase(item.getKey());

        btn_agree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userid = "김관우";
                String text = edit_agree.getText().toString();
                long date = Calendar.getInstance().getTimeInMillis();

                CommentItem comment = new CommentItem(userid, text, date);

                mDatabaseReference.push().setValue(comment);

                edit_agree.setText("");
                recyclerView.smoothScrollToPosition(adapter.getItemCount());

                ref.child("Agenda").child(item.getKey()).child("recommend").setValue(item.getRecommend()+1);
            }
        });
    }

    private void initFirebase(String key) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference();
        mDatabaseReference = mFirebaseDatabase.getReference(key);
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CommentItem item = dataSnapshot.getValue(CommentItem.class);
                item.setKey(dataSnapshot.getKey());
                adapter.addItem(item);
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

    //추가된 소스, ToolBar에 main.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.action_menu:{
                drawer.openDrawer(GravityCompat.END);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

}
