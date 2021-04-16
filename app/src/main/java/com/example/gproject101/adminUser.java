package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class adminUser extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    boolean adminCheck = false;
    Button sigin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        email = (EditText) findViewById(R.id.adminEmail);
        password = (EditText) findViewById(R.id.adminPass);
        sigin = (Button) findViewById(R.id.adminSigin);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToadminSiggnedIn();
            }
        });

    }

    public void goToadminSiggnedIn() {



        final String em = email.getText().toString().trim();
        String regExForEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regExForEmail);
        Matcher matcher = pattern.matcher(em);
        String passcode = password.getText().toString().trim();

        if (passcode.isEmpty()) {
            password.setError("Entrer the password");
            password.requestFocus();
            return;
        }

        if (em.isEmpty() && !matcher.matches()) {
            email.setError("enter the email");
            email.requestFocus();
            return;
        }

        fAuth.signInWithEmailAndPassword(em, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                        final DocumentReference documentReference = firestore.collection("users").document(fAuth.getCurrentUser().getUid());
                        documentReference.addSnapshotListener(adminUser.this, new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                if (documentSnapshot.getBoolean("admin")) {
                                    Toast.makeText(adminUser.this, "Welcome Admin", Toast.LENGTH_LONG).show();
                                    Intent ii = new Intent(adminUser.this, adminSiggnedIn.class);
                                    startActivity(ii);
                                } else {
                                    Toast.makeText(adminUser.this, "you do not have access to this", Toast.LENGTH_LONG).show();
                                }

                            }
                        });


                }

                else {
                    Toast.makeText(adminUser.this, "unsuccessfull", Toast.LENGTH_LONG).show();
                }
            }
        });



    }


    public void gotToHomeAdmin(View v) {

        Intent ii = new Intent(adminUser.this, landingActivity.class);
        startActivity(ii);
    }


}



