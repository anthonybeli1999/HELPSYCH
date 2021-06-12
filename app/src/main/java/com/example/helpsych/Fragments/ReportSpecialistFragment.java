package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.Model.Rating;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportSpecialistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportSpecialistFragment extends Fragment {



    private DatabaseReference RootRef;

    private RecyclerView commentsList;
    private DatabaseReference RatingRef;
    private String CurrentUserID;
    private String image;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportSpecialistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportSpecialistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportSpecialistFragment newInstance(String param1, String param2) {
        ReportSpecialistFragment fragment = new ReportSpecialistFragment();
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

        View v = inflater.inflate(R.layout.fragment_report_specialist, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        CurrentUserID = mAuth.getCurrentUser().getUid();
        RatingRef = FirebaseDatabase.getInstance().getReference().child("Rating").child(CurrentUserID);

        commentsList = v.findViewById(R.id.rated_comments_spe);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Rating> options =
                new FirebaseRecyclerOptions.Builder<Rating>()
                        .setQuery(RatingRef, Rating.class)
                        .build();

        FirebaseRecyclerAdapter<Rating, ReportSpecialistFragment.PsyCommentsRatingViewHolder> adapter =
                new FirebaseRecyclerAdapter<Rating, ReportSpecialistFragment.PsyCommentsRatingViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull PsyCommentsRatingViewHolder holder, final int position, @NonNull Rating model)
                    {

                        holder.userName.setText(model.getFromName());
                        holder.userComment.setText(model.getMessage());

                        RootRef.child("Users").child(model.getFromID())
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        if(dataSnapshot.child("image").exists()) {
                                            String retrieveImage = dataSnapshot.child("image").getValue().toString();
                                            Picasso.get().load(retrieveImage).placeholder(R.drawable.profile_image).into(holder.profileImage);
                                            image = retrieveImage;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                    }

                    @NonNull
                    @Override
                    public ReportSpecialistFragment.PsyCommentsRatingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comments_spe, viewGroup, false);
                        ReportSpecialistFragment.PsyCommentsRatingViewHolder viewHolder = new ReportSpecialistFragment.PsyCommentsRatingViewHolder(view);
                        return viewHolder;
                    }
                };

        commentsList.setLayoutManager(new GridLayoutManager(getContext(),1));
        commentsList.setAdapter(adapter);

        adapter.startListening();
    }

    private String RetrieveUserImage(String userID) {

        RootRef.child("Users").child(userID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.child("image").exists()) {
                            String retrieveImage = dataSnapshot.child("image").getValue().toString();
                            image = retrieveImage;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return  image;
    }

    public static class PsyCommentsRatingViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName, userComment, userType;
        ImageView profileImage;

        public PsyCommentsRatingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            userName = itemView.findViewById(R.id.user_profile_name_r);
            userComment = itemView.findViewById(R.id.user_comment_r);
            profileImage = itemView.findViewById(R.id.users_profile_image_r);
            //userType = itemView.findViewById(R.id.user_status_chat_r);
        }
    }
}