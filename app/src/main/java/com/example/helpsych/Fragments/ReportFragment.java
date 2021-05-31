package com.example.helpsych.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    private DatabaseReference RootRef, ContactsRef,UsersRef;
    private int countFriends;
    private String registrationDay;

    private String currentUserID, currentUserType, currentEmal;

    private FirebaseAuth mAuth;

    private Button UpdateAccountSettings;
    private TextView userNameP, userLastNameP, userEmailP, userSexP, userBirthDateP, userDescriptionP;
    private ImageView userProfileImage;

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
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        txtAllChats = (TextView) rootView.findViewById(R.id.txt_all_chats);
        txtRegistrationDay = (TextView) rootView.findViewById(R.id.txt_date_registration);
        txtRankingQuantity = (TextView) rootView.findViewById(R.id.txt_ranking_quantity);

        RetrieveInformationFriends();
        SetInformation();
        RetrieveInformationRegistrationDate();


        //return v;
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


}