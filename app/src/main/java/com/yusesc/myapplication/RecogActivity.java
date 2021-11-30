package com.yusesc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
// picture upload import

// carmera import
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;


import com.yusesc.myapplication.customview.OverlayView;
import com.yusesc.myapplication.env.ImageUtils;
import com.yusesc.myapplication.env.Logger;
import com.yusesc.myapplication.env.Utils;
import com.yusesc.myapplication.tflite.Classifier;
import com.yusesc.myapplication.tflite.YoloV4Classifier;
import com.yusesc.myapplication.tracking.MultiBoxTracker;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RecogActivity extends AppCompatActivity {



    private TextView det;

    public static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recog);

        det = findViewById(R.id.detect_result);

        cameraButton = findViewById(R.id.cameraButton);
        detectButton = findViewById(R.id.detectButton);
        imageView = findViewById(R.id.imageView);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            }
        });

        detectButton.setOnClickListener(v -> {
            //코드 작성
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //startActivity(intent);
            startActivityForResult(intent, 0);
            //startActivityForResult(intent, 0);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //이미지업로드
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Glide 사용해서 이미지 출력
                // Load : 이미지 경로, override : 이미지 가로, 세로 크기 조정, into : 이미지를 출력할 ImageView 객체
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
                        det.setText(idolname);
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

    private Button cameraButton, detectButton;
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
