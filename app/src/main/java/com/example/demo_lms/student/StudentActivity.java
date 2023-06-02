package com.example.demo_lms.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.demo_lms.Assigments_List;
import com.example.demo_lms.Notice_list;
import com.example.demo_lms.R;
import com.example.demo_lms.User_map;
import com.example.demo_lms.quiz;

public class StudentActivity extends AppCompatActivity {

    private CardView Feedback;
    private CardView Notes;
    private CardView Video;
    private CardView Video4;
    private CardView Assignment;
    private CardView Video7;
    private CardView Video8;
    private CardView Profile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student2);


        String txt_ID=getIntent().getStringExtra("IDfromlogin");
        Notes=findViewById(R.id.Notes);
        Video=findViewById(R.id.Video);
        Video4=findViewById(R.id.Video4);
        Assignment=findViewById(R.id.Assignment);
        Video7=findViewById(R.id.Video7);
        Video8=findViewById(R.id.Video8);
        Profile=findViewById(R.id.Profile);


        Video7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add quiz
                startActivity(new Intent(StudentActivity.this, quiz.class));
            }
        });

        Video8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Nearest Liberary
                startActivity(new Intent(StudentActivity.this, User_map.class));
            }
        });



        Assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, Assigments_List.class));
            }
        });

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
        Video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // notice section
                startActivity(new Intent(StudentActivity.this, Notice_list.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivity.this,Studentprofile.class).putExtra("IDfromlogin",txt_ID));
            }
        });

    }
}