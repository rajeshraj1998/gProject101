package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class loggedInUser extends AppCompatActivity {

    TextView fName, emailId;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    String userID;
    String hha = " ";
    Vector<String> list=new Vector<String>();
   // ArrayList<String> list = new ArrayList<>();
    Iterator ii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user);
       // fName = (TextView) findViewById(R.id.disUserName);
       // emailId = (TextView) findViewById(R.id.disUserEmail);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();




       /* firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("success");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        hha += " " + document.getId();
                        // System.out.println(list);
                    }

                } else {

                }

                ii = list.iterator();
                while (ii.hasNext()) {
                    hha +=" "+ ii.next();
                   // System.out.println(ii.next() + "--");
                }

                fName.setText(hha);
            }

        });


        final DocumentReference documentReference = firestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fName.setText(documentSnapshot.getString("full_name"));
                emailId.setText(documentSnapshot.getString("email"));
            }
        });
        */

    }
}