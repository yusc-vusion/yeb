package com.yusesc.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Evaluate extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    private TextView[] idolname = new TextView[5];
    private TextView[] confi= new TextView[5];

    private ImageView[] img = new ImageView[5];

    private int cnt=0,vCnt=0;
    private HashSet<Integer> hs = new HashSet<Integer>();
    private IdolHash ih = new IdolHash();

    private ImageButton[] userImg = new ImageButton[5];
    private ImageButton redirect;
    private TextView drawerNickname;
    private String nickname;

    private int temp;

    private UserRecord[] record = new UserRecord[5];
    private String[] nicknames = new String[5];
    private String[] dates = new String[5];

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);

        Intent i = getIntent();
        nickname = i.getStringExtra("nickname");

        //메뉴관련

        drawerLayout =(DrawerLayout) findViewById(R.id.layout_evaluate);
        drawerView =(View)findViewById(R.id.layout_drawer);
        drawerNickname = findViewById(R.id.drawer_nickname);
        drawerNickname.setText(nickname+"님 환영합니다.");
        //메뉴관련

        //팝업관련
        userImg[0] = (ImageButton) findViewById(R.id.image_user1);
        userImg[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evaluate.this, EvaluatePopup.class);
                intent.putExtra("confidence",record[0].getConfidence());
                intent.putExtra("Idol", ih.getVal(record[0].getIdolname()));
                intent.putExtra("url",record[0].getProfile());
                startActivityForResult(intent, 0);
            }
        });
        userImg[1] = (ImageButton) findViewById(R.id.image_user2);
        userImg[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evaluate.this, EvaluatePopup.class);
                intent.putExtra("confidence",record[1].getConfidence());
                intent.putExtra("Idol", ih.getVal(record[1].getIdolname()));
                intent.putExtra("url",record[1].getProfile());
                startActivityForResult(intent, 1);
            }
        });
        userImg[2] = (ImageButton) findViewById(R.id.image_user3);
        userImg[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evaluate.this, EvaluatePopup.class);
                intent.putExtra("confidence",record[2].getConfidence());
                intent.putExtra("Idol", ih.getVal(record[2].getIdolname()));
                intent.putExtra("url",record[2].getProfile());
                startActivityForResult(intent, 2);
            }
        });
        userImg[3] = (ImageButton) findViewById(R.id.image_user4);
        userImg[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evaluate.this, EvaluatePopup.class);
                intent.putExtra("confidence",record[3].getConfidence());
                intent.putExtra("Idol", ih.getVal(record[3].getIdolname()));
                intent.putExtra("url",record[3].getProfile());
                startActivityForResult(intent, 3);
            }
        });
        userImg[4] = (ImageButton) findViewById(R.id.image_user5);
        userImg[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Evaluate.this, EvaluatePopup.class);
                intent.putExtra("confidence",record[4].getConfidence());
                intent.putExtra("Idol", ih.getVal(record[4].getIdolname()));
                intent.putExtra("url",record[4].getProfile());
                startActivityForResult(intent, 4);
            }
        });
        redirect = findViewById(R.id.redirect);

        //팝업관련

        ih.setVal();

        idolname[0] = findViewById(R.id.idolname1);
        idolname[1] = findViewById(R.id.idolname2);
        idolname[2] = findViewById(R.id.idolname3);
        idolname[3] = findViewById(R.id.idolname4);
        idolname[4] = findViewById(R.id.idolname5);

        confi[0] = findViewById(R.id.confidence1);
        confi[1] = findViewById(R.id.confidence2);
        confi[2] = findViewById(R.id.confidence3);
        confi[3] = findViewById(R.id.confidence4);
        confi[4] = findViewById(R.id.confidence5);

        img[0] = findViewById(R.id.image_idol1);
        img[1] = findViewById(R.id.image_idol2);
        img[2] = findViewById(R.id.image_idol3);
        img[3] = findViewById(R.id.image_idol4);
        img[4] = findViewById(R.id.image_idol5);

        //Toast.makeText(Evaluate.this, new Integer(ih.getVal("Jijel")).toString(), Toast.LENGTH_SHORT).show();

        ref.child("whoami").child("Record").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    cnt += 1;
                }
                Random rand = new Random();
                while(hs.size()<5){
                    hs.add(rand.nextInt(cnt)+1);
                }
                cnt=0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    cnt+=1;
                    for(DataSnapshot snapshot: postSnapshot.getChildren()){
                        if (hs.contains(cnt)){
                            nicknames[vCnt] = postSnapshot.getKey().toString();
                            dates[vCnt] = snapshot.getKey().toString();
                            //Toast.makeText(Evaluate.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                            record[vCnt++] = snapshot.getValue(UserRecord.class);
                            //ih.getVal(record.getIdolname())
                            break;
                        }
                    }
                }
                vCnt=0;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        });

//        userImg[].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), EvaluatePopup.class);
//                intent.putExtra("UserImg",record.getProfile()); // String
//                intent.putExtra("IdolImg",temp); // Integer
//                intent.putExtra("Confidence",record.getConfidence()); // Float
//                startActivity(intent);
//            }
//        });

        //Toast.makeText(Evaluate.this, record.getIdolname(), Toast.LENGTH_SHORT).show();

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<5;i++){
                    if(record[i]!=null)
                    idolname[i].setText(record[i].getIdolname());
                    confi[i].setText(new Float(record[i].getConfidence()).toString());
                    img[i].setImageResource(ih.getVal(record[i].getIdolname()));
                    Glide.with(Evaluate.this).load(record[i].getProfile()).into(userImg[i]);
                }
            }
        });


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

        //얼굴인식버튼(main)
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

        //결과아이돌리스트버튼
        Button btn_idolList = (Button)findViewById(R.id.btn_drawer_idolList);
        btn_idolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.IdolListActivity.class);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                record[requestCode].setRating((record[requestCode].getRating() * record[requestCode].getCount() + data.getFloatExtra("rating",requestCode))/(record[requestCode].getCount()+1));
                record[requestCode].setCount(record[requestCode].getCount()+1);
                ref.child("whoami").child("Record").child(nicknames[requestCode]).child(dates[requestCode]).setValue(record[requestCode]);
            }
        }
    }

    //메뉴관련
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
