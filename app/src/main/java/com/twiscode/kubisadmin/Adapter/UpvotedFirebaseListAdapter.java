package com.twiscode.kubisadmin.Adapter;

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
import com.google.firebase.database.Query;
import com.twiscode.kubisadmin.POJO.Startup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crusader on 8/2/2016.
 */
public abstract class UpvotedFirebaseListAdapter extends BaseAdapter {

    private Query mRef;
    private Class<Startup> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<Startup> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;

    private String id,key;

    public UpvotedFirebaseListAdapter(Query mRef, Class<Startup> mModelClass, int mLayout, Activity activity, String mId) {
        this.mRef = mRef;
        this.mModelClass = mModelClass;
        this.mLayout = mLayout;
        mInflater = activity.getLayoutInflater();
        this.id = mId;
        mModels = new ArrayList<Startup>();
        mKeys = new ArrayList<String>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                //T model = dataSnapshot.getValue(UpvotedFirebaseListAdapter.this.mModelClass);

                Startup model = dataSnapshot.getValue(Startup.class);
                key = dataSnapshot.getKey();
                ArrayList<String> upvoters = (ArrayList<String>) dataSnapshot.child("upvoters").getValue();
                if (!upvoters.isEmpty() && upvoters!=null && !upvoters.contains(""))
                {
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
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A model was removed from the list. Remove it from our list and the name mapping
                String key = dataSnapshot.getKey();
                int index = mKeys.indexOf(key);

                mKeys.remove(index);
                mModels.remove(index);

                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A model changed position in the list. Update our list accordingly
                String key = dataSnapshot.getKey();
                // T newModel = dataSnapshot.getValue(UpvotedFirebaseListAdapter.this.mModelClass);
                Startup newModel =dataSnapshot.getValue(Startup.class);
                int index = mKeys.indexOf(key);
                mModels.remove(index);
                mKeys.remove(index);
                if (previousChildName == null) {
                    mModels.add(0, newModel);
                    mKeys.add(0, key);
                } else {
                    int previousIndex = mKeys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == mModels.size()) {
                        mModels.add(newModel);
                        mKeys.add(key);
                    } else {
                        mModels.add(nextIndex, newModel);
                        mKeys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }

        });
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
        return new Pair( mModels.get(i), mKeys.get(i));
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
        populateView(view, model, key);
        return view;
    }

    protected abstract void populateView(View v, Startup model, String key);
}
