package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PopupTestActivity extends AppCompatActivity {

    TextView TitleTest, NQuestionTest;
    TextView QuestionTest;
    RadioButton AnswerNever, AnswerSometimes, AnswerMedium, AnswerEver;
    Button BtnPrevious, BtnNext;
    RadioGroup Answers;
    int nota = 0;
    int NPregunta = 1;

    private DatabaseReference UsersRef;
    ArrayList listaUsuarios;
    private Random randomGenerator;
    private DatabaseReference ChatRequestRef, NotificationRef, ContactsRef;
    private FirebaseAuth mAuth;
    private String senderUserID, receiverUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_test);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.5));

        InitializeFields();

        ChatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        senderUserID = mAuth.getCurrentUser().getUid();
        NotificationRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next(v);
            }
        });

        BtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Next (View v){
        //Ninguna opcion seleccionada
        if (!AnswerNever.isChecked() && !AnswerSometimes.isChecked() && !AnswerMedium.isChecked() && !AnswerEver.isChecked()){
            Toast.makeText(this, "Ninguna opción seleccionada", Toast.LENGTH_SHORT).show();
        }
        else {
            switch (NPregunta){
                case 1:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me siento atemorizado(a) sin motivo.");

                    Answers.clearCheck();

                    BtnPrevious.setVisibility(View.GONE);

                    break;
                case 2:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me altero o agito con rapidez.");

                    Answers.clearCheck();
                    break;
                case 3:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me siento hecho(a) pedazos.");

                    Answers.clearCheck();

                    break;
                case 4:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Creo que todo está bien y que no va a pasar nada malo.");

                    Answers.clearCheck();

                    break;
                case 5:
                    if(AnswerNever.isChecked()){
                        nota = nota + 4;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 1;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me tiemblan las manos, los brazos y las piernas.");

                    Answers.clearCheck();

                    break;
                case 6:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Sufro dolores de cabeza, de cuello y de la espalda.");

                    Answers.clearCheck();

                    break;
                case 7:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me siento débil y me canso fácilmente.");

                    Answers.clearCheck();

                    break;
                case 8:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me siento tranquilo(a) y me es fácil estarme quieto(a).");

                    Answers.clearCheck();
                    break;
                case 9:
                    if(AnswerNever.isChecked()){
                        nota = nota + 4;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 1;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Siento que el corazón me late aprisa.");

                    Answers.clearCheck();
                    break;
                case 10:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Sufro mareos (vértigos).");

                    Answers.clearCheck();
                    break;
                case 11:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Me desmayo o siento que voy a desmayarme.");

                    Answers.clearCheck();
                    break;
                case 12:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Puedo respirar fácilmente.");

                    Answers.clearCheck();
                    break;
                case 13:
                    if(AnswerNever.isChecked()){
                        nota = nota + 4;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 1;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Se me duermen y me hormiguean los dedos de las manos y de los pies.");

                    Answers.clearCheck();
                    break;
                case 14:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Sufro dolores de estómago e indigestión.");

                    Answers.clearCheck();
                    break;
                case 15:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Tengo que orinar con mucha frecuencia.");

                    Answers.clearCheck();
                    break;
                case 16:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Por lo general tengo las manos secas y calientes.");

                    Answers.clearCheck();
                    break;
                case 17:
                    if(AnswerNever.isChecked()){
                        nota = nota + 4;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 1;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("La cara se me pone caliente y roja.");

                    Answers.clearCheck();
                    break;
                case 18:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Duermo fácilmente y descanso bien por las noches.");

                    Answers.clearCheck();
                    break;
                case 19:
                    if(AnswerNever.isChecked()){
                        nota = nota + 4;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 1;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");
                    QuestionTest.setText("Tengo pesadillas.");

                    Answers.clearCheck();
                    break;
                case 20:
                    if(AnswerNever.isChecked()){
                        nota = nota + 1;
                    }
                    else if(AnswerSometimes.isChecked()){
                        nota = nota + 2;
                    }
                    else if(AnswerMedium.isChecked()){
                        nota = nota + 3;
                    }
                    else if(AnswerEver.isChecked()){
                        nota = nota + 4;
                    }
                    //Numero de pregunta
                    NPregunta = NPregunta + 1;

                    NQuestionTest.setText("Pregunta "+ NPregunta + ":");

                    Answers.clearCheck();

                    if(nota < 50){
                        SendChatRequest();
                        Toast.makeText(this, "Limites normales - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 50 && nota < 60){
                        SendChatRequest();
                        Toast.makeText(this, "Ansiedad leve moderada - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 60 && nota < 70){
                        SendChatRequest();
                        Toast.makeText(this, "Ansiedad moderada a intensa - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 70){
                        SendChatRequest();
                        Toast.makeText(this, "Ansiedad intensa - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void InitializeFields() {
        TitleTest = (TextView) findViewById(R.id.test_title);
        NQuestionTest = (TextView) findViewById(R.id.test_question_number);
        QuestionTest = (TextView) findViewById(R.id.test_question);

        Answers = (RadioGroup) findViewById(R.id.test_questions);

        AnswerNever = (RadioButton) findViewById(R.id.test_answer_never);
        AnswerSometimes = (RadioButton) findViewById(R.id.test_answer_sometimes);
        AnswerMedium = (RadioButton) findViewById(R.id.test_answer_medium);
        AnswerEver = (RadioButton) findViewById(R.id.test_answer_ever);

        BtnPrevious = (Button) findViewById(R.id.test_btn_previous);
        BtnNext = (Button) findViewById(R.id.test_btn_next);
    }

    private void SendChatRequest()
    {
        //UsersRef.orderByChild("usertype").equalTo("1")
        //private DatabaseReference UsersRef;
        //UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        UsersRef.orderByChild("usertype").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaUsuarios = new ArrayList<String>();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String id_usuario = snapshot.getKey();
                    listaUsuarios.add(id_usuario);

                }
                randomGenerator = new Random();
                int index = randomGenerator.nextInt(listaUsuarios.size());
                receiverUserID = String.valueOf(listaUsuarios.get(index));


                ContactsRef.child(senderUserID)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                final String[] retImage = {"default_image"};

                                if (dataSnapshot.hasChild(receiverUserID))
                                {
                                    UsersRef.child(receiverUserID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot)
                                        {
                                            if (dataSnapshot.exists())
                                            {
                                                if (dataSnapshot.hasChild("image"))
                                                {
                                                    retImage[0] = dataSnapshot.child("image").getValue().toString();
                                                }

                                                final String retName = dataSnapshot.child("name").getValue().toString();


                                                Intent chatIntent = new Intent(PopupTestActivity.this, ChatActivity.class);
                                                chatIntent.putExtra("visit_user_id", receiverUserID);
                                                chatIntent.putExtra("visit_user_name", retName);
                                                chatIntent.putExtra("visit_image", retImage[0]);
                                                startActivity(chatIntent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                                else
                                {
                                    ChatRequestRef.child(senderUserID).child(receiverUserID)
                                            .child("request_type").setValue("sent")
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        ChatRequestRef.child(receiverUserID).child(senderUserID)
                                                                .child("request_type").setValue("received")
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task)
                                                                    {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            HashMap<String, String> chatNotificationMap = new HashMap<>();
                                                                            chatNotificationMap.put("from", senderUserID);
                                                                            chatNotificationMap.put("type", "Nueva solicitud de chat");
                                                                            chatNotificationMap.put("message", " le ha mandado una solicitud de chat.");

                                                                            NotificationRef.child(receiverUserID).push()
                                                                                    .setValue(chatNotificationMap)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task)
                                                                                        {
                                                                                            if (task.isSuccessful())
                                                                                            {
                                                                                                Toast.makeText(PopupTestActivity.this, "Notification send succesfuly...", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}