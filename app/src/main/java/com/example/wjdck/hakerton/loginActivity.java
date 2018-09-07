package com.example.wjdck.hakerton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;


public class loginActivity extends AppCompatActivity {

    String url = "https://mypnu.net/index.php?act=procMemberLogin";

    EditText ID,PW;
    Button loginButton;
    String userId,userPw;
    TextView findIdAndPw;
    CheckBox loginCheckBox;

    public static SharedPreferences appData;
    private long backKeyPressedTime = 0;
    public static String Uid = "djstjr14";
    public static Toast toast;
    private final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";
    boolean loginFlag = true;
    boolean SaveLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appData = getSharedPreferences("appData",MODE_PRIVATE);
        SaveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        userId = appData.getString("ID", "");
        userPw = appData.getString("PW", "");

        ID = (EditText) findViewById(R.id.etEmail);
        PW = (EditText) findViewById(R.id.etPassword);
        loginButton = (Button) findViewById(R.id.btn_login);
        findIdAndPw = (TextView)findViewById(R.id.findId);
        loginCheckBox = (CheckBox)findViewById(R.id.checkBox);

        if (SaveLoginData) {
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 버튼 누름
                userId = ID.getText().toString();
                userPw = PW.getText().toString();
                Uid = userId;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loginFlag = login();
                    }
                }).start();
               if(!loginFlag) {
                   wrongIdOrPw();
               }
            }
        });


        findIdAndPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(loginActivity.this, findIdAndPwActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean login(){
        try{
            Connection.Response loginForm = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .header("Origin","http://mypnu.net")
                    .header("Referer","http://mypnu.net/")
                    .header("Upgrade-Insecure-Requests","1")
                    .data("user_id",userId)
                    .data("password",userPw)
                    .method(Connection.Method.POST)
                    .execute();

            boolean flag = loginForm.hasHeaderWithValue("Set-Cookie","xe_logged=true");

            if(flag){

               SharedPreferences.Editor editor = appData.edit();

               editor.putBoolean("SAVE_LOGIN_DATA",loginCheckBox.isChecked());
               editor.putString("ID",ID.getText().toString());
               editor.putString("PW",PW.getText().toString());

               editor.apply();

               Intent intent = new Intent(loginActivity.this, MainActivity.class);
               startActivity(intent);
            }
            return flag;

        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(this);
            System.runFinalizersOnExit(true);
            System.exit(0);
        }
    }

    public void showGuide() {
        toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void wrongIdOrPw() {
        Intent intent = new Intent(loginActivity.this, agreePopupActivity.class);
        intent.putExtra("data", "잘못된 ID 혹은 패스워드입니다.");
        startActivity(intent);
    }
}


