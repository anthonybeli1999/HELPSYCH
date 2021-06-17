package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button LoginButton, PhoneLoginButton;
    private EditText UserEmail, UserPassword;
    private Button NeedNewAccountLink, ForgetPasswordLink;

    private DatabaseReference UsersRef;
    private TextView ForgotPassword;

    private ImageView BtnImgShowPassword, BtnImgHidePassword;

    String currentUserType;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.robotolight);


        InitializeFields();

        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity();
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AllowUserToLogin();
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPass = new Intent(LoginActivity.this, PopupForgotPassword.class);
                startActivity(forgotPass);
            }
        });

        BtnImgShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                BtnImgShowPassword.setVisibility(View.GONE);
                BtnImgHidePassword.setVisibility(View.VISIBLE);
                UserPassword.setTypeface(typeface);
            }
        });

        BtnImgHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                BtnImgHidePassword.setVisibility(View.GONE);
                BtnImgShowPassword.setVisibility(View.VISIBLE);
                UserPassword.setTypeface(typeface);
            }
        });
    }

    private void AllowUserToLogin()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Por favor ingresa tu correo electronico...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Por favor ingresa tu contraseña...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Ingresando");
            loadingBar.setMessage("Espere un momento...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                String currentUserId = mAuth.getCurrentUser().getUid();
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                UsersRef.child(currentUserId).child("usertype")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                currentUserType = snapshot.getValue().toString();
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                UsersRef.child(currentUserId).child("device_token")
                                        .setValue(deviceToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    switch (currentUserType)
                                                    {
                                                        case "0":
                                                            SendUserToMainActivity_a();
                                                            Toast.makeText(LoginActivity.this, "Sesión iniciada correctamente...", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                            break;
                                                        case "1":
                                                            SendUserToMainActivity_s();
                                                            Toast.makeText(LoginActivity.this, "Sesión iniciada correctamente...", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                            break;
                                                        case "2":
                                                            SendUserToMainActivity();
                                                            Toast.makeText(LoginActivity.this, "Sesión iniciada correctamente...", Toast.LENGTH_SHORT).show();
                                                            loadingBar.dismiss();
                                                            break;
                                                        default:
                                                            break;

                                                    }

                                                }
                                            }
                                        });
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToMainActivity_s()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MainSpecialistActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToMainActivity_a()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MainAdminActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToRegisterActivity()
    {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void InitializeFields()
    {
        LoginButton = (Button) findViewById(R.id.btnLogin);
        //PhoneLoginButton = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText) findViewById(R.id.txtEmail);
        UserPassword = (EditText) findViewById(R.id.txtPassword);
        NeedNewAccountLink = (Button) findViewById(R.id.btn_registro);
        //ForgetPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        ForgotPassword = (TextView) findViewById(R.id.txt_forgot_password_login);

        BtnImgShowPassword = (ImageView) findViewById(R.id.login_img_show_password);
        BtnImgHidePassword = (ImageView) findViewById(R.id.login_img_hide_password);

        loadingBar = new ProgressDialog(this);
    }
}