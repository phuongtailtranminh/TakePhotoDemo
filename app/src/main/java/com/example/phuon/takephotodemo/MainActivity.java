package com.example.phuon.takephotodemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA_CODE = 1;
    private ArrayList<Bitmap> listOfBitmap;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        listOfBitmap = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_CAMERA_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bm = (Bitmap)extras.get("data");
            listOfBitmap.add(bm);
            listView.setAdapter(new CustomAdapter(this, listOfBitmap));
        }
    }

    private class CustomAdapter extends ArrayAdapter<Bitmap> {

        List<Bitmap> listBM;

        public CustomAdapter(Context context, List<Bitmap> listBM) {
            super(context, R.layout.list_view, listBM);
            this.listBM = listBM;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {
                myView = getLayoutInflater().inflate(R.layout.list_view, null, true);
            }
            Bitmap bm = listBM.get(position);
            ImageView imgView = (ImageView) myView.findViewById(R.id.iv);
            imgView.setImageBitmap(bm);
            return myView;
        }
    }

}
