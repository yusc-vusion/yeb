package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModi extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView tv_email;
    private EditText et_password, et_pwchk, et_nickname;
    private ImageButton userModiBtn, deleteBtn;
    private String password;
    private String pwchk;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_modi);

        drawerLayout =(DrawerLayout) findViewById(R.id.layout_userModi);
        drawerView =(View)findViewById(R.id.layout_drawer);

        tv_email = findViewById(R.id.email_text);
        et_password = findViewById(R.id.text_userModi_pw);
        et_pwchk = findViewById(R.id.text_userModi_pw_re);
        et_nickname = findViewById(R.id.text_userModi_nickname);

        userModiBtn = findViewById(R.id.btn_userModi_modi);
        deleteBtn = findViewById(R.id.btn_userModi_leave);

        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        tv_email.setText(User.getEmail());

        userModiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_password.getText().toString();
                pwchk = et_pwchk.getText().toString();
                nickname = et_nickname.getText().toString();
                if (password.equals(pwchk)){
                    CurrentUser cUser = (CurrentUser) getApplication();
                    if(password.equals(cUser.getUser().getPassword().toString())){
                        cUser.getUser().setNickname(nickname);
                        Toast.makeText(UserModi.this, "닉네임 변경 완료", Toast.LENGTH_SHORT).show();
                        ref.child("whoami").child("UserAccount").child(cUser.getUser().getIdToken()).setValue(cUser.getUser());
                        finish();
                    }
                    else{
                        Toast.makeText(UserModi.this, "비밀번호 오류", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserModi.this, "비밀번호 확인 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_password.getText().toString();
                pwchk = et_pwchk.getText().toString();
                nickname = et_nickname.getText().toString();
                if (password.equals(pwchk)){
                    CurrentUser cUser = (CurrentUser) getApplication();
                    if(password.equals(cUser.getUser().getPassword().toString())){
                        Toast.makeText(UserModi.this, "탈퇴 완료", Toast.LENGTH_SHORT).show();
                        cUser.setUser(new UserAccount());
                        User.delete();
                        Intent intents = new Intent(UserModi.this, MainActivity.class);
                        intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intents);
                        finish();
                    }
                    else{
                        Toast.makeText(UserModi.this, "비밀번호 오류", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserModi.this, "비밀번호 확인 오류", Toast.LENGTH_SHORT).show();
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

        //얼굴분석하기버튼
        Button btn_faceRecog = (Button)findViewById(R.id.btn_drawer_faceRecog);
        btn_faceRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecogActivity.class);
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
        Button btn_idolList = (Button)findViewById(R.id.btn_drawer_idolList);
        btn_idolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.IdolListActivity.class);
                startActivity(intent);
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
