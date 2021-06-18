package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterSpecialist extends AppCompatActivity {

    private Button CreateAccountButton, CancelarCreate;
    private EditText UserEmail, UserPassword, UserName, UserLastName, UserSex, UserBirthDate, UserCountry, UserCity, UserPhone,UserLinkedin;
    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth2;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;

    private ImageView ShowPass, HidePass;
    private ImageView CalendarImg;

    String formattedDate ="";

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
        UserBirthDate.setText(nDayIni + "/" + (nMonthIni + 1) + "/" + nYearIni);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_specialist);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.robotolight);

        sYearIni = C.get(Calendar.YEAR);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);

        //userNameP = (TextView) v.findViewById(R.id.txt_name_p);
        CreateAccountButton = (Button) findViewById(R.id.btnRegister_s);
        CancelarCreate = (Button) findViewById(R.id.btnCancel_s);
        UserEmail = (EditText) findViewById(R.id.txtEmail_rs);
        UserPassword = (EditText) findViewById(R.id.txtPassword_rs);

        UserName = (EditText) findViewById(R.id.txtNombre_rs);
        UserLastName = (EditText) findViewById(R.id.txtApellido_rs);
        UserSex = (EditText) findViewById(R.id.txtSexo_rs);

        UserBirthDate = (EditText) findViewById(R.id.txtFechaNacimiento_rs);

        UserPhone = (EditText) findViewById(R.id.txt_phone_rs);
        UserCountry = (EditText) findViewById(R.id.txt_country_rs);
        UserCity = (EditText) findViewById(R.id.txt_city_rs);
        UserLinkedin = (EditText) findViewById(R.id.txt_linkedin_rs);

        ShowPass = (ImageView) findViewById(R.id.img_show_register_s);
        HidePass = (ImageView) findViewById(R.id.img_hide_register_s);

        loadingBar = new ProgressDialog(this);

        CalendarImg = (ImageView) findViewById(R.id.register_s_btn_img_calendar);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });

        CancelarCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                ShowPass.setVisibility(View.GONE);
                HidePass.setVisibility(View.VISIBLE);
                UserPassword.setTypeface(typeface);
            }
        });

        HidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                HidePass.setVisibility(View.GONE);
                ShowPass.setVisibility(View.VISIBLE);
                UserPassword.setTypeface(typeface);
            }
        });

        CalendarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }


    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        String userName = UserName.getText().toString();
        String userLastName = UserLastName.getText().toString();
        String userSex = UserSex.getText().toString();
        String userBirthDay = UserBirthDate.getText().toString();
        String userLinkedin = UserLinkedin.getText().toString();

        String userPhone = UserPhone.getText().toString();
        String userCity = UserCity.getText().toString();
        String userCountry = UserCountry.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Por favor ingrese un correo electrónico...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Por favor ingrese una contraseña...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creando cuenta de especialista");
            loadingBar.setMessage("Espera un momento, estamos creando la nueva cuenta...");
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

                                RootRef.child("Users").child(currentUserID).child("phone")
                                        .setValue(userPhone);

                                RootRef.child("Users").child(currentUserID).child("country")
                                        .setValue(userCountry);

                                RootRef.child("Users").child(currentUserID).child("description")
                                        .setValue("");

                                RootRef.child("Users").child(currentUserID).child("city")
                                        .setValue(userCity);

                                RootRef.child("Users").child(currentUserID).child("linkedin")
                                        .setValue(userLinkedin);

                                RootRef.child("Users").child(currentUserID).child("registrationDay")
                                        .setValue(formattedDate);

                                RootRef.child("Users").child(currentUserID).child("usertype")
                                        .setValue("1");

                                Toast.makeText(getApplicationContext(), "El usuario especialista se ha registrado correctamente...", Toast.LENGTH_SHORT).show();
                                finish();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

}