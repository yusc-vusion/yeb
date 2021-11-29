package org.techtown.menupractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

// picture upload import
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
//import com.bumptech.glide.Glide;
// carmera import
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    //메뉴관련 변수
    private DrawerLayout drawerLayout;
    private View drawerView;

    //얼굴인식관련 변수
    ImageView imageView;
    final private static String TAG = "tag";
    ImageButton btn_photo;
    ImageButton btn_result;
    ImageView iv_photo;
    final static int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_recog);

        //메뉴관련 시작
        drawerLayout =(DrawerLayout) findViewById(R.id.layout_faceRecog);
        drawerView =(View)findViewById(R.id.layout_drawer);

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

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //회원정보수정버튼
        Button btn_userModi = (Button)findViewById(R.id.btn_drawer_userModify);
        btn_userModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserModi.class);
                startActivity(intent);
            }
        });
        //메뉴관련 끝

        //얼굴인식관련 시작
        iv_photo = findViewById(R.id.img_faceRecog_picture);
        btn_photo = findViewById(R.id.btn_faceRecog_camera);
        imageView = findViewById(R.id.img_faceRecog_picture);
        btn_result = (ImageButton) findViewById(R.id.btn_faceRecog_confirm);

        btn_result.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
            }
        });

        //버튼 클릭 이벤트 생성
        ImageButton imageButton = findViewById(R.id.btn_faceRecog_album);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //코드 작성
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivity(intent);
                startActivityForResult(intent, 0);
                //startActivityForResult(intent, 0);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_faceRecog_camera:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        //얼굴인식관련 끝


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

    //@Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //이미지업로드
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                // Glide 사용해서 이미지 출력
//                // Load : 이미지 경로, override : 이미지 가로, 세로 크기 조정, into : 이미지를 출력할 ImageView 객체
//                Glide.with(getApplicationContext()).load(data.getData()).into(imageView);
//            }
//        }
//
//        //사진찍기
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case TAKE_PICTURE:
//                if (resultCode == RESULT_OK && data.hasExtra("data")) {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    if (bitmap != null) {
//                        iv_photo.setImageBitmap(bitmap);
//                    }
//                }
//                break;
//        }
//    }
}