package com.twiscode.kubisadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.Startup;
import com.twiscode.kubisadmin.R;

/**
 * Created by Crusader on 8/2/2016.
 */
public class UpvotedStartupsFirebaseAdapter extends UpvotedFirebaseListAdapter {
    Activity activity;
    Context context;
    DatabaseReference database;

    public UpvotedStartupsFirebaseAdapter(Query mRef, Class<Startup> mModelClass, int mLayout, Activity activity, Context context) {
        super(mRef, mModelClass, mLayout, activity, "");
        this.activity=activity;
        this.context=context;
    }

    @Override
    protected void populateView(final View v, final Startup model, final String key) {
        database = FirebaseDatabase.getInstance().getReference();
        final TextView startupName = (TextView) v.findViewById(R.id.startupName);
        final TextView startupDesc = (TextView) v.findViewById(R.id.startupDesc);
        final ImageView startupImage = (ImageView) v.findViewById(R.id.startupImage);
        startupName.setText(model.getName());
        startupDesc.setText(model.getType());
        if(model.getThumbnail()==null || model.getThumbnail().equals(""))
        {

        }
        else Picasso.with(context).load(model.getThumbnail()).into(startupImage);
    }
}
