package com.adityavarshney.bennettmedica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout meettheteam;
    private LinearLayout uploadscan;
    private  LinearLayout symptomdetection;
    private  LinearLayout contactUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        uploadscan = (LinearLayout) findViewById((R.id.uploadScan));
        uploadscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openuploadscan();
            }

        });

        symptomdetection = (LinearLayout) findViewById((R.id.symptomDetection));
        symptomdetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensymptomdetection();
            }

        });


        meettheteam = (LinearLayout) findViewById(R.id.meetTheTeam);
        meettheteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmeettheteam();
            }
        });




        contactUs = (LinearLayout) findViewById((R.id.contactUs));
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencontactUs();
            }

        });

    }

    public void openmeettheteam() {
        Intent intent = new Intent(this, meettheteam.class);
        startActivity(intent);
    }

    public void openuploadscan() {
        Intent intent = new Intent(this, UploadScan.class);
        startActivity(intent);
    }
    public void opensymptomdetection(){
        Intent intent =new Intent(this,SymptomDetection.class);
        startActivity(intent);
    }
    public  void opencontactUs(){
        Intent intent = new Intent(this,ContactUs.class);
        startActivity(intent);
    }
}
