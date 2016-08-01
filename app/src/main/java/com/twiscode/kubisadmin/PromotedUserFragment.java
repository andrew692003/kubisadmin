package com.twiscode.kubisadmin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromotedUserFragment extends Fragment {

    @BindView(R.id.promoteUser)
    LinearLayout promoteUser;
    @BindView(R.id.superAdmin)
    LinearLayout superAdmin;
    @BindView(R.id.commentator)
    LinearLayout commentator;

    public PromotedUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promoted_user, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        return view;
    }

}
