package com.example.helpsych.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Activity.PopupDetailUser;
import com.example.helpsych.Activity.RegisterActivity;
import com.example.helpsych.Activity.RegisterSpecialist;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersAdminFragment extends Fragment {

    private Button CreateAccountButton;
    private FloatingActionButton RegisterSpecialistButton;
    private EditText UserEmail, UserPassword, UserName, UserLastName, UserSex, UserBirthDate, UserCountry, UserCity, UserPhone,UserLinkedin;
    private TextView AlreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth mAuth2;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;

    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference UsersRef;

    String formattedDate ="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsersAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersAdminFragment newInstance(String param1, String param2) {
        UsersAdminFragment fragment = new UsersAdminFragment();
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

        View v = inflater.inflate(R.layout.fragment_users_admin, container, false);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        RegisterSpecialistButton = (FloatingActionButton) v.findViewById(R.id.article_add_admin_new_specialist);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FindFriendsRecyclerList = v.findViewById(R.id.find_friends_recycler_list);


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(UsersRef, User.class)
                        .build();


        FirebaseRecyclerAdapter<User, UsersAdminFragment.FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<User, UsersAdminFragment.FindFriendViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull UsersAdminFragment.FindFriendViewHolder holder, final int position, @NonNull User model)
                    {
                        holder.userName.setText(model.getName());
                        holder.userStatus.setText(model.getLastName());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.profileImage);
                        holder.userType.setText("Usuarios");
                        holder.userType.setTextColor(Color.rgb(186, 50, 79));


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                //String visit_user_id = getRef(position).getKey();
                                //Intent profileIntent = new Intent(getContext(), PopupDetailUser.class);
                                //profileIntent.putExtra("visit_user_id", visit_user_id);
                                //startActivity(profileIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public UsersAdminFragment.FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
                        UsersAdminFragment.FindFriendViewHolder viewHolder = new UsersAdminFragment.FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        FindFriendsRecyclerList.setLayoutManager(new GridLayoutManager(getContext(),1));
        FindFriendsRecyclerList.setAdapter(adapter);

        adapter.startListening();

        RegisterSpecialistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterSpecialist.class);
                startActivity(intent);
            }
        });

        return v;
    }

    public static class FindFriendViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName, userStatus, userType;
        ImageView profileImage;

        public FindFriendViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userStatus = itemView.findViewById(R.id.user_status);
            profileImage = itemView.findViewById(R.id.users_profile_image);
            userType = itemView.findViewById(R.id.user_status_chat);
        }
    }

}