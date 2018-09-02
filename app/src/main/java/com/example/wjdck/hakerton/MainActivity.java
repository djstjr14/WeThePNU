package com.example.wjdck.hakerton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.wjdck.hakerton.loginActivity.Uid;
import static com.example.wjdck.hakerton.loginActivity.appData;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    ChildEventListener mChildEventListener;

    ListView listView;
    ListViewAdapter adapter;
    Toolbar toolbar_main;
    DrawerLayout drawer;
    NavigationView navigation;
    ImageButton btn_rec_prog;
    ImageButton btn_rec_expired;
    ImageButton btn_lat_prog;
    ImageButton btn_lat_expired;
    Button category1;
    Button category2;
    Button category3;
    Button category4;
    Button category5;
    Button category6;
    Button category7;

    TextView toolbar_text;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.agenda_listview);
        toolbar_main = findViewById(R.id.main_toolbar);
        drawer = findViewById(R.id.drawer_main);
        navigation = findViewById(R.id.navigation_main);
        btn_rec_expired = findViewById(R.id.expir_recommend_btn);
        btn_rec_prog = findViewById(R.id.prog_recommend_btn);
        btn_lat_expired = findViewById(R.id.expir_new_btn);
        btn_lat_prog = findViewById(R.id.prog_new_btn);
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        category3 = findViewById(R.id.category3);
        category4 = findViewById(R.id.category4);
        category5 = findViewById(R.id.category5);
        category6 = findViewById(R.id.category6);
        category7 = findViewById(R.id.category7);
        toolbar_text = findViewById(R.id.toolbar_text);

        //Toolbar 추가
        setSupportActionBar(toolbar_main);

        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.attachToListView(listView);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Agenda");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, detailActivity.class);
                ListViewItem item = adapter.getItem(position);
                intent.putExtra("ITEM", item);
                if(!item.getClicked().containsKey(Uid)){
                    item.getClicked().put(Uid, true);
                    mDatabaseReference.child(item.getKey()).setValue(item);
                }
                adapter.clickedList(view);
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
                        Intent intent1 = new Intent(MainActivity.this, MyListActivity.class);
                        intent1.putExtra("OPTION", 1);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(MainActivity.this, MyListActivity.class);
                        intent2.putExtra("OPTION", 2);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(MainActivity.this, discussActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item4:
                        break;

                    case R.id.navigation_item5:
                        Intent intent5 = new Intent(MainActivity.this, loginActivity.class);
                        Uid = null;
                        startActivity(intent5); SharedPreferences.Editor editor= appData.edit();
                        editor.clear().apply();
                        break;
                }

                return true;
            }
        });

        btn_rec_prog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setSort(1);
                btn_rec_prog.setImageResource(R.drawable.btn_progressing_recommend_clicked);
                btn_lat_prog.setImageResource(R.drawable.btn_progressing_latest_normal);
                btn_rec_expired.setImageResource(R.drawable.btn_expired_recommend_normal);
                btn_lat_expired.setImageResource(R.drawable.btn_expired_latest_normal);
            }
        });
        btn_lat_prog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setSort(2);
                btn_rec_prog.setImageResource(R.drawable.btn_progressing_recommend_normal);
                btn_lat_prog.setImageResource(R.drawable.btn_progressing_latest_clicked);
                btn_rec_expired.setImageResource(R.drawable.btn_expired_recommend_normal);
                btn_lat_expired.setImageResource(R.drawable.btn_expired_latest_normal);
            }
        });
        btn_rec_expired.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.setSort(3);
                btn_rec_prog.setImageResource(R.drawable.btn_progressing_recommend_normal);
                btn_lat_prog.setImageResource(R.drawable.btn_progressing_latest_normal);
                btn_rec_expired.setImageResource(R.drawable.btn_expired_recommend_clicked);
                btn_lat_expired.setImageResource(R.drawable.btn_expired_latest_normal);
            }
        });
        btn_lat_expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_rec_prog.setImageResource(R.drawable.btn_progressing_recommend_normal);
                btn_lat_prog.setImageResource(R.drawable.btn_progressing_latest_normal);
                btn_rec_expired.setImageResource(R.drawable.btn_expired_recommend_normal);
                btn_lat_expired.setImageResource(R.drawable.btn_expired_latest_clicked);
                adapter.setSort(4);
            }
        });
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("전체");
                adapter.setCategory("전체");
            }
        });
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("행정");
                adapter.setCategory("행정");
            }
        });
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("보건/복지");
                adapter.setCategory("보건/복지");
            }
        });
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("교내시설");
                adapter.setCategory("교내시설");
            }
        });
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("인권/평등");
                adapter.setCategory("인권/평등");
            }
        });
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("예술/문화");
                adapter.setCategory("예술/문화");
            }
        });
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_text.setText("기타");
                adapter.setCategory("기타");
            }
        });


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
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
                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
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
                Intent intent = new Intent(MainActivity.this, addAgendaActivity.class);
                startActivity(intent);
            }
        });
    }

    //추가된 소스, ToolBar에 main.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawer.openDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}
