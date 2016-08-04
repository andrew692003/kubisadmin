package com.twiscode.kubisadmin;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.UpvotedStartupsFirebaseAdapter;
import com.twiscode.kubisadmin.POJO.Product;
import com.twiscode.kubisadmin.Interface.CustomToolbarActivity;
import com.twiscode.kubisadmin.POJO.Startup;

import org.parceler.Parcels;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpvotedPostsFragment extends Fragment implements CustomToolbarActivity{

    private ListView upvotedListView;
    private UpvotedStartupsFirebaseAdapter upvotedListViewAdapt;
    private ArrayList<Product> products;
    private TextView toolbarTitle;
    private UpvotedStartupsFirebaseAdapter mUpvoterListAdapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String memberID;

    public UpvotedPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upvoted_posts, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        upvotedListView = (ListView) view.findViewById(R.id.upvotedListView);

        String memberID = getArguments().getString("memberID");

        mUpvoterListAdapter = new UpvotedStartupsFirebaseAdapter(mDatabase.child("startups").orderByChild("creatorId").equalTo(memberID),Startup.class,R.layout.item_submission,getActivity(),getContext());
        upvotedListView.setAdapter(mUpvoterListAdapter);
        mUpvoterListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                setListViewHeightBasedOnChildren(upvotedListView);
            }
        });
        upvotedListView.setAdapter(mUpvoterListAdapter);

        upvotedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent (getActivity(), StartupDetailActivity.class);
                final String keynow = ((Pair<Startup, String>)parent.getItemAtPosition(position)).second;
                mDatabase.child("startups").child(keynow).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Startup theStartup = dataSnapshot.getValue(Startup.class);
                            Log.d("HomeFragment", dataSnapshot.toString());
                            Parcelable startupWrapped = Parcels.wrap(theStartup);

                            Intent i = new Intent(getActivity(), StartupDetailActivity.class);
                            i.putExtra("startup",startupWrapped);
                            i.putExtra("startupId",keynow);
                            startActivity(i);
                        } else {
                            Log.d("HomeFragment", "Startup not found!");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if(databaseError != null) {
                            Log.d("HomeFragment", databaseError.getMessage());
                        }
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight=0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
