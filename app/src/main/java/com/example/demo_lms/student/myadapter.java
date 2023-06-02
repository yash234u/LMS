package com.example.demo_lms.student;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_lms.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {


    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.header.setText(model.getName());
        holder.Course.setText(model.getCourse());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.header.getContext(),viewpdf.class);
                intent.putExtra("filename",model.getName());
                intent.putExtra("fileurl",model.getLocation());
                intent.putExtra("filecourse",model.getCourse());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.header.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return  new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img1;
        TextView header;
        TextView Course;
        CardView cardView;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            img1=itemView.findViewById(R.id.img1);
            header=itemView.findViewById(R.id.header);
            Course=itemView.findViewById(R.id.Course);
            cardView=itemView.findViewById(R.id.CardView);
        }
    }
}
