package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
 private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        button3=findViewById(R.id.button3);

    button3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signout();
        }

        private void signout() {
            FirebaseAuth.getInstance().signOut();
        }
    });}
}