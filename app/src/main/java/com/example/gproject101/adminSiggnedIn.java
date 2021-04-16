package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class adminSiggnedIn extends AppCompatActivity {

    private static final String TAG = "adminSiggnedIn";
    ListView listView;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;

    private DatabaseReference mDatabase;

   // ArrayList<userUids> users = new ArrayList<userUids>();
     ArrayList<String> data = new ArrayList<String>();
    ArrayList<String> data1 = new ArrayList<String>();
   // ArrayList<checkStoreUser> check = new ArrayList<checkStoreUser>();

    String[] arrData;
    String userID;
    int count=1;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    Object cheeee = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_siggned_in);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        listView = (ListView) findViewById(R.id.dis_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String ddaa;


 /*      mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {


               Object jj = snapshot.child("users").getValue();
               String usersss = (snapshot.child("nonAdmin").getValue()).toString();
               String pp;

               usersss= usersss.replace("{", " ");
               usersss=usersss.replace("}", " ");
               usersss=usersss.replace("-", " ");
               usersss.trim();
               String[] stArr = usersss.split(",");



               for(int i=0;i<stArr.length;i++){
                   stArr[i] = stArr[i].substring(22);
                   System.out.println(stArr[i]);
                   System.out.println((snapshot.child("users").child(stArr[i]).getValue()));
               }

               System.out.println(stArr[2]);

              Iterator itr = data.iterator();
               while(itr.hasNext())
                   System.out.println(itr.next());



           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



  */






       firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (final QueryDocumentSnapshot document : task.getResult()) {

                       data1.add((String)document.getId());

                    }


                } else {
                    Toast.makeText(adminSiggnedIn.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(adminSiggnedIn.this, adminUser.class));
                }




                Iterator itt = data1.iterator();
                while(itt.hasNext()){

                    DocumentReference documentReference = firestore.collection("users").document((String) itt.next());
                    documentReference.addSnapshotListener(adminSiggnedIn.this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if(!snapshot.getBoolean("admin")){

                                System.out.println("Name:"+snapshot.getString("full_name")+"addr:"+snapshot.getString("address")+"ph:"+snapshot.getString("phone"));
                                 data.add("Name: "+snapshot.getString("full_name")+" "+"addr: "+snapshot.getString("address")+" "+"ph: "+snapshot.getString("phone")+" "
                                 +"venue: "+snapshot.getString("date")+" At "+ snapshot.getString("time"));
                      }

                            String stringArr[] = new String[data.size()];
                            data.toArray(stringArr);

                            ArrayAdapter adapter = new ArrayAdapter<String>(adminSiggnedIn.this,
                                    R.layout.activitylistview, stringArr);


                            listView.setAdapter(adapter);




                        }


                    });

                    String stringArr[] = new String[data.size()];
                    data1.toArray(stringArr);

                    ArrayAdapter adapter = new ArrayAdapter<String>(adminSiggnedIn.this,
                            R.layout.activitylistview, stringArr);


                    listView.setAdapter(adapter);

                }





            }





        });



/*
        String[] stringArr = new String[data.size()];
        data.toArray(stringArr);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activitylistview, mobileArray);


        listView.setAdapter(adapter);*/



    }
}