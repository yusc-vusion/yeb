package com.yusesc.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    private ImageView idolImg;
    private TextView resultText;

    private String nickname;
    private String result = "";
    private String artname = "";

    private IdolHash ih = new IdolHash();
    private String Idol, url;
    private Bitmap image;

    private ImageButton retry, moreInfo, share;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_recog_result);

        Intent i = getIntent();
        nickname = i.getStringExtra("nickname");

        drawerLayout =(DrawerLayout) findViewById(R.id.layout_faceRecogResult);
        drawerView =(View)findViewById(R.id.layout_drawer);

        idolImg = findViewById(R.id.faceRecogResult);
        resultText = findViewById(R.id.ResultExplain);
        TextView drawer_nickname = findViewById(R.id.drawer_nickname);
        drawer_nickname.setText(nickname+"님 환영합니다.");

        retry = findViewById(R.id.SearchAgain);
        moreInfo = findViewById(R.id.CheckIdolInfo);
        share = findViewById(R.id.share_to_sns);

        Intent preintent = getIntent();
        float confidence = preintent.getFloatExtra("Confidence",0);
        Idol = preintent.getStringExtra("Idol");
        url = preintent.getStringExtra("url");

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, RecogActivity.class);
                startActivity(intent);
                finish();
            }
        });

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ResultActivity.this, DisplayData.class);
                intent.putExtra("result",Idol);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-hh-mm");
                String getTime = sdf.format(date);
                CurrentUser cUser = (CurrentUser) getApplication();
                ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).child(getTime).child("idolname").setValue(Idol);
                ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).child(getTime).child("profile").setValue(url);
                ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).child(getTime).child("count").setValue(0);
                ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).child(getTime).child("confidence").setValue(confidence*100);
                ref.child("whoami").child("Record").child(cUser.getUser().getNickname()).child(getTime).child("rating").setValue(0);

                Toast.makeText(ResultActivity.this, "공유하기 완료", Toast.LENGTH_SHORT).show();
            }
        });

        ih.setVal();


        ref.child("whoami").child("IdolData").child(Idol.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                IdolData idol = dataSnapshot.getValue(IdolData.class);

                result = "당신과 가장 닮은 아이돌은\n"+ idol.getArtname() + "입니다!\n" + new Float(confidence*100).toString()+"% 유사합니다.";
                resultText.setText(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


        idolImg.setImageResource(ih.getVal(Idol));

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
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        //회원정보수정버튼
        Button btn_userModi = (Button)findViewById(R.id.btn_drawer_userModify);
        btn_userModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.UserModi.class);
                intent.putExtra("nickname", nickname);
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
