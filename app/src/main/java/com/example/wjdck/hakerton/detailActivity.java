package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

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

        items = new ArrayList<>();
        recyclerView = findViewById(R.id.comment);
        adapter = new CommentViewAdapter(this, items, item.getTitle(), item.getText());
        recyclerView.setAdapter(adapter);

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

}
