package com.example.aman.whatsapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vpager);
        tabLayout = findViewById(R.id.tlayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));


    }
    @Override
    protected void onStart() {
        super.onStart();

  }

    public void ImageView(View view) {

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(i);
        }

    public void onClick(MenuItem item) {
        if(item.getItemId()==R.id.signOut)
        {
            firebaseAuth.signOut();
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);

        }
    }


    public class MyFragmentAdapter extends FragmentPagerAdapter
      {


          public MyFragmentAdapter(FragmentManager fm) {
              super(fm);
          }

          @Nullable
          @Override
          public CharSequence getPageTitle(int position) {
              switch(position){


                  case  1: return "STATUS";
                  case 2: return "CALLS";
                  case 0: return "CHATS";
                  default: return null;




              }
          }

          @Override
          public Fragment getItem(int position) {
              switch(position)
              {
                  case 0 : return( new FragA());
                  case 1:return (new FragB());
                  case 2: return (new FragC());
                  default: return null;


              }
          }

          @Override
          public int getCount() {
              return 3;
          }
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.signout,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
