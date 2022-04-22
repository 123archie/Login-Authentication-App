package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {
 private Button button3;
 FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        button3=findViewById(R.id.button3);
        mAuth=FirebaseAuth.getInstance();
    button3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mAuth.signOut();
            signOutUser();
finish();
        }

        private void signOutUser() {

            FirebaseUser mUser=mAuth.getCurrentUser();
            if(mUser==null){
                Intent intent=new Intent(Dashboard.this, Login.class);
                startActivity(intent);
            }

        }
    });}
}