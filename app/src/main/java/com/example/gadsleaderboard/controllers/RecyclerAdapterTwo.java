package com.example.gadsleaderboard.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.models.LearnerOne;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterTwo extends RecyclerView.Adapter<RecyclerAdapterTwo.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<LearnerOne> mLearnerOneList;

    public RecyclerAdapterTwo(Context context, ArrayList<LearnerOne> learnerOneList) {
        mContext = context;
        mLearnerOneList = learnerOneList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        LearnerOne learnerOne = mLearnerOneList.get(position);

        String badgeImageUrl = learnerOne.getBadgeImageUrl();
        String name = learnerOne.getName();
        String country = learnerOne.getCountry();
        int score = learnerOne.getScore();

        holder.nameTextView.setText(name);
        holder.countryTextView.setText(country);
        holder.scoreTextView.setText(String.valueOf(score));
        Picasso.get().load(badgeImageUrl).fit().centerInside().into(holder.badgeImageView);
    }

    @Override
    public int getItemCount() {
        return mLearnerOneList.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView badgeImageView;
        public TextView nameTextView;
        public TextView countryTextView;
        public TextView scoreTextView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            badgeImageView = itemView.findViewById(R.id.badge_image);
            nameTextView = itemView.findViewById(R.id.name);
            countryTextView = itemView.findViewById(R.id.country);
            scoreTextView = itemView.findViewById(R.id.score);
        };
    };
}
