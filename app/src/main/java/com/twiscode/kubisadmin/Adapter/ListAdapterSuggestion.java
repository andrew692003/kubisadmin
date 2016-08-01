package com.twiscode.kubisadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.Request;
import com.twiscode.kubisadmin.POJO.User;
import com.twiscode.kubisadmin.R;

/**
 * Created by Crusader on 8/1/2016.
 */
public class ListAdapterSuggestion extends SuggestionFirebaseAdapter {
    Activity activity;
    Context context;
    DatabaseReference database;
    FirebaseAuth mAuth;
    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public ListAdapterSuggestion(Query mRef, int mLayout, Activity activity, Class<Request> mModelClass) {
        super(mRef, mLayout, activity, mModelClass);
        this.activity=activity;
        this.context=context;
    }

    @Override
    protected void populateView(View v, final Request model) {
        final TextView startupName = (TextView) v.findViewById(R.id.startupName);
        final TextView startupDesc = (TextView) v.findViewById(R.id.startupDesc);
        final ImageView startupImage = (ImageView) v.findViewById(R.id.startupImage);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        database.child("users").child(model.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                startupName.setText(user.getName());
                if(user.getImageUrl()==null || user.getImageUrl().equals(""))
                {

                }
                else Picasso.with(context).load(user.getImageUrl()).into(startupImage);
                startupDesc.setText(model.getDeskripsi());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
