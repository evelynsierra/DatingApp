package com.example.datingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.datingapp.adapter.ChatRecyclerAdapter;
import com.example.datingapp.util.Chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.Timestamp;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mChatRecyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private EditText mChatText;
    private ImageView mSend;
    String toId="";
    private ChatRecyclerAdapter mChatRecyclerAdapter;
    List<Chat> mChatList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatRecyclerView = findViewById(R.id.chat_recycler);
        mChatText = findViewById(R.id.chat_msg);
        mSend = findViewById(R.id.chat_btn);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        mChatList = new ArrayList<>();
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toId=getIntent().getStringExtra("doc_id");
        mChatRecyclerAdapter = new ChatRecyclerAdapter(this,mChatList);
        mChatRecyclerView.setAdapter(mChatRecyclerAdapter);

        mStore.collection("Message").orderBy("time_stamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    DocumentSnapshot snapshot=doc.getDocument();
                    Chat chat;
                    chat = snapshot.toObject(Chat.class);
                    if((chat.getFrom().equals(mAuth.getCurrentUser().getUid()) || chat.getFrom().equals(toId))
                            && (chat.getTo().equals(mAuth.getCurrentUser().getUid()) || chat.getTo().equals(toId))){
                        mChatList.add(chat);
                        mChatRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        //Toast.makeText(this, ""+getIntent().getStringExtra("doc_id"), Toast.LENGTH_SHORT).show();

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mChatText.getText().toString().isEmpty()){
                    Map<String,Object> map = new HashMap<>();
                    map.put("message",mChatText.getText().toString());
                    map.put("from",mAuth.getCurrentUser().getUid());
                    map.put("to",toId);
                    map.put("time_stamp",new Date());
                    mStore.collection("Message").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                mChatText.setText("");
                                Toast.makeText(ChatActivity.this, "message sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
