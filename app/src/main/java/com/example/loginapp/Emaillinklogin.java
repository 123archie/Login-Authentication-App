package com.example.loginapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Emaillinklogin extends AppCompatActivity {
private EditText editemail;
private TextView text27;
private Button button;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emaillinklogin);
        editemail=findViewById(R.id.editTextTextEmailAddress3);
        text27=findViewById(R.id.textView24);
        button=findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
                if(validateEmail()){

                        ActionCodeSettings actionCodeSettings =
                        ActionCodeSettings.newBuilder()
                                // URL you want to redirect back to. The domain (www.example.com) for this
                                // URL must be whitelisted in the Firebase Console.
                                .setUrl("https://loginauthenticate.page.link")
                                .setHandleCodeInApp(true)
                                .setIOSBundleId("com.example.ios")
                                .setAndroidPackageName(
                                        "com.example.loginapp",
                                        true, /* installIfNotAvailable */
                                        "12"    /* minimumVersion */)
                                .build();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendSignInLinkToEmail(email, actionCodeSettings)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Emaillinklogin.this, "Email sent", Toast.LENGTH_SHORT).show();
                                        verifyandsignin(); }
else{
                                        Toast.makeText(Emaillinklogin.this, "no", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }}

            private void verifyandsignin() {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Intent intent = getIntent();
                String emailLink = intent.getData().toString();

// Confirm the link is a sign-in with email link.
                if (auth.isSignInWithEmailLink(emailLink)) {
                    // Retrieve this from wherever you stored it
                    String email = "someemail@domain.com";

                    // The client SDK will parse the code from the link for you.
                    auth.signInWithEmailLink(email, emailLink)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "Successfully signed in with email link!");
                                        AuthResult result = task.getResult();
                                        // You can access the new user via result.getUser()
                                        // Additional user info profile *not* available via:
                                        // result.getAdditionalUserInfo().getProfile() == null
                                        // You can check if the user is new or existing:
                                        // result.getAdditionalUserInfo().isNewUser()
                                    } else {
                                        Toast.makeText(Emaillinklogin.this, "Error signing in with email link", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            private boolean validateEmail() {
                 email=editemail.getText().toString();
                if(EMAIL_ADDRESS.matcher(email).matches()){
                    text27.setText("");
                    return true;
                }
                else{
                    text27.setText("Invalid Email");
                    return false;
                }
                }

        });
    }
}