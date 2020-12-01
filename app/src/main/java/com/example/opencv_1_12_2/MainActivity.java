package com.example.opencv_1_12_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Mat mat;
    public static Button pictureClick;
    public static ImageView imageView;
    public static ImageView imageView1;
    public static Bitmap imageBitmap;

    static {
        if(OpenCVLoader.initDebug()){
            Log.i(TAG,"Opencv loaded");
        }
        else{
            Log.i(TAG,"Opencv not loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("opencv_java3");
        setContentView(R.layout.activity_main);

        imageView=(ImageView)findViewById(R.id.image);
        imageView1=(ImageView)findViewById(R.id.image1);
        pictureClick=(Button)findViewById(R.id.button1);
        pictureClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imageView1.setImageBitmap(convert(imageBitmap));
        }
    }

    private Bitmap convert(Bitmap imageBitmap) {
        Mat tmp = new Mat (imageBitmap.getHeight(), imageBitmap.getWidth(), CvType.CV_32FC1);
        Bitmap bmp32 = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, tmp);
        Mat Mat2 = new Mat(imageBitmap.getWidth(), imageBitmap.getHeight(), CvType.CV_32FC1);

        // convert image to grayscale
        Imgproc.cvtColor(tmp, Mat2, Imgproc.COLOR_BGR2GRAY);

        // doing a gaussian blur prevents getting a lot of false hits
        Imgproc.GaussianBlur(Mat2, tmp, new Size(3, 3), 2, 2); // ?

        // now apply canny function
        int param_threshold1 = 25; // manually defined
        int param_threshold2 = param_threshold1 * 3; // Cannys recommendation
        Imgproc.Canny(tmp, Mat2, param_threshold1, param_threshold2);

        // ?
        Imgproc.cvtColor(Mat2, tmp, Imgproc.COLOR_GRAY2BGRA, 4);

        // convert matrix to output bitmap
        Bitmap output = Bitmap.createBitmap(imageBitmap.getWidth(), imageBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.matToBitmap(tmp, output);
        return output;
    }

}