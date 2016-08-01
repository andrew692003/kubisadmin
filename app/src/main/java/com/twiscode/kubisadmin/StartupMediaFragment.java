package com.twiscode.kubisadmin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartupMediaFragment extends Fragment {

    @BindView(R.id.gridFounder)
    ExpandableHeightGridView gridFounder;
    @BindView(R.id.gridUpvotes)
    ExpandableHeightGridView gridUpvotes;

    private static final Integer[] idFounder = {
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
//            R.drawable.chorong
    };
    private static final Integer[] idUpvotes = {
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong,
            R.drawable.chorong
    };

    ThumbAdapter adapterFounder;
    ThumbAdapter adapterUpvotes;

    public StartupMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_startup_media, container, false);
    }

}
