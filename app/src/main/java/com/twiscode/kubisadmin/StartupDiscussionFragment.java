package com.twiscode.kubisadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.DiscussionAdapter;
import com.twiscode.kubisadmin.POJO.Discussion;
import com.twiscode.kubisadmin.POJO.Startup;
import com.twiscode.kubisadmin.POJO.User;
import com.twiscode.kubisadmin.View.HeaderDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartupDiscussionFragment extends Fragment {

    //    @BindView(R.id.tvDescriptionLong)
    TextView tvDescriptionLong;
    @BindView(R.id.lvDiscussion) RecyclerView lvDiscussion;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
//    @BindView(R.id.rvDiscussionHeader)
//    RecyclerViewHeader rvDiscussionHeader;

    TextView headerTitle, headerText;

    DiscussionAdapter adapterDiscussion;
    DatabaseReference mRef, usersRef, discussRef;

    ArrayList<Discussion> listDiscussion;
    ArrayList<User> listName;

    Startup startupCurrent;
    String startupID;

    public StartupDiscussionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_startup_discussion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        startupID = getArguments().getString("startupID");// "-KNpcrYOHczcILXToTOS";
        startupCurrent = Parcels.unwrap(getArguments().getParcelable("startup"));
        listDiscussion = new ArrayList<>();
        listName = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        lvDiscussion.setLayoutManager(llm);

//        RecyclerViewHeader header = (RecyclerViewHeader) view.findViewById(R.id.rvDiscussionHeader);
//        header.attachTo(lvDiscussion);


        adapterDiscussion = new DiscussionAdapter(getContext(), listDiscussion, listName);

        adapterDiscussion.setHeader(startupCurrent.getDescription());
//        adapterDiscussion.setHeader("Testing");

        lvDiscussion.setAdapter(adapterDiscussion);

//        HeaderDecoration headerDecoration = new HeaderDecoration(getContext(), lvDiscussion, R.layout.header_detail_discussion);
//        View headerView = headerDecoration.getView();
//        headerTitle = (TextView) headerView.findViewById(R.id.headerTitle);
//        headerTitle.setText("Description");
//        headerText = (TextView) headerView.findViewById(R.id.headerText);
//        if(startupCurrent != null) {
//            headerText.setText(startupCurrent.getDescription());
//        }
//        lvDiscussion.addItemDecoration(headerDecoration);
//        lvDiscussion.addItemDecoration(HeaderDecorationAlt.with(lvDiscussion).inflate(R.layout.header_detail_discussion).parallax(0.2f).build());

        mRef = FirebaseDatabase.getInstance().getReference();
        usersRef = mRef.child("users");
        discussRef = mRef.child("discuss");

        discussRef.orderByChild("sid").equalTo(startupID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        discussRef.orderByChild("sid").equalTo(startupID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressBar.setVisibility(View.GONE);
                final Discussion discussion = dataSnapshot.getValue(Discussion.class);
                usersRef.child(discussion.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                            Log.d("Firebase data", dataSnapshot.toString());
                        if(dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
//                            String name = (String) dataSnapshot.child("name").getValue();
                            adapterDiscussion.refillAdapter(discussion, user);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        if(databaseError != null) {
                            Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Discussion discussion = dataSnapshot.getValue(Discussion.class);
                adapterDiscussion.spillAdapter(discussion);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapterDiscussion.cleanUp();
    }
}
