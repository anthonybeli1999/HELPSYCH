package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpsych.Adapters.MessageAdapter;
import com.example.helpsych.Adapters.MoodsAdapter;
import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Model.Messages;
import com.example.helpsych.Model.Mood;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MoodHistoryActivity extends AppCompatActivity {

    private static final String TAG = "MoodHistoryActivity";

    private RecyclerView moodsRecyclerView;

    private MoodsAdapter moodsAdapter;
    private SharedPreferences mPreferences;
    private ArrayList<Integer> moods = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();
    private DatabaseReference MoodRef;
    String messageReceiverID;

    private final List<Mood> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MoodsAdapter messageAdapter;
    private RecyclerView userMessagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        messageReceiverID = getIntent().getExtras().get("visit_user_id").toString();
        MoodRef = FirebaseDatabase.getInstance().getReference().child("MoodStatus").child(messageReceiverID);
        //moodsRecyclerView = findViewById(R.id.reycler_moods);

        messageAdapter = new MoodsAdapter(MoodHistoryActivity.this, messagesList);
        userMessagesList = (RecyclerView) findViewById(R.id.reycler_moods);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

Retrieve();

    }

    public void Retrieve() {

        MoodRef
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s)
                    {
                        Mood moods = dataSnapshot.getValue(Mood.class);
                        messagesList.add(moods);
                        messageAdapter.notifyDataSetChanged();
                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}