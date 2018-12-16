package com.informatika.parentcare.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.informatika.parentcare.R;
import com.informatika.parentcare.model.Anak;
import com.informatika.parentcare.model.Gejala;

import java.util.ArrayList;

public class GejalaAdapter  extends BaseAdapter {

    private Context context;
    public static ArrayList<Gejala> gejalaArrayList;


    public GejalaAdapter(Context context, ArrayList<Gejala> gejalaArrayList) {
        this.context = context;
        this.gejalaArrayList = gejalaArrayList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gejala_list, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.tvGejala = (TextView) convertView.findViewById(R.id.gejala);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText("Checkbox "+position);
        holder.tvGejala.setText(gejalaArrayList.get(position).getDesk());

        holder.checkBox.setChecked(gejalaArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.gejala);
                Integer pos = (Integer)  holder.checkBox.getTag();
                Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(gejalaArrayList.get(pos).getSelected()){
                    gejalaArrayList.get(pos).setSelected(false);
                }else {
                    gejalaArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {
        protected CheckBox checkBox;
        private TextView tvGejala;

    }

}