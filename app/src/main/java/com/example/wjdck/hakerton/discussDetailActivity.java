package com.example.wjdck.hakerton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import static com.example.wjdck.hakerton.loginActivity.cussWords;
import static com.example.wjdck.hakerton.loginActivity.toast;

public class discussDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigation;
    DrawerLayout drawer;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    DatabaseReference ref;
    ChildEventListener mChildEventListener;

    RecyclerView recyclerView;
    CommentViewAdapter adapter;
    ArrayList<CommentItem> items;

    EditText Edit_comment;
    Button register_btn;
    TextView title;

    String thisKey ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss_detail);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.discuss_detail_toolbar);
        navigation = findViewById(R.id.navigation_discuss_detail);
        drawer = findViewById(R.id.drawer_discuss_detail);
        Edit_comment = findViewById(R.id.detail_commend_edit);
        register_btn = findViewById(R.id.detail_commend_btn);

        final discussItem item = (discussItem) intent.getSerializableExtra("ITEM");
        thisKey = item.getKey();

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.comment);
        adapter = new CommentViewAdapter(this, items, null, item, 2);
        recyclerView.setAdapter(adapter);

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
                        Intent intent1 = new Intent(discussDetailActivity.this, MyListActivity.class);
                        intent1.putExtra("OPTION", 1);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_item2:
                        Intent intent2 = new Intent(discussDetailActivity.this, MyListActivity.class);
                        intent2.putExtra("OPTION", 2);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_item3:
                        Intent intent3 = new Intent(discussDetailActivity.this, discussActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.navigation_item4:
                        Intent intent4 = new Intent(discussDetailActivity.this, AnswerActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.navigation_item5:
                        Intent intent5 = new Intent(discussDetailActivity.this, loginActivity.class);
                        Uid = null;
                        startActivity(intent5); SharedPreferences.Editor editor= appData.edit();
                        editor.clear().apply();
                        startActivity(intent5);
                        break;
                }

                return true;
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //키보드 내리기
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String text = Edit_comment.getText().toString();
                long date = Calendar.getInstance().getTimeInMillis();

                AlertDialog.Builder dialog = new AlertDialog.Builder(discussDetailActivity.this);
                boolean cussFlag = false;
                String word = "";

                for (String cussWord : cussWords) {
                    if (text.contains(cussWord)) {
                        cussFlag = true;
                        word = text;
                        break;
                    }
                }

                dialog.setTitle("욕설 / 비속어")
                        .setMessage("입력하신 " + word + "는 욕설/비속어 입니다")
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                if(cussFlag)
                    dialog.show();
                else{
                    CommentItem comment = new CommentItem(thisKey, Uid, text, date);
                    onRegisterClicked(ref.child(thisKey), comment);
                    Edit_comment.setText("");
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                }

            }

        });

        initFirebase(thisKey);
    }

    private void initFirebase(String key) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference("Discuss");
        mDatabaseReference = mFirebaseDatabase.getReference("Comment").child("dicuss").child(key);
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

    private void onRegisterClicked(DatabaseReference registerRef, final CommentItem comment) {
        registerRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                discussItem item = mutableData.getValue(discussItem.class);
                if(item == null) {
                    return Transaction.success(mutableData);
                }
                mDatabaseReference.push().setValue(comment);
                item.setComments(item.getComments()+1);
                mutableData.setValue(item);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                Log.d("discussDetailActivity", "register Transaction : onComplete:" + databaseError);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_discuss_detail);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

}
