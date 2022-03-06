package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginactivityOtpverificationActivity extends AppCompatActivity {
String name;
String email;
String password;
private PinView pinView;
    private TextView textView13;
  private Button btn;
private Button btn2;
  String phone;
  String otpid;
    FirebaseAuth mAuth;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_otpverification);
       pinView= findViewById(R.id.pinView);
        textView13=findViewById(R.id.textView13);
        btn=findViewById(R.id.button4);
        btn2=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        btn2.setEnabled(false);
        phone= getIntent().getStringExtra("phone");

        initiate_otp();
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(pinView.length()==0 || pinView.length()!=6){
                     Toast.makeText(LoginactivityOtpverificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                 }else
                 {
                     Toast.makeText(LoginactivityOtpverificationActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                     PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid, String.valueOf(pinView));
                     signInWithPhoneAuthCredential(credential);}
             }
         });}

    private void initiate_otp() {

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {



            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);

            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(LoginactivityOtpverificationActivity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(LoginactivityOtpverificationActivity.this, "The SMS quota for the plan has been exceeded", Toast.LENGTH_SHORT).show();
                }

//                else {
//                    btn2.setEnabled(true);
//                    btn2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {PhoneAuthProvider.ForceResendingToken token;
////                            private void resendVerificationCode(phone,
////                                    token) {
////
////                                PhoneAuthProvider.getInstance().verifyPhoneNumber(
////                                        phone,        // Phone number to verify
////                                        60,                 // Timeout duration
////                                        TimeUnit.SECONDS,   // Unit of timeout
////                                        this,               // Activity (for callback binding)
////                                        mCallbacks,         // OnVerificationStateChangedCallbacks
////                                        token);             // ForceResendingToken from callbacks
////                            }
////                                         ForceResendingToken from callbacks
//
//                            }
//                        });
//
//                }

            }
            @Override

            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                         otpid=s;


                          }
            public void onCodeAutoRetrievalTimeOut(String s){
                btn2.setEnabled(true);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                    }
                });
            }


        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginactivityOtpverificationActivity.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent=new Intent(LoginactivityOtpverificationActivity.this,Dashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginactivityOtpverificationActivity.this, "OTP Verification failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                });
    }
}