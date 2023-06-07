package com.example.healthgo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthgo.ml.MobilenetV110224Quant;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

import org.tensorflow.lite.support.image.TensorImage;

public class MedicineIdentifier extends AppCompatActivity {
Button selectbtn,predictbtn,capturebtn;
TextView result;
Bitmap bitmap;
String[] labels;
ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_identifier);
        labels = new String[10000];
        int cnt=0;
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line=bufferedReader.readLine();
            while (line!=null){
                labels[cnt]=line;
                cnt++;
                line=bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        selectbtn=findViewById(R.id.SelectImage);
        predictbtn=findViewById(R.id.predictbtn);
        capturebtn=findViewById(R.id.capturebtn);
        result=findViewById(R.id.result);
        imageView=findViewById(R.id.imageview);






        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });
       capturebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent camerai=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(camerai,100);
           }
       });
        predictbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MobilenetV110224Quant model = MobilenetV110224Quant.newInstance(MedicineIdentifier.this);

                    // Create inputs for inference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

                    // Convert bitmap to ARGB_8888 format.
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                    // Resize bitmap to match the model input size.
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

                    // Load the resized bitmap into the input tensor buffer.
                    TensorImage tensorImage = new TensorImage(DataType.UINT8);
                    tensorImage.load(resizedBitmap);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Perform model inference and get the results.
                    MobilenetV110224Quant.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Display the predicted class.
                    result.setText(labels[getMax(outputFeature0.getFloatArray())] + " ");

                    // Release model resources.
                    model.close();
                } catch (IOException e) {
                    // Handle the exception appropriately.
                    e.printStackTrace();
                }
            }
        });




    }
    int getMax(float[] arr){
        int max=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>arr[max])
                max=i;
        }
        return  max;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)  {
        if(requestCode==10){
            if(data!=null){
                Uri uri=data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            if(requestCode==100){
                try{
                    bitmap=(Bitmap)data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);}
                catch (Exception e){
                    e.printStackTrace();
                }
            }


    }
}}