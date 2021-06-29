package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpsych.Model.AttentionSheet;
import com.example.helpsych.Model.Treatment;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TreatmentActivity extends AppCompatActivity {


    ImageView ImgAddTreatment;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private RecyclerView treatmentList;
    String receiverAttentionSheetId, receiverPatientId, receiverSpecialistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        receiverAttentionSheetId = getIntent().getExtras().get("attention_sheet_id").toString();
        receiverPatientId= getIntent().getExtras().get("patient_id").toString();
        receiverSpecialistId = getIntent().getExtras().get("specialist_id").toString();

        treatmentList = (RecyclerView) findViewById(R.id.treatment_list);
        ImgAddTreatment = (findViewById(R.id.img_btn_add_treatment));
        LlenarTreatmentList();
        ImgAddTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreatmentActivity.this, PopupAddTreatmentActivity.class);
                intent.putExtra("attention_sheet_id", receiverAttentionSheetId);
                intent.putExtra("patient_id", receiverPatientId);
                intent.putExtra("specialist_id", receiverSpecialistId);
                startActivity(intent);
            }
        });
    }

    public void LlenarTreatmentList()
    {
        FirebaseRecyclerOptions<Treatment> options =
                new FirebaseRecyclerOptions.Builder<Treatment>()
                        .setQuery(RootRef.child("Treatment").child(receiverAttentionSheetId), Treatment.class)
                        .build();

        FirebaseRecyclerAdapter<Treatment, TreatmentViewHolder> adapter =
                new FirebaseRecyclerAdapter<Treatment, TreatmentViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TreatmentViewHolder holder, final int position, @NonNull Treatment model)
                    {
                        holder.tr_procedure_display.setText(model.getProcedimiento());
                        holder.tr_recommendation_display.setText(model.getRecomendacion());
                        holder.tr_date_display.setText(model.getFechaSesion());
                    }

                    @NonNull
                    @Override
                    public TreatmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.treatment_display_layout, viewGroup, false);
                        TreatmentViewHolder viewHolder = new TreatmentViewHolder(view);
                        return viewHolder;
                    }
                };

        treatmentList.setLayoutManager(new GridLayoutManager(TreatmentActivity.this,1));
        treatmentList.setAdapter(adapter);

        adapter.startListening();
    }
    public static class TreatmentViewHolder extends RecyclerView.ViewHolder
    {
        TextView tr_procedure_display;
        TextView tr_recommendation_display;
        TextView tr_date_display;

        public TreatmentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tr_procedure_display = (TextView) itemView.findViewById(R.id.tr_procedure_display);
            tr_recommendation_display = (TextView) itemView.findViewById(R.id.tr_recommendation_display);
            tr_date_display = (TextView) itemView.findViewById(R.id.tr_date_display);
        }
    }
}