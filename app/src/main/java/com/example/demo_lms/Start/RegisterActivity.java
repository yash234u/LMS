package com.example.demo_lms.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_lms.R;
import com.example.demo_lms.admin.RegisterteacherActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String[]item={"BSCIT"};
    private AutoCompleteTextView Course;
    ArrayAdapter<String> adapterItems;
    private TextInputLayout txtid;
    private TextInputLayout txtname;

    private TextInputLayout txtemail;
    private RadioGroup Gender;
    private RadioButton Genbutton;
    private TextInputLayout txtcontact;
    private TextInputLayout Address;
    private TextInputLayout txtpass;
    private TextInputLayout txtrpass;
    private Button Register;
    private TextView second;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtid=findViewById(R.id.txtid);
        txtname=findViewById(R.id.txtname);
        txtemail=findViewById(R.id.txtemail);
        Gender=(RadioGroup)findViewById(R.id.Gender);
        txtcontact=findViewById(R.id.txtcontact);
        Address=findViewById(R.id.Address);
        txtpass=findViewById(R.id.txtpass);
        txtrpass=findViewById(R.id.txtrpass);
        Register=(Button)findViewById(R.id.Register);
        second=(TextView) findViewById(R.id.second);

        Course=findViewById(R.id.drop);
        adapterItems=new ArrayAdapter<String>(this,R.layout.drop_lis,item);
        Course.setAdapter(adapterItems);
        Course.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String item=adapterView.getItemAtPosition(position).toString();
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID_txt=txtid.getEditText().getText().toString();
                String Name_txt=txtname.getEditText().getText().toString();
                String Email_txt=txtemail.getEditText().getText().toString();
                String Contact_txt=txtcontact.getEditText().getText().toString();
                String Course_txt=Course.getText().toString();
                String Address_txt=Address.getEditText().getText().toString();
                String Pass_txt=txtpass.getEditText().getText().toString();
                String Rpass_txt=txtrpass.getEditText().getText().toString();
                int selectedgender=Gender.getCheckedRadioButtonId();
                Genbutton=findViewById(selectedgender);

                if(!validateId() | !validateEmail()  | !validateFullName() |!validatePassword() | !validateGender()
                        |!validatePhoneNumber() | !validateRPassword()| !validateADD() | !validateCourse())
                {
                    Toast.makeText(RegisterActivity.this, "Please Fill all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.child("Student_master").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(ID_txt))
                            {
                                Toast.makeText(RegisterActivity.this,"ID already Exists",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                database.child("Student_master").child(ID_txt).child("Name").setValue(Name_txt);
                                database.child("Student_master").child(ID_txt).child("Email").setValue(Email_txt);
                                database.child("Student_master").child(ID_txt).child("Gender").setValue(Genbutton.getText());
                                database.child("Student_master").child(ID_txt).child("Contact").setValue(Contact_txt);
                                database.child("Student_master").child(ID_txt).child("Course").setValue(Course_txt);
                                database.child("Student_master").child(ID_txt).child("Address").setValue(Address_txt);
                                database.child("Student_master").child(ID_txt).child("Enable").setValue("Yes");

                                database.child("User_master").child(ID_txt).child("Password").setValue(Pass_txt);
                                database.child("User_master").child(ID_txt).child("Type").setValue("Student");
                                database.child("User_master").child(ID_txt).child("Enable").setValue("Yes");
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                clear();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterActivity.this, "Error in Inserting Data", Toast.LENGTH_SHORT).show();
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
    private boolean validateADD() {
        String val = Address.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Address.setError("Field can not be empty");
            return false;
        } else {
            Address.setError(null);
            Address.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateCourse() {
        String val = Course.getText().toString().trim();
        if (val.isEmpty()) {
            Course.setError("Field can not be empty");
            return false;
        } else {
            Course.setError(null);
            //Course.setErrorEnabled(false);
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
        Gender.clearCheck();
        txtemail.getEditText().setText("");
        txtcontact.getEditText().setText("");
        Address.getEditText().setText("");
        Course.clearListSelection();
        txtpass.getEditText().setText("");
        txtrpass.getEditText().setText("");
        Course.setText("");
    }
}