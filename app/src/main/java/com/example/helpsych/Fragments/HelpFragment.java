package com.example.helpsych.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.helpsych.Activity.AllUserActivity;
import com.example.helpsych.Activity.EditProfileActivity;
import com.example.helpsych.Activity.PopupSelectTestActivity;
import com.example.helpsych.Activity.PopupTestActivity;
import com.example.helpsych.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
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

        //View v = inflater.inflate(R.layout.fragment_help, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_help, container, false);

        TextView txt_btn_alluser;
        Button Btn_Help;
        txt_btn_alluser = rootView.findViewById(R.id.txt_btn_alluser);
        Btn_Help = rootView.findViewById(R.id.btn_help);

        txt_btn_alluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllUserActivity.class);
                startActivity(intent);
            }
        });

        Btn_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAlert();
            }
        });

        return rootView;
    }

    private void showDialogAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
        builder.setTitle("¡Advertencia!");
        builder.setMessage("Tenga en cuenta que los siguientes tests son orientativos y no representan un diagnóstico definitivo.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), PopupSelectTestActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }
}