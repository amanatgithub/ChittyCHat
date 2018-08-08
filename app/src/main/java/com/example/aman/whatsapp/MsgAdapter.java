package com.example.aman.whatsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyHolder> {
    ArrayList<Message> arrayList;

    public MsgAdapter(ArrayList<Message> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).getI();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
       if(viewType==1)
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sender,parent,false);
       else
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver,parent,false);
       return new MyHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tView.setText(arrayList.get(position).getMessage());
        }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tView;
        public MyHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType==1)
                tView=itemView.findViewById(R.id.senderMsg);
            else
                tView=itemView.findViewById(R.id.receiverMsg);
        }
    }




}
