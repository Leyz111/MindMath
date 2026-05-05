package com.example.mindmath.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindmath.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardUserAdapter extends RecyclerView.Adapter<LeaderboardUserAdapter.UserViewHolder> {

    private final List<LeaderboardUser> userList;

    public LeaderboardUserAdapter(List<LeaderboardUser> userList) {
        this.userList = (userList != null) ? userList : new ArrayList<>();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        LeaderboardUser user = userList.get(position);

        holder.tvRank.setText(String.valueOf(user.getRank()));
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank, tvName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.place_text_view);
            tvName = itemView.findViewById(R.id.name_text_view);
        }
    }
}