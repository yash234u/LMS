package com.example.demo_lms.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demo_lms.R;
import com.example.demo_lms.Start.loginActivity;

public class TeacherActivity extends AppCompatActivity {

    private CardView UploadNotes;
    private CardView Assignments;
    private CardView Video;
    private CardView Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        UploadNotes = findViewById(R.id.UploadNotes);
        Assignments=findViewById(R.id.Assignment);
        Video=findViewById(R.id.videoUpload);

        UploadNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this, NotesUpload.class));
            }
        });
        Assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this, AssignmentUpload.class));
            }
        });
        Video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherActivity.this,VideoUpload.class));
            }
        });

    }
}