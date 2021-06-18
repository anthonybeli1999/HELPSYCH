package com.example.helpsych.Fragments;

import android.content.Intent;
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

import com.example.helpsych.Activity.ArticleDetails;
import com.example.helpsych.Activity.PopupAddArticle;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleSpecialistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleSpecialistFragment extends Fragment {

    TextView TopArticleTitle, TopArticleDate;
    ImageView TopArticleImage;

    private RecyclerView ArticlesRecyclerList;
    private DatabaseReference ArticlesRef;
    List<User> UserList;

    private FloatingActionButton BtnNewArticle;

    private DatabaseReference RootRef;
    private ArrayList listaIdAarticles;
    private Random randomGenerator;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleSpecialistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleSpecialistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleSpecialistFragment newInstance(String param1, String param2) {
        ArticleSpecialistFragment fragment = new ArticleSpecialistFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_article_specialist, container, false);

        // Inflate the layout for this fragment
        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticlesRecyclerList = rootView.findViewById(R.id.article_recycler_list);

        RootRef = FirebaseDatabase.getInstance().getReference();

        TopArticleTitle = (TextView) rootView.findViewById(R.id.txt_article_top_title_specialist);
        TopArticleDate = (TextView) rootView.findViewById(R.id.txt_article_top_date_specialist);
        TopArticleImage = (ImageView) rootView.findViewById(R.id.img_article_top_image_specialist);

        BtnNewArticle = (FloatingActionButton) rootView.findViewById(R.id.article_add_specialist_new);

        BtnNewArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PopupAddArticle.class);
                startActivity(intent);
            }
        });

        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(ArticlesRef.orderByChild("state").equalTo("1"), Article.class)
                        .build();


        FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ArticleFragment.ArticlesViewHolder holder, final int position, @NonNull Article model)
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

        //Toast.makeText(getContext(), "WAAA", Toast.LENGTH_SHORT).show();

        getIdArticle();

        return rootView;
    }

    private void getIdArticle()
    {
        RootRef.child("Article").orderByChild("state").equalTo("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaIdAarticles = new ArrayList<String>();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id_article = snapshot.getKey();
                    listaIdAarticles.add(id_article);
                }

                randomGenerator = new Random();
                int index = randomGenerator.nextInt(listaIdAarticles.size());
                //Toast.makeText(getContext(), "B"+listaIdAarticles.get(index), Toast.LENGTH_SHORT).show();

                ArticlesRef.child(listaIdAarticles.get(index).toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if ((dataSnapshot.exists())  &&  (dataSnapshot.hasChild("image")))
                        {
                            String dateArticle = dataSnapshot.child("date").getValue().toString();
                            String titleArticle = dataSnapshot.child("title").getValue().toString();
                            String imageArticle = dataSnapshot.child("image").getValue().toString();

                            Picasso.get().load(imageArticle).placeholder(R.drawable.article).into(TopArticleImage);

                            TopArticleTitle.setText(titleArticle);
                            TopArticleDate.setText(dateArticle);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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