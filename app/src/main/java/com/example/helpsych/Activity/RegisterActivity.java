package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword, UserName, UserLastName, UserSex, UserBirthDate;
    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;
    String formattedDate ="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();


        InitializeFields();


        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendUserToLoginActivity();
            }
        });


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });
    }




    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        String userName = UserName.getText().toString();
        String userLastName = UserLastName.getText().toString();
        String userSex = UserSex.getText().toString();
        String userBirthDay = UserBirthDate.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait, while we wre creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                String currentUserID = mAuth.getCurrentUser().getUid();
                                RootRef.child("Users").child(currentUserID).setValue("");


                                RootRef.child("Users").child(currentUserID).child("device_token")
                                        .setValue(deviceToken);

                                RootRef.child("Users").child(currentUserID).child("name")
                                        .setValue(userName);

                                RootRef.child("Users").child(currentUserID).child("lastName")
                                        .setValue(userLastName);

                                RootRef.child("Users").child(currentUserID).child("sex")
                                        .setValue(userSex);

                                RootRef.child("Users").child(currentUserID).child("birthdate")
                                        .setValue(userBirthDay);

                                RootRef.child("Users").child(currentUserID).child("registrationDay")
                                        .setValue(formattedDate);

                                //Tipo de usuario
                                //Administrator = 0
                                //Specialist = 1
                                //Patient = 2
                                RootRef.child("Users").child(currentUserID).child("usertype")
                                        .setValue("2");

                                SendUserToMainActivity();
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }




    private void InitializeFields()
    {
        CreateAccountButton = (Button) findViewById(R.id.btnRegister);
        UserEmail = (EditText) findViewById(R.id.txtEmail);
        UserPassword = (EditText) findViewById(R.id.txtPassword);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.txtCancelar);

        UserName = (EditText) findViewById(R.id.txtNombre);
        UserLastName = (EditText) findViewById(R.id.txtApellido);
        UserSex = (EditText) findViewById(R.id.txtSexo);
        UserBirthDate = (EditText) findViewById(R.id.txtFechaNacimiento);

        loadingBar = new ProgressDialog(this);
    }


    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}