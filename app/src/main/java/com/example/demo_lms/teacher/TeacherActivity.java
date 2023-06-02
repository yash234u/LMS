package com.example.demo_lms.teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
    AlertDialog.Builder builder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        UploadNotes = findViewById(R.id.UploadNotes);
        Assignments=findViewById(R.id.Assignment);
        Video=findViewById(R.id.videoUpload);
        Profile=findViewById(R.id.viewprofile);

        String ID_txt=getIntent().getStringExtra("IDfromlogin");

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
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this,TeacherProfile.class).putExtra("IDfromlogin",ID_txt));
            }
        });

    }

    @Override
    public void onBackPressed() {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.app_name) .setTitle(R.string.app_name);
        builder.setMessage("Do you want to close LMS?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }
}