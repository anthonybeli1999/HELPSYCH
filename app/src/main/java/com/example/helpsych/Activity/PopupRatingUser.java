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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PopupRatingUser extends AppCompatActivity {

    private Button Cancel, Send;
    private EditText RatingComment;
    private RatingBar RatingBarUser;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference RatingRef, RatedRef;

    private String currentSpecialistID = "";
    private String currentUserUID = "";
    private String nameUserUID , lastnameUserUID;
    private String saveCurrentTime, saveCurrentDate;
    private DatabaseReference NotificationRef;


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
        //lastnameUserUID = "";



        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        RatingRef = FirebaseDatabase.getInstance().getReference().child("Rating");
        RatedRef = FirebaseDatabase.getInstance().getReference().child("Rated");
        RetrieveUserInfo();
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

        String RatingSenderRef = "Rating/" + currentUserUID ;
        String RatingReceiverRef = "Rating/" + currentSpecialistID;

        DatabaseReference userRatingKeyRef = RootRef.child("Rating")
                .child(currentUserUID).push();

        String RatingPushID = userRatingKeyRef.getKey();
        String fullName = nameUserUID + " " + lastnameUserUID;
        String ratingValue= String.valueOf(RatingPushID);


        Map ratingTextBody = new HashMap();
        ratingTextBody.put("message", comment);
        ratingTextBody.put("valuation", String.valueOf(RatingBarUser.getRating()));
        ratingTextBody.put("fromID", currentUserUID);
        ratingTextBody.put("toID", currentSpecialistID);
        ratingTextBody.put("fromName", fullName);
        ratingTextBody.put("messageID", ratingValue);
        ratingTextBody.put("time", saveCurrentTime);
        ratingTextBody.put("date", saveCurrentDate);

        Map ratingBodyDetails = new HashMap();
        ratingBodyDetails.put(RatingSenderRef + "/" + RatingPushID, ratingTextBody);
        ratingBodyDetails.put( RatingReceiverRef + "/" + RatingPushID, ratingTextBody);

        RootRef.updateChildren(ratingBodyDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if (task.isSuccessful())
                {
                    HashMap<String, String> chatNotificationMap = new HashMap<>();
                    chatNotificationMap.put("from", currentUserUID);
                    chatNotificationMap.put("type", "Nuevo valoración recibida");
                    chatNotificationMap.put("message", " ha valorado su atención. Gracias por su tiempo.");

                    NotificationRef.child(currentSpecialistID).push()
                            .setValue(chatNotificationMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(PopupRatingUser.this, "Notification send successful...", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                    Toast.makeText(PopupRatingUser.this, "Rating saved Successfully...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PopupRatingUser.this, "Error", Toast.LENGTH_SHORT).show();
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
                        String retrieveUserLastName = dataSnapshot.child("lastName").getValue().toString();
                        lastnameUserUID = retrieveUserLastName;
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