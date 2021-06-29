package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.helpsych.R;

public class TreatmentActivity extends AppCompatActivity {

    ImageView ImgAddTreatment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        ImgAddTreatment = (findViewById(R.id.img_btn_add_treatment));

        ImgAddTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreatmentActivity.this, PopupAddTreatmentActivity.class);
                startActivity(intent);
            }
        });
    }
}