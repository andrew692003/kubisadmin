package com.twiscode.kubisadmin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twiscode.kubisadmin.Adapter.ListAdapterSubmission;
import com.twiscode.kubisadmin.POJO.Startup;

import java.util.ArrayList;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        this.inflater=inflater;
        this.container=container;
        this.savedInstanceState=savedInstanceState;

        ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getFragmentManager();
        mAuthProgressDialog = new ProgressDialog(getContext());
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
//        Query mRef, Class<Startup> mModelClass, int mLayout, Activity activity, Context context
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),Startup.class,R.layout.item_submission,getActivity(),getContext());
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
        if(submissionView != null)
        {
            mAuthProgressDialog.dismiss();
            submissionView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //Startup select = (Startup) submissionView.getItemAtPosition(position);
                    Intent j = new Intent(getActivity(), StartupDetailActivity.class);
                    String keynow = ((Pair<Startup, String>)parent.getItemAtPosition(position)).second;
                    j.putExtra("key", keynow);
//                    Log.v("key", String.valueOf(parent.getItemIdAtPosition(position)));
//                    j.putExtra("key", parent.getItemIdAtPosition(position));
                    startActivityForResult(j, 1);
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
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),Startup.class,R.layout.item_submission,getActivity(),getContext());
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
        submissionAdapter=new ListAdapterSubmission(database.child("startups"),Startup.class,R.layout.item_submission,getActivity(),getContext());
        submissionView.setAdapter(submissionAdapter);
        submissionAdapter.notifyDataSetChanged();
        if(submissionView!=null)
        {
            mAuthProgressDialog.dismiss();
        }
    }
}
