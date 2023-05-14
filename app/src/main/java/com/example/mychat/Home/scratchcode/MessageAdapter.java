package com.example.mychat.Home.scratchcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MeassageViewholder>{

    Context context;
    List<MessegeModel> mgslist;

    public MessageAdapter(Context context) {
        this.context = context;
        mgslist =new ArrayList<>();
    }

    public void add(MessegeModel messegeModel)
    {
        mgslist.add(messegeModel);
        notifyDataSetChanged();
    }
    public void clear()
    {
        mgslist.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MeassageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_messege_card,parent,false);
       return new MeassageViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeassageViewholder holder, int position) {
        MessegeModel messegeModel=mgslist.get(position);


        if (messegeModel.getSenderid().equals(FirebaseAuth.getInstance().getUid()))
        {
            holder.leftchatview.setVisibility(View.GONE);
            holder.rightchatview.setVisibility(View.VISIBLE);
            holder.rightchattextview.setText(messegeModel.getMesseg());

        }
        else
        {
            holder.rightchatview.setVisibility(View.GONE);
            holder.leftchatview.setVisibility(View.VISIBLE);
            holder.leftchattextview.setText(messegeModel.getMesseg());
        }


    }

    @Override
    public int getItemCount() {
        return mgslist.size();
    }

    public class MeassageViewholder extends RecyclerView.ViewHolder {
        LinearLayout leftchatview,rightchatview;
        TextView leftchattextview,rightchattextview;
        public MeassageViewholder(@NonNull View itemView) {
            super(itemView);
            leftchatview=itemView.findViewById(R.id.left_chat_view);
            rightchatview=itemView.findViewById(R.id.right_chat_view);

            leftchattextview=itemView.findViewById(R.id.left_chat_text_view);
            rightchattextview=itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}
