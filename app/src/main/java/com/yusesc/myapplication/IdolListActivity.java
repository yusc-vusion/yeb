package com.yusesc.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class IdolListActivity extends AppCompatActivity {

    //메뉴관련 변수
    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView drawerNickname;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idol_list);

        Intent i = getIntent();
        nickname = i.getStringExtra("nickname");

        //메뉴관련 시작
        drawerLayout =(DrawerLayout) findViewById(R.id.layout_idolList);
        drawerView =(View)findViewById(R.id.layout_drawer);

        drawerNickname = findViewById(R.id.drawer_nickname);
        drawerNickname.setText(nickname+"님 환영합니다.");

        //menu open버튼
        ImageButton btn_menuOpen= (ImageButton) findViewById(R.id.btn_drawerback_open);
        btn_menuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //menu close버튼
        ImageButton btn_menuClose = (ImageButton)findViewById(R.id.btn_drawer_close);
        btn_menuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //회원정보수정버튼
        Button btn_userModi = (Button)findViewById(R.id.btn_drawer_userModify);
        btn_userModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserModi.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        //얼굴분석하기버튼
        Button btn_faceRecog = (Button)findViewById(R.id.btn_drawer_faceRecog);
        btn_faceRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecogActivity.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });


        //나의얼굴분석기록버튼
        Button btn_searchRecord = (Button)findViewById(R.id.btn_drawer_myfaceRecord);
        btn_searchRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchRecordActivity.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        //분석결과평가하기버튼
        Button btn_evaluate = (Button)findViewById(R.id.btn_drawer_faceResultEsti);
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Evaluate.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        //결과아이돌리스트버튼

        //메뉴관련 끝

    }

    DrawerLayout.DrawerListener listener=new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
}
