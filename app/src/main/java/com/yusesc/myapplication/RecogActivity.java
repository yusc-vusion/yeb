package com.yusesc.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
// picture upload import

// carmera import
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
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
import com.yusesc.myapplication.customview.OverlayView;
import com.yusesc.myapplication.env.ImageUtils;
import com.yusesc.myapplication.env.Logger;
import com.yusesc.myapplication.env.Utils;
import com.yusesc.myapplication.tflite.Classifier;
import com.yusesc.myapplication.tflite.YoloV4Classifier;
import com.yusesc.myapplication.tracking.MultiBoxTracker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RecogActivity extends AppCompatActivity {

    //???????????? ??????
    private DrawerLayout drawerLayout;
    private View drawerView;


    private String nickname;
    private String url = "";

    //private TextView det;

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recog);

        Intent i = getIntent();
        nickname = i.getStringExtra("nickname");

        //???????????? ??????
        drawerLayout =(DrawerLayout) findViewById(R.id.layout_faceRecog);
        drawerView =(View)findViewById(R.id.layout_drawer);

        CurrentUser cUser = (CurrentUser) getApplication();
        TextView drawerNickname = findViewById(R.id.drawer_nickname);
        drawerNickname.setText(nickname+"??? ???????????????.");
        //menu open??????
        ImageButton btn_menuOpen= (ImageButton) findViewById(R.id.btn_drawerback_open);
        btn_menuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //menu close??????
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

        //????????????????????????
        Button btn_userModi = (Button)findViewById(R.id.btn_drawer_userModify);
        btn_userModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserModi.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        //????????????????????????

        //??????????????????????????????
        Button btn_searchRecord = (Button)findViewById(R.id.btn_drawer_myfaceRecord);
        btn_searchRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchRecordActivity.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        //??????????????????????????????
        Button btn_evaluate = (Button)findViewById(R.id.btn_drawer_faceResultEsti);
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Evaluate.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        //??????????????????????????????
        Button btn_idolList = (Button)findViewById(R.id.btn_drawer_idolList);
        btn_idolList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.yusesc.myapplication.IdolListActivity.class);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });


        //???????????? ???

        //det = findViewById(R.id.detect_result);

        //cameraButton = findViewById(R.id.cameraButton);
        detectButton = findViewById(R.id.detectButton);
        imageView = findViewById(R.id.imageView);

//        cameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
//            }
//        });

        detectButton.setOnClickListener(v -> {
            //?????? ??????
            Intent intent = new Intent();
            intent.putExtra("nickname", nickname);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //startActivity(intent);
            startActivityForResult(intent, 0);
            //startActivityForResult(intent, 0);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //??????????????????
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Glide ???????????? ????????? ??????
                // Load : ????????? ??????, override : ????????? ??????, ?????? ?????? ??????, into : ???????????? ????????? ImageView ??????
                try {
                    this.sourceBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    this.cropBitmap = Utils.processBitmap(sourceBitmap, TF_OD_API_INPUT_SIZE);
                    this.imageView.setImageBitmap(cropBitmap);
                    initBox();
                    final List<Classifier.Recognition> results = detector.recognizeImage(cropBitmap);
                    handleResult(cropBitmap, results);
                    if(results.size()>0){
                        float max_of_confidence = 0;
                        String idolname="";
                        for(Classifier.Recognition tmp : results){
                            if(tmp.getConfidence() > max_of_confidence){
                                max_of_confidence = tmp.getConfidence();
                                idolname = tmp.getTitle();
                            }
                        }
                        //det.setText(idolname);

                        StorageReference sRef = FirebaseStorage.getInstance().getReference();
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-hh-mm");
                        String getTime = sdf.format(date);
                        CurrentUser cUser = (CurrentUser) getApplication();

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] bytes = baos.toByteArray();
                        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
                        Bitmap bitmap = sourceBitmap;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data_bit = baos.toByteArray();

                        UploadTask uploadTask = sRef.child(cUser.getUser().getEmailID()+getTime).putStream(bs);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                //url = sRef.child(cUser.getUser().getEmailID()+getTime).getDownloadUrl().toString();
                                url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            }
                        });
                        //
                        Intent intent = new Intent (RecogActivity.this, ResultActivity.class);
                        intent.putExtra("Idol", idolname);
                        intent.putExtra("Confidence", max_of_confidence);
                        intent.putExtra("url",FirebaseStorage.getInstance().getReference().child(cUser.getUser().getNickname()+getTime).getDownloadUrl().toString());
                        intent.putExtra("nickname", nickname);
                        startActivity(intent);
                        //???????????? ??????????????????
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final Logger LOGGER = new Logger();

    public static final int TF_OD_API_INPUT_SIZE = 416;

    private static final boolean TF_OD_API_IS_QUANTIZED = false;

    private static final String TF_OD_API_MODEL_FILE = "Vusion.tflite";

    private static final String TF_OD_API_LABELS_FILE = "file:///android_asset/coco.txt";

    // Minimum detection confidence to track a detection.
    private static final boolean MAINTAIN_ASPECT = false;
    private Integer sensorOrientation = 90;

    private Classifier detector;

    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private MultiBoxTracker tracker;
    private OverlayView trackingOverlay;

    protected int previewWidth = 0;
    protected int previewHeight = 0;

    private Bitmap sourceBitmap;
    private Bitmap cropBitmap;

    private ImageButton detectButton;
    private ImageView imageView;

    private void initBox() {
        previewHeight = TF_OD_API_INPUT_SIZE;
        previewWidth = TF_OD_API_INPUT_SIZE;
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE,
                        sensorOrientation, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);

        tracker = new MultiBoxTracker(this);
        trackingOverlay = findViewById(R.id.tracking_overlay);
        trackingOverlay.addCallback(
                canvas -> tracker.draw(canvas));

        tracker.setFrameConfiguration(TF_OD_API_INPUT_SIZE, TF_OD_API_INPUT_SIZE, sensorOrientation);

        try {
            detector =
                    YoloV4Classifier.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_IS_QUANTIZED);
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    private void handleResult(Bitmap bitmap, List<Classifier.Recognition> results) {
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);

        final List<Classifier.Recognition> mappedRecognitions =
                new LinkedList<Classifier.Recognition>();

        for (final Classifier.Recognition result : results) {
            final RectF location = result.getLocation();
            if (location != null && result.getConfidence() >= MINIMUM_CONFIDENCE_TF_OD_API) {
                canvas.drawRect(location, paint);
//                cropToFrameTransform.mapRect(location);
//
//                result.setLocation(location);
//                mappedRecognitions.add(result);
            }
        }
//        tracker.trackResults(mappedRecognitions, new Random().nextInt());
//        trackingOverlay.postInvalidate();
        imageView.setImageBitmap(bitmap);
    }
}
