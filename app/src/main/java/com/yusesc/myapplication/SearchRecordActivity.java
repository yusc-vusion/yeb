package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchRecordActivity extends AppCompatActivity {

    //메뉴관련 변수
    private DrawerLayout drawerLayout;
    private ImageButton redirect;
    private View drawerView;
    private int cnt = 0;
    private UserRecord[] records = new UserRecord[5];
    private TextView[] tv_idol = new TextView[5];
    private TextView[] tv_rating = new TextView[5];
    private ImageView[] iv_idol = new ImageView[5];
    private ImageView[] iv_user = new ImageView[5];

    private TextView drawerNickname;
    private String nickname;

    private IdolHash ih = new IdolHash();

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_record);

        Intent i = getIntent();
        nickname = i.getStringExtra("nickname");

        CurrentUser cUser = (CurrentUser)getApplication();

        ih.setVal();

        redirect = findViewById(R.id.redirect_log);
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<5; i++)
                if(records[i]!=null){
                    tv_idol[i].setText(records[i].getIdolname());
                    tv_rating[i].setText(new Float(records[i].getRating()).toString());
                    Glide.with(SearchRecordActivity.this).load(records[i].getProfile()).into(iv_user[i]);
                    iv_idol[i].setImageResource(ih.getVal(records[i].getIdolname()));
                }
            }
        });

        tv_idol[0] = findViewById(R.id.idolname1);
        tv_idol[1] = findViewById(R.id.idolname2);
        tv_idol[2] = findViewById(R.id.idolname3);
        tv_idol[3] = findViewById(R.id.idolname4);
        tv_idol[4] = findViewById(R.id.idolname5);

        tv_rating[0] = findViewById(R.id.rating1);
        tv_rating[1] = findViewById(R.id.rating2);
        tv_rating[2] = findViewById(R.id.rating3);
        tv_rating[3] = findViewById(R.id.rating4);
        tv_rating[4] = findViewById(R.id.rating5);

        iv_idol[0] = findViewById(R.id.image_idol1);
        iv_idol[1] = findViewById(R.id.image_idol2);
        iv_idol[2] = findViewById(R.id.image_idol3);
        iv_idol[3] = findViewById(R.id.image_idol4);
        iv_idol[4] = findViewById(R.id.image_idol5);

        iv_user[0] = findViewById(R.id.image_user1);
        iv_user[1] = findViewById(R.id.image_user2);
        iv_user[2] = findViewById(R.id.image_user3);
        iv_user[3] = findViewById(R.id.image_user4);
        iv_user[4] = findViewById(R.id.image_user5);

        ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot PostSnapshot : snapshot.getChildren()){

                    records[cnt] = PostSnapshot.getValue(UserRecord.class);
                    Toast.makeText(SearchRecordActivity.this, records[cnt++].getIdolname(), Toast.LENGTH_SHORT).show();
                    if (cnt>4){
                        cnt=0;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //메뉴관련 시작
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_searchRecord);
        drawerView = (View) findViewById(R.id.layout_drawer);

        drawerNickname = findViewById(R.id.drawer_nickname);
        drawerNickname.setText(nickname+"님 환영합니다.");

        //menu open버튼
        ImageButton btn_menuOpen = (ImageButton) findViewById(R.id.btn_drawerback_open);
        btn_menuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //menu close버튼
        ImageButton btn_menuClose = (ImageButton) findViewById(R.id.btn_drawer_close);
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
        Button btn_userModi = (Button) findViewById(R.id.btn_drawer_userModify);
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

        //분석결과평가하기버튼
        Button btn_evaluate = (Button) findViewById(R.id.btn_drawer_faceResultEsti);
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Evaluate.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        //결과아이돌리스트버튼
        Button btn_idolList = (Button) findViewById(R.id.btn_drawer_idolList);
        btn_idolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.IdolListActivity.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });
        //메뉴관련 끝
    }

    //drawer관련
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
