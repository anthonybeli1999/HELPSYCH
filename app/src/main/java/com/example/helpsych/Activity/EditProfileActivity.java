package com.example.helpsych.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private Button UpdateAccountSettings, ButtonUploadPhoto;
    private EditText userName, userLastName, userEmail, userSex, userBirthDate, userDescription;
    //private CircleImageView ;
    private ImageView userProfileImage;

    private TextView btnCancel;
    private String currentUserID, currentUserType;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private static final int GalleryPick = 1;
    private StorageReference UserProfileImagesRef;
    private ProgressDialog loadingBar;

    private Toolbar SettingsToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        //currentUserType = mAuth.getCurrentUser().getEmail();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        btnCancel = (TextView) findViewById(R.id.user_textbutton_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        InitializeFields();

        //userName.setVisibility(View.INVISIBLE);

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UpdateSettings();
            }
        });
        RetrieveUserInfo();


        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        ButtonUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfileActivity.this);
            }
        });
    }

    private void InitializeFields()
    {
        //NOmbre, apellido, mail, fn, sexo, botones de upload y upload photo
        //DESCRIPCION

        UpdateAccountSettings = (Button) findViewById(R.id.btn_upload);
        userName = (EditText) findViewById(R.id.edt_name);
        userLastName = (EditText) findViewById(R.id.edt_lastname);
        userEmail = (EditText) findViewById(R.id.edt_email);
        userSex = (EditText) findViewById(R.id.edt_sex);
        userBirthDate = (EditText) findViewById(R.id.edt_date);
        userDescription = (EditText) findViewById(R.id.edt_description);
        userProfileImage = (ImageView) findViewById(R.id.userProfilePhotoEdit);
        loadingBar = new ProgressDialog(this);
        ButtonUploadPhoto = (Button) findViewById(R.id.btn_upload_photo);

        /*SettingsToolBar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(SettingsToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Account Settings");
    */}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri = result.getUri();


                StorageReference filePath = UserProfileImagesRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(EditProfileActivity.this, "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                            Task<Uri> filepath = task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    final String downloaedUrl = uri.toString();


                                    RootRef.child("Users").child(currentUserID).child("image")
                                            .setValue(downloaedUrl)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(EditProfileActivity.this, "Image save in Database, Successfully...", Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }
                                                    else
                                                    {
                                                        String message = task.getException().toString();
                                                        Toast.makeText(EditProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }
                                                }
                                            });

                                }
                            });



                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(EditProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }



    private void UpdateSettings()
    {

        String setUserName = userName.getText().toString();
        String setUserLastName = userLastName.getText().toString();
        String setUserSex = userSex.getText().toString();
        String setUserBirthDate = userBirthDate.getText().toString();
        String setUserDescription = userDescription.getText().toString();

        if (TextUtils.isEmpty(setUserName) || TextUtils.isEmpty(setUserLastName) | TextUtils.isEmpty(setUserSex) || TextUtils.isEmpty(setUserBirthDate))
        {
            Toast.makeText(this, "Please fill the blanks.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", setUserName);
            profileMap.put("lastName", setUserLastName);
            profileMap.put("sex", setUserSex);
            profileMap.put("birthdate", setUserBirthDate);
            profileMap.put("description", setUserDescription);
            RootRef.child("Users").child(currentUserID).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                SendUserToMainActivity();
                                Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(EditProfileActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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

                            userName.setText(retrieveUserName);
                            userLastName.setText(retrievesUserLastName);
                            userEmail.setText(retrievesEmail);
                            userSex.setText(retrievesUserSex);
                            userLastName.setText(retrievesUserLastName);
                            userBirthDate.setText(retrievesUserBirthDate);
                            userDescription.setText(retrievesUserDescription);
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

                            userName.setText(retrieveUserName);
                            userLastName.setText(retrievesUserLastName);
                            userEmail.setText(retrievesEmail);
                            userSex.setText(retrievesUserSex);
                            userLastName.setText(retrievesUserLastName);
                            userBirthDate.setText(retrievesUserBirthDate);
                            userDescription.setText(retrievesUserDescription);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")&& (dataSnapshot.hasChild("image"))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            userName.setText(retrieveUserName);
                            userLastName.setText(retrievesUserLastName);
                            userEmail.setText(retrievesEmail);
                            userSex.setText(retrievesUserSex);
                            userLastName.setText(retrievesUserLastName);
                            userBirthDate.setText(retrievesUserBirthDate);
                            Picasso.get().load(retrieveProfileImage).into(userProfileImage);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrievesUserLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrievesEmail = mAuth.getCurrentUser().getEmail();
                            String retrievesUserSex = dataSnapshot.child("sex").getValue().toString();
                            String retrievesUserBirthDate = dataSnapshot.child("birthdate").getValue().toString();

                            userName.setText(retrieveUserName);
                            userLastName.setText(retrievesUserLastName);
                            userEmail.setText(retrievesEmail);
                            userSex.setText(retrievesUserSex);
                            userLastName.setText(retrievesUserLastName);
                            userBirthDate.setText(retrievesUserBirthDate);
                        }
                        else
                        {
                            userName.setVisibility(View.VISIBLE);
                            Toast.makeText(EditProfileActivity.this, "Please set & update your profile information...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void SendUserToMainActivity()
    {
        finish();
    }

}
