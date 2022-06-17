package com.example.loginapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
 private EditText email;
 private EditText password;
 private Button button5;
 private String Email;
 private String Password;
 private TextView textViewcreateOne;
 private Button btn6;
 private TextView text23;
 private TextView text24;

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

      textViewcreateOne.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(Login.this, MainActivity.class);
              startActivity(intent);
              textViewcreateOne.setText(Html.fromHtml("<u>Create One</u>"));
              }});

      button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EMAIL=email.getText().toString();

                PASS=password.getText().toString();
                Log.d("TAG","Value of EMAIL:"+EMAIL);
                Log.d("TAG","Value of PASS:"+PASS);

                validateEmail();
                validatePassword();
                if(validateEmail() && validatePassword()){
                 Email=

                Log.d("TAG","Value of Email:"+Email);
                Log.d("TAG","Value of Password:"+Password);

                if(Email.equals(EMAIL) && Password.equals(PASS)){
                  Intent intent=new Intent(Login.this, Dashboard.class);
                  startActivity(intent);
                    finish();}
                else{
                    Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            }


      });
      btn6.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                    Intent intent=new Intent(Login.this, Emaillinklogin.class);
                    startActivity(intent);

          }
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

