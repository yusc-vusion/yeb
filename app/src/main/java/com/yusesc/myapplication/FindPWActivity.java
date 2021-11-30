package com.yusesc.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindPWActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;             // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText find_pw_email;  // 회원가입 인풋 필드
    private Button cancel, find_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("whoami");

        find_pw_email = findViewById(R.id.find_pw_email);
        cancel = findViewById(R.id.find_pw_cancel);
        find_pw = findViewById(R.id.find_pw_ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = find_pw_email.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(FindPWActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FindPWActivity.this, "이메일 전송 완료", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(FindPWActivity.this, "비밀번호 찾기 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}