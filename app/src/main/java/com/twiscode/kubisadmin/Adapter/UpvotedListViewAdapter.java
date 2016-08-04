package com.twiscode.kubisadmin.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twiscode.kubisadmin.POJO.Product;
import com.twiscode.kubisadmin.R;

import java.util.ArrayList;

/**
 * Created by Crusader on 8/2/2016.
 */
public class UpvotedListViewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Product> data;
    private LayoutInflater inflater;

    public UpvotedListViewAdapter(Activity activity, ArrayList<Product> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Product category = data.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_home, null);
        }

        TextView homeListTitle = (TextView) convertView.findViewById(R.id.homeListTitle);
        TextView homeListDesc = (TextView) convertView.findViewById(R.id.homeListDesc);
        final ImageView upvote = (ImageView) convertView.findViewById(R.id.imgUpvote);
        final TextView homeListUpvote = (TextView) convertView.findViewById(R.id.homeListUpvote);
        final LinearLayout upvoteBtn = (LinearLayout) convertView.findViewById(R.id.upvoteButton);

        homeListTitle.setText(category.getPro_title());
        homeListDesc.setText(category.getPro_desc());
        homeListUpvote.setText(String.valueOf(category.getPro_upvotes()));

        upvoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkImageResource(activity, upvote, R.drawable.upvote_yet) == true) {
                    upvote.setImageResource(R.drawable.upvote);
                    homeListUpvote.setTextColor(Color.parseColor("#ffffff"));
                    upvoteBtn.setBackgroundResource(R.drawable.layout_upvote);
                } else if (checkImageResource(activity, upvote, R.drawable.upvote) == true) {
                    upvote.setImageResource(R.drawable.upvote_yet);
                    homeListUpvote.setTextColor(Color.parseColor("#8cc86e"));
                    upvoteBtn.setBackgroundResource(R.drawable.layout_upvote_yet);
                }

            }
        });

        //final ImageView searchPick = (ImageView) convertView.findViewById(R.id.searchPick);
        //TextView searchCateNam = (TextView) convertView.findViewById(R.id.searchCateName);
//        TextView txtNama = (TextView) convertView.findViewById(R.id.textViewNama);

        //searchCateName.setText(category);


        return convertView;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static boolean checkImageResource(Context ctx, ImageView imageView,
                                             int imageResource) {
        boolean result = false;

        if (ctx != null && imageView != null && imageView.getDrawable() != null) {
            Drawable.ConstantState constantState;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = ctx.getResources()
                        .getDrawable(imageResource, ctx.getTheme())
                        .getConstantState();
            } else {
                constantState = ctx.getResources().getDrawable(imageResource)
                        .getConstantState();
            }

            if (imageView.getDrawable().getConstantState() == constantState) {
                result = true;
            }
        }

        return result;
    }
}
