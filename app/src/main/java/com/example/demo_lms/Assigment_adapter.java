package com.example.demo_lms;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_lms.student.model;
import com.example.demo_lms.student.myadapter;
import com.example.demo_lms.student.viewpdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class Assigment_adapter extends FirebaseRecyclerAdapter<model, Assigment_adapter.myviewholder> {

    public Assigment_adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.header.setText(model.getName());
        holder.Course.setText(model.getCourse());
        holder.Date.setText(model.getSubmissionDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.header.getContext(),viewpdf.class);
                intent.putExtra("filename",model.getName());
                intent.putExtra("fileurl",model.getLocation());
                intent.putExtra("filecourse",model.getCourse());
                intent.putExtra("filesubdate",model.getSubmissionDate());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.header.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assigment_items,parent,false);
        return  new Assigment_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView header;
        TextView Course;
        TextView Date;
        CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            header=itemView.findViewById(R.id.header);
            Course=itemView.findViewById(R.id.Course);
            Date=itemView.findViewById(R.id.Date);
            cardView=itemView.findViewById(R.id.CardView);
        }
    }
}
