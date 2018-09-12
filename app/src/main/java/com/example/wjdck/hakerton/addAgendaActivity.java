package com.example.wjdck.hakerton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static com.example.wjdck.hakerton.loginActivity.cussWords;

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
                AlertDialog.Builder dialog = new AlertDialog.Builder(addAgendaActivity.this);
                String key = "";
                String title = edit_title.getText().toString();
                String text = edit_agenda.getText().toString();
                String word = "";
                boolean cussFlag = false;

                for (String cussWord : cussWords) {
                    if (title.contains(cussWord) || text.contains(cussWord)) {
                        if(title.contains(cussWord)) word = title;
                        else                         word = text;
                        cussFlag = true;
                        break;
                    }
                }

                dialog.setTitle("욕설 / 비속어")
                        .setMessage("입력하신 " + word + "는 욕설/비속어 입니다")
                        .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                if(cussFlag)
                    dialog.show();
                else{
                    String category = category_spinner.getSelectedItem().toString();
                    long recommend = 0;
                    long date = Calendar.getInstance().getTimeInMillis();
                    int answerNum = 0;
                    String answerDate = "";

                    ListViewItem agenda = new ListViewItem(key, title, text, category, recommend, Long.toString(date), answerNum, answerDate);

                    ref.push().setValue(agenda.toMap());
                }


            }
        });

    }
}
