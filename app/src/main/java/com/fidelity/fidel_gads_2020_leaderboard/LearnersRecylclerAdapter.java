package com.fidelity.fidel_gads_2020_leaderboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LearnersRecylclerAdapter  extends RecyclerView.Adapter<LearnersRecylclerAdapter.ViewHolder>{
    ArrayList<LeanersInfo> mLearners;
    String skill_hours;

    public LearnersRecylclerAdapter( ArrayList<LeanersInfo> learners, String skill_hours) {
        this.mLearners = learners;
        this.skill_hours = skill_hours;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = null;
        if(skill_hours == "hours"){
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_learners_list, parent,false);
        }else if(skill_hours == "skill"){
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_skill_list, parent,false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mLearners.get(position));
    }

    @Override
    public int getItemCount() {
        return mLearners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextName;
        private TextView mTextScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextScore = itemView.findViewById(R.id.section_label);
        }

        public void bind(LeanersInfo leanersInfo){
            mTextName.setText(leanersInfo.getName());
            if(skill_hours == "hours"){
            mTextScore.setText(leanersInfo.getHours() +" learning hours, " + leanersInfo.getCountry());
            }else if(skill_hours == "skill"){
            mTextScore.setText(leanersInfo.getHours() +" skill IQ Score, " + leanersInfo.getCountry());
            }

        }

    }
}
