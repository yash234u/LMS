package com.example.demo_lms.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowstudentActivity extends AppCompatActivity {

    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private TextInputLayout txtid;
    private TextInputLayout txtname;
    private TextInputLayout txtusername;
    private TextInputLayout txtemail;
    private TextInputLayout txtcontact;
    private TextInputLayout txtaddress;
    private TextInputLayout txtcourse;
    private TextInputLayout txtenable;
    private RadioGroup Gender;
    private RadioButton Genbutton;
    private RadioButton Genmalebutton;
    private RadioButton Genfemalebutton;
    private RadioButton Genotherbutton;
    private Button btnupdate;
    private RadioGroup Enable;
    private RadioButton Enbutton;
    private RadioButton Enyesbutton;
    private RadioButton Ennobutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showstudent);

        Intent intent=getIntent();
        String txt_identered=intent.getStringExtra("Student_ID");

        txtid=findViewById(R.id.txtid);
        txtname=findViewById(R.id.txtname);
        txtusername=findViewById(R.id.txtusername);
        txtemail=findViewById(R.id.txtemail);
        txtcontact=findViewById(R.id.txtcontact);
        txtaddress=findViewById(R.id.txtaddress);
        txtcourse=findViewById(R.id.txtcourse);
        Gender=(RadioGroup)findViewById(R.id.Gender);
        Genmalebutton=findViewById(R.id.male);
        Genfemalebutton=findViewById(R.id.female);
        Genotherbutton=findViewById(R.id.others);
        Enable=(RadioGroup)findViewById(R.id.Enable);
        Enyesbutton=findViewById(R.id.Yes);
        Ennobutton=findViewById(R.id.No);
        btnupdate=findViewById(R.id.btnupdate);

        database.child("Student_master").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(txt_identered).exists())
                {
                    String txtnameDB = snapshot.child(txt_identered).child("Name").getValue().toString();
                    String txtemailDB = snapshot.child(txt_identered).child("Email").getValue().toString();
                    String txtusernameDB = snapshot.child(txt_identered).child("Username").getValue().toString();
                    String txtcontactDB = snapshot.child(txt_identered).child("Contact").getValue().toString();
                    String txtaddressDB = snapshot.child(txt_identered).child("Address").getValue().toString();
                    String txtenableDB = snapshot.child(txt_identered).child("Enable").getValue().toString();
                    String txtgenderDB = snapshot.child(txt_identered).child("Gender").getValue().toString();
                    String txtcourseDB = snapshot.child(txt_identered).child("Course").getValue().toString();

                    if(txtgenderDB.equals("Male"))
                    {
                        Genmalebutton.setChecked(true);
                    }
                    else if(txtgenderDB.equals("Female"))
                    {
                        Genfemalebutton.setChecked(true);
                    }
                    else
                    {
                        Genotherbutton.setChecked(true);
                    }

                    if(txtenableDB.equals("Yes"))
                    {
                        Enyesbutton.setChecked(true);
                    }
                    else
                    {
                        Ennobutton.setChecked(true);
                    }

                    txtid.getEditText().setText(txt_identered);
                    txtname.getEditText().setText(txtnameDB);
                    txtusername.getEditText().setText(txtusernameDB);
                    txtemail.getEditText().setText(txtemailDB);
                    txtcontact.getEditText().setText(txtcontactDB);
                    txtaddress.getEditText().setText(txtaddressDB);
                    txtcourse.getEditText().setText(txtcourseDB);
                }
                else
                {
                    Toast.makeText(ShowstudentActivity.this, "Student Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowstudentActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_id=txtid.getEditText().getText().toString();
                String txt_name=txtname.getEditText().getText().toString();
                String txt_username=txtusername.getEditText().getText().toString();
                String txt_email=txtemail.getEditText().getText().toString();
                String txt_contact=txtcontact.getEditText().getText().toString();
                String txt_address=txtaddress.getEditText().getText().toString();

                int selectedgender=Gender.getCheckedRadioButtonId();
                Genbutton=findViewById(selectedgender);

                int selectedenable=Enable.getCheckedRadioButtonId();
                Enbutton=findViewById(selectedenable);

                if(!validateId() | !validateUsername() | !validateFullName() | !validateGender() |!validateAddress() |!validatePhoneNumber() | !validateEmail() | !validateEnable())
                {
                    Toast.makeText(ShowstudentActivity.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.child("Student_master").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(txt_id))
                            {
                                database.child("Student_master").child(txt_id).child("Name").setValue(txt_name);
                                database.child("Student_master").child(txt_id).child("Username").setValue(txt_username);
                                database.child("Student_master").child(txt_id).child("Email").setValue(txt_email);
                                database.child("Student_master").child(txt_id).child("Gender").setValue(Genbutton.getText());
                                database.child("Student_master").child(txt_id).child("Contact").setValue(txt_contact);
                                database.child("Student_master").child(txt_id).child("Address").setValue(txt_address);
                                database.child("Student_master").child(txt_id).child("Enable").setValue(Enbutton.getText());

                                database.child("User_master").child(txt_username).child("Enable").setValue(Enbutton.getText());
                                Toast.makeText(ShowstudentActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ShowstudentActivity.this, SearchStudentActivity.class));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ShowstudentActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validateId() {
        String val = txtid.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            txtid.setError("Field can not be empty");
            return false;
        } else {
            txtid.setError(null);
            txtid.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {
        String val = txtname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            txtname.setError("Field can not be empty");
            return false;
        } else {
            txtname.setError(null);
            txtname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = txtusername.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            txtusername.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            txtusername.setError("Username is too large!");
            return false;
        } else {
            txtusername.setError(null);
            txtusername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = txtemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            txtemail.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail) || !val.endsWith(".com")) {
            txtemail.setError("Invalid Email!");
            return false;
        } else {
            txtemail.setError(null);
            txtemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String val = txtcontact.getEditText().getText().toString().trim();
        String MobilePattern = "[0-9]{10}";
        if (val.isEmpty()) {
            txtcontact.setError("Field can not be empty");
            return false;
        } else if (!val.matches(MobilePattern)) {
            txtcontact.setError("Invalid Number!");
            return false;
        } else {
            txtcontact.setError(null);
            txtcontact.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender() {
        if (Gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEnable() {
        if (Enable.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Enable", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAddress() {
        String val = txtaddress.getEditText().getText().toString();
        if (val.isEmpty()) {
            txtaddress.setError("Field can not be empty");
            return false;
        } else {
            txtaddress.setError(null);
            txtaddress.setErrorEnabled(false);
            return true;
        }
    }

}