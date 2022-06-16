package com.example.chatappandroidclient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import converters.DataConverter;

public class Settings extends AppCompatActivity {
    private ChatAppDB db;
    private ImagesDao imagesDao;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = Room.databaseBuilder(myApplication.context, ChatAppDB.class, "ChatsDB")
                .allowMainThreadQueries()
                .build();
        imagesDao = db.imagesDao();

        ImageView profilePicture = findViewById(R.id.profilePicture);

        ProfilePicture pp = imagesDao.getPictureById(getIntent().getExtras().getString("myName"));

        //if the user has a profile picture uploaded
        if(pp!=null){
            byte[] imageBytes = pp.getPicture();
            Bitmap imageBitmap = DataConverter.convertByteArrayToBitmap(imageBytes);
            profilePicture.setImageBitmap(imageBitmap);
        } else{
            //the user did not upload an image.
            profilePicture.setImageResource(R.drawable.jon_snow);
        }

        FloatingActionButton btn_back = findViewById(R.id.btn_back_to_contacts);
        btn_back.setOnClickListener(view -> {
            finish();
        });
    }
}