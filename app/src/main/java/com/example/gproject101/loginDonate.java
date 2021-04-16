package com.example.gproject101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class loginDonate extends AppCompatActivity {

    EditText addr, foodContent;
    TextView date, time;

    Button submit;
    String address, fdcon, loca, DateFull,TimeFull,  userID, Name = "", emai= "", phone= "";
    Boolean admin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_donate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        addr = (EditText) findViewById(R.id.addrget);
        foodContent = (EditText) findViewById(R.id.fdcon);

        submit = (Button) findViewById(R.id.submit);

        date = (TextView) findViewById(R.id.in_date);
        time = (TextView) findViewById(R.id.in_time);




        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        firestore = FirebaseFirestore.getInstance();


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(loginDonate.this,new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                DateFull = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(loginDonate.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText(hourOfDay + ":" + minute);
                                TimeFull = hourOfDay + ":" + minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        userID = mAuth.getCurrentUser().getUid();

        try {
            final DocumentReference documentReference = firestore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                    Name = documentSnapshot.getString("full_name");
                    emai = documentSnapshot.getString("email");
                    phone = documentSnapshot.getString("phone");
                    admin = documentSnapshot.getBoolean("admin");

                }
            });
        }
        catch (Exception e){

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address = addr.getText().toString();
                fdcon = foodContent.getText().toString();


                DocumentReference documentReference = fstore.collection("users").document(userID);
                Map<String, Object> userss = new HashMap<>();
                userss.put("full_name", Name);
                userss.put("email", emai);
                userss.put("phone", phone);
                userss.put("admin", admin);
                userss.put("date", DateFull);
                userss.put("time", TimeFull);
                userss.put("address", address);
                userss.put("food_content", fdcon);
                documentReference.set(userss).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(loginDonate.this, "User Account creation SuccessFull", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginDonate.this, "User Account creation unSuccessfull", Toast.LENGTH_LONG).show();
                    }
                });

                Intent i1 = new Intent(loginDonate.this, customerUser.class);
                startActivity(i1);
            }
        });

    }
}