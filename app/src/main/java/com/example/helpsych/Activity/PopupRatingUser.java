package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PopupRatingUser extends AppCompatActivity {

    private Button Cancel, Send;
    private EditText RatingComment;
    private RatingBar RatingBarUser;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference RatingRef;

    private String currentSpecialistID = "";
    private String currentUserUID = "";
    private String nameUserUID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_rating_user);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.7));

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();


        Intent intent = getIntent();
        currentSpecialistID = intent.getStringExtra("uid");
        currentUserUID = intent.getStringExtra("useruid");
        nameUserUID = "";

        RatingRef = FirebaseDatabase.getInstance().getReference().child("Rating");

        InitializeFields();

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRatingToSpecialist();
                finish();
            }
        });

    }

    private void SendRatingToSpecialist() {
        String comment = RatingComment.getText().toString();

        RetrieveUserInfo();

        //mAuth.create
        RatingRef.child(currentSpecialistID).child(currentUserUID).child("Valoración")
                .setValue(RatingBarUser.getRating()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful()){
                    RatingRef.child(currentSpecialistID).child(currentUserUID).child("Autor").setValue(nameUserUID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            RatingRef.child(currentSpecialistID).child(currentUserUID).child("Comentario").setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Rating guardado", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            }
        });

        //mAuth.create
        RatingRef.child(currentSpecialistID).child(currentUserUID).child("Valoración")
                .setValue(RatingBarUser.getRating()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Rating guardado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void RetrieveUserInfo()
    {
        RootRef.child("Users").child(currentUserUID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                        nameUserUID = retrieveUserName;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void InitializeFields()
    {
        Cancel = (Button) findViewById(R.id.btn_rating_cancel);
        Send = (Button) findViewById(R.id.btn_rating_send);

        RatingBarUser = (RatingBar) findViewById(R.id.ratingbar_user);

        RatingComment = (EditText) findViewById(R.id.rating_comment);
    }
}