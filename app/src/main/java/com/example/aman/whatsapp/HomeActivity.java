package com.example.aman.whatsapp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    EditText numb, otp;
    Button btnNumb, btnOtp;
    ImageView logo;
    String codeSent;
    Animation animation;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation);

        logo = findViewById(R.id.logo);
        logo.setAnimation(animation);
        numb = findViewById(R.id.numb);
        progressBar=findViewById(R.id.progressBar);
        otp = findViewById(R.id.otp);
        btnOtp = findViewById(R.id.btnotp);
        btnNumb = findViewById(R.id.btnnumb);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser == null) {
                            numb.setVisibility(View.VISIBLE);
                            btnNumb.setVisibility(View.VISIBLE);
                            final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {

                                    Log.e("TAG", "onVerificationFailed: " + e);

                                }

                                @Override
                                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(s, forceResendingToken);
                                    progressBar.setVisibility(View.GONE);
                                    otp.setVisibility(View.VISIBLE);
                                    btnOtp.setVisibility(View.VISIBLE);

                                    Log.e("TAG", "onCodeSent: " + forceResendingToken + "-------" + s);

                                    codeSent = s;
                                }

                            };
                            btnNumb.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    String s = numb.getText().toString();
                                    if (s.isEmpty() || s.length() != 10) {
                                        numb.setError("Input the Details");
                                        numb.requestFocus();
                                        return;
                                    } else {
                                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                                "+91" + s,
                                                60,
                                                TimeUnit.SECONDS,
                                                HomeActivity.this,
                                                mCallbacks

                                        );
                                        Log.e("TAG", "onClick: " + s);
                                        numb.setVisibility(View.GONE);
                                        btnNumb.setVisibility(View.GONE);
                                        progressBar.setVisibility(View.VISIBLE);

                                    }
                                }

                            });
                            btnOtp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    otp.setVisibility(View.GONE);
                                    btnOtp.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    String s = otp.getText().toString();
                                    if (s.isEmpty()) {
                                        numb.setError("Enter the Input");
                                        numb.requestFocus();
                                        return;
                                    } else {
                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, s);
                                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(HomeActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else
                                                {

                                                    numb.setVisibility(View.VISIBLE);
                                                    btnNumb.setVisibility(View.VISIBLE); progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(HomeActivity.this, "OTP not Matched", Toast.LENGTH_SHORT).show();}
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });


            }
        }, 1500);


    }
}
