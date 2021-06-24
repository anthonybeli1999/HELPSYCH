package com.example.helpsych.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.ArticleDetails;
import com.example.helpsych.Activity.MainActivity;
import com.example.helpsych.Adapters.ArticleAdapter;
import com.example.helpsych.Model.Article;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.Model.TestResult;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
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
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    TextView TopArticleTitle, TopArticleDate;
    ImageView TopArticleImage;

    private RecyclerView ArticlesRecyclerList;
    private DatabaseReference ArticlesRef;
    List<User> UserList;

    private DatabaseReference RootRef;
    private ArrayList listaIdAarticles;
    private Random randomGenerator;
    private String currentUserID;
    private FirebaseAuth mAuth;
    private int sw = 0;

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
    public void onStart() {
        super.onStart();
        List<Article> articles = new ArrayList();
        RootRef.child("Article").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        String id = ds.child("id").getValue().toString();
                        String title = ds.child("title").getValue().toString();
                        String body = ds.child("body").getValue().toString();
                        String date = ds.child("date").getValue().toString();
                        String author = ds.child("author").getValue().toString();
                        String image = ds.child("image").getValue().toString();
                        String creationdate = ds.child("creationdate").getValue().toString();
                        String approach = ds.child("approach").getValue().toString();
                        String state = ds.child("state").getValue().toString();
                        if(state.equals("1")){
                            articles.add(new Article(id,title,body,date,author,image,creationdate,approach,state));}
                    }

                    List<Article> destacados = new ArrayList();
                    //Obtener arreglo de approches críticos
                    List<TestResult> testResults= new ArrayList();
                    RootRef.child("TestResults").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                for(DataSnapshot ds: snapshot.getChildren())
                                {
                                    String idTestResult = ds.child("idTestResult").getValue().toString();
                                    String resultValue = ds.child("resultValue").getValue().toString();
                                    String idUser = ds.child("idUser").getValue().toString();
                                    String level = ds.child("level").getValue().toString();
                                    String nameApproach = ds.child("nameApproach").getValue().toString();

                                    testResults.add(new TestResult(idTestResult,resultValue,idUser,level,nameApproach));
                                }

                                for(TestResult tr: testResults)
                                {
                                    if(tr.getLevel().equals("3"))
                                    {
                                        for(Article ar: articles)
                                        {
                                            if(tr.getNameApproach().equals(ar.getApproach()))
                                            {
                                                destacados.add(ar);
                                            }
                                        }
                                    }
                                    else if(tr.getLevel().equals("2"))
                                    {
                                        for(Article ar: articles)
                                        {
                                            if(tr.getNameApproach().equals(ar.getApproach()))
                                            {
                                                destacados.add(ar);
                                            }
                                        }
                                    }
                                    else if(tr.getLevel().equals("1"))
                                    {
                                        for(Article ar: articles)
                                        {
                                            if(tr.getNameApproach().equals(ar.getApproach()))
                                            {
                                                destacados.add(ar);
                                            }
                                        }
                                    }
                                }
                            }

                            //List<Article> arraySinDestacados = new ArrayList();
                            if(destacados.size()>=1) {
                                for (Article de : destacados) {
                                    articles.removeIf((articulo) -> articulo.getId().equals(de.getId()));
                                }
                            }

                            //Uniendo las dos listas
                            List<Article> resultList3;
                            (resultList3 = new ArrayList<Article>(destacados)).addAll(articles);
                            ArticleAdapter adapter = new ArticleAdapter(ArticleFragment.this.getContext(), resultList3);
                            ArticlesRecyclerList.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }


                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_article, container, false);


        ArticlesRef = FirebaseDatabase.getInstance().getReference().child("Article");
        ArticlesRecyclerList = rootView.findViewById(R.id.article_recycler_list);
        ArticlesRecyclerList.setLayoutManager(new GridLayoutManager(getContext(),1));


        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        TopArticleTitle = (TextView) rootView.findViewById(R.id.txt_article_top_title);
        TopArticleDate = (TextView) rootView.findViewById(R.id.txt_article_top_date);
        TopArticleImage = (ImageView) rootView.findViewById(R.id.img_article_top_image);
        currentUserID = mAuth.getCurrentUser().getUid();
        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                        .setQuery(ArticlesRef.orderByChild("state").equalTo("1"), Article.class)
                        .build();




        //Obtener arreglo de artículos
        List<Article> articles = new ArrayList();




/*
        FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Article, ArticleFragment.ArticlesViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position, @NonNull Article model)
                    {
                        //Primero colocar los artículos destacados

                        if(sw < destacados.size())
                        {
                            Picasso.get().load(destacados.get(sw).getImage()).placeholder(R.drawable.article).into(holder.article_image);
                            holder.article_approach.setText(destacados.get(sw).getApproach());
                            holder.article_title.setText(destacados.get(sw).getTitle());
                            holder.article_date.setText(destacados.get(sw).getDate());

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
                            sw++;
                        }
                        else
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
*/
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