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
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.melnykov.fab.FloatingActionButton;

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

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.agenda_listview);
        toolbar_main = findViewById(R.id.main_toolbar);
        drawer = findViewById(R.id.drawer_main);
        navigation = findViewById(R.id.navigation_main);

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
                    //item.clicked.put(Uid, true);
                }
                //ListViewItem item = new ListViewItem(); // clicked call
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

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListViewItem item = dataSnapshot.getValue(ListViewItem.class);
                if(item.getKey().equalsIgnoreCase("")) {
                    item.setKey(dataSnapshot.getKey());
                    mDatabaseReference.child(dataSnapshot.getKey()).child("key").setValue(dataSnapshot.getKey());
                }
                adapter.addItem(item);
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
                //intent.putExtra("");
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
