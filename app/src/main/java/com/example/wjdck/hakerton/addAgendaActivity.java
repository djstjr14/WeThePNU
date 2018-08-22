package com.example.wjdck.hakerton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class addAgendaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);

        Button btn_cancle = this.findViewById(R.id.cancle_btn);
        Button btn_regist = this.findViewById(R.id.regist_btn);
        final Spinner category_spinner = this.findViewById(R.id.spinner_category);
        final EditText edit_title = this.findViewById(R.id.title_edit);
        final EditText edit_agenda = this.findViewById(R.id.agenda_edit);
        Toolbar toolbar_agenda = findViewById(R.id.agenda_toolbar);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Agenda");

        //Toolbar 추가
        setSupportActionBar(toolbar_agenda);

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
                String category = category_spinner.getSelectedItem().toString();
                long recommend = 0;
                long date = Calendar.getInstance().getTimeInMillis();

                ListViewItem agenda = new ListViewItem(key, title, text, category, recommend, Long.toString(date));

                ref.push().setValue(agenda.toMap());
                finish();
            }
        });

    }
}
