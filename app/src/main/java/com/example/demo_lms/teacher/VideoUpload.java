package com.example.demo_lms.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.demo_lms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoUpload extends AppCompatActivity {

    String[]item={"BSCIT"};
    private AutoCompleteTextView Course;
    ArrayAdapter<String> adapterItems;
    private  static final int PICK_VIDEO_REQUEST=1;
    private TextInputLayout name;
    private Button choosebtn;
    private Button uploadbtn;
    private VideoView videoView;
    private Uri videoUri;
    MediaController mediaController;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);

        choosebtn=findViewById(R.id.Choosebtn);
        uploadbtn=findViewById(R.id.Uploadbtn);
        name=findViewById(R.id.txtname);
        videoView=findViewById(R.id.videoView);

        Course=findViewById(R.id.drop);
        adapterItems=new ArrayAdapter<String>(this,R.layout.drop_lis,item);
        Course.setAdapter(adapterItems);
        Course.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String item=adapterView.getItemAtPosition(position).toString();
            }
        });

        mediaController =new MediaController(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadvideo();
            }
        });

    }

    private void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_VIDEO_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            videoUri=data.getData();
            videoView.setVideoURI(videoUri);
        }
    }

    private String getFileExt(Uri videoUri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mineTypeMap=MimeTypeMap.getSingleton();
        return mineTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));
    }

    private void uploadvideo()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        ((ProgressDialog) dialog).setMessage("Uploading");
        dialog.show();
        StorageReference reference=storageReference.child("Video/"+name.getEditText().getText().toString()+"."+getFileExt(videoUri));
        reference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri url=uriTask.getResult();

                String UniqueKey=databaseReference.child("Video_master").push().getKey();
                if(Course.getText().toString().equals("BSCIT"))
                {
                    databaseReference.child("Video_master").child(UniqueKey).child("name").setValue(name.getEditText().getText().toString());
                    databaseReference.child("Video_master").child(UniqueKey).child("Location").setValue(url.toString());
                    databaseReference.child("Video_master").child(UniqueKey).child("Course").setValue(Course.getText().toString());
                    Toast.makeText(VideoUpload.this, "Video Uploaded", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                dialog.setMessage("Uploading:"+(int)progress+"%");
            }
        });
    }

}