package com.informatika.parentcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Gejala;

import java.util.ArrayList;

public class GejalaAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<Gejala> gejalaArrayList;
    public ArrayList<Gejala> dataGejala = new ArrayList<>();

    public GejalaAdapter(ArrayList<Gejala> modelArrayList, Context context) {
        this.context = context;
        this.gejalaArrayList = modelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return gejalaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gejalaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gejala_list, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.gejala_checkBox);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.gejala_desk);
            holder.gj_layout = (RelativeLayout) convertView.findViewById(R.id.gj_layout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvAnimal.setText(gejalaArrayList.get(position).getDesk());
        holder.checkBox.setChecked(gejalaArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.gejala_desk);
                Integer pos = (Integer) holder.checkBox.getTag();
                Toast.makeText(context, gejalaArrayList.get(pos).getKode() + " dipilih", Toast.LENGTH_SHORT).show();

                if (gejalaArrayList.get(pos).getSelected()) {
                    gejalaArrayList.get(pos).setSelected(false);
                    dataGejala.remove(gejalaArrayList.get(pos));
                } else {
                    gejalaArrayList.get(pos).setSelected(true);
                    dataGejala.add(gejalaArrayList.get(pos));
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        protected CheckBox checkBox;
        private TextView tvAnimal;
        private RelativeLayout gj_layout;
    }

}
