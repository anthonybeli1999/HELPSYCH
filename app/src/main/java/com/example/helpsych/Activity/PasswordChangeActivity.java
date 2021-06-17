package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class PasswordChangeActivity extends AppCompatActivity {

    private Button ChangePassword;
    private EditText edtOldPassword, edtNewPassword1, edtNewPassword2;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView txvGoBack;

    private ImageView ImgShowOldPassword,ImgHideOldPassword;
    private ImageView ImgShowNew1Password,ImgHideNew1Password;
    private ImageView ImgShowNew2Password,ImgHideNew2Password;

    private Toolbar SettingsToolBar;
    private String currentUserID, currentUserType;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    String retrievesEmail="EMAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();
        retrievesEmail = mAuth.getCurrentUser().getEmail();

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.robotolight);

        txvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToProfileFragment();
            }
        });


        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UpdatePassword();
            }
        });

        ImgShowOldPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                ImgShowOldPassword.setVisibility(View.GONE);
                ImgHideOldPassword.setVisibility(View.VISIBLE);
                edtOldPassword.setTypeface(typeface);
            }
        });

        ImgHideOldPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ImgHideOldPassword.setVisibility(View.GONE);
                ImgShowOldPassword.setVisibility(View.VISIBLE);
                edtOldPassword.setTypeface(typeface);
            }
        });

        ImgShowNew1Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNewPassword1.setInputType(InputType.TYPE_CLASS_TEXT);
                ImgShowNew1Password.setVisibility(View.GONE);
                ImgHideNew1Password.setVisibility(View.VISIBLE);
                edtNewPassword1.setTypeface(typeface);
            }
        });

        ImgHideNew1Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNewPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ImgHideNew1Password.setVisibility(View.GONE);
                ImgShowNew1Password.setVisibility(View.VISIBLE);
                edtNewPassword1.setTypeface(typeface);
            }
        });

        ImgShowNew2Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNewPassword2.setInputType(InputType.TYPE_CLASS_TEXT);
                ImgShowNew2Password.setVisibility(View.GONE);
                ImgHideNew2Password.setVisibility(View.VISIBLE);
                edtNewPassword2.setTypeface(typeface);
            }
        });

        ImgHideNew2Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNewPassword2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ImgHideNew2Password.setVisibility(View.GONE);
                ImgShowNew2Password.setVisibility(View.VISIBLE);
                edtNewPassword2.setTypeface(typeface);
            }
        });

    }

    private void InitializeFields() {

        txvGoBack = (TextView) findViewById(R.id.txtCancelar_pc);
        ChangePassword = (Button) findViewById(R.id.btnChangePassword);
        edtOldPassword = (EditText) findViewById(R.id.txvOldPassword);
        edtNewPassword1 = (EditText) findViewById(R.id.txvNewPassword1);
        edtNewPassword2 = (EditText) findViewById(R.id.txvNewPassword2);

        ImgShowOldPassword = (ImageView) findViewById(R.id.img_show_old_password_change_password);
        ImgHideOldPassword = (ImageView) findViewById(R.id.img_hide_old_password_change_password);

        ImgShowNew1Password = (ImageView) findViewById(R.id.img_show_new1_password_change_password);
        ImgHideNew1Password = (ImageView) findViewById(R.id.img_hide_new1_password_change_password);

        ImgShowNew2Password = (ImageView) findViewById(R.id.img_show_new2_password_change_password);
        ImgHideNew2Password = (ImageView) findViewById(R.id.img_hide_new2_password_change_password);
    }

    private void SendUserToProfileFragment()
    {
        finish();
    }

    private void UpdatePassword()
    {
        String oldPassword = edtOldPassword.getText().toString();
        String newPassword1 = edtNewPassword1.getText().toString();
        String newPassword2 = edtNewPassword2.getText().toString();

        if(TextUtils.isEmpty(newPassword1) || TextUtils.isEmpty(newPassword2) || TextUtils.isEmpty(oldPassword))
        {
            Toast.makeText(PasswordChangeActivity.this, "Debe rellenar todos los campos...", Toast.LENGTH_SHORT).show();
        }
        else {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(retrievesEmail, oldPassword);
            if (newPassword1.equals(newPassword2)) {
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.updatePassword(newPassword1)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(PasswordChangeActivity.this, "Su contraseña se ha actualizado...", Toast.LENGTH_SHORT).show();
                                                    SendUserToProfileFragment();
                                                }
                                            }
                                        });
                            }
                        });

                user.reauthenticate(credential).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PasswordChangeActivity.this, "La contraseña antigua no es correcta...", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(PasswordChangeActivity.this, "Las nuevas contraseñas no coinciden...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}