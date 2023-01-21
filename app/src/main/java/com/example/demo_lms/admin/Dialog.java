package com.example.demo_lms.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.demo_lms.R;

public class Dialog {

    Activity activity;
    AlertDialog dialog;

    Dialog(Activity myActivity)
    {
        activity=myActivity;
    }

    void startLoadingDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alertdialoug,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }

    void dismissDialog()
    {
        dialog.dismiss();
    }
}
