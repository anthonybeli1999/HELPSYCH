package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpsych.R;

public class PopupAddTreatmentActivity extends AppCompatActivity {

    EditText EdtProcedure, EdtRecomendation;
    Button btnRegisterTreatment;
    TextView Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_add_treatment);

        EdtProcedure = (EditText) findViewById(R.id.edt_treatment_procedure);
        EdtRecomendation = (EditText) findViewById(R.id.edt_treatment_recommendation);

        btnRegisterTreatment = (Button) findViewById(R.id.btn_treatment_register);

        Cancel = (TextView) findViewById(R.id.txtCancelar);
    }
}