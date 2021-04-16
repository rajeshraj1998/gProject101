package com.example.gproject101;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

public class siggnedInUser extends AppCompatActivity {

    TextView disUserName;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    String userID;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siggned_in_user);


        disUserName = (TextView) findViewById(R.id.disUser);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        signOut = (Button) findViewById(R.id.signOut);

        final DocumentReference documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                disUserName.setText("WELCOME "+documentSnapshot.getString("full_name"));

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Toast.makeText(siggnedInUser.this, "Siggning out", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(siggnedInUser.this, customerUser.class);
                startActivity(i2);
            }
        });

    }

    public void goTologinDonate(View v){

        Intent ii = new Intent(this, loginDonate.class);
        startActivity(ii);

    }

    public void goToEditProfile(View v){
        Intent ii = new Intent(this, editProfile.class);
        startActivity(ii);
    }
}