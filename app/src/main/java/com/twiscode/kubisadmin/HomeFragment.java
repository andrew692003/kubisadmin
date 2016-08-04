package com.twiscode.kubisadmin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.ListAdapterSubmission;
import com.twiscode.kubisadmin.Adapter.ListAdapterSuggestion;
import com.twiscode.kubisadmin.POJO.Request;
import com.twiscode.kubisadmin.POJO.Startup;

import org.parceler.Parcels;

import java.security.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.submissionListView)
    ListView submissionView;
    ArrayList<Startup> submissionList = new ArrayList<Startup>();
    ArrayList<String> key = new ArrayList<String>();
    ListAdapterSubmission submissionAdapter;
    DatabaseReference database;
    FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    ProgressDialog mAuthProgressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    LayoutInflater inflater;
    ViewGroup container;
    Bundle savedInstanceState;
    SwipeRefreshLayout refresher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        this.inflater=inflater;
        this.container=container;
        this.savedInstanceState=savedInstanceState;
        refresher = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
        ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getFragmentManager();
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date datefrommillis = cal.getTime();
        Date todaydate = new Date();
        try {
            todaydate = sdf.parse(sdf.format(new Date()));
            datefrommillis = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.v("getTime",todaydate.toString());
//        Log.v("getTimeMillisString",datefrommillis.toString());
//        Log.v("getTimeMillis", String.valueOf(cal.getTimeInMillis()));
        mAuthProgressDialog = new ProgressDialog(getContext());
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
//        Query mRef, Class<Startup> mModelClass, int mLayout, Activity activity, Context context
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),R.layout.item_submission,getActivity(),getContext());
        submissionView.setAdapter(submissionAdapter);
        submissionAdapter.notifyDataSetChanged();
//        database.child("startups").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                final Startup startup= dataSnapshot.getValue(Startup.class);
//                String keysnap = dataSnapshot.getKey();
//                if(!submissionList.contains(startup)) {
//                    submissionList.add(startup);
//                    key.add(dataSnapshot.getKey());
//                }
//                else
//                {
//                    submissionList.remove(startup);
//                }
//                submissionView.setAdapter(submissionAdapter);
//                submissionAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Startup startup = dataSnapshot.getValue(Startup.class);
//                submissionList.remove(startup);
//                key.remove(dataSnapshot.getKey());
//                submissionAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                submissionAdapter=new ListAdapterSubmission(database.child("startups"),R.layout.item_submission,getActivity(),getContext());
                submissionView.setAdapter(submissionAdapter);
                submissionAdapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        refresher.setRefreshing(false);
                    }
                });
                if(submissionAdapter.isEmpty())
                {
                    refresher.setRefreshing(false);
                }
            }
        });
        if(submissionView != null)
        {
            mAuthProgressDialog.dismiss();
            submissionView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //Startup select = (Startup) submissionView.getItemAtPosition(position);
                    Intent j = new Intent(getActivity(), StartupDetailActivity.class);
                    final String keynow = ((Pair<Startup, String>)parent.getItemAtPosition(position)).second;
                    database.child("startups").child(keynow).addListenerForSingleValueEvent(new ValueEventListener() {
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
        }
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuthProgressDialog.show();
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),R.layout.item_submission,getActivity(),getContext());
        submissionView.setAdapter(submissionAdapter);
        submissionAdapter.notifyDataSetChanged();
        if(submissionView!=null)
        {
            mAuthProgressDialog.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mAuthProgressDialog.show();
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),R.layout.item_submission,getActivity(),getContext());
        submissionView.setAdapter(submissionAdapter);
        submissionAdapter.notifyDataSetChanged();
        if(submissionView!=null)
        {
            mAuthProgressDialog.dismiss();
        }
    }
}
