package com.example.helpsych.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.Activity.LoginActivity;
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
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class PerfilFragment extends Fragment {

    private DatabaseReference RootRef;

    private String currentUserID, currentUserType;

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


    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);


        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        userNameP = (TextView) v.findViewById(R.id.txt_name_p);
        userLastNameP = (TextView) v.findViewById(R.id.txt_lastname_p);
        userEmailP =  (TextView)v.findViewById(R.id.txt_email_p);
        userSexP =  (TextView) v.findViewById(R.id.txt_sex_p);
        userBirthDateP = (TextView)v.findViewById(R.id.txt_date_p);
        userDescriptionP = (TextView) v.findViewById(R.id.txt_description_p);
        userProfileImage = (ImageView) v.findViewById(R.id.img_userphoto_p);

        RetrieveUserInfo();

        Button btnLogout = v.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });

        Button btnTurnIntoListener = v.findViewById(R.id.btnTurnIntoListener);
        btnTurnIntoListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mAuth.signOut();
                ChangeUserType();
            }
        });

        TextView txtEditarPerfil = v.findViewById(R.id.user_textbutton_edit);

        txtEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });



        return v;


    }

    public void ChangeUserType()
    {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("usertype", "2");
            RootRef.child("Users").child(currentUserID).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {

                                Toast.makeText(getContext(), "Tipo de usuario actualizado...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }
    public void Logout(View view) {

    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        //finish();
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
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
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
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
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
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
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