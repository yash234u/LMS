package com.example.demo_lms.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.demo_lms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewVIdeo extends AppCompatActivity {

    private ArrayList<modelvideo> videoArrayList;
    private VideoListAdpater adapterVideo;
    private RecyclerView videosRv;
    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/Student_master");

    String tutor = "";
    String courseTitle = "";
    public String Details="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_video);

        videosRv = findViewById(R.id.courseRv);

        Intent intent = getIntent();
        tutor = intent.getStringExtra("tutor");
        courseTitle = intent.getStringExtra("courseTitle");

        String txt_ID=getIntent().getStringExtra("IDfromlogin");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(txt_ID))
                {
                    Details=snapshot.child(txt_ID).child("Course").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        videoArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Video_master");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    modelvideo modelVideo = ds.getValue(modelvideo.class);
                    videoArrayList.add(modelVideo);
                }
                adapterVideo = new VideoListAdpater(ViewVIdeo.this, videoArrayList);
                videosRv.setAdapter(adapterVideo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}