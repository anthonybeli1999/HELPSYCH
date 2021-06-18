package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Fragments.ArticleFragment;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RequestArticleAdminActivity extends AppCompatActivity {

    private RecyclerView ArticlesRecyclerList;
    private DatabaseReference ArticlesRef;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_article_admin);

        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticlesRecyclerList = findViewById(R.id.article_recycler_list);

        RootRef = FirebaseDatabase.getInstance().getReference();
        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");

        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(ArticlesRef.orderByChild("state").equalTo("0"), Article.class)
                        .build();

        FirebaseRecyclerAdapter<Article, RequestArticleAdminActivity.ArticlesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Article, RequestArticleAdminActivity.ArticlesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RequestArticleAdminActivity.ArticlesViewHolder holder, final int position, @NonNull Article model)
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
                                Intent profileIntent = new Intent(getApplicationContext(), ArticleDetails.class);
                                profileIntent.putExtra("id_article", visit_article_id);
                                startActivity(profileIntent);
                            }
                        });

                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                String visit_article_id = getRef(position).getKey();

                                AlertDialog.Builder builder = new AlertDialog.Builder(RequestArticleAdminActivity.this);
                                builder.setTitle("Solicitud artículo");

                                String[] animals =
                                        {
                                                "Aprobar artículo",
                                                "Cancelar"
                                        };
                                builder.setItems(animals, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                ArticlesRef.child(visit_article_id).child("state").setValue("1").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(getApplicationContext(), "Articulo aprobado satisfactoriamente...",Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                });
                                                break;
                                            case 1:
                                                break;
                                        }
                                    }
                                });
                                builder.show();
                                return false;
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RequestArticleAdminActivity.ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_display_layout, viewGroup, false);
                        RequestArticleAdminActivity.ArticlesViewHolder viewHolder = new RequestArticleAdminActivity.ArticlesViewHolder(view);
                        return viewHolder;
                    }
                };

        ArticlesRecyclerList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        ArticlesRecyclerList.setAdapter(adapter);

        adapter.startListening();

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