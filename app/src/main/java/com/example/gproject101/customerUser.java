package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class customerUser extends AppCompatActivity {

    private FirebaseAuth lauth;
    private EditText email, password;
    private Button logBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_user);
        lauth = FirebaseAuth.getInstance();
        email = findViewById(R.id.logEmail);
        password = findViewById(R.id.logPass);
        logBtn = findViewById(R.id.sigInBtn);
    }

    public void goToLandingPage(View v){
        Intent i4 = new Intent(customerUser.this, landingActivity.class);
        startActivity(i4);
    }

    public void goToRegister(View v){

        Intent i1 = new Intent(customerUser.this, cutomerRegis.class);
        startActivity(i1);
    }

    public void goTOLogin(View v){

        final String em = email.getText().toString().trim();
        String regExForEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regExForEmail);
        Matcher matcher = pattern.matcher(em);
        String passcode = password.getText().toString().trim();




        if(passcode.isEmpty()){
            password.setError("can't leave password field empty");
            password.requestFocus();
            return;
        }

        if(passcode.length()<6){
            password.setError("password should be more than 6 digits");
            password.requestFocus();
            return;
        }

        if(em.isEmpty() && !matcher.matches()){
            email.setError("can;t be empty");
            email.requestFocus();
            return;
        }

        if(!matcher.matches()){
            email.setError("enter email in proper format");
            email.requestFocus();
            return;
        }



        lauth.signInWithEmailAndPassword(em, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(customerUser.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(customerUser.this, loginDonate.class);
                    startActivity(ii);
                        }

                else{
                    Toast.makeText(customerUser.this, "unsuccessfull", Toast.LENGTH_LONG).show();
                }
                    }});
                }

    }
