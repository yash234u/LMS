package com.example.demo_lms.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demo_lms.R;

public class TeacherActivity extends AppCompatActivity {

    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        cardView = findViewById(R.id.UploadNotes);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNotesUpload();
            }
        });
    }

    private  void  goToNotesUpload(){
        Intent i = new Intent(this,NotesUpload.class);
        startActivity(i);
    }
}