package com.yusesc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Random;

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
                //????????? ??? ???????????? get????????? ???????????? Together_group_list.class?????? ????????????

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });
    }
}