package com.example.demo_lms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo_lms.Start.MainActivity;
import com.example.demo_lms.admin.AdminActivity;
import com.example.demo_lms.admin.ApiClient;
import com.example.demo_lms.admin.RespMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Add_notice extends AppCompatActivity {

    ProgressDialog dialog;
    int resp;
    ApiInterface apiInterface;
    Retrofit retrofit;
    Button btnSubmit;
    EditText editcomment;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        progressDialog = new ProgressDialog(this);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        editcomment=(EditText)findViewById(R.id.edit_comment);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getfeedback();
            }

            private void getfeedback() {

                retrofit = ApiClient.getClient();
                apiInterface = retrofit.create(ApiInterface.class);
                EditText Usercomment =(EditText)findViewById(R.id.edit_comment);
                final  String comment=Usercomment.getText().toString().trim();

                if(Usercomment.length()!=0)
                {
                    if(!ApiClient.checkNetworkAvailable(Add_notice.this))
                        Toast.makeText(Add_notice.this,"No network",Toast.LENGTH_LONG).show();
                    else {

                        progressDialog.show();
                        Call<RespMessage> call = apiInterface.Feedback(comment);
                        call.enqueue(new Callback<RespMessage>() {
                            @Override
                            public void onResponse(Call<RespMessage> call, Response<RespMessage> response) {

                                if (response.isSuccessful()) {
                                    RespMessage rd = response.body();
                                    String message = rd.getMessage();
                                    Toast.makeText(Add_notice.this, message, Toast.LENGTH_LONG).show();
                                    //where you wana take
                                    Intent intent = new Intent(Add_notice.this, AdminActivity.class);

                                    startActivity(intent);

                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<RespMessage> call, Throwable t) {
                                Toast.makeText(Add_notice.this, t.toString(), Toast.LENGTH_LONG).show();
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                            }

                        });

                    }}else{
                    Toast.makeText(Add_notice.this, "Enter Feedbach", Toast.LENGTH_SHORT).show();
            }


    }

        });
    }
    }