package com.example.loginapp;
import static android.util.Patterns.EMAIL_ADDRESS;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class Emaillinklogin extends AppCompatActivity {
    private EditText editemail;
    private TextView text27;
    Button button;
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
                                .setUrl("https://loginauthenticate.page.link")
                                .setHandleCodeInApp(true)
                                .setIOSBundleId("com.example.ios")
                                .setAndroidPackageName(
                                        "com.example.loginapp",
                                        true,
                                        "12"
                                )
                                .build();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendSignInLinkToEmail(email, actionCodeSettings)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Emaillinklogin.this, "Email sent", Toast.LENGTH_SHORT).show();
                                    verifyandsignin();
                               }
                                else{
                                    Toast.makeText(Emaillinklogin.this, "no", Toast.LENGTH_SHORT).show();
                                }
                            });
                }}
            private void verifyandsignin() {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Intent intent = getIntent();
                String emailLink = intent.getData().toString();
                if (auth.isSignInWithEmailLink(emailLink)) {
                    String email = "someemail@domain.com";
                    auth.signInWithEmailLink(email, emailLink)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    AuthResult result = task.getResult();
                                } else {
                                    Toast.makeText(Emaillinklogin.this, "Error signing in with email link", Toast.LENGTH_SHORT).show();
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