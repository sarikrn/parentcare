package com.informatika.parentcare.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.informatika.parentcare.AddChildInfoActivity;
import com.informatika.parentcare.ChildProfileActivity;
import com.informatika.parentcare.ConsultationActivity;
import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Anak;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private List<Anak> dfanak;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView child_layout;
        public TextView txt_nama, txt_status;
        public ImageView txt_foto;
        public Button btn_lakukanTest;

        public MyViewHolder(View view) {
            super(view);
            child_layout = view.findViewById(R.id.child_layout);
            txt_nama = view.findViewById(R.id.txt_nama);
            txt_status = view.findViewById(R.id.txt_status);
            txt_foto = view.findViewById(R.id.txt_foto);
            btn_lakukanTest = view.findViewById(R.id.btn_lakukanTest);
        }
    }

    public ChildAdapter(List<Anak> dfanak, Activity activity) {
        this.dfanak = dfanak;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anak_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Anak anak = dfanak.get(position);

        holder.txt_nama.setText(anak.getNama());
        holder.txt_status.setText(anak.getUrutan());
        holder.txt_foto.setImageResource(R.drawable.avatar);

        holder.child_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, ChildProfileActivity.class);
                goDetail.putExtra("kodeAnak", anak.getKey());
                goDetail.putExtra("nama", anak.getNama());
                goDetail.putExtra("urutan", anak.getUrutan());
                goDetail.putExtra("orangTua", anak.getKode_orangtua());
                mActivity.startActivity(goDetail);
            }
        });

        holder.btn_lakukanTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDetail = new Intent(mActivity, ConsultationActivity.class);
                goDetail.putExtra("kodeAnak", anak.getKey());
                goDetail.putExtra("nama", anak.getNama());
                goDetail.putExtra("urutan", anak.getUrutan());
                goDetail.putExtra("orangTua", anak.getKode_orangtua());
                goDetail.putExtra("halaman","home");
                mActivity.startActivity(goDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dfanak.size();
    }

}
