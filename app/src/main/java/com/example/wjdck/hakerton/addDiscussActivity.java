package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class addDiscussActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discuss);

        Button btn_cancle = this.findViewById(R.id.add_discuss_cancle_btn);
        Button btn_regist = this.findViewById(R.id.add_discuss_regist_btn);
        final EditText edit_title = this.findViewById(R.id.add_discuss_title);
        final EditText edit_agenda = this.findViewById(R.id.add_discuss_content);
        Toolbar toolbar = findViewById(R.id.add_discuss_toolbar);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Discuss");

        //Toolbar 추가
        setSupportActionBar(toolbar);

        btn_cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String key = "";
                String title = edit_title.getText().toString();
                String text = edit_agenda.getText().toString();
                long recommend = 0;
                long unrecommend = 0;
                long date = Calendar.getInstance().getTimeInMillis();
                long hits = 0;
                long comments = 0;

                discussItem item = new discussItem(key, title, text, recommend, unrecommend, Long.toString(date), hits, comments);

                ref.push().setValue(item.toMap());
                finish();
            }
        });

    }
}
