package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.LoginActivity;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.Model.Test;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportAdminFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String currentUserID;
    private DatabaseReference UserRef, RootRef;
    Spinner mSpinnerApproaches;
    String selectedApproach = "";
    String selectedNameApproach = "";
    private DatabaseReference TestRef;
    private EditText questionTest;
    private TextView allUsers, allpatients, allspecialists, allarticles,allapproaches, alltests;
    private int countAllUsers, countAllPatientes, countAllSpecialists, countAllArticles, countAllApproaches, countAllTests;

    private RecyclerView questionlist;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportAdminFragment newInstance(String param1, String param2) {
        ReportAdminFragment fragment = new ReportAdminFragment();
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
        View v = inflater.inflate(R.layout.fragment_report_admin, container, false);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RootRef = FirebaseDatabase.getInstance().getReference();
        TestRef = FirebaseDatabase.getInstance().getReference().child("Test");
        currentUserID = mAuth.getCurrentUser().getUid();

        //Iniciar variables
        allUsers = v.findViewById(R.id.txt_all_users_admin);
        allpatients = v.findViewById(R.id.txt_all_users_p_admin);
        allspecialists = v.findViewById(R.id.txt_all_users_s_admin);
        allarticles = v.findViewById(R.id.txt_all_articles_admin);
        allapproaches = v.findViewById(R.id.txt_all_approach_admin);
        alltests = v.findViewById(R.id.txt_all_tests_admin);

        RetrieveInformation();

        Button Signout = (Button)  v.findViewById(R.id.btn_Signout_admin);
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });

        return v;
    }


    private void RetrieveInformation()
    {
        RootRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    /*for (DataSnapshot snap: snapshot.child(currentUserID).getChildren()) {
                        Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    }*/
                    countAllUsers = (int) snapshot.getChildrenCount();
                    allUsers.setText(String.valueOf(countAllUsers));
                }
                else
                {
                    allUsers.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RootRef.child("Users").orderByChild("usertype").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    /*for (DataSnapshot snap: snapshot.child(currentUserID).getChildren()) {
                        Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    }*/
                    countAllSpecialists = (int) snapshot.getChildrenCount();
                    allspecialists.setText(String.valueOf(countAllSpecialists));
                }
                else
                {
                    allspecialists.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        RootRef.child("Users").orderByChild("usertype").equalTo("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    /*for (DataSnapshot snap: snapshot.child(currentUserID).getChildren()) {
                        Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    }*/
                    countAllPatientes = (int) snapshot.getChildrenCount();
                    allpatients.setText(String.valueOf(countAllPatientes));
                }
                else
                {
                    allpatients.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RootRef.child("Article").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    /*for (DataSnapshot snap: snapshot.child(currentUserID).getChildren()) {
                        Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    }*/
                    countAllArticles = (int) snapshot.getChildrenCount();
                    allarticles.setText(String.valueOf(countAllArticles));
                }
                else
                {
                    allarticles.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RootRef.child("psyapproach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    countAllApproaches = (int) snapshot.getChildrenCount();
                    allapproaches.setText(String.valueOf(countAllApproaches));
                }
                else
                {
                    allapproaches.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RootRef.child("Test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    countAllTests = (int) snapshot.getChildrenCount();
                    alltests.setText(String.valueOf(countAllTests));
                }
                else
                {
                    alltests.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SendUserToLoginActivity()
    {
        UserRef.child(currentUserID).child("device_token")
                .setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful()){

                }
            }
        });

        getActivity().finish();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

}