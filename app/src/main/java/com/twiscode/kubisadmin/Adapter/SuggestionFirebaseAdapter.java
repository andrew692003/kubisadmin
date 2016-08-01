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
import com.twiscode.kubisadmin.POJO.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crusader on 8/1/2016.
 */
public abstract class SuggestionFirebaseAdapter extends BaseAdapter {

    private Query mRef;
    private Class<Request> mModelClass;
    private int mLayout;
    private LayoutInflater mInflater;
    private List<Request> mModels;
    private List<String> mKeys;
    private ChildEventListener mListener;


    /**
     * @param mRef        The Firebase location to watch for data changes. Can also be a slice of a location, using some
     *                    combination of <code>limit()</code>, <code>startAt()</code>, and <code>endAt()</code>,
     * @param mLayout     This is the mLayout used to represent a single list item. You will be responsible for populating an
     *                    instance of the corresponding view with the data from an instance of mModelClass.
     * @param activity    The activity containing the ListView
     */
    public SuggestionFirebaseAdapter(Query mRef, int mLayout, Activity activity, Class<Request> mModelClass) {
        this.mRef = mRef;
        this.mLayout = mLayout;
        this.mModelClass=mModelClass;
        mInflater = activity.getLayoutInflater();
        mModels = new ArrayList<Request>();
        mKeys = new ArrayList<String>();
        // Look for all child events. We will then map them to our own internal ArrayList, which backs ListView
        mListener = this.mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                Request model = dataSnapshot.getValue(SuggestionFirebaseAdapter.this.mModelClass);
                String key = dataSnapshot.getKey();

                if(model.getStatus()==0) {
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
                // One of the mModels changed. Replace it in our list and name mapping
                String key = dataSnapshot.getKey();
                Request newModel = dataSnapshot.getValue(SuggestionFirebaseAdapter.this.mModelClass);
                if(newModel.getStatus()==0) {
                    int index = mKeys.indexOf(key);

                    mModels.set(index, newModel);
                }

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
                Request newModel = dataSnapshot.getValue(SuggestionFirebaseAdapter.this.mModelClass);
                if(newModel.getStatus()==0) {
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

        Request model = mModels.get(i);
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
    protected abstract void populateView(View v, Request model);
}
