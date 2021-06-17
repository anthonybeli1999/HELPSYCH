package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PopupForgotPassword extends AppCompatActivity {

    private Button BtnSendPasswordEmail;
    private EditText EdtEmailForgotPassword;
    private TextView TxtCancelForgotPassword;

    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_forgot_password);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int) (width * 0.85), (int) (height * 0.45));

        InitializeFields();

        BtnSendPasswordEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword();


            }
        });

        TxtCancelForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private  void ForgotPassword()
    {
        Email = EdtEmailForgotPassword.getText().toString();

        if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();
        }

        else {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle("Reinicio de contraseña");
            dialogo1.setMessage("Se le enviará un correo electrónico a: " + Email + " con los pasos para recuperar su contraseña.");
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    auth.sendPasswordResetEmail(Email)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Se le ha enviado un correo electrónico para recuperar su contraseña.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error, inténtelo más tarde", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
            dialogo1.show();
        }
    }

    private void InitializeFields() {

        BtnSendPasswordEmail = (Button) findViewById(R.id.btn_send_email_forgot_password);
        EdtEmailForgotPassword = (EditText) findViewById(R.id.edt_Email_forgot_password);
        TxtCancelForgotPassword = (TextView) findViewById(R.id.txtCancelar_forgot_password);
    }
}