package com.example.demo_lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demo_lms.admin.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Notice_list extends AppCompatActivity {
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    Retrofit retrofit;
    List<NoticeAdapter> mDeliveryList;
    ProgressDialog progressDialog;
    NoticeAdapter deliveryAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Alert");
        progressDialog.setMessage("Please wait...");

        if (!ApiClient.checkNetworkAvailable(Notice_list.this))
            Toast.makeText(Notice_list.this,"No network",Toast.LENGTH_LONG).show();
        else
            getDeliveryList();
    }

    private void getDeliveryList() {

        progressDialog.show();
        retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        Call<List<NoticeModel>> call = apiInterface.getNotice();
        call.enqueue(new Callback<List<NoticeModel>>() {
            @Override
            public void onResponse(Call<List<NoticeModel>> call, Response<List<NoticeModel>> response) {

                if(response.isSuccessful()){
                   List<NoticeModel> mDeliveryList = response.body();

                        fillRecyclerView(mDeliveryList);
                        progressDialog.dismiss();
                }
            }

            private void fillRecyclerView(List<NoticeModel> mDeliveryList) {

                recyclerView = findViewById(R.id.rvDelvList);
                deliveryAdapter = new NoticeAdapter(Notice_list.this,mDeliveryList);
                layoutManager = new LinearLayoutManager(Notice_list.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(deliveryAdapter);
            }

            @Override
            public void onFailure(Call<List<NoticeModel>> call, Throwable t) {
                Toast.makeText(Notice_list.this,t.toString(),Toast.LENGTH_LONG).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });


    }
}