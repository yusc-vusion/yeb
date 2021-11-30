package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;             // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    private EditText join_email, join_password;  // 회원가입 인풋 필드
    private Button join_button, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("whoami");

        join_email = findViewById((R.id.join_email));
        join_password = findViewById((R.id.join_password));

        delete = findViewById(R.id.delete);
        //delete.setOnClickListener(new View.OnClickListener(){})

        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserEmail = join_email.getText().toString();
                final String UserPW = join_password.getText().toString();

                mAuth.createUserWithEmailAndPassword(UserEmail, UserPW).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailID(firebaseUser.getEmail());
                            account.setPassword(UserPW);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SignUpActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            finish();
                        } else{
                            Toast.makeText(SignUpActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}