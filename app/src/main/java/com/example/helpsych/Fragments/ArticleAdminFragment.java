package com.example.helpsych.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.helpsych.Activity.PreviewArticleActivity;
import com.example.helpsych.Model.Article;
import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleAdminFragment extends Fragment {

    private EditText Article_title, Article_subtitle ,Article_body, Article_date, Article_author, Article_label;

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

        Article_title = (EditText) v.findViewById(R.id.edt_article_title);
        Article_subtitle = (EditText) v.findViewById(R.id.edt_article_subtitle);
        Article_body = (EditText) v.findViewById(R.id.edt_article_body);
        Article_date = (EditText) v.findViewById(R.id.edt_article_date);
        Article_author = (EditText) v.findViewById(R.id.edt_article_author);
        Article_label = (EditText) v.findViewById(R.id.edt_article_label);

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
        String ArticleSubtitle = Article_subtitle.getText().toString();
        String ArticleBody = Article_body.getText().toString();
        String ArticleDate = Article_date.getText().toString();
        String ArticleAuthor = Article_author.getText().toString();
        String ArticleLabel = Article_label.getText().toString();

        String UID = UUID.randomUUID().toString();

        if (TextUtils.isEmpty(ArticleTitle)) {
            Toast.makeText(getContext(), "Por favor ingrese un título...", Toast.LENGTH_SHORT).show();
        } else {
            Article ar = new Article();
            ar.setId(UID);
            ar.setTitle(ArticleTitle);
            ar.setSubtitle(ArticleSubtitle);
            ar.setBody(ArticleBody);
            ar.setDate(ArticleDate);
            ar.setAuthor(ArticleAuthor);
            ar.setLabel(ArticleLabel);
            RootRef.child("Article").child(ar.getId()).setValue(ar);

            Toast.makeText(getContext(), "Artículo creado satisfactoriamente...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), PreviewArticleActivity.class);
            intent.putExtra("id_article", UID);
            startActivity(intent);
        }
    }
}
