package com.example.demo_lms.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NotesUpload extends AppCompatActivity {

    EditText editText;
    Button btn;
    TextInputLayout name;
    String[]item={"BSCIT"};
    private AutoCompleteTextView Course;
    ArrayAdapter<String> adapterItems;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_upload);

        editText = findViewById(R.id.editText);
        btn = findViewById(R.id.btn);
        name=findViewById(R.id.txtname);
        Course=findViewById(R.id.drop);
        adapterItems=new ArrayAdapter<String>(this,R.layout.drop_lis,item);
        Course.setAdapter(adapterItems);
        Course.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String item=adapterView.getItemAtPosition(position).toString();
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");

        btn.setEnabled(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFiles();
            }
        });

    }

    private void selectFiles() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            btn.setEnabled(true);

            editText.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(name.getEditText().getText().toString().isEmpty()||Course.getText().toString().isEmpty())
                    {
                        Toast.makeText(NotesUpload.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    UploadFiles(data.getData());
                    }
                }
            });
        }
    }

    private void UploadFiles(Uri data) {
        final ProgressDialog dialog = new ProgressDialog(this);
        ((ProgressDialog) dialog).setMessage("Uploading");
        dialog.show();
        StorageReference reference=storageReference.child("Notes/"+name.getEditText().getText().toString()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri url=uriTask.getResult();

                String UniqueKey=databaseReference.child("Notes_master").push().getKey();
                if(Course.getText().toString().equals("BSCIT"))
                {
                    databaseReference.child("Notes_master").child(UniqueKey).child("name").setValue(name.getEditText().getText().toString());
                    databaseReference.child("Notes_master").child(UniqueKey).child("Location").setValue(url.toString());
                    databaseReference.child("Notes_master").child(UniqueKey).child("Course").setValue(Course.getText().toString());
                    Toast.makeText(NotesUpload.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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

