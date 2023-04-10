package com.example.demo_lms.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterteacherActivity extends AppCompatActivity {

    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private TextInputLayout txtid;
    private TextInputLayout txtname;
    private TextInputLayout txtusername;
    private RadioGroup Gender;
    private RadioButton Genbutton;
    private TextInputLayout txtemail;
    private TextInputLayout txtcontact;
    private TextInputLayout txtaddress;
    private TextInputLayout txtpass;
    private TextInputLayout txtrpass;
    private Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerteacher);

        txtid = findViewById(R.id.txtid);
        txtname = findViewById(R.id.txtname);
        txtusername = findViewById(R.id.txtusername);
        Gender = (RadioGroup) findViewById(R.id.Gender);
        txtemail = findViewById(R.id.txtemail);
        txtaddress = findViewById(R.id.txtaddress);
        txtcontact = findViewById(R.id.txtcontact);
        txtpass = findViewById(R.id.txtpass);
        txtrpass = findViewById(R.id.txtrpass);
        btnregister = findViewById(R.id.btnregister);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_id = txtid.getEditText().getText().toString();
                String txt_name = txtname.getEditText().getText().toString();
                String txt_username = txtusername.getEditText().getText().toString();
                String txt_email = txtemail.getEditText().getText().toString();
                String txt_address = txtaddress.getEditText().getText().toString();
                String txt_pass = txtpass.getEditText().getText().toString();
                int selectedgender = Gender.getCheckedRadioButtonId();
                Genbutton = findViewById(selectedgender);

                    if(!validateId() | !validateEmail() | !validateUsername() | !validateFullName() | !validateAddress() |!validatePassword() | !validateGender()
                            |!validatePhoneNumber() | !validateRPassword())
                    {
                        Toast.makeText(RegisterteacherActivity.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        database.child("Teacher_master").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if (snapshot.hasChild(txt_id)) {
                                    Toast.makeText(RegisterteacherActivity.this, "ID already Exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    database.child("Teacher_master").child(txt_id).child("Name").setValue(txt_name);
                                    database.child("Teacher_master").child(txt_id).child("Username").setValue(txt_username);
                                    database.child("Teacher_master").child(txt_id).child("Email").setValue(txt_email);
                                    database.child("Teacher_master").child(txt_id).child("Gender").setValue(Genbutton.getText());
                                    database.child("Teacher_master").child(txt_id).child("Contact").setValue(txt_address);
                                    database.child("Teacher_master").child(txt_id).child("Enable").setValue("Yes");

                                    database.child("User_master").child(txt_id).child("Password").setValue(txt_username);
                                    database.child("User_master").child(txt_id).child("Password").setValue(txt_pass);
                                    database.child("User_master").child(txt_id).child("Type").setValue("Teacher");
                                    database.child("User_master").child(txt_id).child("Enable").setValue("Yes");
                                    Toast.makeText(RegisterteacherActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(RegisterteacherActivity.this, "Error in Inserting Data", Toast.LENGTH_SHORT).show();
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

    private boolean validatePassword() {
        String val = txtpass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                ".{4,}";               //at least 4 characters;
        if (val.isEmpty()) {
            txtpass.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            txtpass.setError("Password should contain 4 characters!");
            return false;
        } else {
            txtpass.setError(null);
            txtpass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRPassword() {
        String val = txtrpass.getEditText().getText().toString().trim();
        String val1 = txtrpass.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            txtrpass.setError("Field can not be empty");
            return false;
        } else if (!val1.equals(val)) {
            txtrpass.setError("Password does not match");
            return false;
        } else {
            txtrpass.setError(null);
            txtrpass.setErrorEnabled(false);
            return true;
        }
    }

    public void clear()
    {
        txtid.getEditText().setText("");
        txtname.getEditText().setText("");
        txtusername.getEditText().setText("");
        txtemail.getEditText().setText("");
        txtaddress.getEditText().setText("");
        txtcontact.getEditText().setText("");
        txtpass.getEditText().setText("");
        txtrpass.getEditText().setText("");
        Gender.clearCheck();
    }


}