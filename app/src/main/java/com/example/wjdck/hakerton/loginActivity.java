package com.example.wjdck.hakerton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    String url = "https://mypnu.net/index.php?act=procMemberLogin";

    EditText ID,PW;
    Button loginButton;
    String userId,userPw;
    TextView findIdAndPw;

    public static String Uid = "djstjr14";
    private final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ID = (EditText) findViewById(R.id.etEmail);
        PW = (EditText) findViewById(R.id.etPassword);
        loginButton = (Button) findViewById(R.id.btn_login);
        findIdAndPw = (TextView)findViewById(R.id.findId);


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
                        login();
                    }
                }).start();
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
    void login(){
        try{
            Connection.Response loginTry = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .header("Origin","http://mypnu.net")
                    .header("Referer","http://mypnu.net/")
                    .header("Upgrade-Insecure-Requests","1")
                    .method(Connection.Method.GET)
                    .execute();

            String PHPSessID = loginTry.cookie("PHPSESSID");
            Map<String, String> loginCookie = loginTry.cookies();


            Document loginDoc = loginTry.parse();


            String error_return_url = "/home2";
            String mid = "home2";
            String vid = "";
            String ruleset = "@login";
            String act = "procMemberLogin";
            String success_return_url = "https://mypnu.net/home2";
            String xe_validator_id = "widgets/login_info/skins/stylish/login_form/1";


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
                    .cookies(loginCookie)
                    .data("error_return_url",error_return_url)
                    .data("mid",mid)
                    .data("vid",vid)
                    .data("ruleset",ruleset)
                    .data("act",act)
                    .data("success_return_url",success_return_url)
                    .data("xe_validator_id",xe_validator_id)
                    .data("user_id",userId)
                    .data("password",userPw)
                    .method(Connection.Method.POST)
                    .execute();



            Map<String,String> loginHeader = loginForm.headers();

            boolean flag = loginForm.hasHeaderWithValue("Set-Cookie","xe_logged=true");

            for ( Map.Entry<String, String> entry : loginHeader.entrySet() ) {
                Log.d("!!!!!!!!!!!!!",entry.getKey());
                Log.d("!!!!!!!!!!", entry.getValue());
            }

            int ce;

            if(flag)
                ce=3;
            else
                ce= 2;
            String cec = ""+ce;

            Log.d("!!!!!!!!!",cec);
            if(flag){
                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(loginActivity.this, loginActivity.class);
                startActivity(intent);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
