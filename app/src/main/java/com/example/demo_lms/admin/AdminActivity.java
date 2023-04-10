package com.example.demo_lms.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demo_lms.R;

public class AdminActivity extends AppCompatActivity {

    private CardView registerteacher;
    private CardView searchstudent;
    private CardView addCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        registerteacher=findViewById(R.id.registerteacher);
        searchstudent=findViewById(R.id.searchstudent);


        registerteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, RegisterteacherActivity.class));            }
        });

        searchstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, SearchStudentActivity.class));

        }
        });

    }
}