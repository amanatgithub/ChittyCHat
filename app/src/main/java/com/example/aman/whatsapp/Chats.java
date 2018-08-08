package com.example.aman.whatsapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity {
    String clientNumb;
    RecyclerView recyclerView;
    Button sendButton;
    EditText editText;
    ArrayList<Message> messageArrayList=new ArrayList<>();
    MsgAdapter msgAdapter=new MsgAdapter(messageArrayList);
    TaskDatabase taskDatabase;
    TaskDao taskDao;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceMe;
    DatabaseReference databaseReferenceMeToYou;
    DatabaseReference databaseReferenceYouToMe;
    DatabaseReference databaseReferenceYou;
    MediaPlayer mediaPlayer1,mediaPlayer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_block);
        Intent intent=getIntent();
        clientNumb=intent.getStringExtra("number");
        setTitle(intent.getStringExtra("name"));
        editText=findViewById(R.id.msg);
        sendButton=findViewById(R.id.btnSend);
        recyclerView=findViewById(R.id.rViewChatBlock);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(msgAdapter);
        taskDatabase= Room.databaseBuilder(this,TaskDatabase.class,clientNumb+"-db").allowMainThreadQueries().build();
        taskDao=taskDatabase.getTaskDao();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        databaseReferenceMe=databaseReference.child(firebaseUser.getPhoneNumber());
        databaseReferenceYou=databaseReference.child(clientNumb);
        databaseReferenceMeToYou=databaseReferenceMe.child(clientNumb);
        databaseReferenceYouToMe=databaseReferenceYou.child(firebaseUser.getPhoneNumber());
        mediaPlayer1=MediaPlayer.create(this,R.raw.knob);
        mediaPlayer2=MediaPlayer.create(this,R.raw.inquisitiveness);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString()))
                    sendData();
                }});

        databaseReferenceYouToMe.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message=dataSnapshot.getValue(Message.class);
                taskDao.insertTask(new Task(message.getMessage(),2));
                mediaPlayer2.start();
                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        LiveData<List<Task>> listLiveData=taskDao.getAllTasks();
        listLiveData.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(messageArrayList.size()!=0)
                    messageArrayList.clear();

                    for(Task task:tasks)
                        messageArrayList.add(new Message(task.getMessage(),task.getMessageOwner()));
                       msgAdapter.notifyDataSetChanged();
            }
        });



    }

    private void sendData() {
        final Message sender=new Message(editText.getText().toString(),1);
        mediaPlayer1.start();

        editText.setText("");
//        key = databaseReferenceMe.push().getKey();
//        databaseReferenceMe.child(key).setValue(dataSend);
        Log.e("TAG", "sendData: "+sender.getMessage() );
        databaseReferenceMeToYou.push().setValue(sender);
        taskDao.insertTask(new Task(sender.getMessage(),sender.getI()));

    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReferenceYouToMe.removeValue();
    }
}
