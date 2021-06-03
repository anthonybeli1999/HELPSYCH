package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helpsych.Model.Article;
import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class PopupAddArticle extends AppCompatActivity {

    String UAID = UUID.randomUUID().toString();
    String downloaedUrl = "";

    EditText EdtAddArticleTile, EdtAddArticleBody, EdtAddApproach;
    Button BtnAddArticleImg, BtnAddArticleSave;

    private ProgressDialog loadingBar;

    DatabaseReference ArticleRef, RootRef;
    StorageReference ArticleImagesRef;

    private static final int GalleryPick = 1;
    String formattedDate ="";
    FirebaseAuth mAuth;
    String senderUserID = "";
    String nameUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_add_article);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.9));

        RootRef = FirebaseDatabase.getInstance().getReference();
        ArticleRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticleImagesRef = FirebaseStorage.getInstance().getReference().child("Article Images");

        InitializeFields();

        loadingBar = new ProgressDialog(this.getApplicationContext());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        mAuth = FirebaseAuth.getInstance();
        senderUserID = mAuth.getCurrentUser().getUid();

        RetrieveUserInfo();

        BtnAddArticleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewArticle_m();
            }
        });

        BtnAddArticleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });

    }

    private void RetrieveUserInfo()
    {
        RootRef.child("Users").child(senderUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                        nameUser = retrieveUserName;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void AddNewArticle_m() {
        String ArticleTitle = EdtAddArticleTile.getText().toString();
        String ArticleBody = EdtAddArticleBody.getText().toString();
        String ArticleApproach = EdtAddApproach.getText().toString();
        String UID = UAID;

        if (TextUtils.isEmpty(ArticleTitle) || TextUtils.isEmpty(ArticleBody) || TextUtils.isEmpty(ArticleApproach) || downloaedUrl == null) {
            Toast.makeText(getApplicationContext(), "Por favor llene todos los campos...", Toast.LENGTH_SHORT).show();
        } else {
            Article ar = new Article();
            ar.setId(UID);
            ar.setTitle(ArticleTitle);
            ar.setBody(ArticleBody);
            ar.setApproach(ArticleApproach);
            ar.setDate(formattedDate);
            ar.setCreationdate(formattedDate);
            ar.setImage(downloaedUrl);
            ar.setAuthor(nameUser);
            RootRef.child("Article").child(ar.getId()).setValue(ar);
            finish();
            Toast.makeText(getApplicationContext(), "Artículo creado satisfactoriamente...", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            Uri ImageUri = data.getData();
            StorageReference filePath = ArticleImagesRef.child(UAID + ".jpg");

            filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(PopupAddArticle.this, "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                        Task<Uri> filepath = task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                downloaedUrl = uri.toString();

                            }
                        });
                    }
                    else
                    {
                        String message = task.getException().toString();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }


    private void InitializeFields(){
        EdtAddArticleTile = (EditText) findViewById(R.id.article_add_title);
        EdtAddArticleBody = (EditText) findViewById(R.id.article_add_body);
        EdtAddApproach = (EditText) findViewById(R.id.article_add_approach);

        BtnAddArticleImg = (Button) findViewById(R.id.article_add_upload_image);
        BtnAddArticleSave = (Button) findViewById(R.id.article_add_save);
    }
}