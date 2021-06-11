package com.example.helpsych.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpsych.Model.Messages;
import com.example.helpsych.Model.Mood;
import com.example.helpsych.R;
import com.example.helpsych.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mutwakil Mo on ${Date}
 */
public class MoodsAdapter extends RecyclerView.Adapter<MoodsAdapter.MoodViewHolder> {

    static final String TAG = "MoodsAdapter";
    private Context mContext;
    private FirebaseAuth mAuth;
    List<Mood> mMoods;

    //*** Constructor*
    public MoodsAdapter(Context context,List<Mood> moods) {
        this.mContext = context;
        this.mMoods = moods;
    }

    @Override
    public int getItemCount() {
        return mMoods.size();
    }

    public class MoodViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout leftFrameLayout;
        private FrameLayout rightFrameLayout;
        private ImageButton commentButton;
        private TextView daysTextView;

        public MoodViewHolder(View itemView) {
            super(itemView);

            leftFrameLayout = itemView.findViewById(R.id.left_frame_layout);
            rightFrameLayout = itemView.findViewById(R.id.right_frame_layout);
            commentButton = itemView.findViewById(R.id.btn_show_comment);
            daysTextView = itemView.findViewById(R.id.tv_days);
        }
    }


    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_mood, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();
        return new MoodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder moodViewHolder, int i) {

        String messageSenderId = mAuth.getCurrentUser().getUid();
        Mood moods = mMoods.get(i);
        String date = moods.getDate();

        String fromUserID = moods.getFrom();

        int mood = Integer.parseInt(moods.getMood());
        LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        float weight;
        switch (mood) {
            case 0:
                moodViewHolder.daysTextView.setText(date);
                weight = 0.3f;

                break;
            case 1:
                moodViewHolder.daysTextView.setText(date);
                weight = 0.4f;
                break;
            case 2:
                moodViewHolder.daysTextView.setText(date);
                weight = 0.6f;
                break;
            case 3:
                moodViewHolder.daysTextView.setText(date);
                weight = 0.8f;
                break;
            case 4:
                moodViewHolder.daysTextView.setText(date);
                weight = 1.0f;
                break;
            default:
                moodViewHolder.daysTextView.setText(date);
                weight = 0.8f;
        }
        leftLayoutParams.weight = weight;
        rightLayoutParams.weight = 1.0f - weight;
        moodViewHolder.leftFrameLayout.setLayoutParams(leftLayoutParams);
        moodViewHolder.rightFrameLayout.setLayoutParams(rightLayoutParams);
        moodViewHolder.leftFrameLayout.setBackgroundResource(Constants.moodColorsArray[mood]);

        //** if there's a comment, show the icon and a toast on click*
        final String comment = moods.getComment();
        if (comment != null && !comment.isEmpty()) {
            moodViewHolder.commentButton.setVisibility(View.VISIBLE);
            moodViewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(date);
                    builder.setMessage(comment);
                    builder.setPositiveButton("OK",null);
                    builder.create();
                    builder.show();
                }
            });
        } else {
            moodViewHolder.commentButton.setVisibility(View.INVISIBLE);
        }
    }

}
