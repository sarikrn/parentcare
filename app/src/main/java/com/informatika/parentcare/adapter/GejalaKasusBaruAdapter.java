package com.informatika.parentcare.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Gejala;

import java.util.List;

public class GejalaKasusBaruAdapter extends RecyclerView.Adapter<GejalaKasusBaruAdapter.MyViewHolder> {
    private List<Gejala> dfGejala;
    private Context mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pilihan_gejala;
        public LinearLayout newcase_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pilihan_gejala = itemView.findViewById(R.id.pilihan_gejala);
            newcase_layout = itemView.findViewById(R.id.newcase_layout);
        }
    }

    public GejalaKasusBaruAdapter(List<Gejala> dfGejala, Context activity){
        this.dfGejala = dfGejala;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rl_newcase, viewGroup,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Gejala gejala = dfGejala.get(i);
        myViewHolder.pilihan_gejala.setText(gejala.getKode() + ": " + gejala.getDesk());
    }

    @Override
    public int getItemCount() {
        return dfGejala.size();
    }

}
