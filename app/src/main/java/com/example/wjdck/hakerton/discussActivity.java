package com.example.wjdck.hakerton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.melnykov.fab.FloatingActionButton;

import static com.example.wjdck.hakerton.loginActivity.Uid;
import static com.example.wjdck.hakerton.loginActivity.appData;

public class discussActivity extends AppCompatActivity {
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mChildEventListener;

    ListView listView;
    discussItemAdapter adapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigation;
    ImageButton btn_rec;
    ImageButton btn_latest;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        listView = findViewById(R.id.listview_discuss);
        toolbar = findViewById(R.id.toolbar_discuss);
        drawer = findViewById(R.id.drawer_discuss);
        navigation = findViewById(R.id.navigation_discuss);
        btn_latest = findViewById(R.id.new_btn);
        btn_rec = findViewById(R.id.recommend_btn);

        //Toolbar 추가
        setSupportActionBar(toolbar);
        //뒤로가기 버튼 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);

        adapter = new discussItemAdapter();
        listView.setAdapter(adapter);

        fab = findViewById(R.id.fab_discuss);
        fab.attachToListView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(discussActivity.this, discussDetailActivity.class);
                discussItem item = adapter.getItem(position);
                onItemClicked(mDatabaseReference.child(item.getKey()));
                adapter.clickedList(view);
                intent.putExtra("ITEM", adapter.getItem(position));
                startActivity(intent);
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_item1:
                        Intent intent1 = new Intent(discussActivity.this, MyListActivity.class);
                        intent1.putExtra("OPTION", 1);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(discussActivity.this, MyListActivity.class);
                        intent2.putExtra("OPTION", 2);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(discussActivity.this, discussActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item4:
                        Intent intent4 = new Intent(discussActivity.this, AnswerActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.navigation_item5:
                        Intent intent5 = new Intent(discussActivity.this, loginActivity.class);
                        Uid = null;
                        startActivity(intent5); SharedPreferences.Editor editor= appData.edit();
                        editor.clear().apply();
                        startActivity(intent5);
                        break;
                }

                return true;
            }
        });

        btn_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setSort(1);
                btn_rec.setImageResource(R.drawable.btn_recommend_normal);
                btn_latest.setImageResource(R.drawable.btn_latest_clicked);
            }
        });
        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setSort(2);
                btn_rec.setImageResource(R.drawable.btn_recommend_clicked);
                btn_latest.setImageResource(R.drawable.btn_latest_normal);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Discuss");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                discussItem item = dataSnapshot.getValue(discussItem.class);
                if(item.getKey().equalsIgnoreCase("")) {
                    item.setKey(dataSnapshot.getKey());
                    mDatabaseReference.child(dataSnapshot.getKey()).child("key").setValue(dataSnapshot.getKey());
                }
                adapter.addItem(item);
                adapter.listSort();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                discussItem item = dataSnapshot.getValue(discussItem.class);
                if(item.getKey().equalsIgnoreCase("")){
                    item.setKey(dataSnapshot.getKey());
                    mDatabaseReference.child(dataSnapshot.getKey()).child("key").setValue(dataSnapshot.getKey());
                }
                adapter.replaceItem(item);
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
        mDatabaseReference.addChildEventListener(mChildEventListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(discussActivity.this, addDiscussActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onItemClicked(DatabaseReference agreeRef) {
        agreeRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                discussItem item = mutableData.getValue(discussItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                if(!item.getClicked().containsKey(Uid)){
                    item.getClicked().put(Uid, true);
                }
                item.setHits(item.getHits()+1);;
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Hits Transaction : onComplete:" + databaseError);
            }
        });
    }

    //추가된 소스, ToolBar에 main.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout drawerLinear = (LinearLayout) inflate.inflate(R.layout.drawer_header, null);

        TextView drawer_id = (TextView) findViewById(R.id.drawer_id_main);
        drawer_id.setText(Uid);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_discuss);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}
