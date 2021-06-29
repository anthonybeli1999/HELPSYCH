package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Model.AttentionSheet;
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

public class PopupAddAttentionSheet extends AppCompatActivity {

    EditText EdtReason, EdtBackground;
    Button btnCreateSheet;
    TextView Cancel;

    String receiverUserID, receiverPatientName;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    String formattedDate = "";
    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_add_attention_sheet);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        //currentUserType = mAuth.getCurrentUser().getEmail();
        RootRef = FirebaseDatabase.getInstance().getReference();
        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        receiverPatientName = getIntent().getExtras().get("patient_name").toString();

        EdtReason = (EditText) findViewById(R.id.attention_sheet_reason_p);
        EdtBackground = (EditText) findViewById(R.id.attention_sheet_background_p);

        btnCreateSheet = (Button) findViewById(R.id.btn_create_sheet);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        Cancel = (TextView) findViewById(R.id.txtCancelar);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCreateSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAttentionSheet();
                finish();
            }
        });
    }

    public void AddAttentionSheet()
    {
        String motivoConsulta = EdtReason.getText().toString();
        String Antecedentes = EdtBackground.getText().toString();
        String nombresapellidos = receiverPatientName;
        String idPaciente =receiverUserID;
        String idEspecialista=currentUserID;

        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String retrieveUserName = snapshot.child("name").getValue().toString();
                        String retrieveUserLastName = snapshot.child("lastName").getValue().toString();
                        String fullname = retrieveUserName + " " + retrieveUserLastName;

                        if (TextUtils.isEmpty(motivoConsulta) || TextUtils.isEmpty(Antecedentes)) {
                            Toast.makeText(PopupAddAttentionSheet.this, "Por favor rellene los campos", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference PatientHistoryKeyRef = RootRef.child("AttentionSheet").push();

                            String fichaAtencionPushID = PatientHistoryKeyRef.getKey();

                            AttentionSheet fa = new AttentionSheet();
                            fa.setIdFichaAtencion(fichaAtencionPushID);
                            fa.setNombresApellidosPaciente(nombresapellidos);
                            fa.setFechaEmperejamiento(formattedDate);
                            fa.setMotivoConsulta(motivoConsulta);
                            fa.setAntecedentes(Antecedentes);
                            fa.setEspecialista(fullname);
                            fa.setIdPaciente(idPaciente);
                            fa.setIdEspecialista(idEspecialista);
                            RootRef.child("AttentionSheet").child(fa.getIdFichaAtencion()).setValue(fa);
                            Toast.makeText(PopupAddAttentionSheet.this, "Ficha de atención generada correctamente", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PopupAddAttentionSheet.this, "Ha ocurrido un error en la creación de la ficha de atención.", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}