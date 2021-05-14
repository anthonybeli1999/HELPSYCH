package com.example.helpsych.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.Activity.PreviewArticleActivity;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleAdminFragment extends Fragment {

    private EditText Article_title, Article_body;

    ImageView ArticleImage;
    private Button AddNewArticle, AddNewImageArticle;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;

    private DatabaseReference ArticleRef;

    private static final int GalleryPick = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleAdminFragment newInstance(String param1, String param2) {
        ArticleAdminFragment fragment = new ArticleAdminFragment();
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

        View v = inflater.inflate(R.layout.fragment_article_admin, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        ArticleRef = FirebaseDatabase.getInstance().getReference().child("Article");

        Article_title = (EditText) v.findViewById(R.id.txt_article_title_c);
        Article_body = (EditText) v.findViewById(R.id.txt_article_c);


        loadingBar = new ProgressDialog(this.getActivity());
        AddNewArticle = (Button) v.findViewById(R.id.btnNewArticle);

        AddNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewArticle_m();
                Article_title.setText("");
                Article_body.setText("");
            }
        });

        return v;
    }

    private void AddNewArticle_m() {
        String ArticleTitle = Article_title.getText().toString();
        String ArticleBody = Article_body.getText().toString();

        String UID = UUID.randomUUID().toString();

        if (TextUtils.isEmpty(ArticleTitle)) {
            Toast.makeText(getContext(), "Por favor ingrese un título...", Toast.LENGTH_SHORT).show();
        } else {
            Article ar = new Article();
            ar.setArticle_Id(UID);
            ar.setArticle_title(ArticleTitle);
            ar.setArticle_body(ArticleBody);
            RootRef.child("Article").child(ar.getArticle_Id()).setValue(ar);


            Toast.makeText(getContext(), "Artículo creado satisfactoriamente...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), PreviewArticleActivity.class);
            intent.putExtra("id_article", UID);
            startActivity(intent);
        }
    }
}
