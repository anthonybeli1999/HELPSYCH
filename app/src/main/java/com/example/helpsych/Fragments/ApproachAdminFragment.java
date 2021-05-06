package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.Model.User;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproachAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApproachAdminFragment extends Fragment {

    private EditText PsyApproach_Name;

    private DatabaseReference RootRef;

    private RecyclerView psyapporachlist;
    private DatabaseReference ApproachRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApproachAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApproachAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApproachAdminFragment newInstance(String param1, String param2) {
        ApproachAdminFragment fragment = new ApproachAdminFragment();
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

        View v = inflater.inflate(R.layout.fragment_approach_admin, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();

        ApproachRef = FirebaseDatabase.getInstance().getReference().child("psyapproach");

        Button addPsyApproach = (Button) v.findViewById(R.id.btnAdd_PsyApproach);
        PsyApproach_Name = (EditText) v.findViewById(R.id.txtPsyApproach);


        psyapporachlist = v.findViewById(R.id.psy_approach_list);

        addPsyApproach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewPsyApproach();
                PsyApproach_Name.setText("");
            }
        });


        return v;
    }

    private void AddNewPsyApproach() {
        String PsyApproachName = PsyApproach_Name.getText().toString();
        String UID = UUID.randomUUID().toString();

        if (TextUtils.isEmpty(PsyApproachName)) {
            Toast.makeText(getContext(), "Please enter the psyco approach...", Toast.LENGTH_SHORT).show();
        } else {
            Psychological_approach pa = new Psychological_approach();
            pa.setP_approachId(UID);
            pa.setP_approachName(PsyApproachName);
            RootRef.child("psyapproach").child(pa.getP_approachId()).setValue(pa);

            Toast.makeText(getContext(), "Account Created Successfully...", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Psychological_approach> options =
                new FirebaseRecyclerOptions.Builder<Psychological_approach>()
                        .setQuery(ApproachRef, Psychological_approach.class)
                        .build();

        FirebaseRecyclerAdapter<Psychological_approach, ApproachAdminFragment.PsyApproachViewHolder> adapter =
                new FirebaseRecyclerAdapter<Psychological_approach, ApproachAdminFragment.PsyApproachViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ApproachAdminFragment.PsyApproachViewHolder holder, final int position, @NonNull Psychological_approach model)
                    {
                        holder.PsyApproach.setText(model.getP_approachName());


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String approach_id = getRef(position).getKey();

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Eliminar enfoque psicológico",
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle(model.getP_approachName() );

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            ApproachRef.child(approach_id).removeValue();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ApproachAdminFragment.PsyApproachViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.psyapproach_display_layout, viewGroup, false);
                        ApproachAdminFragment.PsyApproachViewHolder viewHolder = new ApproachAdminFragment.PsyApproachViewHolder(view);
                        return viewHolder;
                    }
                };

        psyapporachlist.setLayoutManager(new GridLayoutManager(getContext(),1));
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


