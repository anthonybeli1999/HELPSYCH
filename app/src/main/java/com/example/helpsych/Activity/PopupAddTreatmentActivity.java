package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Model.AttentionSheet;
import com.example.helpsych.Model.Treatment;
import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopupAddTreatmentActivity extends AppCompatActivity {

    EditText EdtProcedure, EdtRecomendation;
    Button btnRegisterTreatment;
    TextView Cancel;

    String receiverAttentionSheetId, receiverPatientId, receiverSpecialistId;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    String formattedDate = "";
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_add_treatment);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        //currentUserType = mAuth.getCurrentUser().getEmail();
        RootRef = FirebaseDatabase.getInstance().getReference();
        receiverAttentionSheetId = getIntent().getExtras().get("attention_sheet_id").toString();
        receiverPatientId= getIntent().getExtras().get("patient_id").toString();
        receiverSpecialistId = getIntent().getExtras().get("specialist_id").toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        EdtProcedure = (EditText) findViewById(R.id.edt_treatment_procedure);
        EdtRecomendation = (EditText) findViewById(R.id.edt_treatment_recommendation);

        btnRegisterTreatment = (Button) findViewById(R.id.btn_treatment_register);

        Cancel = (TextView) findViewById(R.id.txtCancelar);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegisterTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTreatment();
                finish();
            }
        });

    }

    public void AddTreatment()
    {
        String idFichaAtencion = receiverAttentionSheetId;
        String procedimiento = EdtProcedure.getText().toString();
        String recomendacion = EdtRecomendation.getText().toString();
        String idPaciente =receiverPatientId;
        String idEspecialista=receiverSpecialistId;

        DatabaseReference TreatmentKeyRef = RootRef.child("Treatment").push();

        String TreatmentPushID = TreatmentKeyRef.getKey();

        Treatment fa = new Treatment();
        fa.setIdFichaAtencion(idFichaAtencion);
        fa.setIdSesion(TreatmentPushID);
        fa.setFechaSesion(formattedDate);
        fa.setProcedimiento(procedimiento);
        fa.setRecomendacion(recomendacion);
        fa.setIdPaciente(idPaciente);
        fa.setIdEspecialista(idEspecialista);
        RootRef.child("Treatment").child(idFichaAtencion).child(fa.getIdSesion()).setValue(fa);
        Toast.makeText(PopupAddTreatmentActivity.this, "Ficha de tratamiento generada correctamente", Toast.LENGTH_SHORT).show();

    }
}