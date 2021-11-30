package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class UserModi extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_modi);

        drawerLayout =(DrawerLayout) findViewById(R.id.layout_userModi);
        drawerView =(View)findViewById(R.id.layout_drawer);

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

        //얼굴인식버튼(main)
        Button btn_faceRecog = (Button)findViewById(R.id.btn_drawer_faceRecog);
        btn_faceRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetectorActivity_temp.class);
                startActivity(intent);
            }
        });

        //나의얼굴분석기록버튼

        //분석결과평가하기버튼
        Button btn_evaluate = (Button)findViewById(R.id.btn_drawer_faceResultEsti);
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.Evaluate.class);
                startActivity(intent);
            }
        });

        //결과아이돌리스트버튼

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


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
