package com.twiscode.kubisadmin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
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
import com.twiscode.kubisadmin.Adapter.ListAdapterStartups;
import com.twiscode.kubisadmin.Adapter.ListAdapterSubmission;
import com.twiscode.kubisadmin.POJO.Startup;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileStartupFragment extends Fragment {

    @BindView(R.id.my_startup_list)
    ListView startupsView;

    ListAdapterStartups startupsAdapter;
    DatabaseReference database;
    FirebaseAuth mAuth;
    ProgressDialog mAuthProgressDialog;

    String memberID;

    public ProfileStartupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_startup, container, false);
        ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthProgressDialog = new ProgressDialog(getContext());
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        memberID = getArguments().getString("memberID");
        Log.v("memberId",memberID);
        startupsAdapter=new ListAdapterStartups(database.child("startups").orderByChild("creatorId").equalTo(memberID),Startup.class,R.layout.item_submission,getActivity(),getContext());
        startupsView.setAdapter(startupsAdapter);
        startupsAdapter.notifyDataSetChanged();
        if(startupsView != null)
        {
            mAuthProgressDialog.dismiss();
            startupsView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
        Log.v("memberId",memberID);
        startupsAdapter=new ListAdapterStartups(database.child("startups").orderByChild("creatorId").equalTo(memberID),Startup.class,R.layout.item_submission,getActivity(),getContext());
        startupsView.setAdapter(startupsAdapter);
        startupsAdapter.notifyDataSetChanged();
        if(startupsView!=null)
        {
            mAuthProgressDialog.dismiss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mAuthProgressDialog.show();
        Log.v("memberId",memberID);
        startupsAdapter=new ListAdapterStartups(database.child("startups").orderByChild("creatorId").equalTo(memberID),Startup.class,R.layout.item_submission,getActivity(),getContext());
        startupsView.setAdapter(startupsAdapter);
        startupsAdapter.notifyDataSetChanged();
        if(startupsView!=null)
        {
            mAuthProgressDialog.dismiss();
        }
    }
}
