package com.yusesc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class EvaluatePopup extends Activity {

    private ImageView idolImg, myImg;
    private TextView tv_confidence;
    private RatingBar ratingBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.evaluate_popup);

        ratingBar = findViewById(R.id.ratingbar);

        Intent intent = getIntent();
        float confidence = intent.getFloatExtra("confidence",0);
        int idol = intent.getIntExtra("Idol",0);
        String url = intent.getStringExtra("url");
        //img[vCnt].setImageResource(intent.getIntExtra("IdolImg"));
        idolImg = findViewById(R.id.idol_popup);
        idolImg.setImageResource(idol);
        myImg = findViewById(R.id.user_popup);
        Glide.with(this).load(url).into(myImg);
        tv_confidence = findViewById(R.id.popup_result);
        tv_confidence.setText(new Float(confidence).toString() + '%');


        //확인버튼
        ImageButton btn_confirm = (ImageButton) findViewById(R.id.evalPopup_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("rating",ratingBar.getRating());
                setResult(Activity.RESULT_OK, intent);
                finish();
                //확인버튼구현하기
            }
        });

        //취소버튼
        ImageButton btn_close = (ImageButton) findViewById(R.id.evalPopup_cancel);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //바깥레이어 클릭시 닫히는 거 막기
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    // 안드로이드 백버튼 막기
    @Override
    public void onBackPressed() {
        return;
    }
}
