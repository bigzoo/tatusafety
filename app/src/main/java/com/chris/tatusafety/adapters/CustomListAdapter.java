package com.chris.tatusafety.adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chris.tatusafety.R;
import com.chris.tatusafety.UI.Report;


public class CustomListAdapter extends BaseAdapter {
    Context mContext;
    //EDIT HERE
    ArrayList<Report> data;
    public CustomListAdapter(Context context, ArrayList<Report> data) {
        this.mContext = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_layout, parent,false);
            viewHolder = new ViewHolder();
            //EDIT HERE
            viewHolder.tvRoad = (TextView) convertView.findViewById(R.id.tvRoad);
            viewHolder.tvSacco = (TextView) convertView.findViewById(R.id.tvSacco);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
            viewHolder.tvPlate = (TextView) convertView.findViewById(R.id.tvPlate);
            viewHolder.tvCounty = (TextView) convertView.findViewById(R.id.tvCounty);
//road sacco date time
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //EDIT HERE
        Report report = data.get(position);
        viewHolder.tvRoad.setText("Road: "+report.getRoad());
        viewHolder.tvSacco.setText("Sacco: "+report.getSacco());
        viewHolder.tvDate.setText("On: "+report.getDate());
        viewHolder.tvTime.setText("At: "+report.getTime());
        viewHolder.tvDesc.setText("Description: "+report.getExtras());
        viewHolder.tvPlate.setText(report.getPlates());
        viewHolder.tvCounty.setText("County: "+report.getCounty());
        return convertView;
    }
    static class ViewHolder {
        //EDIT HERE
        TextView tvRoad;
        TextView tvSacco;
        TextView tvDate;
        TextView tvTime;
        TextView tvDesc;
        TextView tvPlate;
        TextView tvCounty;

    }

}
