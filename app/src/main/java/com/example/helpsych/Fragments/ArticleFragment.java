package com.example.helpsych.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helpsych.Activity.AllUserActivity;
import com.example.helpsych.Activity.ArticleDetails;
import com.example.helpsych.Activity.PopupDetailUser;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    private RecyclerView ArticlesRecyclerList;
    private DatabaseReference ArticlesRef;
    List<User> UserList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String param1, String param2) {
        ArticleFragment fragment = new ArticleFragment();
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

        View v = inflater.inflate(R.layout.fragment_article, container, false);

        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticlesRecyclerList = v.findViewById(R.id.article_recycler_list);


        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(ArticlesRef, Article.class)
                        .build();

        FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position, @NonNull Article model)
                    {
                        holder.article_title.setText(model.getArticle_title());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.online).into(holder.article_image);


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
                    public ArticleFragment.ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_display_layout, viewGroup, false);
                        ArticleFragment.ArticlesViewHolder viewHolder = new ArticleFragment.ArticlesViewHolder(view);
                        return viewHolder;
                    }
                };

        ArticlesRecyclerList.setLayoutManager(new GridLayoutManager(getContext(),1));
        ArticlesRecyclerList.setAdapter(adapter);

        adapter.startListening();



        return v;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder
    {
        TextView article_title;
        ImageView article_image;

        public ArticlesViewHolder(@NonNull View itemView)
        {
            super(itemView);

            article_title = itemView.findViewById(R.id.article_title_rv);
            article_image = itemView.findViewById(R.id.article_image_rv);
        }
    }
}