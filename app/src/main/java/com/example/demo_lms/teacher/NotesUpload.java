package com.example.demo_lms.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NotesUpload extends AppCompatActivity {

    EditText editText;
    Button btn;
//
//    StorageReference storageReference;
//    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_upload);

        editText = findViewById(R.id.editText);
        btn = findViewById(R.id.btn);

//        storageReference = FirebaseStorage.getInstance().getReference();
//        databaseReference = FirebaseDatabase.getInstance().getReference("UploadPDF");
        btn.setEnabled(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });

    }

    public void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//        12 && resultCode = RESULT_OK && data = null && data.getData() != null
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            btn.setEnabled(true);
            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDFiles(data);
//                    uploadPDFFileFirebase (data.getData());

                }

            });
        }

    }



    private  void uploadPDFiles(Intent data){

        // Here we are initialising the progress dialog box
       ProgressDialog dialog = new ProgressDialog(this);
        ((ProgressDialog) dialog).setMessage("Uploading");

        // this will show message uploading
        // while pdf is uploading
        dialog.show();
        Uri imageuri = data.getData();
        final String timestamp = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final String messagePushID = timestamp;
        Toast.makeText(this, imageuri.toString(), Toast.LENGTH_SHORT).show();

        // Here we are uploading the pdf in firebase storage with the name of current time
        final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
        Toast.makeText(this, filepath.getName(), Toast.LENGTH_SHORT).show();
        filepath.putFile(imageuri).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // After uploading is done it progress
                    // dialog box will be dismissed
                    dialog.dismiss();
                    Uri uri = task.getResult();
                    String myurl;
                    myurl = uri.toString();
                    Toast.makeText(NotesUpload.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Log.d("Exception","Exe "+task.getException());
                    Toast.makeText(NotesUpload.this, "UploadedFailed"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    private void uploadPDFFileFirebase(Uri data) {
//        final ProgressDialog progressDialog= new ProgressDialog( this);
//        progressDialog.setTitle("File is loading...");
//        progressDialog.show();
//        StorageReference reference =storageReference.child("uploadPDF" +System.currentTimeMillis() +".pdf");
//        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete());
//                Uri uri = uriTask.getResult();
//                puPDF putPDF = new puPDF (editText.getText().toString(), uri.toString());
//                databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
//                Toast.makeText( NotesUpload.this, "File Upload", Toast.LENGTH_LONG).show();
//                progressDialog.dismiss();
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//
//                double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                progressDialog.setMessage("File Uploaded.." +(int)progress+"%");
//            }
//        });
//
//
//    }
}