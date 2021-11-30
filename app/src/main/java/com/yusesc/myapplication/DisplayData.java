package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayData extends AppCompatActivity {

    private TextView tv_realname, tv_artname, tv_bloodtype, tv_nation, tv_age, tv_group, tv_height;
    private ImageButton okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        tv_realname = findViewById(R.id.info_realname);
        tv_artname = findViewById(R.id.info_artname);
        tv_bloodtype = findViewById(R.id.info_bloodtype);
        tv_nation = findViewById(R.id.info_nation);
        tv_age = findViewById(R.id.info_age);
        tv_group = findViewById(R.id.info_group);
        tv_height = findViewById(R.id.info_height);
        okay = findViewById(R.id.btn_idolInfoPopup_confirm);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("whoami").child("IdolData").child(result).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                IdolData idol = dataSnapshot.getValue(IdolData.class);

                tv_realname.setText(idol.getRealname());
                tv_artname.setText(idol.getArtname());
                tv_bloodtype.setText(idol.getBloodtype());
                tv_nation.setText(idol.getNation());
                tv_age.setText(new Integer(idol.getAge()).toString());
                tv_group.setText(idol.getGroup());
                tv_height.setText(new Float(idol.getHeight()).toString());
                //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

//        ref.child("IdolData").child(result).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                for (DataSnapshot messageData : task.getResult().getChildren()) {
//                    Toast.makeText(DisplayData.this, "1" + messageData.getValue().toString(), Toast.LENGTH_SHORT).show();
//                    // child 내에 있는 데이터만큼 반복합니다.
//
//                }
//            }
//        });
    }
}