package com.example.aman.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private ArrayList<ContactList> arrayList;
    private Context c;
    private Activity a;

    public MyAdapter(ArrayList<ContactList> arrayList, Context c,Activity a) {
        this.arrayList = arrayList;
        this.c = c;
        this.a=a;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyHolder(LayoutInflater.from(c).inflate(R.layout.item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

       holder.tview.setText(arrayList.get(position).getName());
        Picasso.get().load("https://picsum.photos/200/300/?random").into(holder.cView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tview;
        CircularImageView cView;
        public MyHolder(final View itemView) {
            super(itemView);
            tview=itemView.findViewById(R.id.contName);
            cView=itemView.findViewById(R.id.contImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(a,Chats.class);
                    i.putExtra("number","+91"+arrayList.get(getAdapterPosition()).getNumber());
                    i.putExtra("name",arrayList.get(getAdapterPosition()).getName());
                    a.startActivity(i);
                }
            });
        }
    }

}
