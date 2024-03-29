package com.example.helpsych.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.Activity.LoginActivity;
import com.example.helpsych.Activity.PasswordChangeActivity;
import com.example.helpsych.Model.User;
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

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class ProfileFragment extends Fragment {

    private DatabaseReference RootRef;
    private DatabaseReference UserRef;

    private String currentUserID, currentUserType, currentEmal;

    private FirebaseAuth mAuth;

    private Button UpdateAccountSettings;
    private TextView userNameP, userLastNameP, userEmailP, userSexP, userBirthDateP, userDescriptionP;
    private ImageView userProfileImage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);


        RootRef = FirebaseDatabase.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentEmal = mAuth.getCurrentUser().getEmail();


        userNameP = (TextView) rootView.findViewById(R.id.txt_name_p);
        userLastNameP = (TextView) rootView.findViewById(R.id.txt_lastname_p);
        userEmailP =  (TextView)rootView.findViewById(R.id.txt_email_p);
        userSexP =  (TextView) rootView.findViewById(R.id.txt_sex_p);
        userBirthDateP = (TextView)rootView.findViewById(R.id.txt_date_p);
        userDescriptionP = (TextView) rootView.findViewById(R.id.txt_description_p);
        userProfileImage = (ImageView) rootView.findViewById(R.id.img_userphoto_p);

        try{
            RetrieveUserInfo();
        }
        catch (Exception e)
        {

        }


        Button btnLogout = rootView.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });

        Button btnTurnIntoListener = rootView.findViewById(R.id.btnChangePassword_p);
        btnTurnIntoListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PasswordChangeActivity.class);
                startActivity(intent);
            }
        });

        TextView txtEditarPerfil = rootView.findViewById(R.id.user_textbutton_edit);

        txtEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

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


    private void RetrieveUserInfo()
    {
        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image") && (dataSnapshot.hasChild("description")))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = currentEmal;
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrievesUserDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            userNameP.setText(retrieveUserName);
                            userLastNameP.setText(retrievesUserLastName);
                            userEmailP.setText(retrievesEmail);
                            userSexP.setText(retrievesUserSex);
                            userLastNameP.setText(retrievesUserLastName);
                            userBirthDateP.setText(retrievesUserBirthDate);
                            userDescriptionP.setText(retrievesUserDescription);
                            Picasso.get().load(retrieveProfileImage).into(userProfileImage);

                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")&& (dataSnapshot.hasChild("description"))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = currentEmal;
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrievesUserDescription = dataSnapshot.child("description").getValue().toString();

                            userNameP.setText(retrieveUserName);
                            userLastNameP.setText(retrievesUserLastName);
                            userEmailP.setText(retrievesEmail);
                            userSexP.setText(retrievesUserSex);
                            userLastNameP.setText(retrievesUserLastName);
                            userBirthDateP.setText(retrievesUserBirthDate);
                            userDescriptionP.setText(retrievesUserDescription);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")&& (dataSnapshot.hasChild("image"))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            userNameP.setText(retrieveUserName);
                            userLastNameP.setText(retrievesUserLastName);
                            userEmailP.setText(retrievesEmail);
                            userSexP.setText(retrievesUserSex);
                            userLastNameP.setText(retrievesUserLastName);
                            userBirthDateP.setText(retrievesUserBirthDate);
                            Picasso.get().load(retrieveProfileImage).into(userProfileImage);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = currentEmal;
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();

                            userNameP.setText(retrieveUserName);
                            userLastNameP.setText(retrievesUserLastName);
                            userEmailP.setText(retrievesEmail);
                            userSexP.setText(retrievesUserSex);
                            userLastNameP.setText(retrievesUserLastName);
                            userBirthDateP.setText(retrievesUserBirthDate);
                        }
                        else
                        {
                            //userName.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Please set & update your profile information...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}