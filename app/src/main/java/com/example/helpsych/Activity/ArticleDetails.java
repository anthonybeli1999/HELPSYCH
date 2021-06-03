package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ArticleDetails extends AppCompatActivity {

    ImageView like, unlike;
    int state = 0;
    String uidArticle;

    TextView TxttilteArticle, TxtdateArticle, TxtbodyArticle, TxtapproachArticle, TxtauthorArticle;
    ImageView imgArticle;

    private DatabaseReference ArticleRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        mAuth = FirebaseAuth.getInstance();
        ArticleRef = FirebaseDatabase.getInstance().getReference().child("Article");

        InitializeFields();

        Intent intent = getIntent();
        uidArticle = intent.getStringExtra("id_article");

        //Toast.makeText(getApplicationContext(), uidArticle, Toast.LENGTH_SHORT).show();

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislike(unlike);
                like.setVisibility(View.GONE);
                unlike.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Dislike",Toast.LENGTH_SHORT).show();
                state = 0;
            }
        });

        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == 0){
                    like(like);
                    like.setVisibility(View.VISIBLE);
                    unlike.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Like",Toast.LENGTH_SHORT).show();
                    state = 1;
                }
            }
        });

        InitializeFields();
        RetrieveUserInfo();

    }

    private void RetrieveUserInfo() {
        ArticleRef.child(uidArticle).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((dataSnapshot.exists())  &&  (dataSnapshot.hasChild("image")))
                {
                    String dateArticle = dataSnapshot.child("date").getValue().toString();
                    String titleArticle = dataSnapshot.child("title").getValue().toString();
                    String bodyArticle = dataSnapshot.child("body").getValue().toString();
                    String approachArticle = dataSnapshot.child("approach").getValue().toString();
                    String authorArticle = dataSnapshot.child("author").getValue().toString();
                    String imageArticle = dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(imageArticle).placeholder(R.drawable.article).into(imgArticle);

                    TxtdateArticle.setText(dateArticle);
                    TxttilteArticle.setText(titleArticle);
                    TxtbodyArticle.setText(bodyArticle);
                    TxtapproachArticle.setText(approachArticle);
                    TxtauthorArticle.setText(authorArticle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static Animation prepareAnimationn (Animation animation){
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    public static void dislike(final ImageView view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimationn(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimationn(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    public static void like(final ImageView view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    private static Animation prepareAnimation (Animation animation){
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    private void InitializeFields()
    {
        like = (ImageView) findViewById(R.id.article_detail_like);
        unlike = (ImageView) findViewById(R.id.article_detail_unlike);
        imgArticle = (ImageView) findViewById(R.id.img_article_detail_image);
        TxttilteArticle = (TextView) findViewById(R.id.txt_detail_article_title);
        TxtdateArticle = (TextView) findViewById(R.id.txt_detail_article_date);
        TxtbodyArticle = (TextView) findViewById(R.id.txt_detail_article_body);
        TxtapproachArticle = (TextView) findViewById(R.id.txt_detail_article_psyapproach);
        TxtauthorArticle = (TextView) findViewById(R.id.txt_detail_article_author);
    }
}