package com.example.habitac.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.User;
import com.example.habitac.model.LeaderBoardModel;
import com.example.habitac.utils.AvatarGetter;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>{

    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    private Context context;


    public LeaderboardAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        // TODO:应该是在这和数据库连一下，用户的名字balabala
        String userName = userList.get(position).getUser_name();
        int userLevel = userList.get(position).getCurrentLevel();
        int userRank = position + 1;
        holder.setData(userName,userLevel,userRank);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userLevel,userRanking,userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userLevel = itemView.findViewById(R.id.rank_level_num);
            userRanking = itemView.findViewById(R.id.rank_num);
            userName = itemView.findViewById(R.id.rank_name);
        }

        public void setData(String name,int level,int rank){
            userName.setText(name);
            userLevel.setText(String.valueOf(level));
            userRanking.setText(String.valueOf(rank));
        }

    }
}
