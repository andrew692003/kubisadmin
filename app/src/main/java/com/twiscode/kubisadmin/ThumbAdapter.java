package com.twiscode.kubisadmin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.User;

import java.util.ArrayList;

public class ThumbAdapter extends BaseAdapter {

    private ArrayList<User> mThumbIds;
    private Context mContext;

    public ThumbAdapter(Context c, ArrayList<User> i) {
        mContext = c;
        mThumbIds = i;
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        GridImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new GridImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (GridImageView) convertView;
        }

        Picasso.with(mContext).load(mThumbIds.get(position).getImageUrl()).into(imageView);
        return imageView;
    }

}