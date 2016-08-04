package com.twiscode.kubisadmin.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twiscode.kubisadmin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Crusader on 8/2/2016.
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> listMedia;
    private Context context;
    private static final int VIEW_TYPE = 0;

    public MediaAdapter(Context context, ArrayList<String> listMedia) {
        this.context = context;
        this.listMedia = listMedia;
    }

    @Override
    public int getItemCount() {
        return this.listMedia.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewSender = inflater.inflate(R.layout.list_item_media, parent, false);
        viewHolder = new ViewHolderMedia(viewSender);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ViewHolderMedia viewHolderMedia = (ViewHolderMedia) viewHolder;
        configureMediaView(viewHolderMedia,position);
    }

    private void configureMediaView(ViewHolderMedia viewHolderSender, int position) {
        String imageUri = listMedia.get(position);
        final ImageView imageView = viewHolderSender.getImageView();
//        Picasso.with(context).load(imageUri).into(imageView);
        Picasso.with(context).load(imageUri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public void refillAdapter(String imageUri){
        this.listMedia.add(imageUri);
        Log.d("MediaAdapter", imageUri);
        notifyDataSetChanged();
    }

    public void spillAdapter(String imageUri) {
        this.listMedia.remove(imageUri);
        notifyDataSetChanged();
    }

    public void cleanUp() {
        listMedia.clear();
    }

    public class ViewHolderMedia extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolderMedia(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getImageView() {
            return imageView;
        }

    }
}
