package com.example.helpsych.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpsych.Activity.ArticleDetails;
import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.Messages;
import com.example.helpsych.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<Article> ArticleList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private Context mContext;

    public ArticleAdapter (Context context,List<Article> ArticleList)
    {
        this.mContext = context;
        this.ArticleList = ArticleList;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public static ImageView article_image;
        public TextView article_approach;
        public TextView article_title;
        public TextView article_date;

        public ArticleViewHolder(@NonNull View itemView)
        {
            super(itemView);
            article_approach = itemView.findViewById(R.id.txt_article_psyappoach);
            article_title = itemView.findViewById(R.id.txt_article_title);
            article_date = itemView.findViewById(R.id.txt_article_date);
            article_image = itemView.findViewById(R.id.img_article_image);
        }
    }

    @NonNull
    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_display_layout, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new ArticleViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ArticleViewHolder holder, int position) {


        Article articles = ArticleList.get(position);

        String ArticleID = articles.getId();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Article").child(ArticleID);

        Picasso.get().load(articles.getImage()).placeholder(R.drawable.article).into(ArticleViewHolder.article_image);
        holder.article_approach.setText(articles.getApproach());
        holder.article_title.setText(articles.getTitle());
        holder.article_date.setText(articles.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String visit_article_id = (articles.getId());
                Intent profileIntent = new Intent(mContext, ArticleDetails.class);
                profileIntent.putExtra("id_article", visit_article_id);
                mContext.startActivity(profileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ArticleList.size();
    }


}
