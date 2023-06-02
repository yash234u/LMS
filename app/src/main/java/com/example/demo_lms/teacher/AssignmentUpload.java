package com.example.demo_lms.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AssignmentUpload extends AppCompatActivity {

    EditText editText;
    Button btn;
    TextInputLayout name;
    TextInputEditText date;
    int year,month,dayofmonth;
    String[]item={"BSCIT"};
    private AutoCompleteTextView Course;
    ArrayAdapter<String> adapterItems;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_upload);

        editText = findViewById(R.id.editText);
        btn = findViewById(R.id.btn);
        name=findViewById(R.id.txtname);
        date=findViewById(R.id.date);
        Intent intent=getIntent();
        String ID_txt=intent.getStringExtra("IDfromlogin");

        Course=findViewById(R.id.drop);
        adapterItems=new ArrayAdapter<String>(this,R.layout.drop_lis,item);
        Course.setAdapter(adapterItems);
        Course.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String item=adapterView.getItemAtPosition(position).toString();
            }
        });

        final Calendar calendar=Calendar.getInstance();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AssignmentUpload.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,dayofmonth);
                datePickerDialog.show();
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
                    if(name.getEditText().getText().toString().isEmpty()||Course.getText().toString().isEmpty()
                            ||date.getEditableText().toString().isEmpty())
                    {
                        Toast.makeText(AssignmentUpload.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    UploadFiles(data.getData());}
                }
            });
        }
    }

    private void UploadFiles(Uri data) {
       final ProgressDialog dialog = new ProgressDialog(this);
        ((ProgressDialog) dialog).setMessage("Uploading");
        dialog.show();
        StorageReference reference=storageReference.child("Assignments/"+name.getEditText().getText().toString()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri url=uriTask.getResult();

                String UniqueKey=databaseReference.child("Video_master").push().getKey();
                if(Course.getText().toString().equals("BSCIT")) {
                    databaseReference.child("Assignments_master").child(UniqueKey).child("name").setValue(name.getEditText().getText().toString());
                    databaseReference.child("Assignments_master").child(UniqueKey).child("Location").setValue(url.toString());
                    databaseReference.child("Assignments_master").child(UniqueKey).child("Course").setValue(Course.getText().toString());
                    databaseReference.child("Assignments_master").child(UniqueKey).child("SubmissionDate").setValue(date.getText().toString());
                    Toast.makeText(AssignmentUpload.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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