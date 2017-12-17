package com.joshua.experiment.activity.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

public class ShowPhotoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        ImageView imageView= (ImageView) findViewById(R.id.iv_pic);
        String pic=getIntent().getStringExtra("pic");
        Glide.with(this).load(pic).error(R.drawable.load_error).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
