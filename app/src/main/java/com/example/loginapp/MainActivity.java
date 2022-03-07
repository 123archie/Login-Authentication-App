package com.example.loginapp;

import static android.util.Patterns.EMAIL_ADDRESS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;



import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
String email;
String password;
    private static  final Pattern PASSWORD_PATTERN=
        Pattern.compile("^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{8,20}" +
                "$");
    private EditText editTextTextPersonName3;
    private EditText editTextTextEmailAddress;
    private TextView textView9;
    private EditText editTextTextPassword;
    private TextView textView4;
    private EditText editTextTextPassword2;
    private TextView textView10;
    com.hbb20.CountryCodePicker ccp;
    private EditText editTextNumber;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTextPersonName3=findViewById(R.id.editTextTextPersonName3);
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        textView9=findViewById(R.id.textView9);
        editTextTextPassword=findViewById(R.id.editTextTextPassword);
        textView4=findViewById(R.id.textView4);
        editTextTextPassword2=findViewById(R.id.editTextTextPassword2);
        textView10=findViewById(R.id.textView10);
        ccp=findViewById(R.id.ccp);


        editTextNumber=findViewById(R.id.editTextNumber);
        ccp.registerCarrierNumberEditText(editTextNumber);


        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String name=editTextTextPersonName3.getText().toString();

           validateEmail();
           validatePassword();
           confirmPassword();
           if(validateEmail() && validatePassword() && confirmPassword()){
           Intent intent=new Intent(MainActivity.this, LoginactivityOtpverificationActivity.class);
           intent.putExtra("name",name);
           intent.putExtra("email",email);
           intent.putExtra("password",password);
           intent.putExtra("phone",ccp.getFullNumberWithPlus().trim());
           
           startActivity(intent);

            }}

        });}

        private boolean validateEmail()
    {
           email=editTextTextEmailAddress.getText().toString();
            if(EMAIL_ADDRESS.matcher(email).matches()){
               textView9.setText("");
               return true;

        }
            else{
                 textView9.setText("Invalid Email");
                 return false;
            }
    }
 private boolean validatePassword(){
     password = editTextTextPassword.getText().toString();
     if(PASSWORD_PATTERN.matcher(password).matches())
        {
         textView4.setText("");
         return true;
        }
        else{
          textView4.setText("Password should contain at least 8 characters including atleast 1 digit, 1 uppercase letter, 1 lowercase letter, 1 special character and should not contain any white spaces.");
        return  false;}
 }
    private boolean confirmPassword(){
        String passwordc=editTextTextPassword2.getText().toString();
        if(PASSWORD_PATTERN.matcher(passwordc).matches())
        {
            textView10.setText("");
            return true;
        }
        else{
            textView10.setText("Password should contain at least 8 characters including atleast 1 digit, 1 uppercase letter, 1 lowercase letter, 1 special character and should not contain any white spaces.");
            return  false;}
    }


}
