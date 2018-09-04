package com.example.wjdck.hakerton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reportPopupActivity extends Activity{
    EditText et;
    FirebaseDatabase DB;
    DatabaseReference ref;

    String key;
    String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.report_popup_activity);

        Intent intent = getIntent();
        key = intent.getStringExtra("data");
        et = findViewById(R.id.report_text);

        DB = FirebaseDatabase.getInstance();
        ref = DB.getReference("Report");
    }

    //확인 버튼 클릭
    public void Close(View v){
        //액티비티(팝업) 닫기
        finish();
    }
    public void Check(View v){
        content = et.getText().toString();
        reportItem item = new reportItem(key, content);
        ref.push().setValue(item);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}