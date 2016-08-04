package com.twiscode.kubisadmin.Adapter;

/**
 * Created by Crusader on 8/1/2016.
 */
import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.MyApplication;
import com.twiscode.kubisadmin.POJO.Startup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is a generic way of backing an Android ListView with a Firebase location.
 * It handles all of the child events at the given Firebase location. It marshals received data into the given
 * class type. Extend this class and provide an implementation of <code>populateView</code>, which will be given an
 * instance of your list item mLayout and an instance your class that holds your data. Simply populate the view however
 * you like and this class will handle updating the list as the data changes.
 *
 */
public abstract class FirebaseListAdapter extends BaseAdapter {

    private Query mRef;
    private Class<Startup> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<Startup> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;
    private DatabaseReference database;
    private Long limit;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public FirebaseListAdapter(Query mRef, int mLayout, final Activity activity) {
        database = FirebaseDatabase.getInstance().getReference();
        this.mRef = mRef;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<Startup>();
        mKeys = new ArrayList<String>();
        final Long[] limit = {0L};
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, final String previousChildName) {
                final Startup model = dataSnapshot.getValue(Startup.class);
                final String key = dataSnapshot.getKey();
                database.child("startupstatus").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            final Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(model.getTimestamp());
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date datefrommillis = cal.getTime();
                            Date todaydate = new Date();
                            try {
                                todaydate = sdf.parse(sdf.format(new Date()));
                                datefrommillis = sdf.parse(sdf.format(cal.getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.v("datefrommillis",datefrommillis.toString());
                            Log.v("todaydate",todaydate.toString());
                            if(datefrommillis.equals(todaydate) && (Boolean)dataSnapshot.getValue())
                            {
                                limit[0] = limit[0] + 1;
                            }
                        }
                        else
                        {

                            Log.v("adastatus",key);
                            // Insert into the correct location, based on previousChildName
                            if (previousChildName == null) {
                                mModels.add(0, model);
                                mKeys.add(0, key);
                            } else {
                                int previousIndex = mKeys.indexOf(previousChildName);
                                int nextIndex = previousIndex + 1;
                                if (nextIndex == mModels.size()) {
                                    mModels.add(model);
                                    mKeys.add(key);
                                } else {
                                    mModels.add(nextIndex, model);
                                    mKeys.add(nextIndex, key);
                                }
                            }
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // One of the mModels changed. Replace it in our list and name mapping
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, final String previousChildName) {

                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
        ((MyApplication) activity.getApplication()).setLimit(limit);
        notifyDataSetChanged();
    }

    public void cleanup() {
        // We're being destroyed, let go of our mListener and forget about all of the mModels
        mRef.removeEventListener(mListener);
        mModels.clear();
        mKeys.clear();
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int i) {
        return new Pair(mModels.get(i),mKeys.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(mLayout, viewGroup, false);
        }

        Startup model = mModels.get(i);
        // Call out to subclass to marshall this model into the provided view
        populateView(view, model);
        return view;
    }

    /**
     * Each time the data at the given Firebase location changes, this method will be called for each item that needs
     * to be displayed. The arguments correspond to the mLayout and mModelClass given to the constructor of this class.
     * <p/>
     * Your implementation should populate the view using the data contained in the model.
     *
     * @param v     The view to populate
     * @param model The object containing the data used to populate the view
     */
    protected abstract void populateView(View v, Startup model);
}

