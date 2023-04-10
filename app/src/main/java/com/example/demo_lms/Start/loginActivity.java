package com.example.demo_lms.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.example.demo_lms.admin.AdminActivity;
import com.example.demo_lms.student.StudentActivity;
import com.example.demo_lms.student.ViewNotes;
import com.example.demo_lms.teacher.AssignmentUpload;
import com.example.demo_lms.teacher.TeacherActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private TextInputLayout ID;
    private TextInputLayout pass;
    private Button login;
    private TextView second;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ID = (TextInputLayout) findViewById(R.id.Class);
        pass = (TextInputLayout) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.btn);
        second = (TextView) findViewById(R.id.second);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID_txt = ID.getEditText().getText().toString();
                String pass_txt = pass.getEditText().getText().toString();

                if (ID_txt.isEmpty() || pass_txt.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Please enter Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    database.child("User_master").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(ID_txt)) {
                                String getPass = snapshot.child(ID_txt).child("Password").getValue().toString();
                                String getEnable = snapshot.child(ID_txt).child("Enable").getValue().toString();
                                if (getEnable.equals("Yes")) {
                                    if (getPass.equals(pass_txt)) {
                                        String type = snapshot.child(ID_txt).child("Type").getValue().toString();
                                        if (type.equals("Admin")) {
                                            Toast.makeText(loginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginActivity.this, AdminActivity.class));
                                            finish();
                                        } else if (type.equals("Student")) {
                                            Toast.makeText(loginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginActivity.this, StudentActivity.class).putExtra("IDfromlogin",ID_txt));
                                            finish();
                                        } else if (type.equals("Teacher")) {
                                            Toast.makeText(loginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(loginActivity.this, TeacherActivity.class));
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(loginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(loginActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(loginActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));

            }
        });
    }
}