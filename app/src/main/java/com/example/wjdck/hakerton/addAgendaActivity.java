package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class addAgendaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);

        Button btn_cancle = this.findViewById(R.id.cancle_btn);
        Button btn_regist = this.findViewById(R.id.regist_btn);
        final EditText edit_title = this.findViewById(R.id.title_edit);
        final EditText edit_agenda = this.findViewById(R.id.agenda_edit);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("agenda");

        btn_cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String title = edit_title.getText().toString();
                String text = edit_agenda.getText().toString();
                long recommend = 0;
                long date = Calendar.getInstance().getTimeInMillis();


                Agenda agenda = null;
                agenda = new Agenda(title, text, recommend, date);

                ref.push().setValue(agenda);
                onBackPressed();
            }
        });

    }

}
