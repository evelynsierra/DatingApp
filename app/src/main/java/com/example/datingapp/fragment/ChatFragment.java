package com.example.datingapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datingapp.R;
import com.example.datingapp.adapter.MatchRecyclerAdapter;
import com.example.datingapp.util.Match;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView mMatchRecyclerView;
    private List<Match> mMatchList;
    private MatchRecyclerAdapter matchRecyclerAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        mMatchRecyclerView=view.findViewById(R.id.match_recycler);
        mMatchList=new ArrayList<>();
        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        mMatchRecyclerView.setHasFixedSize(true);
        mMatchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        matchRecyclerAdapter=new MatchRecyclerAdapter(getContext(),mMatchList);
        mMatchRecyclerView.setAdapter(matchRecyclerAdapter);

        mStore.collection("Users").document(mAuth.getCurrentUser().getUid() )
                .collection("Match").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult()){
                        Match match = documentSnapshot.toObject(Match.class);
                        mMatchList.add(match);
                        matchRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        return view;
    }
}