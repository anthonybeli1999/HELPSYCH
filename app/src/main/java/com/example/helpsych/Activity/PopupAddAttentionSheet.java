package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpsych.R;

public class PopupAddAttentionSheet extends AppCompatActivity {

    EditText EdtReason, EdtBackground;
    Button btnCreateSheet;
    TextView Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_add_attention_sheet);

        EdtReason = (EditText) findViewById(R.id.attention_sheet_reason_p);
        EdtBackground = (EditText) findViewById(R.id.attention_sheet_background_p);

        btnCreateSheet = (Button) findViewById(R.id.btn_create_sheet);

        Cancel = (TextView) findViewById(R.id.txtCancelar);
    }
}