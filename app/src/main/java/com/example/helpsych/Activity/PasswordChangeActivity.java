package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    private TextView txvForgotPassword, txvGoBack;

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

        txvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToProfileFragment();
            }
        });

        txvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ForgotPassword();
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UpdatePassword();
            }
        });

    }

    private  void ForgotPassword()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Reinicio de contraseña");
        dialogo1.setMessage("Se le enviará un correo electrónico a: " + retrievesEmail + " con los pasos para recuperar su contraseña.");
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(retrievesEmail)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PasswordChangeActivity.this, "Se le ha enviado un correo electrónico para recuperar su contraseña.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PasswordChangeActivity.this, "Ha ocurrido un error, inténtelo más tarde", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        dialogo1.show();


    }

    private void InitializeFields() {

        txvGoBack = (TextView) findViewById(R.id.txtCancelar_pc);
        ChangePassword = (Button) findViewById(R.id.btnChangePassword);
        txvForgotPassword = (TextView) findViewById(R.id.txt_ForgotPassword_pc);
        edtOldPassword = (EditText) findViewById(R.id.txvOldPassword);
        edtNewPassword1 = (EditText) findViewById(R.id.txvNewPassword1);
        edtNewPassword2 = (EditText) findViewById(R.id.txvNewPassword2);
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