package com.twiscode.kubisadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.Adapter.MediaAdapter;
import com.twiscode.kubisadmin.POJO.Startup;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartupMediaFragment extends Fragment {

    @BindView(R.id.rvMedia)
    RecyclerView rvMedia;
    //    @BindView(R.id.lvMedia)
//    ListView lvMedia;
//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;
    @BindView(R.id.tvNoData)
    TextView tvNoData;

    ArrayList<String> listMedia;
    MediaAdapter adapterMedia;
//    ImageAdapter adapterImage;

    String startupID;
    Startup startupCurrent;

    public StartupMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_startup_media, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        startupID = getArguments().getString("startupID");
        startupCurrent = Parcels.unwrap(getArguments().getParcelable("startup"));

//        mRef = FirebaseDatabase.getInstance().getReference();
//        Query startupRef = mRef.child("startups").child(startupID);

        listMedia = new ArrayList<>();
        adapterMedia = new MediaAdapter(getContext(), listMedia);
//        adapterImage = new ImageAdapter(getContext(), listMedia);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rvMedia.setLayoutManager(llm);
        rvMedia.setAdapter(adapterMedia);
//        lvMedia.setAdapter(adapterImage);

        if(startupCurrent.getImageUrl() != null) {
            Log.d(this.getTag(), listMedia.toString());
            for(String imageUrl : startupCurrent.getImageUrl()) {
//                listMedia.add(imageUrl);
                adapterMedia.refillAdapter(imageUrl);
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }

//        startupRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                progressBar.setVisibility(View.GONE);
//                if (dataSnapshot.exists()) {
//                    ArrayList<String> imageLists = (ArrayList<String>) dataSnapshot.child("imageUrl").getValue();
//                    listMedia = imageLists;
//                    adapterMedia.notifyDataSetChanged();
//                } else {
//                    tvNoData.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                if(databaseError != null) {
//                    Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });




    }
}
