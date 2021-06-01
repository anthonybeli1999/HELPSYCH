package com.example.helpsych.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import java.security.acl.Group;

public class PopupTestActivity extends AppCompatActivity {

    TextView TitleTest, NQuestionTest;
    TextView QuestionTest;
    RadioButton AnswerNever, AnswerSometimes, AnswerMedium, AnswerEver;
    Button BtnPrevious, BtnNext;
    RadioGroup Answers;
    int nota = 0;
    int NPregunta = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_test);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.5));

        InitializeFields();

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
                        Toast.makeText(this, "Limites normales - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 50 && nota < 60){
                        Toast.makeText(this, "Ansiedad leve moderada - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 60 && nota < 70){
                        Toast.makeText(this, "Ansiedad moderada a intensa - "+ NPregunta +"Nota: "+nota , Toast.LENGTH_SHORT).show();
                    } else if (nota >= 70){
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
}