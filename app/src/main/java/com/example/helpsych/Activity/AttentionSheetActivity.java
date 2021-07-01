package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Model.AttentionSheet;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AttentionSheetActivity extends AppCompatActivity {

    ImageView AddSheet, userImage;
    TextView TxtnameP, TxtdescriptionP;
    String receiverUserID, receiverPatientName;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private RecyclerView attentionSheetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_sheet);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        //currentUserType = mAuth.getCurrentUser().getEmail();
        RootRef = FirebaseDatabase.getInstance().getReference();

        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        receiverPatientName = getIntent().getExtras().get("patient_name").toString();

        userImage = (ImageView) findViewById(R.id.visit_profile_image_sv);
        AddSheet = (ImageView) findViewById(R.id.img_btn_add_attention_sheet);
        TxtnameP = (TextView) findViewById(R.id.txt_name_attention_pacient);
        TxtdescriptionP = (TextView) findViewById(R.id.txt_description_attention_pacient);
        attentionSheetList = (RecyclerView) findViewById(R.id.attention_sheet_list);
        RetrieveInformation();
        RetrieveAttentionSheetList();

        AddSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttentionSheetActivity.this, PopupAddAttentionSheet.class);
                intent.putExtra("visit_user_id", receiverUserID);
                intent.putExtra("patient_name", receiverPatientName);
                startActivity(intent);
            }
        });


    }

    private void RetrieveAttentionSheetList() {
        //attention_sheet_list
        FirebaseRecyclerOptions<AttentionSheet> options =
                new FirebaseRecyclerOptions.Builder<AttentionSheet>()
                        .setQuery(RootRef.child("AttentionSheet").child(receiverUserID), AttentionSheet.class)
                        .build();

        FirebaseRecyclerAdapter<AttentionSheet, AttentionSheetViewHolder> adapter =
                new FirebaseRecyclerAdapter<AttentionSheet, AttentionSheetViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AttentionSheetViewHolder holder, final int position, @NonNull AttentionSheet model)
                    {
                        holder.as_reason_display.setText(model.getMotivoConsulta());
                        holder.as_specialist_display.setText(model.getEspecialista());
                        holder.as_background_display.setText(model.getAntecedentes());
                        holder.as_date_display.setText(model.getFechaEmperejamiento());


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                Intent chatIntent = new Intent(AttentionSheetActivity.this, TreatmentActivity.class);
                                chatIntent.putExtra("attention_sheet_id", model.getIdFichaAtencion());
                                chatIntent.putExtra("patient_id", model.getIdPaciente());
                                chatIntent.putExtra("specialist_id", model.getIdEspecialista());

                                chatIntent.putExtra("motivo_consulta", model.getMotivoConsulta());
                                chatIntent.putExtra("fecha", model.getFechaEmperejamiento());
                                chatIntent.putExtra("antecedentes", model.getAntecedentes());
                                chatIntent.putExtra("especialista", model.getEspecialista());
                                startActivity(chatIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AttentionSheetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attention_sheet_display_layout, viewGroup, false);
                        AttentionSheetViewHolder viewHolder = new AttentionSheetViewHolder(view);
                        return viewHolder;
                    }
                };

        attentionSheetList.setLayoutManager(new GridLayoutManager(AttentionSheetActivity.this,1));
        attentionSheetList.setAdapter(adapter);

        adapter.startListening();
    }

    public static class AttentionSheetViewHolder extends RecyclerView.ViewHolder
    {
        TextView as_reason_display;
        TextView as_specialist_display;
        TextView as_background_display;
        TextView as_date_display;

        public AttentionSheetViewHolder(@NonNull View itemView)
        {
            super(itemView);

            as_reason_display = (TextView) itemView.findViewById(R.id.as_reason_display);
            as_specialist_display = (TextView) itemView.findViewById(R.id.as_specialist_display);
            as_background_display = (TextView) itemView.findViewById(R.id.as_background_display);
            as_date_display = (TextView) itemView.findViewById(R.id.as_date_display);
        }
    }

    private void RetrieveInformation() {
        RootRef.child("Users").child(receiverUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String retrieveUserImage = "";
                        if(snapshot.child("image").exists())
                        {
                             retrieveUserImage = snapshot.child("image").getValue().toString();
                        }

                        String retrieveUserName = snapshot.child("name").getValue().toString();
                        String retrieveUserLastName = snapshot.child("lastName").getValue().toString();
                        String retrieveUserDescription= snapshot.child("description").getValue().toString();

                        if(!retrieveUserImage.equals(""))
                        {
                            Picasso.get().load(retrieveUserImage).into(userImage);
                        }

                        TxtnameP.setText(retrieveUserName + " " + retrieveUserLastName );
                        TxtdescriptionP.setText(retrieveUserDescription);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}