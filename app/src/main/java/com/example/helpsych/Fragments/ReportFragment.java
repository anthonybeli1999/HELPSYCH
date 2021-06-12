package com.example.helpsych.Fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.ChatActivity;
import com.example.helpsych.Activity.MainActivity;
import com.example.helpsych.Activity.MoodHistoryActivity;
import com.example.helpsych.R;
import com.example.helpsych.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {



    private DatabaseReference RootRef, ContactsRef,UsersRef;
    private int countFriends;
    private int countRatings;
    private String registrationDay;

    private String currentUserID, currentUserType, currentEmal, messageSenderID;

    private FirebaseAuth mAuth;

    private Button UpdateAccountSettings;
    private TextView userNameP, userLastNameP, userEmailP, userSexP, userBirthDateP, userDescriptionP;
    private ImageView userProfileImage;

    //ModTracker
    private ImageView moodImageView;
    private ImageButton moodHistoryButton;
    private ImageButton addCommentButton;
    private ImageButton shareAppButton;
    private GestureDetectorCompat mDetector;
    private RelativeLayout parentRelativeLayout;

    private SharedPreferences mPreferences;
    private String currentDay;
    private int currentMoodIndex;
    private String currentComment;
    private RelativeLayout relative;

    // Mood Tracker
    private DatabaseReference MoodRef, RatingRef;
    TextView txtAllChats, txtRegistrationDay, txtRankingQuantity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //View v = inflater.inflate(R.layout.fragment_report, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_report, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentEmal = mAuth.getCurrentUser().getEmail();
        RootRef = FirebaseDatabase.getInstance().getReference();
        ContactsRef = FirebaseDatabase.getInstance().getReference();
        RatingRef = FirebaseDatabase.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        txtAllChats = (TextView) rootView.findViewById(R.id.txt_all_chats);
        txtRegistrationDay = (TextView) rootView.findViewById(R.id.txt_date_registration);
        txtRankingQuantity = (TextView) rootView.findViewById(R.id.txt_ranking_quantity);

        RetrieveInformationFriends();
        RetrieveInformationRating();
        SetInformation();
        RetrieveInformationRegistrationDate();


        messageSenderID = mAuth.getCurrentUser().getUid();
        //MoodTracker
        moodImageView = rootView.findViewById(R.id.my_mood);
        parentRelativeLayout = rootView.findViewById(R.id.parent_relative_layout);
        addCommentButton = rootView.findViewById(R.id.btn_add_comment);
        moodHistoryButton = rootView.findViewById(R.id.btn_mood_history);

        //parent_relative_layout

        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener()
        { @Override
        public boolean onDown(MotionEvent e)
        { return true; }
        @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            final int SWIPE_MIN_DISTANCE = 50;
            final int SWIPE_MAX_OFF_PATH = 500;
            final int SWIPE_THRESHOLD_VELOCITY = 200;
            try { //if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) return false;
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE )
                {
                    if (currentMoodIndex < 4) {
                        currentMoodIndex++;
                        changeUiForMood(currentMoodIndex);
                        //.saveMood(currentMoodIndex, currentDay, mPreferences);
                        Toast.makeText(getContext(), "Comment Saved", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE )
                {
                    if (currentMoodIndex > 0) {
                        currentMoodIndex--;
                        changeUiForMood(currentMoodIndex);
                        //SharedPreferencesHelper.saveMood(currentMoodIndex, currentDay, mPreferences);
                        Toast.makeText(getContext(), "Comment Saved", Toast.LENGTH_SHORT).show();
                    }
                } } catch (Exception e) { // nothing
                 }
                return super.onFling(e1, e2, velocityX, velocityY); } });
        rootView.setOnTouchListener(new View.OnTouchListener() { @Override public boolean onTouch(View v, MotionEvent event) { return gesture.onTouchEvent(event); } });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        currentDay = df.format(c);
        MoodRef = FirebaseDatabase.getInstance().getReference().child("MoodStatus");

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText editText = new EditText(getContext());


                builder.setMessage("Comment\uD83E\uDD14 \uD83D\uDCDD").setView(editText)
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().isEmpty()) {
                                    //SharedPreferencesHelper.saveComment(editText.getText().toString(), currentDay, mPreferences);
                                }

                                HashMap<String, String> moodStatus = new HashMap<>();
                                moodStatus.put("from", messageSenderID);
                                moodStatus.put("mood", String.valueOf(currentMoodIndex));
                                moodStatus.put("date", currentDay);
                                moodStatus.put("comment", editText.getText().toString());

                                MoodRef.child(messageSenderID).push()
                                        .setValue(moodStatus)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    //SendMessageRequestButton.setEnabled(true);
                                                    //Current_State = "request_sent";
                                                    //SendMessageRequestButton.setText("Cancelar solicitud");
                                                    Toast.makeText(getContext(), "Notification send successful...", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });

                                dialog.dismiss();

                                Toast.makeText(getContext(), "Comment Saved", Toast.LENGTH_SHORT).show();

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Toast.makeText(getContext(), "Comment Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                        .create().show();


            }
        });


        moodHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MoodHistoryActivity.class);
                intent.putExtra("visit_user_id", currentUserID);
                startActivity(intent);
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        return rootView;
    }

    private void SetInformation() {
    }

    private void RetrieveInformationRegistrationDate() {
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    registrationDay = snapshot.child("registrationDay").getValue().toString();
                    txtRegistrationDay.setText(registrationDay);
                }
                else
                {
                    txtRegistrationDay.setText("20/05/2021");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void RetrieveInformationFriends() {
        ContactsRef.child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    countFriends = (int) snapshot.child(currentUserID).getChildrenCount();
                    txtAllChats.setText(String.valueOf(countFriends));
                }
                else
                {
                    txtAllChats.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RetrieveInformationRating()
    {
        ContactsRef.child("Rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    /*for (DataSnapshot snap: snapshot.child(currentUserID).getChildren()) {
                        Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    }*/
                    countRatings = (int) snapshot.child(currentUserID).getChildrenCount();
                    txtRankingQuantity.setText(String.valueOf(countRatings));
                }
                else
                {
                    txtAllChats.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changeUiForMood(int currentMoodIndex) {
        moodImageView.setImageResource(Constants.moodImagesArray[currentMoodIndex]);
        parentRelativeLayout.setBackgroundResource(Constants.moodColorsArray[currentMoodIndex]);
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), Constants.moodSoundsArray[currentMoodIndex]);
        mediaPlayer.start();
    }


}