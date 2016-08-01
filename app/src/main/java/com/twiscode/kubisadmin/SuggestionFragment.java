package com.twiscode.kubisadmin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.twiscode.kubisadmin.Adapter.ListAdapterSuggestion;
import com.twiscode.kubisadmin.POJO.Request;
import com.twiscode.kubisadmin.POJO.Startup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionFragment extends Fragment {

    @BindView(R.id.listSuggest)
    ListView listSuggest;
    ListAdapterSuggestion listAdapterSuggestion;

    DatabaseReference database;
    FirebaseAuth mAuth;

    ProgressDialog mAuthProgressDialog;

    public SuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuthProgressDialog = new ProgressDialog(getContext());
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        ButterKnife.bind(this, view);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        listAdapterSuggestion=new ListAdapterSuggestion(database.child("request"), R.layout.item_submission, getActivity(), Request.class);
        listAdapterSuggestion.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mAuthProgressDialog.dismiss();
            }
        });
        listSuggest.setAdapter(listAdapterSuggestion);
        listAdapterSuggestion.notifyDataSetChanged();
        if(listSuggest != null)
        {
            listSuggest.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //Startup select = (Startup) submissionView.getItemAtPosition(position);
                    Intent j = new Intent(getActivity(), SuggestionDetailActivity.class);
                    String keynow = ((Pair<Request, String>)parent.getItemAtPosition(position)).second;
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
        listAdapterSuggestion=new ListAdapterSuggestion(database.child("request"), R.layout.item_submission, getActivity(), Request.class);
        listAdapterSuggestion.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mAuthProgressDialog.dismiss();
            }
        });
        listSuggest.setAdapter(listAdapterSuggestion);
        listAdapterSuggestion.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        listAdapterSuggestion=new ListAdapterSuggestion(database.child("request"), R.layout.item_submission, getActivity(), Request.class);
        listAdapterSuggestion.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                mAuthProgressDialog.dismiss();
            }
        });
        listSuggest.setAdapter(listAdapterSuggestion);
        listAdapterSuggestion.notifyDataSetChanged();
    }
}
