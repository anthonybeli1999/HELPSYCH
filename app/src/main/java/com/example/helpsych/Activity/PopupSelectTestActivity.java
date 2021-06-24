package com.example.helpsych.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helpsych.Fragments.ApproachAdminFragment;
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopupSelectTestActivity extends AppCompatActivity {

    private EditText PsyApproach_Name;

    private DatabaseReference RootRef;

    private RecyclerView psyapporachlist;
    private DatabaseReference ApproachRef;
    Button BtnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_select_test);

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int width = medidasVentana.widthPixels;
        int height = medidasVentana.heightPixels;

        getWindow().setLayout((int)(width*0.9), (int)(height*0.5));

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        ApproachRef = FirebaseDatabase.getInstance().getReference().child("psyapproach");
        psyapporachlist = findViewById(R.id.approachTest_list);
        RetrieveApproachInformation();

    }

    public void RetrieveApproachInformation()
    {
        FirebaseRecyclerOptions<Psychological_approach> options =
                new FirebaseRecyclerOptions.Builder<Psychological_approach>()
                        .setQuery(ApproachRef, Psychological_approach.class)
                        .build();

        FirebaseRecyclerAdapter<Psychological_approach, PopupSelectTestActivity.PsyApproachViewHolder> adapter =
                new FirebaseRecyclerAdapter<Psychological_approach, PopupSelectTestActivity.PsyApproachViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull PopupSelectTestActivity.PsyApproachViewHolder holder, final int position, @NonNull Psychological_approach model)
                    {
                        holder.PsyApproach.setText(model.getP_approachName());


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String approach_id = getRef(position).getKey();

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Comenzar con el test...",
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(PopupSelectTestActivity.this);
                                builder.setTitle(model.getP_approachName() );

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            Intent testIntent = new Intent(PopupSelectTestActivity.this, PopupTestActivity.class);
                                            testIntent.putExtra("approach_id", model.getP_approachId());
                                            testIntent.putExtra("approach_name", model.getP_approachName());
                                            startActivity(testIntent);
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public PopupSelectTestActivity.PsyApproachViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.psyapproach_display_layout, viewGroup, false);
                        PopupSelectTestActivity.PsyApproachViewHolder viewHolder = new PopupSelectTestActivity.PsyApproachViewHolder(view);
                        return viewHolder;
                    }
                };

        psyapporachlist.setLayoutManager(new GridLayoutManager(PopupSelectTestActivity.this,1));
        psyapporachlist.setAdapter(adapter);

        adapter.startListening();
    }

    public static class PsyApproachViewHolder extends RecyclerView.ViewHolder
    {
        TextView PsyApproach;

        public PsyApproachViewHolder(@NonNull View itemView)
        {
            super(itemView);

            PsyApproach = (TextView) itemView.findViewById(R.id.psy_approach_name);
        }
    }
}