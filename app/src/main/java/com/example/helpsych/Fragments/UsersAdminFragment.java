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
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.AllUserActivity;
import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.Activity.LoginActivity;
import com.example.helpsych.Activity.MainActivity;
import com.example.helpsych.Activity.RegisterActivity;
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

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersAdminFragment extends Fragment {

    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword, UserName, UserLastName, UserSex, UserBirthDate, UserCountry, UserCity, UserPhone,UserLinkedin;
    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth2;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersAdminFragment newInstance(String param1, String param2) {
        UsersAdminFragment fragment = new UsersAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_users_admin, container, false);

        mAuth = FirebaseAuth.getInstance();

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://helpsych-66739-default-rtdb.firebaseio.com")
                .setApiKey("AIzaSyCCilUA5-Pu1LafwsvbyD3HBchsKE1oqTY")
                .setApplicationId("helpsych-66739").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        RootRef = FirebaseDatabase.getInstance().getReference();


        //userNameP = (TextView) v.findViewById(R.id.txt_name_p);
        CreateAccountButton = (Button) v.findViewById(R.id.btnRegister_s);
        UserEmail = (EditText) v.findViewById(R.id.txtEmail_rs);
        UserPassword = (EditText) v.findViewById(R.id.txtPassword_rs);

        UserName = (EditText) v.findViewById(R.id.txtNombre_rs);
        UserLastName = (EditText) v.findViewById(R.id.txtApellido_rs);
        UserSex = (EditText) v.findViewById(R.id.txtSexo_rs);

        UserBirthDate = (EditText) v.findViewById(R.id.txtFechaNacimiento_rs);

        UserPhone = (EditText) v.findViewById(R.id.txt_phone_rs);
        UserCountry = (EditText) v.findViewById(R.id.txt_country_rs);
        UserCity = (EditText) v.findViewById(R.id.txt_city_rs);
        UserLinkedin = (EditText) v.findViewById(R.id.txt_linkedin_rs);

        loadingBar = new ProgressDialog(getContext());


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount();
            }
        });

        return v;
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
            Toast.makeText(getContext(), "Por favor ingrese un correo electrónico...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(getContext(), "Por favor ingrese una contraseña...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creando una cuenta nueva");
            loadingBar.setMessage("Por favor espere mientras registramos al usuario...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth2.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                String currentUserID = mAuth2.getCurrentUser().getUid();
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

                                RootRef.child("Users").child(currentUserID).child("city")
                                        .setValue(userCity);

                                RootRef.child("Users").child(currentUserID).child("linkedin")
                                        .setValue(userLinkedin);



                                RootRef.child("Users").child(currentUserID).child("usertype")
                                        .setValue("1");

                                Toast.makeText(getContext(), "El usuario se ha registrado correctamente...", Toast.LENGTH_SHORT).show();
                                ClearEditBoxes();
                                mAuth2.signOut();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(getContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void ClearEditBoxes()
    {
        UserEmail.setText("");
        UserPassword.setText("");

        UserName.setText("");
        UserLastName.setText("");
        UserSex.setText("");
        UserBirthDate.setText("");
        UserLinkedin.setText("");

        UserPhone.setText("");
        UserCity.setText("");
        UserCountry.setText("");
    }
}