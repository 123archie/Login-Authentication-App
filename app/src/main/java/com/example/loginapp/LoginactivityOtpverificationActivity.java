package com.example.loginapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class LoginactivityOtpverificationActivity extends AppCompatActivity {
    String name;
    String email;
    String password;
    private PinView pinView;
    TextView textView13;
    Button btn;
    Button btn2;
    String phone;
    String otpid;
    String userID;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    int k;
    String Em[];
    String Pa[];

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity_otpverification);
        pinView= findViewById(R.id.pinView);
        textView13=findViewById(R.id.textView13);
        btn=findViewById(R.id.button4);
        btn2=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        phone= getIntent().getStringExtra("phone");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        btn2.setEnabled(false);
        initiate_otp();
        btn.setOnClickListener(view -> {
            if(pinView.length()==0 || pinView.length()!=6){
                Toast.makeText(LoginactivityOtpverificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(LoginactivityOtpverificationActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid, String.valueOf(pinView));
                Log.d("TAG","credential: "+credential);
                signInWithPhoneAuthCredential(credential);}
        });
    }
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
                }
                else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(LoginactivityOtpverificationActivity.this, "The SMS quota for the plan has been exceeded", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                 otpid=s;
                          }
            public void onCodeAutoRetrievalTimeOut(String s){
                btn2.setEnabled(true);
                btn2.setOnClickListener(view -> initiate_otp());
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
        Log.d("TAG","msg: signingin");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginactivityOtpverificationActivity.this, task -> {
                    Log.d("TAG","mAuth: "+mAuth);
                    if (task.isSuccessful()) {
                        Log.d("TAG","signing in successful");

                        mAuth.createUserWithEmailAndPassword(email, password);
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fstore.collection("users").document();
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        user.put("password", password);
                        user.put("phone", phone);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "User Profile is created for" + userID);
                                FirebaseUser user = task.getResult().getUser();
                            }
                        });
                        Intent intent = new Intent(LoginactivityOtpverificationActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();


                        Em[k]=email;
                        Pa[k]=password;
                        k++;
                        user.put("email_array",Em);
                        user.put("password_array",Pa);
                        user.put("k",k);

                    } else {
                        Toast.makeText(LoginactivityOtpverificationActivity.this, "OTP Verification failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}