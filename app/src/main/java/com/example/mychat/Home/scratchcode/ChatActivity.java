package com.example.mychat.Home.scratchcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mychat.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String receverId;
    String receverName;
    String senderRoom, reseveeRoom;
    DatabaseReference databaseReferencesender, databaseReferenceresever;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receverId = getIntent().getStringExtra("Userid");
        receverName = getIntent().getStringExtra("Username");
        getSupportActionBar().setTitle(receverName);
        senderRoom = FirebaseAuth.getInstance().getUid() + receverId;
        reseveeRoom = receverId + FirebaseAuth.getInstance().getUid();
        messageAdapter = new MessageAdapter(this);
        binding.chatsRecycler.smoothScrollToPosition(messageAdapter.getItemCount());
        binding.chatsRecycler.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        llm.isSmoothScrollbarEnabled();
        binding.chatsRecycler.setLayoutManager(llm);


        databaseReferencesender = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom);

        databaseReferenceresever = FirebaseDatabase.getInstance().getReference("Chats").child(reseveeRoom);


        databaseReferencesender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessegeModel messegeModel = dataSnapshot.getValue(MessegeModel.class);
                    System.out.println(snapshot);
                    messageAdapter.add(messegeModel);
                    messageAdapter.notifyDataSetChanged();
                    binding.chatsRecycler.smoothScrollToPosition(messageAdapter.getItemCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error::"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.meassageSendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String Messege = binding.meassageEd.getText().toString();
                        if (Messege.trim().length() > 0) {
                            SendeingMessege(Messege);
                            messageAdapter.notifyDataSetChanged();
                            binding.chatsRecycler.smoothScrollToPosition(messageAdapter.getItemCount());
                        }

                        binding.meassageEd.setText(null);
                    }
                });
            }
        });

    }

    private void SendeingMessege(String messege) {
        String MessegeId = databaseReferenceresever.push().getKey().toString();
        MessegeModel messegeModel = new MessegeModel(MessegeId, FirebaseAuth.getInstance().getUid(), messege);
        messageAdapter.add(messegeModel);
        databaseReferencesender.child(MessegeId).setValue(messegeModel);
        databaseReferenceresever.child(MessegeId).setValue(messegeModel);

    }
}