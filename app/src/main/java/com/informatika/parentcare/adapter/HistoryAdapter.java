package com.informatika.parentcare.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.informatika.parentcare.ChildProfileActivity;
import com.informatika.parentcare.ConsultationActivity;
import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Anak;
import com.informatika.parentcare.model.Konsultasi;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<Konsultasi> dfKonsultasi;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rw_layout;
        public TimelineView timeline;
        public TextView jadwal, solusi;

        public MyViewHolder(View view) {
            super(view);
            rw_layout = view.findViewById(R.id.rw_layout);
            timeline = view.findViewById(R.id.timeline);
            jadwal = view.findViewById(R.id.jadwal);
            solusi = view.findViewById(R.id.solusi);
        }
    }

    public HistoryAdapter(List<Konsultasi> dfKonsultasi, Activity activity) {
        this.dfKonsultasi = dfKonsultasi;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.riwayat_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Konsultasi konsultasi = dfKonsultasi.get(position);
        holder.jadwal.setText(konsultasi.getJadwal());
        holder.solusi.setText(konsultasi.getSolusi());
    }

    @Override
    public int getItemCount() {
        return dfKonsultasi.size();
    }
}
