package com.twiscode.kubisadmin;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.FounderDialogAdapter;
import com.twiscode.kubisadmin.POJO.Startup;
import com.twiscode.kubisadmin.POJO.User;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartupInfoFragment extends Fragment {

    @BindView(R.id.gridFounder)
    ExpandableHeightGridView gridFounder;
    @BindView(R.id.flowTags)
    TagFlowLayout flowTags;

//    @BindView(R.id.gridUpvotes)
//    ExpandableHeightGridView gridUpvotes;

    private static final Integer[] idFounder = {
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong
    };
//    private static final Integer[] idUpvotes = {
//            R.drawable.chorong,
//            R.drawable.chorong,
//            R.drawable.chorong,
//            R.drawable.chorong,
//            R.drawable.chorong
//    };

    private static final int FOUNDER_DISPLAY = 6;

    ArrayList<User> listFounder;
//    ArrayList<Integer> listUpvotes;

    ThumbAdapter adapterFounder;
//    ThumbAdapter adapterUpvotes;

    DatabaseReference mRef;

    String startupID;
    Startup startupCurrent;

    LayoutInflater inflater;

    public StartupInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_startup_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        startupID = getArguments().getString("startupID");
        startupCurrent = Parcels.unwrap(getArguments().getParcelable("startup"));

        mRef = FirebaseDatabase.getInstance().getReference();

        listFounder = new ArrayList<>();
//        listUpvotes = new ArrayList<>();

        adapterFounder = new ThumbAdapter(getContext(), listFounder);
        gridFounder.setAdapter(adapterFounder);
        gridFounder.setExpanded(true);

//        boolean founderCollapse = false;
//        int addFounder = FOUNDER_DISPLAY;
//        if(idFounder.length > FOUNDER_DISPLAY) {
//            addFounder = FOUNDER_DISPLAY - 1;
//            founderCollapse = true;
//        } else if(idFounder.length < FOUNDER_DISPLAY) {
//            addFounder = idFounder.length;
//        }
//        for (int i=0; i<addFounder; i++) {
//            listFounder.add(idFounder[i]);
//        }
//        if(founderCollapse) {
//            listFounder.add(R.drawable.ic_more_horiz_black_48dp);
//        }

//        gridUpvotes.setAdapter(adapterUpvotes);
//        gridUpvotes.setExpanded(true);

        if(startupCurrent.getFounder() != null) {
            for (String uid : startupCurrent.getFounder()) {
                mRef.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            User user = dataSnapshot.getValue(User.class);
                            listFounder.add(user);
                            adapterFounder.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }

//        final boolean finalFounderCollapse = founderCollapse;
//        gridFounder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String uid = listFounder.get(position).getUid();
////                if(finalFounderCollapse && position == FOUNDER_DISPLAY) {
////
////                }
//        final boolean finalFounderCollapse = founderCollapse;

        gridFounder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("String", String.valueOf(position)+" - "+String.valueOf(FOUNDER_DISPLAY));

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
//                    builderSingle.setIcon(R.drawable.chorong);
                builderSingle.setTitle("Founder");

                final FounderDialogAdapter arrayAdapter = new FounderDialogAdapter(
                        getActivity(),listFounder);
                builderSingle.setNegativeButton(
                        "cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(
                        arrayAdapter,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User user = (User) arrayAdapter.getItem(which);
                                //Log.v("memberId",user.getUid());
                                Intent i = new Intent(getActivity(),ProfileUserActivity.class);
                                i.putExtra("memberId", user.getUid());
                                startActivity(i);
                            }
                        });
                builderSingle.show();

            }
        });

        flowTags.setAdapter(new TagAdapter<String>(startupCurrent.getHashtag()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) inflater.inflate(R.layout.list_item_text, flowTags, false);
                tv.setText(s);
                return tv;
            }
        });

    }
}
