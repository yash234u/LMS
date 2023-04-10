package com.example.demo_lms.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference database= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-lms-77a72-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String[]item={"BSCIT","BSCCS",};
    private AutoCompleteTextView Course;
    ArrayAdapter<String> adapterItems;
    private EditText ID;
    private EditText Name;
    private EditText Username;
    private EditText Email;
    private RadioGroup Gender;
    private RadioButton Genbutton;
    private EditText Contact;
    private EditText Address;
    private EditText Pass;
    private EditText Rpass;
    private Button Register;
    private TextView second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ID=(EditText)findViewById(R.id.ID);
        Name=(EditText)findViewById(R.id.Name);
        Username=(EditText)findViewById(R.id.Username);
        Email=(EditText)findViewById(R.id.Email);
        Gender=(RadioGroup)findViewById(R.id.Gender);
        Contact=(EditText)findViewById(R.id.Contact);
        Address=(EditText)findViewById(R.id.Address);
        Pass=(EditText)findViewById(R.id.Pass);
        Rpass=(EditText)findViewById(R.id.Rpass);
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
                String ID_txt=ID.getText().toString();
                String Name_txt=Name.getText().toString();
                String Username_txt=Username.getText().toString();
                String Email_txt=Email.getText().toString();
                String Contact_txt=Contact.getText().toString();
                String Course_txt=Course.getText().toString();
                String Address_txt=Address.getText().toString();
                String Pass_txt=Pass.getText().toString();
                String Rpass_txt=Rpass.getText().toString();
                int selectedgender=Gender.getCheckedRadioButtonId();
                Genbutton=findViewById(selectedgender);

                if(ID_txt.isEmpty() || Name_txt.isEmpty() || Username_txt.isEmpty() || Email_txt.isEmpty() || Contact_txt.isEmpty() || Course_txt.isEmpty() || Address_txt.isEmpty() || Pass_txt.isEmpty() || Rpass_txt.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this,"Please fill all the Details",Toast.LENGTH_SHORT).show();
                }
                else if(!Pass_txt.equals(Rpass_txt))
                {
                    Toast.makeText(RegisterActivity.this,"Password are not Matching",Toast.LENGTH_SHORT).show();
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
                                database.child("Student_master").child(ID_txt).child("Username").setValue(Username_txt);
                                database.child("Student_master").child(ID_txt).child("Email").setValue(Email_txt);
                                database.child("Student_master").child(ID_txt).child("Gender").setValue(Genbutton.getText());
                                database.child("Student_master").child(ID_txt).child("Contact").setValue(Contact_txt);
                                database.child("Student_master").child(ID_txt).child("Course").setValue(Course_txt);
                                database.child("Student_master").child(ID_txt).child("Address").setValue(Address_txt);
                                database.child("Student_master").child(ID_txt).child("Enable").setValue("Yes");

                                database.child("User_master").child(ID_txt).child("Username").child(Username_txt);
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



    public void clear()
    {
        ID.setText("");
        Name.setText("");
        Gender.clearCheck();
        Email.setText("");
        Contact.setText("");
        Address.setText("");
        Course.clearListSelection();
        Pass.setText("");
        Rpass.setText("");
    }
}