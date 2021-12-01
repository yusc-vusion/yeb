package com.yusesc.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;             // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText join_email, join_password;  // 회원가입 인풋 필드
    private ImageButton signUpBtn, loginBtn;
    private Button findpwBtn;
    private EditText login_id, login_password;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("whoami");

        login_id = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        signUpBtn = findViewById(R.id.SignUpBtn);
        loginBtn = findViewById(R.id.SignInBtn);
        findpwBtn = findViewById(R.id.FindPwBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        findpwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPWActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String email = login_id.getText().toString();
                String password = login_password.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabaseRef.child("UserAccount").child(User.getUid()).child("nickname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        CurrentUser cUser = (CurrentUser) getApplication();
                                        //Toast.makeText(LoginActivity.this, new Ineteger(cUser.getUser().getNickname().toString().length()).to, Toast.LENGTH_SHORT).show();
                                        UserAccount currentUser = new UserAccount(User.getUid(), email, password, task.getResult().getValue().toString());
                                        cUser.setUser(currentUser);
                                        nickname = task.getResult().getValue().toString();
                                        if(cUser.getUser().getNickname().toString().length()<1){
                                            cUser.getUser().setNickname(cUser.getUser().getEmailID());
                                            mDatabaseRef.child("UserAccount").child(User.getUid()).setValue(new UserAccount(User.getUid(), email, password, email));
                                            nickname = email;
                                        }
                                        Intent intent = new Intent(LoginActivity.this, RecogActivity.class);
                                        intent.putExtra("nickname", nickname);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });


                            // 결과 전송시 intent로 변수 전달
                        } else{
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}