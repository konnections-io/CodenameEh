package com.example.codenameeh.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.File;
import java.util.List;
/**
 * @author Cole Boytinck
 * @version 1.0
 * Scan activity opens a camera, then the user can take a photo of a barcode
 * This barcode is read, and if it contains a valid isbn, then it sends
 * that isbn to the BookListActivity, where it is inserted into a book
 * and the it attempts to get the data from google
 */
public class ScanActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE = 1;

    /**
     * onCreate starts the camera activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, TAKE_PICTURE);

    }

    /**
     * This function gets the picture back from the camera activity,
     * then processes the bitmap, and passes it to the detectBarcode function
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TestScan", "Back from camera");
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    detectBarcode(bitmap);
                }
        }
        finish();
    }

    /**
     * Takes a bitmap, and try's to find a barcode. If a barcode is found,
     * it takes the ISBN from the barcode, and sends it to the BookListActivity,
     * for further processing
     * If a barcode is not found, the functions makes a "cannot find barcode" toast,
     * and finishes
     * @param bitmap bitmap for the image of the barcode
     */
    public void detectBarcode(Bitmap bitmap) {
        Log.e("TestScan", "Detect Barcode");
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_EAN_13)
                        .build();

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        for (FirebaseVisionBarcode barcode: barcodes) {
                            int valueType = barcode.getValueType();

                            if(valueType == FirebaseVisionBarcode.TYPE_ISBN) {
                                String isbn = barcode.getDisplayValue();
                                Intent intent = new Intent(ScanActivity.this, BookListActivity.class);
                                intent.putExtra("isbn", isbn);
                                Log.e("TestScan", "Created Intent");
                                startActivity(intent);
                            } else {
                                Toast.makeText(ScanActivity.this, "Cannot find barcode", Toast.LENGTH_SHORT).show();
                                Log.e("barcode", "Cannot find barcode");
                                finish();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ScanActivity.this, "Cannot find barcode", Toast.LENGTH_SHORT).show();
                        Log.e("barcode", e.toString());
                        Log.e("barcode", "Cannot find barcode");
                        finish();
                    }
                });
    }
}
