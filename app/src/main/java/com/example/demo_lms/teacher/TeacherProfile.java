package com.example.demo_lms.teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfile extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    TextView ID_profile,Name_Profile,Email_profile,Phone_profile,Gender_profile,Enable_profile;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        ID_profile=findViewById(R.id.TID_profile);
        Name_Profile=findViewById(R.id.TName_profile);
        Email_profile=findViewById(R.id.TEmail_profile);
        Phone_profile=findViewById(R.id.TPhone_profile);
        Gender_profile=findViewById(R.id.TGender_profile);
        Enable_profile=findViewById(R.id.TEnable_profile);

        String ID_txt=getIntent().getStringExtra("IDfromlogin");

        database.child("Teacher_master").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(ID_txt).exists())
                {
                    String Name_txt=snapshot.child(ID_txt).child("Name").getValue().toString();
                    String Email_txt=snapshot.child(ID_txt).child("Email").getValue().toString();
                    String Phone_txt=snapshot.child(ID_txt).child("Contact").getValue().toString();
                    String Gender_txt=snapshot.child(ID_txt).child("Gender").getValue().toString();
                    String Enable_txt=snapshot.child(ID_txt).child("Enable").getValue().toString();

                    ID_profile.setText(ID_txt);
                    Name_Profile.setText(Name_txt);
                    Email_profile.setText(Email_txt);
                    Phone_profile.setText(Phone_txt);
                    Gender_profile.setText(Gender_txt);
                    Enable_profile.setText(Enable_txt);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherProfile.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}