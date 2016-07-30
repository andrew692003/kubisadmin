package com.twiscode.kubisadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.SubmissionAdapter;
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
    SubmissionAdapter submissionAdapter;
    DatabaseReference database;
    FirebaseAuth mAuth;
    FragmentManager fragmentManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fragmentManager = getFragmentManager();
        submissionAdapter=new SubmissionAdapter(getActivity(),submissionList);

        database.child("startups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Startup startup= dataSnapshot.getValue(Startup.class);
                String keysnap = dataSnapshot.getKey();
                if(!submissionList.contains(startup)) {
                    submissionList.add(startup);
                    key.add(dataSnapshot.getKey());
                }
                else
                {
                    submissionList.remove(startup);
                }
                submissionView.setAdapter(submissionAdapter);
                submissionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Startup startup = dataSnapshot.getValue(Startup.class);
                submissionList.remove(startup);
                key.remove(dataSnapshot.getKey());
                submissionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(submissionView != null)
        {
            submissionView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Startup select = (Startup) submissionView.getItemAtPosition(position);
                    Intent j = new Intent(getActivity(), StartupDetailActivity.class);
                    j.putExtra("key", key.get(position));
                    startActivityForResult(j, 1);
                }
            });
        }
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        submissionAdapter.notifyDataSetChanged();
    }
    // [END on_stop_remove_listener]

    @Override
    public void onPause() {
        super.onPause();
        submissionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        submissionAdapter.notifyDataSetChanged();
    }
}
