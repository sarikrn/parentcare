package com.informatika.parentcare.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.informatika.parentcare.AddChildInfoActivity;
import com.informatika.parentcare.ChildProfileActivity;
import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Anak;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private List<Anak> dfanak;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_title, tv_email;

        public MyViewHolder(View view) {
            super(view);
            rl_layout = view.findViewById(R.id.rl_layout);
            tv_title = view.findViewById(R.id.tv_title);
            tv_email = view.findViewById(R.id.tv_email);
        }
    }

    public ChildAdapter(List<Anak> dfanak, Activity activity) {
        this.dfanak = dfanak;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Anak anak = dfanak.get(position);

        holder.tv_title.setText(anak.getKey());
        holder.tv_email.setText(anak.getNama());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, ChildProfileActivity.class);
                goDetail.putExtra("id", anak.getKey());
                goDetail.putExtra("nama", anak.getNama());
                goDetail.putExtra("urutan", anak.getUrutan());
                goDetail.putExtra("orangTua", anak.getKode_orangtua());
                mActivity.startActivity(goDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dfanak.size();
    }


}
