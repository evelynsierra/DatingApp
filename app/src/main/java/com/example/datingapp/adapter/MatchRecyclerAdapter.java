package com.example.datingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datingapp.R;

import com.example.datingapp.util.Match;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    Context context;
    List<Match> mMatchList;
    public MatchRecyclerAdapter(Context context, List<Match> mMatchList) {
        this.context = context;
        this.mMatchList = mMatchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_match_user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText(mMatchList.get(position).getName());
        Glide.with(context).load(mMatchList.get(position).getImg_url()).into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        CircleImageView mImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.match_name);
            mImg = itemView.findViewById(R.id.match_img);
        }
    }
}
