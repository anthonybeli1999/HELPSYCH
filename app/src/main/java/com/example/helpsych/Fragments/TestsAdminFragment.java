package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpsych.Model.Psychological_approach;
import com.example.helpsych.Model.Test;
import com.example.helpsych.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestsAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsAdminFragment extends Fragment {

    private FirebaseAuth mAuth;
    private String currentUserID;
    private DatabaseReference UserRef, RootRef;
    Spinner mSpinnerApproaches;
    String selectedApproach = "";
    String selectedNameApproach = "";
    private DatabaseReference TestRef;
    private EditText questionTest;

    private RecyclerView questionlist;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TestsAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestsAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestsAdminFragment newInstance(String param1, String param2) {
        TestsAdminFragment fragment = new TestsAdminFragment();
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
        View v = inflater.inflate(R.layout.fragment_tests_admin, container, false);

        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        RootRef = FirebaseDatabase.getInstance().getReference();
        TestRef = FirebaseDatabase.getInstance().getReference().child("Test");
        currentUserID = mAuth.getCurrentUser().getUid();

        mSpinnerApproaches = v.findViewById(R.id.spinnerApproaches);
        questionTest = v.findViewById(R.id.txtQuestionTest);
        loadApproaches();

        //Test

        questionlist = v.findViewById(R.id.question_test_list);
        //question_test_list

        //Test

        Button addQuestion = (Button) v.findViewById(R.id.btnAddQuestion_Test);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewQuestion(selectedApproach);
                questionTest.setText("");

            }
        });

        return v;
    }

    private void loadApproaches()
    {
        List<Psychological_approach> approaches= new ArrayList();
        RootRef.child("psyapproach").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        String ApproachId = ds.child("p_approachId").getValue().toString();
                        String ApproachName = ds.child("p_approachName").getValue().toString();
                        approaches.add(new Psychological_approach(ApproachId,ApproachName));
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter( getContext(), android.R.layout.simple_dropdown_item_1line,approaches);
                    mSpinnerApproaches.setAdapter(arrayAdapter);
                    mSpinnerApproaches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedNameApproach = parent.getItemAtPosition(position).toString();
                            selectedApproach = approaches.get(position).getP_approachId();
                            LoadQuestionList(selectedApproach);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoadQuestionList(String selectApproach){
        super.onStart();
        FirebaseRecyclerOptions<Test> options =
                new FirebaseRecyclerOptions.Builder<Test>()
                        .setQuery(TestRef.child(selectApproach), Test.class)
                        .build();

        FirebaseRecyclerAdapter<Test, TestsAdminFragment.TestQuestionViewHolder> adapter =
                new FirebaseRecyclerAdapter<Test, TestsAdminFragment.TestQuestionViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull TestsAdminFragment.TestQuestionViewHolder holder, final int position, @NonNull Test test )
                    {
                        holder.QuestionTest.setText(test.getQuestion());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String question_id = getRef(position).getKey();

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Eliminar pregunta",
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle(test.getQuestion() );

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            TestRef.child(selectApproach).child(question_id).removeValue();
                                            LoadQuestionList(selectedApproach);
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public TestsAdminFragment.TestQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_display_layout, viewGroup, false);
                        TestsAdminFragment.TestQuestionViewHolder viewHolder = new TestsAdminFragment.TestQuestionViewHolder(view);
                        return viewHolder;
                    }
                };

        questionlist.setLayoutManager(new GridLayoutManager(getContext(),1));
        questionlist.setAdapter(adapter);

        adapter.startListening();
    }

    private void AddNewQuestion(String selectApproach) {
        String question = questionTest.getText().toString();
        //String UID = UUID.randomUUID().toString();

        if (TextUtils.isEmpty(question)) {
            Toast.makeText(getContext(), "Por favor ingrese una pregunta", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference QuestionKeyRef = RootRef.child("Test")
                    .child(selectApproach).push();

            String questionPushID = QuestionKeyRef.getKey();

            Test test = new Test();
            test.setIdApproach(selectApproach);
            test.setIdQuestion(questionPushID);
            test.setNameApproach(selectedNameApproach);
            test.setQuestion(question);
            RootRef.child("Test").child(selectApproach).child(test.getIdQuestion()).setValue(test);
            Toast.makeText(getContext(), "Pregunta agregada correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public static class TestQuestionViewHolder extends RecyclerView.ViewHolder
    {
        TextView QuestionTest;

        public TestQuestionViewHolder(@NonNull View itemView)
        {
            super(itemView);

            QuestionTest = (TextView) itemView.findViewById(R.id.question_display);
        }
    }
}