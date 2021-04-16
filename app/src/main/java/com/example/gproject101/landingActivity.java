package com.example.gproject101;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class landingActivity extends AppCompatActivity {

    Button adminUser, customerUSer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        adminUser = (Button) findViewById(R.id.adminLogin);
        customerUSer = (Button) findViewById(R.id.customerLogin);



        adminUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(landingActivity.this, adminUser.class);
                startActivity(i1);
            }
        });

        customerUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(landingActivity.this, customerUser.class);
                startActivity(i2);
            }
        });


    }
}