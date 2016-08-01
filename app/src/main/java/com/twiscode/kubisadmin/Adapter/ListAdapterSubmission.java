package com.twiscode.kubisadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.Startup;
import com.twiscode.kubisadmin.R;

/**
 * Created by Crusader on 8/1/2016.
 */
public class ListAdapterSubmission extends FirebaseListAdapter<Startup> {
    Activity activity;
    Context context;
    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mModelClass Firebase will marshall the data at a location into an instance of a class that you provide
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public ListAdapterSubmission(Query mRef, Class<Startup> mModelClass, int mLayout, Activity activity, Context context) {
        super(mRef, mModelClass, mLayout, activity);
        this.activity=activity;
        this.context=context;
    }

    @Override
    protected void populateView(View v, Startup model) {
        TextView startupName = (TextView) v.findViewById(R.id.startupName);
        TextView startupDesc = (TextView) v.findViewById(R.id.startupDesc);
        ImageView startupImage = (ImageView) v.findViewById(R.id.startupImage);
        startupName.setText(model.getName());
        startupDesc.setText(model.getDescription());
        if(model.getThumbnail()==null || model.getThumbnail().equals(""))
        {

        }
        else Picasso.with(context).load(model.getThumbnail()).into(startupImage);
    }
}
