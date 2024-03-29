package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword, UserName, UserLastName, UserBirthDate;
    private TextView AlreadyHaveAccountLink;
    private ImageView Btncalendardate;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;
    String formattedDate ="";

    private ImageView ShowPass, HidePass;

    private LinearLayout LinearOtherGen;
    private EditText UserOtherGen;

    private Spinner UserSex;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        InitializeFields();

        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.robotolight);

        sYearIni = C.get(Calendar.YEAR);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);

        ArrayAdapter<CharSequence> adapterFormat = ArrayAdapter.createFromResource(this, R.array.login_sex, R.layout.support_simple_spinner_dropdown_item);
        adapterFormat.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        UserSex.setAdapter(adapterFormat);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });

        Btncalendardate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

        UserSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        LinearOtherGen.setVisibility(View.GONE);
                        break;
                    case 1:
                        LinearOtherGen.setVisibility(View.GONE);
                        break;
                    case 2:
                        LinearOtherGen.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        String userBirthDay = UserBirthDate.getText().toString();

        String userSex;

        if(LinearOtherGen.getVisibility() == View.VISIBLE){
            userSex = UserOtherGen.getText().toString();
        }
        else{
            userSex = UserSex.getSelectedItem().toString();
        }


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

                                //FirebaseUser user = mAuth.getCurrentUser();
                                //user.sendEmailVerification();
                                SendUserToMainActivity();
                                Toast.makeText(RegisterActivity.this, "Cuenta creada satisfactoriamente...", Toast.LENGTH_SHORT).show();
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
        UserSex = (Spinner) findViewById(R.id.login_cmb_sex);
        UserBirthDate = (EditText) findViewById(R.id.txtFechaNacimiento);
        Btncalendardate = (ImageView) findViewById(R.id.register_btn_img_calendar);

        LinearOtherGen = (LinearLayout) findViewById(R.id.linear_otherger_register) ;
        UserOtherGen = (EditText) findViewById(R.id.txtOtherGen);

        ShowPass = (ImageView) findViewById(R.id.img_show_register_s);
        HidePass = (ImageView) findViewById(R.id.img_hide_register_s);

        loadingBar = new ProgressDialog(this);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}