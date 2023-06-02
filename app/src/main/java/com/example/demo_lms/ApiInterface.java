package com.example.demo_lms;

import com.example.demo_lms.admin.RespMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {


    String Url="http://192.168.161.21/Learning_Service/Service1.svc/";


    @GET("Feedback/{data}")
    Call<RespMessage> Feedback(@Path("data") String data);


    @GET("getNotice")
    Call<List<NoticeModel>> getNotice();

}
