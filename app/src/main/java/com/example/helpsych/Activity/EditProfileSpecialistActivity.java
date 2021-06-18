package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Calendar;
import java.util.HashMap;

public class EditProfileSpecialistActivity extends AppCompatActivity {

    private Button UpdateAccountSettingsSpecialist, ButtonUploadPhotoSpecialist;
    private EditText specialistName, specialistLastname, specialistEmail, specialistDate, specialistGen, specialistNumber,
            specialistDescription, specialistCity, specialistCountry, specialistSocial;
    private ImageView specialistProfileImage;
    private TextView specialistBtnCancel;

    private String currentUserID, currentUserType;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private static final int GalleryPick = 1;
    private StorageReference UserProfileImagesRef;
    private ProgressDialog loadingBar;

    private ImageView ImgCalendarSpecialist;

    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    nYearIni = year;
                    nMonthIni = month;
                    nDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };

    private void colocar_fecha() {
        specialistDate.setText(nDayIni + "/" + (nMonthIni + 1) + "/" + nYearIni);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_specialist);

        sYearIni = C.get(Calendar.YEAR);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        InitializeFields();
        RetrieveSpecialistInfo();

        specialistBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UpdateAccountSettingsSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UpdateSettings();
            }
        });

        ButtonUploadPhotoSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfileSpecialistActivity.this);
            }
        });

        ImgCalendarSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

    }

    private void InitializeFields() {

        UpdateAccountSettingsSpecialist = (Button) findViewById(R.id.btn_upload_s);
        ButtonUploadPhotoSpecialist = (Button) findViewById(R.id.btn_upload_photo_s);

        specialistName = (EditText) findViewById(R.id.edt_name_s);
        specialistLastname = (EditText) findViewById(R.id.edt_lastname_s);
        specialistEmail = (EditText) findViewById(R.id.edt_email_s);
        specialistDate = (EditText) findViewById(R.id.edt_date_s);
        specialistGen = (EditText) findViewById(R.id.edt_gen_s);
        specialistNumber = (EditText) findViewById(R.id.edt_number_s);
        specialistDescription = (EditText) findViewById(R.id.edt_description_s);
        specialistCity = (EditText) findViewById(R.id.edt_city_s);
        specialistCountry = (EditText) findViewById(R.id.edt_country_s);
        specialistSocial = (EditText) findViewById(R.id.edt_social_s);

        ImgCalendarSpecialist = (ImageView) findViewById(R.id.profile_btn_img_calendar_s);
        specialistProfileImage = (ImageView) findViewById(R.id.userProfilePhotoEdit_s);
        specialistBtnCancel = (TextView) findViewById(R.id.specialist_textbutton_cancel);

        loadingBar = new ProgressDialog(this);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Actualizando foto");
                loadingBar.setMessage("Espere por favor, tu imagen de perfil se esta actualizando...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfileImagesRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditProfileSpecialistActivity.this, "Imagen de perfil actualizada correctamente...", Toast.LENGTH_SHORT).show();

                            Task<Uri> filepath = task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    final String downloaedUrl = uri.toString();

                                    RootRef.child("Users").child(currentUserID).child("image")
                                            .setValue(downloaedUrl)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(EditProfileSpecialistActivity.this, "Imagen guardada con éxito...", Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    } else {
                                                        String message = task.getException().toString();
                                                        Toast.makeText(EditProfileSpecialistActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                    }
                                                }
                                            });

                                }
                            });


                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(EditProfileSpecialistActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
    }

    private void UpdateSettings()
    {

        String setSpecialistName = specialistName.getText().toString();
        String setSpecialistLastName = specialistLastname.getText().toString();
        String setSpecialistDate = specialistDate.getText().toString();
        String setSpecialistGen = specialistGen.getText().toString();
        String setSpecialistNumber = specialistNumber.getText().toString();
        String setSpecialistDescription = specialistDescription.getText().toString();
        String setSpecialistCity = specialistCity.getText().toString();
        String setSpecialistCountry = specialistCountry.getText().toString();
        String setSpecialistSocial = specialistSocial.getText().toString();

        if (TextUtils.isEmpty(setSpecialistName) || TextUtils.isEmpty(setSpecialistLastName) | TextUtils.isEmpty(setSpecialistDate) || TextUtils.isEmpty(setSpecialistGen) ||
                TextUtils.isEmpty(setSpecialistNumber) || TextUtils.isEmpty(setSpecialistDescription) || TextUtils.isEmpty(setSpecialistCity) ||
                TextUtils.isEmpty(setSpecialistCountry) || TextUtils.isEmpty(setSpecialistSocial))
        {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", setSpecialistName);
            profileMap.put("lastName", setSpecialistLastName);
            profileMap.put("birthdate", setSpecialistDate);
            profileMap.put("sex", setSpecialistGen);
            profileMap.put("phone", setSpecialistNumber);
            profileMap.put("description", setSpecialistDescription);
            profileMap.put("city", setSpecialistCity);
            profileMap.put("country", setSpecialistCountry);
            profileMap.put("linkedin", setSpecialistSocial);
            RootRef.child("Users").child(currentUserID).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                finish();
                                Toast.makeText(EditProfileSpecialistActivity.this, "Perfil actualizado correctamente...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(EditProfileSpecialistActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void RetrieveSpecialistInfo()
    {
        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image") && (dataSnapshot.hasChild("description")))))
                        {
                            String retrieveSpecialistName = dataSnapshot.child("name").getValue().toString();
                            String retrieveSpecialistLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrieveSpecialistEmail = mAuth.getCurrentUser().getEmail();
                            String retrieveSpecialistDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveSpecialistGen = dataSnapshot.child("sex").getValue().toString();
                            String retrieveSpecialistNumber = dataSnapshot.child("phone").getValue().toString();
                            String retrieveSpecialistDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveSpecialistCity = dataSnapshot.child("city").getValue().toString();
                            String retrieveSpecialistCountry = dataSnapshot.child("country").getValue().toString();
                            String retrieveSpecialistSocial = dataSnapshot.child("linkedin").getValue().toString();
                            String retrieveSpecialistProfileImage = dataSnapshot.child("image").getValue().toString();

                            specialistName.setText(retrieveSpecialistName);
                            specialistLastname.setText(retrieveSpecialistLastName);
                            specialistEmail.setText(retrieveSpecialistEmail);
                            specialistDate.setText(retrieveSpecialistDate);
                            specialistGen.setText(retrieveSpecialistGen);
                            specialistNumber.setText(retrieveSpecialistNumber);
                            specialistDescription.setText(retrieveSpecialistDescription);
                            specialistCity.setText(retrieveSpecialistCity);
                            specialistCountry.setText(retrieveSpecialistCountry);
                            specialistSocial.setText(retrieveSpecialistSocial);
                            Picasso.get().load(retrieveSpecialistProfileImage).into(specialistProfileImage);

                        }

                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")&& (dataSnapshot.hasChild("description"))))
                        {
                            String retrieveSpecialistName = dataSnapshot.child("name").getValue().toString();
                            String retrieveSpecialistLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrieveSpecialistEmail = mAuth.getCurrentUser().getEmail();
                            String retrieveSpecialistDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveSpecialistGen = dataSnapshot.child("sex").getValue().toString();
                            String retrieveSpecialistNumber = dataSnapshot.child("phone").getValue().toString();
                            String retrieveSpecialistDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveSpecialistCity = dataSnapshot.child("city").getValue().toString();
                            String retrieveSpecialistCountry = dataSnapshot.child("country").getValue().toString();
                            String retrieveSpecialistSocial = dataSnapshot.child("linkedin").getValue().toString();

                            specialistName.setText(retrieveSpecialistName);
                            specialistLastname.setText(retrieveSpecialistLastName);
                            specialistEmail.setText(retrieveSpecialistEmail);
                            specialistDate.setText(retrieveSpecialistDate);
                            specialistGen.setText(retrieveSpecialistGen);
                            specialistNumber.setText(retrieveSpecialistNumber);
                            specialistDescription.setText(retrieveSpecialistDescription);
                            specialistCity.setText(retrieveSpecialistCity);
                            specialistCountry.setText(retrieveSpecialistCountry);
                            specialistSocial.setText(retrieveSpecialistSocial);

                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")&& (dataSnapshot.hasChild("image"))))
                        {
                            String retrieveSpecialistName = dataSnapshot.child("name").getValue().toString();
                            String retrieveSpecialistLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrieveSpecialistEmail = mAuth.getCurrentUser().getEmail();
                            String retrieveSpecialistDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveSpecialistGen = dataSnapshot.child("sex").getValue().toString();
                            String retrieveSpecialistNumber = dataSnapshot.child("phone").getValue().toString();
                            String retrieveSpecialistDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveSpecialistCity = dataSnapshot.child("city").getValue().toString();
                            String retrieveSpecialistCountry = dataSnapshot.child("country").getValue().toString();
                            String retrieveSpecialistSocial = dataSnapshot.child("linkedin").getValue().toString();
                            String retrieveSpecialistProfileImage = dataSnapshot.child("image").getValue().toString();

                            specialistName.setText(retrieveSpecialistName);
                            specialistLastname.setText(retrieveSpecialistLastName);
                            specialistEmail.setText(retrieveSpecialistEmail);
                            specialistDate.setText(retrieveSpecialistDate);
                            specialistGen.setText(retrieveSpecialistGen);
                            specialistNumber.setText(retrieveSpecialistNumber);
                            specialistDescription.setText(retrieveSpecialistDescription);
                            specialistCity.setText(retrieveSpecialistCity);
                            specialistCountry.setText(retrieveSpecialistCountry);
                            specialistSocial.setText(retrieveSpecialistSocial);
                            Picasso.get().load(retrieveSpecialistProfileImage).into(specialistProfileImage);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveSpecialistName = dataSnapshot.child("name").getValue().toString();
                            String retrieveSpecialistLastName = dataSnapshot.child("lastName").getValue().toString();
                            String retrieveSpecialistEmail = mAuth.getCurrentUser().getEmail();
                            String retrieveSpecialistDate = dataSnapshot.child("birthdate").getValue().toString();
                            String retrieveSpecialistGen = dataSnapshot.child("sex").getValue().toString();
                            String retrieveSpecialistNumber = dataSnapshot.child("phone").getValue().toString();
                            String retrieveSpecialistDescription = dataSnapshot.child("description").getValue().toString();
                            String retrieveSpecialistCity = dataSnapshot.child("city").getValue().toString();
                            String retrieveSpecialistCountry = dataSnapshot.child("country").getValue().toString();
                            String retrieveSpecialistSocial = dataSnapshot.child("linkedin").getValue().toString();

                            specialistName.setText(retrieveSpecialistName);
                            specialistLastname.setText(retrieveSpecialistLastName);
                            specialistEmail.setText(retrieveSpecialistEmail);
                            specialistDate.setText(retrieveSpecialistDate);
                            specialistGen.setText(retrieveSpecialistGen);
                            specialistNumber.setText(retrieveSpecialistNumber);
                            specialistDescription.setText(retrieveSpecialistDescription);
                            specialistCity.setText(retrieveSpecialistCity);
                            specialistCountry.setText(retrieveSpecialistCountry);
                            specialistSocial.setText(retrieveSpecialistSocial);
                        }
                        else
                        {
                            specialistName.setVisibility(View.VISIBLE);
                            Toast.makeText(EditProfileSpecialistActivity.this, "Please set & update your profile information...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}