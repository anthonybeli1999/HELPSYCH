package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpsych.R;

public class AttentionSheetActivity extends AppCompatActivity {

    ImageView AddSheet;
    TextView TxtnameP, TxtdescriptionP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_sheet);

        AddSheet = (ImageView) findViewById(R.id.img_btn_add_attention_sheet);
        TxtnameP = (TextView) findViewById(R.id.txt_name_attention_pacient);
        TxtdescriptionP = (TextView) findViewById(R.id.txt_description_attention_pacient);


        AddSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttentionSheetActivity.this, PopupAddAttentionSheet.class);
                startActivity(intent);
            }
        });


    }
}