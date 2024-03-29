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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.Psychological_approach;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PopupAddArticle extends AppCompatActivity {

    String UAID = UUID.randomUUID().toString();
    String downloaedUrl = "";

    EditText EdtAddArticleTile, EdtAddArticleBody;
    Button BtnAddArticleImg, BtnAddArticleSave;
    Spinner CmbAddApproach;

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

        getWindow().setLayout((int)(width*0.9), (int)(height*0.8));

        RootRef = FirebaseDatabase.getInstance().getReference();
        ArticleRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticleImagesRef = FirebaseStorage.getInstance().getReference().child("Article Images");

        InitializeFields();

        loadingBar = new ProgressDialog(this.getApplicationContext());

        //ArrayAdapter<CharSequence> adapterFormat = ArrayAdapter.createFromResource(this, R.array.article_add_approach_string, R.layout.support_simple_spinner_dropdown_item);
        //adapterFormat.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //CmbAddApproach = findViewById(R.id.spinnerApproaches);

        loadApproaches();



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

    private void loadApproaches()
    {
        List<Psychological_approach> approaches= new ArrayList();
        RootRef.child("psyapproach").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        String ApproachId = ds.child("p_approachId").getValue().toString();
                        String ApproachName = ds.child("p_approachName").getValue().toString();
                        approaches.add(new Psychological_approach(ApproachId,ApproachName));
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter( getApplicationContext(), android.R.layout.simple_dropdown_item_1line,approaches);
                    CmbAddApproach.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        String ArticleApproach = CmbAddApproach.getSelectedItem().toString();
        String UID = UAID;

        if (TextUtils.isEmpty(ArticleTitle) || TextUtils.isEmpty(ArticleBody) || TextUtils.isEmpty(ArticleApproach) || downloaedUrl.equals("")) {
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
            ar.setState("0");
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
        CmbAddApproach = (Spinner) findViewById(R.id.article_add_approach);

        BtnAddArticleImg = (Button) findViewById(R.id.article_add_upload_image);
        BtnAddArticleSave = (Button) findViewById(R.id.article_add_save);
    }
}