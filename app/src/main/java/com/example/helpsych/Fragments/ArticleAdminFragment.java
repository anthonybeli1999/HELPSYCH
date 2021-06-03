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

import com.example.helpsych.Activity.LoginActivity;
import com.example.helpsych.Activity.PopupAddArticle;
import com.example.helpsych.Activity.PreviewArticleActivity;
import com.example.helpsych.Activity.RegisterActivity;
import com.example.helpsych.Model.Article;
import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    String formattedDate ="";

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

        AddNewArticle = (Button) v.findViewById(R.id.article_add_admin_new);

        AddNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PopupAddArticle.class);
                startActivity(intent);
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);
        return v;
    }

}
