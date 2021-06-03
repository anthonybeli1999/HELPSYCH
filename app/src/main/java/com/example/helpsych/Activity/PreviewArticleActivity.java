package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class PreviewArticleActivity extends AppCompatActivity {

    private DatabaseReference RootRef;

    private FirebaseAuth mAuth;

    private Button UpdateAccountSettings;
    private TextView ArticleTitle, ArticleSubtitle, ArticleBody, ArticleDate, ArticleAuthor, ArticleLabel;

    private ImageView ArticleProfileImage;
    private Button AddImageArticle, SaveArticle;
    private ProgressDialog loadingBar;

    private StorageReference ArticleImagesRef;
    private static final int GalleryPick = 1;

    String id_article ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_article);

        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ArticleImagesRef = FirebaseStorage.getInstance().getReference().child("Article Images");

        id_article = getIntent().getStringExtra("id_article");

        ArticleProfileImage = findViewById(R.id.image_articleImage);

        ArticleTitle = (TextView) findViewById(R.id.txt_article_title_l);
        ArticleSubtitle = (TextView) findViewById(R.id.txt_article_subtitle_l);
        ArticleBody = (TextView) findViewById(R.id.txt_article_body_l);
        ArticleDate = (TextView) findViewById(R.id.txt_article_date_l);
        ArticleAuthor = (TextView) findViewById(R.id.txt_article_author_l);
        ArticleLabel = (TextView) findViewById(R.id.txt_article_label_l);

        loadingBar = new ProgressDialog(this);
        AddImageArticle = (Button) findViewById(R.id.btn_add_articleImage);

        SaveArticle = (Button) findViewById(R.id.btnSaveArticle);

        AddImageArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });

        SaveArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviewArticleActivity.this, MainAdminActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            Uri ImageUri = data.getData();
            StorageReference filePath = ArticleImagesRef.child(id_article + ".jpg");

            filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(PreviewArticleActivity.this, "Profile Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                        Task<Uri> filepath = task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                final String downloaedUrl = uri.toString();


                                RootRef.child("Article").child(id_article).child("image")
                                        .setValue(downloaedUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(PreviewArticleActivity.this, "Image save in Database, Successfully...", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                                else
                                                {
                                                    String message = task.getException().toString();
                                                    Toast.makeText(PreviewArticleActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            }
                                        });

                            }
                        });



                    }
                    else
                    {
                        String message = task.getException().toString();
                        Toast.makeText(PreviewArticleActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }
}