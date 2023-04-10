package com.example.demo_lms.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.demo_lms.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchStudentActivity extends AppCompatActivity {

    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private Button btnsearch;
    private TextInputLayout txtid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        btnsearch=findViewById(R.id.btnsearch);
        txtid=(TextInputLayout) findViewById(R.id.txtid);
        final Dialog dialog=new Dialog(SearchStudentActivity.this);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_id=txtid.getEditText().getText().toString();
                Intent intent=new Intent(getApplicationContext(),ShowstudentActivity.class);
                intent.putExtra("Student_ID",txt_id);

                dialog.startLoadingDialog();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismissDialog();
                        startActivity(intent);
                    }
                },2000);

        }
        });
    }
}