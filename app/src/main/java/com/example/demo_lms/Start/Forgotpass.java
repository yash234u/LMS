package com.example.demo_lms.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forgotpass extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    TextInputLayout phone,id;
    Button btn;String Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        phone=findViewById(R.id.ID_phone);
        id=findViewById(R.id.ID_pass);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("Student_master").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String ID_txt_data=id.getEditText().getText().toString();
                        String Phone_txt_data=phone.getEditText().getText().toString();
                        if(snapshot.child(ID_txt_data).exists())
                        {
                            String data=snapshot.child(ID_txt_data).child("Contact").getValue().toString();
                            if(Phone_txt_data.equals(data))
                            {
                                database.child("User_master").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child(ID_txt_data).exists())
                                        {
                                            String data2=snapshot.child(ID_txt_data).child("Password").getValue().toString();
                                            Message ="Message from LMS"+"\n"+"Your Password is"+" "+'"'+data2+'"'+"\n"+"For ID"+" "+ID_txt_data;
                                            if(ContextCompat.checkSelfPermission(Forgotpass.this, Manifest.permission.SEND_SMS)==
                                                    PackageManager.PERMISSION_GRANTED)
                                            {
                                                sendSMS();
                                                clear();
                                            }
                                            else
                                            {
                                                ActivityCompat.requestPermissions(Forgotpass.this,new String[]{Manifest.permission.SEND_SMS},100);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Forgotpass.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(Forgotpass.this, "Phone Number does not match with ID", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Forgotpass.this, "ID Does not exits", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Forgotpass.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void sendSMS()
    {
        String phone_txt=phone.getEditText().getText().toString();
        String ID_txt=id.getEditText().getText().toString();

        if(!phone_txt.isEmpty()||!ID_txt.isEmpty())
        {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage("+91"+phone_txt,null,Message,null,null);
            Toast.makeText(this, "Message Send Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
    }
    private void clear()
    {
        phone.getEditText().setText("");
        id.getEditText().setText("");
    }
}