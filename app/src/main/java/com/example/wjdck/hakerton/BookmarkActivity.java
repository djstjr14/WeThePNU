//package com.example.wjdck.hakerton;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.melnykov.fab.FloatingActionButton;
//
//
//public class BookmarkActivity extends AppCompatActivity {
//
//    FirebaseDatabase mFirebaseDatabase;
//    DatabaseReference mDatabaseReference;
//    ChildEventListener mChildEventListener;
//
//    ListView listView;
//    ListViewAdapter adapter;
//    Toolbar toolbar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bookmark);
//
//        listView = findViewById(R.id.agenda_listview);
//        toolbar = findViewById(R.id.bookmark_toolbar);
//
//        //Toolbar 추가
//        setSupportActionBar(toolbar);
//        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);
//
//        adapter = new ListViewAdapter();
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                Intent intent = new Intent(BookmarkActivity.this, detailActivity.class);
//                intent.putExtra("ITEM", adapter.getItem(position));
//                startActivity(intent);
//            }
//        });
//
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference("Agenda");
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
//                if(item.getKey().equalsIgnoreCase("")) {
//                    item.setKey(dataSnapshot.getKey());
//                    mDatabaseReference.child(dataSnapshot.getKey()).child("key").setValue(dataSnapshot.getKey());
//                }
//                adapter.addItem(item);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
//                if(item.getKey().equalsIgnoreCase("")){
//                    item.setKey(dataSnapshot.getKey());
//                    mDatabaseReference.child(dataSnapshot.getKey()).child("key").setValue(dataSnapshot.getKey());
//                }
//                adapter.replaceItem(item);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                int position = adapter.findItem(dataSnapshot.getKey());
//                adapter.removeItem(position);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabaseReference.addChildEventListener(mChildEventListener);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, addAgendaActivity.class);
//                //intent.putExtra("");
//                startActivity(intent);
//
//            }
//        });
//    }
//
//    //추가된 소스, ToolBar에 main.xml을 인플레이트함
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //return super.onCreateOptionsMenu(menu);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        drawer.openDrawer(GravityCompat.END);
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
//        if (drawer.isDrawerOpen(GravityCompat.END)) {
//            drawer.closeDrawer(GravityCompat.END);
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
