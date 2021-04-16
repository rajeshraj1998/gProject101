package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cutomerRegis extends AppCompatActivity {

    private static final String TAG = "cutomerRegis";
    private EditText name;
    private EditText ph;
    private EditText email;
    private EditText password;
    private Button regbtn;
    private ProgressBar prg;
    private String pass = null;
    String userID;


    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_regis);

        name = (EditText) findViewById(R.id.inpfullName);
        ph = (EditText) findViewById(R.id.inpph);
        email = (EditText) findViewById(R.id.inpemail);
        password = (EditText) findViewById(R.id.inppass);
        regbtn = (Button) findViewById(R.id.regisbtn);
        prg = (ProgressBar) findViewById(R.id.loadingreg) ;


        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        prg.setVisibility(View.GONE);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    public void registerUser(){

        final String em = email.getText().toString().trim();
        String regExForEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regExForEmail);
        Matcher matcher = pattern.matcher(em);

        final String nam = name.getText().toString().trim();
        String passcode = password.getText().toString().trim();
        final String phnm = ph.getText().toString().trim();


        if(nam.isEmpty()){
            name.setError("Full name is required");
            name.requestFocus();
            return;
        }

        if(phnm.isEmpty()){
            ph.setError("age is required");
            ph.requestFocus();
            return;
        }
        if(passcode.isEmpty()){
            password.setError("Entrer the password");
            password.requestFocus();
            return;
        }

        if(em.isEmpty() && !matcher.matches()){
            email.setError("enter the email");
            email.requestFocus();
            return;
        }


        prg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(em, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    user usr = new user(nam, em, phnm, false);
                   // mDatabase.child("users_id").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    mDatabase.child("nonAdmin").push().setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            prg.setVisibility(View.GONE);
                            Toast.makeText(cutomerRegis.this, "User has been registered successfully", Toast.LENGTH_LONG).show();



                                userID = mAuth.getCurrentUser().getUid();

                                /*****************************************************************************************************************************
                                 ******************************************* Creating the document reference for the user's Data *****************************
                                 *****************************************************************************************************************************/

                                DocumentReference documentReference = fstore.collection("users").document(userID);



                            /*****************************************************************************************************************************
                             ************************************  USing the hash map (JAVA COLLECTIONS) *************************************************
                             *****************************************************************************************************************************/

                                Map<String, Object> userss = new HashMap<>();
                                userss.put("full_name", nam);
                                userss.put("email", em);
                                userss.put("phone", phnm);
                                userss.put("admin", false);







                                documentReference.set(userss).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(cutomerRegis.this, "User Account creation SuccessFull", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(cutomerRegis.this, "User Account creation  unSuccessfull", Toast.LENGTH_LONG).show();
                                    }
                                });

                                Intent ii = new Intent(cutomerRegis.this, customerUser.class );
                                startActivity(ii);
                        }
                    });
                }
                else{
                    Toast.makeText(cutomerRegis.this, "User already exist", Toast.LENGTH_LONG).show();
                    prg.setVisibility(View.GONE);
                }
            }

        });

    }


}