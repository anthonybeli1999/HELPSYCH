package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopupDetailUser_Sv extends AppCompatActivity {

    private String receiverUserID, senderUserID, Current_State, receiverPatientName;

    private ImageView userProfileImage;
    private TextView userProfileName, userProfileLastname, userProfileEmail, userProfileCity, userProfileCountry;
    TextView userProfileBirthday, userProfileGenre;
    private TextView userProfileDescription;
    private Button OkButton;
    private Button HistoryButton;
    ArrayList<String> ValuesArray = new ArrayList<String>();

    private double acumulador;
    private double rating;

    private DatabaseReference UserRef, ChatRequestRef, ContactsRef, NotificationRef, RatingRef;
    private DatabaseReference MessagesRef;
    private FirebaseAuth mAuth;
    private Button PatientHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_detail_user__sv);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int) (width * 0.85), (int) (height * 0.7));

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        RatingRef = FirebaseDatabase.getInstance().getReference().child("Rating");
        MessagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");

        receiverUserID = getIntent().getExtras().get("visit_user_id").toString();
        //patient_name
        receiverPatientName = getIntent().getExtras().get("patient_name").toString();

        senderUserID = mAuth.getCurrentUser().getUid();

        userProfileDescription = (TextView) findViewById(R.id.txt_pop_user_description_sv);
        userProfileImage = (ImageView) findViewById(R.id.visit_profile_image_sv);
        userProfileName = (TextView) findViewById(R.id.txt_pop_user_name_sv);
        userProfileBirthday = (TextView) findViewById(R.id.txt_pop_user_birtthday_sv);
        userProfileGenre = (TextView) findViewById(R.id.txt_pop_user_genre_sv);

        OkButton = (Button) findViewById(R.id.ok_sv_button);
        HistoryButton = (Button) findViewById(R.id.detail_user_p_btn_history);
        Current_State = "new";

        RetrieveUserInfo();



        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupDetailUser_Sv.this, AttentionSheetActivity.class);
                intent.putExtra("visit_user_id", receiverUserID);
                intent.putExtra("patient_name", receiverPatientName);
                startActivity(intent);
            }
        });
    }

    private void RetrieveUserInfo() {
        UserRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("image"))) {
                    String userImage = dataSnapshot.child("image").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userLastname = dataSnapshot.child("lastName").getValue().toString();
                    String userDescription = dataSnapshot.child("description").getValue().toString();
                    //String userCity = dataSnapshot.child("city").getValue().toString();
                    //String userCountry = dataSnapshot.child("country").getValue().toString();
                    String userBirthday = dataSnapshot.child("birthdate").getValue().toString();
                    String userGenre = dataSnapshot.child("sex").getValue().toString();

                    Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(userProfileImage);
                    userProfileName.setText(userName + " " + userLastname);

                    if(userDescription.equals("")){
                        userProfileDescription.setText("Por definir");
                    }
                    else{
                        userProfileDescription.setText(userDescription);
                    }

                    userProfileBirthday.setText(userBirthday);
                    userProfileGenre.setText(userGenre);

                } else {

                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userLastname = dataSnapshot.child("lastName").getValue().toString();
                    String userDescription = dataSnapshot.child("description").getValue().toString();
                    //String userCity = dataSnapshot.child("city").getValue().toString();
                    //String userCountry = dataSnapshot.child("country").getValue().toString();
                    String userBirthday = dataSnapshot.child("birthdate").getValue().toString();
                    String userGenre = dataSnapshot.child("sex").getValue().toString();

                    userProfileName.setText(userName + " " + userLastname);

                    if(userDescription.equals("")){
                        userProfileDescription.setText("Por definir");
                    }
                    else{
                        userProfileDescription.setText(userDescription);
                    }

                    userProfileBirthday.setText(userBirthday);
                    userProfileGenre.setText(userGenre);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}