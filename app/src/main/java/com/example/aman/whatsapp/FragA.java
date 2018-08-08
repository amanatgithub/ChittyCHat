package com.example.aman.whatsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FragA extends Fragment {
    ArrayList<ContactList> arrayList=new ArrayList<>();
    FloatingActionButton addContact;
    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fraga,container,false);
        addContact=view.findViewById(R.id.fabAdd);
        RecyclerView recyclerView=view.findViewById(R.id.rview);
        sharedPreferences = getContext().getSharedPreferences("my_prefs_file", getContext().MODE_PRIVATE);
        final MyAdapter myAdapter=new MyAdapter(arrayList,getContext(),getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        if(sharedPreferences.contains(firebaseUser.getPhoneNumber()))
        {
            ContactList[] contactLists=new Gson().fromJson(sharedPreferences.getString(firebaseUser.getPhoneNumber(),""),ContactList[].class);
            for(ContactList contactList:contactLists)
            {   arrayList.clear();
                arrayList.add(contactList);
                myAdapter.notifyDataSetChanged();
            }

        }


        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View view1=LayoutInflater.from(getContext()).inflate(R.layout.alert_add_button,null);


                AlertDialog alertDialog=new AlertDialog.Builder(getActivity(),R.style.MyDialogue).setTitle("Contact").
                        setView(view1).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editNumber=view1.findViewById(R.id.addNumber);
                        EditText editName=view1.findViewById(R.id.addName);
                        String name=editName.getText().toString();
                        String number =editNumber.getText().toString();
                       if(name.isEmpty()||number.length()!=10)
                           Toast.makeText(getContext() ,"Contact cant be Added. Try Again!!!", Toast.LENGTH_SHORT).show();
                     else
                       {
                        arrayList.add(new ContactList(name,number));
                        myAdapter.notifyDataSetChanged();}

                    }
                }).show();
            }
        });







       return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Gson gson=new Gson();
        String s=gson.toJson(arrayList);
        SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
        sharedPrefsEditor.remove(firebaseUser.getPhoneNumber());
        sharedPrefsEditor.putString(firebaseUser.getPhoneNumber(),s);
        sharedPrefsEditor.apply();
    }
}
