package com.example.demo_lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    List<NoticeModel> mDeliveryList;

    public NoticeAdapter(Context context, List<NoticeModel> mDeliveryList) {
        this.context = context;
        this.mDeliveryList = mDeliveryList;
    }



    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView txtOid,txtDelFname,txtDelVname;
        public NoticeViewHolder(@NonNull View itemView) {

            super(itemView);

            txtOid = itemView.findViewById(R.id.txtDelOid);
            txtDelFname = itemView.findViewById(R.id.txtDelFname);
            txtDelVname = itemView.findViewById(R.id.txtDelVname);


        }
    }


    @NonNull
    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_items,viewGroup,false);
        NoticeViewHolder dvh = new NoticeViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.NoticeViewHolder holder, int i) {
        final NoticeModel d = mDeliveryList.get(i);
        holder.txtOid.setText(d.getNotice_id());
        holder.txtDelFname.setText(d.getNotice_Disc());
        holder.txtDelVname.setText(d.getDate());
    }

    @Override
    public int getItemCount() {
        return mDeliveryList.size();
    }


}
