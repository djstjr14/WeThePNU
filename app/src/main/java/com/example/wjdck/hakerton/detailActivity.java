package com.example.wjdck.hakerton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.wjdck.hakerton.loginActivity.Uid;
import static com.example.wjdck.hakerton.loginActivity.appData;


public class detailActivity extends AppCompatActivity {

    TextView Title;
    ImageButton btn_agree;
    ToggleButton btn_push;
    ToggleButton btn_bookmark;
    EditText edit_agree;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigation;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference ref;
    DatabaseReference userRef;
    ChildEventListener mChildEventListener;

    RecyclerView recyclerView;
    CommentViewAdapter adapter;
    ArrayList<CommentItem> items;

    String thisKey = "";
    Boolean toastFlag = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final ListViewItem item = (ListViewItem) intent.getSerializableExtra("ITEM");
        final PostItem post = new PostItem(item.getKey(), item.getTitle(), item.getText(), item.getCategory(), item.getRecommend(), item.getDate());
        thisKey = item.getKey();

        btn_agree = findViewById(R.id.agree_btn);
        btn_push = findViewById(R.id.push_btn);
        btn_bookmark = findViewById(R.id.bookmark_btn);
        edit_agree = findViewById(R.id.agree_edit);
        Title = findViewById(R.id.detail_title);
        toolbar = findViewById(R.id.detail_toolbar);
        drawer= findViewById(R.id.drawer);
        navigation= findViewById(R.id.navigation);

        //즐겨찾기 버튼 초기셋팅
        if(item.getBookmark().containsKey(Uid)){
            btn_bookmark.setChecked(true);
            post.setBookmark(true);
        }
        //푸쉬알람 버튼 초기셋팅
        if(item.getPushalarm().containsKey(Uid)){
            btn_push.setChecked(true);
            post.setPush(true);
        }

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.comment);
        adapter = new CommentViewAdapter(this, items, item.getTitle(), item.getText());
        recyclerView.setAdapter(adapter);
        Title.setText("참여인원 : ["+Long.toString(item.getRecommend())+"명]");

        //Toolbar 추가

        setSupportActionBar(toolbar);
        //Toolbar의 왼쪽에 뒤로가기 버튼을 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_18dp);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_item1:
                        Intent intent1 = new Intent(detailActivity.this, MyListActivity.class);
                        intent1.putExtra("OPTION", 1);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(detailActivity.this, MyListActivity.class);
                        intent2.putExtra("OPTION", 2);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(detailActivity.this, discussActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item4:
                        break;

                    case R.id.navigation_item5:
                        Intent intent5 = new Intent(detailActivity.this, loginActivity.class);
                        Uid = null;
                        startActivity(intent5); SharedPreferences.Editor editor= appData.edit();
                        editor.clear().apply();
                        startActivity(intent5);
                        break;
                }

                return true;
            }
        });

        initFirebase(item.getKey());

        btn_agree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //키보드 내리기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String text = edit_agree.getText().toString();
                long date = Calendar.getInstance().getTimeInMillis();

                CommentItem comment = new CommentItem(Uid, text, date);
                onAgreeClicked(ref.child(thisKey), comment);
                if(toastFlag){
                    toast = Toast.makeText(getApplicationContext(), "동의는 한 번만 할 수 있습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    toastFlag = false;
                }else{
                    Title.setText("참여인원 : ["+Long.toString(item.getRecommend()+1)+"명]");
                }

                edit_agree.setText("");
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }

        });

        btn_bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ref != null) {
                    if(isChecked) {
                        btn_bookmark.setChecked(true);
                    } else {
                        btn_bookmark.setChecked(false);
                    }
                    onBookmarkClicked(ref.child(thisKey));
                    onBookmarkSave(userRef, post);
                }
            }
        });
        btn_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ref != null) {
                    if(isChecked) {
                        btn_push.setChecked(true);
                     } else {
                        btn_push.setChecked(false);
                     }
                    onPushClicked(ref.child(thisKey));
                    onPushSave(userRef, post);
                }
            }
        });
    }

    private void initFirebase(String key) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference("Agenda");
        userRef = mFirebaseDatabase.getReference("User").child(Uid);
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
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout drawerLinear = (LinearLayout) inflate.inflate(R.layout.drawer_header, null);

        TextView drawer_id = (TextView) findViewById(R.id.drawer_id_main);
        drawer_id.setText(Uid);
        return super.onCreateOptionsMenu(menu);
    }

    private void onAgreeClicked(DatabaseReference agreeRef, final CommentItem comment) {
        agreeRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ListViewItem item = mutableData.getValue(ListViewItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                if (item.getAgree().containsKey(Uid)) {
                    toastFlag = true;
                    return Transaction.success(mutableData);
                } else {
                    mDatabaseReference.push().setValue(comment);

                    item.setRecommend(item.getRecommend()+1);
                    item.getAgree().put(Uid, true);
                }
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Agree Transaction : onComplete:" + databaseError);
            }
        });
    }

    public static void onBookmarkClicked(DatabaseReference agendaRef) {
        agendaRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ListViewItem item = mutableData.getValue(ListViewItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                if (item.getBookmark().containsKey(Uid)) {
                    item.getBookmark().remove(Uid);
                } else {
                    item.getBookmark().put(Uid, true);
                }
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Agenda Transaction : onComplete:" + databaseError);
            }
        });
    }

    public static void onBookmarkSave(DatabaseReference userRef, final PostItem post) {
        userRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                UserItem ui = mutableData.getValue(UserItem.class);
                if(ui == null) {
                    ui = new UserItem();
                    mutableData.setValue(ui.toMap());
                }
                if(ui.getBookmark().containsKey(post.getKey())){
                    ui.getBookmark().remove(post.getKey());
                    if(ui.getPushalarm().containsKey(post.getKey())){
                        post.setBookmark(false);
                        ui.getPushalarm().put(post.getKey(), post);
                    }
                }else{
                    post.setBookmark(true);
                    ui.getBookmark().put(post.getKey(), post);
                    if(ui.getPushalarm().containsKey(post.getKey())){
                        ui.getPushalarm().put(post.getKey(), post);
                    }
                }
                mutableData.setValue(ui);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Agenda Transaction : onComplete:" + databaseError);
            }
        });
    }

    public static void onPushClicked(DatabaseReference agendaRef) {
        agendaRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ListViewItem item = mutableData.getValue(ListViewItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                if (item.getPushalarm().containsKey(Uid)) {
                    item.getPushalarm().remove(Uid);
                } else {
                    item.getPushalarm().put(Uid, true);
                }
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Agenda Transaction : onComplete:" + databaseError);
            }
        });
    }

    public static void onPushSave(DatabaseReference userRef, final PostItem post) {
        userRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                UserItem ui = mutableData.getValue(UserItem.class);
                if(ui == null) {
                    ui = new UserItem();
                    mutableData.setValue(ui.toMap());
                }
                if(ui.getPushalarm().containsKey(post.getKey())){
                    if(ui.getBookmark().containsKey(post.getKey())){
                        post.setPush(false);
                        ui.getBookmark().put(post.getKey(), post);
                    }
                    ui.getPushalarm().remove(post.getKey());
                }else{
                    post.setPush(true);
                    ui.getPushalarm().put(post.getKey(), post);
                    if(ui.getBookmark().containsKey(post.getKey())){
                        ui.getBookmark().put(post.getKey(), post);
                    }
                }
                mutableData.setValue(ui);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("DetailActivity", "Agenda Transaction : onComplete:" + databaseError);
            }
        });
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
