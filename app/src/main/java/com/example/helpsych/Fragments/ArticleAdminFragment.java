package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.ArticleDetails;
import com.example.helpsych.Activity.PopupAddArticle;
import com.example.helpsych.Activity.RequestArticleAdminActivity;
import com.example.helpsych.Model.Article;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleAdminFragment extends Fragment {

    private EditText Article_title, Article_subtitle ,Article_body, Article_date, Article_author, Article_label;

    ImageView ArticleImage;
    private Button AddNewImageArticle;
    private FloatingActionButton RequestNewArticle;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;

    private DatabaseReference ArticleRef;

    private RecyclerView ArticlesRecyclerList;
    private DatabaseReference ArticlesRef;

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_article_admin, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        ArticleRef = FirebaseDatabase.getInstance().getReference().child("Article");
        RequestNewArticle = (FloatingActionButton) rootView.findViewById(R.id.article_btn_admin_request_article);

        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticlesRecyclerList = rootView.findViewById(R.id.article_recycler_list);

        RootRef = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(ArticlesRef.orderByChild("state").equalTo("1"), Article.class)
                        .build();

        FirebaseRecyclerAdapter<Article, ArticleAdminFragment.ArticlesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Article, ArticleAdminFragment.ArticlesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ArticleAdminFragment.ArticlesViewHolder holder, final int position, @NonNull Article model)
                    {
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.article).into(holder.article_image);
                        holder.article_approach.setText(model.getApproach());
                        holder.article_title.setText(model.getTitle());
                        holder.article_date.setText(model.getDate());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String visit_article_id = getRef(position).getKey();
                                Intent profileIntent = new Intent(getContext(), ArticleDetails.class);
                                profileIntent.putExtra("id_article", visit_article_id);
                                startActivity(profileIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ArticleAdminFragment.ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_display_layout, viewGroup, false);
                        ArticleAdminFragment.ArticlesViewHolder viewHolder = new ArticleAdminFragment.ArticlesViewHolder(view);
                        return viewHolder;
                    }
                };

        ArticlesRecyclerList.setLayoutManager(new GridLayoutManager(getContext(),1));
        ArticlesRecyclerList.setAdapter(adapter);

        adapter.startListening();


        RequestNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RequestArticleAdminActivity.class);
                startActivity(intent);
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);
        return rootView;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder
    {
        TextView article_approach;
        TextView article_title;
        TextView article_date;
        ImageView article_image;

        public ArticlesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            article_approach = itemView.findViewById(R.id.txt_article_psyappoach);
            article_title = itemView.findViewById(R.id.txt_article_title);
            article_date = itemView.findViewById(R.id.txt_article_date);
            article_image = itemView.findViewById(R.id.img_article_image);
        }
    }
}
