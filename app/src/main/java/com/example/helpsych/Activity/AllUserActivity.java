package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Fragments.FindUsersFragment;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllUserActivity extends AppCompatActivity {

    private RecyclerView FindFriendsRecyclerList;
    private DatabaseReference UsersRef;
    List<User> UserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FindFriendsRecyclerList = findViewById(R.id.find_friends_recycler_list);


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(UsersRef.orderByChild("usertype").equalTo("1"), User.class)
                        .build();

        //orderByChild("ownerID").equalTo(user.getUid()

        FirebaseRecyclerAdapter<User, FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<User, FindFriendViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull User model)
                    {
                        holder.userName.setText(model.getName());
                        holder.userStatus.setText(model.getLastName());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.profileImage);
                        holder.userType.setText("Oyente");
                        holder.userType.setTextColor(Color.rgb(186, 50, 79));


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String visit_user_id = getRef(position).getKey();
                                Intent profileIntent = new Intent(getApplicationContext(), PopupDetailUser.class);
                                profileIntent.putExtra("visit_user_id", visit_user_id);
                                startActivity(profileIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
                        FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                        return viewHolder;
                    }
                };

        FindFriendsRecyclerList.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        FindFriendsRecyclerList.setAdapter(adapter);

        adapter.startListening();

        Toolbar toolbar = findViewById(R.id.toolbarspecialist);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.items_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("SearchHere");

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.filter:
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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