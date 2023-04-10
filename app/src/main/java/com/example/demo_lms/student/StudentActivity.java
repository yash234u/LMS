package com.example.demo_lms.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.demo_lms.R;

public class StudentActivity extends AppCompatActivity {

    private CardView Feedback;
    private CardView Notes;
    private CardView Video;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student2);


        String txt_ID=getIntent().getStringExtra("IDfromlogin");
        Notes=findViewById(R.id.Notes);
        Video=findViewById(R.id.Video);

       Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this,ViewNotes.class));
            }
        });
       Video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(StudentActivity.this, ViewVIdeo.class).putExtra("IDfromlogin",txt_ID));
           }
       });

    }
}