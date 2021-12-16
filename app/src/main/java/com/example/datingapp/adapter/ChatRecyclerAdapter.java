package com.example.datingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.R;
import com.example.datingapp.util.Chat;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<Chat> mChatList;
    FirebaseAuth mAuth;
    public ChatRecyclerAdapter(Context context, List<Chat> mChatList) {
        this.context = context;
        this.mChatList = mChatList;
        mAuth =FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType==0){
            view = LayoutInflater.from(context).inflate(R.layout.sender_single_item,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.receiver_single_item,parent,false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==0){
            SenderViewHolder senderViewHolder= (SenderViewHolder) holder;
            senderViewHolder.mMessage.setText(mChatList.get(position).getMessage());
        }else{
            ReceiverViewHolder receiverViewHolder= (ReceiverViewHolder) holder;
            receiverViewHolder.mMessage.setText(mChatList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mChatList.get(position).getFrom().equals(mAuth.getCurrentUser().getUid())){
            return 0;
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        private TextView mMessage;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.sender_id);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        private TextView mMessage;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.receiver_id);
        }
    }

}
