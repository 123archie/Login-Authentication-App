package com.example.loginapp;
import static android.util.Patterns.EMAIL_ADDRESS;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.regex.Pattern;
public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    Button button5;
    private TextView textViewcreateOne;
    Button btn6;
    private TextView text23;
    private TextView text24;
    FirebaseFirestore fstore;
    private static  final Pattern PASSWORD_PATTERN=
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,20}" +
                    "$");
    FirebaseAuth mAuth;
    private String EMAIL;
    private String PASS;
    private Task<DocumentSnapshot> users;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editTextTextEmailAddress2);
        text23=findViewById(R.id.textView23);
        text24=findViewById(R.id.textView25);
        password=findViewById(R.id.editTextTextPassword3);
        mAuth= FirebaseAuth.getInstance();
        button5=findViewById(R.id.button5);
        textViewcreateOne=findViewById(R.id.textView18);
        btn6=findViewById(R.id.button6);
        textViewcreateOne.setOnClickListener(view -> {
            Intent intent=new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            textViewcreateOne.setText(Html.fromHtml("<u>Create One</u>"));
            });

//      users=fstore.collection("users").document().get();
//      DocumentReference documentReference_email=users.getResult().getDocumentReference("email");
//      DocumentReference documentReference_password=users.getResult().getDocumentReference("password");
      button5.setOnClickListener(view -> {
          EMAIL=email.getText().toString();
          PASS=password.getText().toString();
          Log.d("TAG","Value of EMAIL:"+EMAIL);
          Log.d("TAG","Value of PASS:"+PASS);
          validateEmail();
          validatePassword();
          if(validateEmail() && validatePassword()){
              Log.d("TAG","Value of Email: "+MainActivity.return_email());
              Log.d("TAG","Value of Password:"+MainActivity.return_password());
          if(MainActivity.return_email().equals(EMAIL) && MainActivity.return_password().equals(PASS)){
            Intent intent=new Intent(Login.this, Dashboard.class);
            startActivity(intent);
              finish();}
          else{
              Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
          }
      }
      });
      btn6.setOnClickListener(view -> {
                Intent intent=new Intent(Login.this, Emaillinklogin.class);
                startActivity(intent);
      });
}
    private boolean validatePassword() {
        if(PASSWORD_PATTERN.matcher(PASS).matches())
        {
            text24.setText("");
            return true;
        }
        else{
            text24.setText("Password should contain at least 8 characters including atleast 1 digit, 1 uppercase letter, 1 lowercase letter, 1 special character and should not contain any white spaces.");
            return  false;}
    }
    private boolean validateEmail() {
        if(EMAIL_ADDRESS.matcher(EMAIL).matches()){
            text23.setText("");
            return true;
        }
        else{
            text23.setText("Invalid Email");
            return false;
        }
    }
}

