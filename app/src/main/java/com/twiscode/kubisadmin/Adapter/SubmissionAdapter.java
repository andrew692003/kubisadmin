package com.twiscode.kubisadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.twiscode.kubisadmin.POJO.Startup;
import com.twiscode.kubisadmin.R;

import java.util.ArrayList;

/**
 * Created by Crusader on 7/29/2016.
 */
public class SubmissionAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Startup> data;
    LayoutInflater inflater;

    public SubmissionAdapter(Activity activity, ArrayList<Startup> data)
    {
        this.activity=activity;
        this.data=data;
        this.inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Startup startup = data.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_submission, null);
        }
        TextView startupName = (TextView) convertView.findViewById(R.id.startupName);
        TextView startupDesc = (TextView) convertView.findViewById(R.id.startupDesc);
        startupName.setText(startup.getName());
        startupDesc.setText(startup.getDescription());
        return convertView;
    }
}
