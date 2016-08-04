package com.twiscode.kubisadmin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.Request;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionDetailFragment extends Fragment {


    @BindView(R.id.suggestDesc)
    TextView descSuggest;
    @BindView(R.id.nominateImage)
    ImageView imageNominate;
    @BindView(R.id.nominateName)
    TextView nameNominate;

    DatabaseReference database;

    public SuggestionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_detail, container, false);
        ButterKnife.bind(this, view);
        String idRequest = getArguments().getString("idRequest");
        database = FirebaseDatabase.getInstance().getReference();
        database.child("request").child(idRequest).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String deskripsi = dataSnapshot.child("deskripsi").getValue(String.class);
                if(deskripsi!=null){ descSuggest.setText(deskripsi); }
                String nominateid = dataSnapshot.child("nominateid").getValue(String.class);
                database.child("users").child(nominateid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String namanominate = dataSnapshot.child("name").getValue(String.class);
                        String imageurlnominate = dataSnapshot.child("imageUrl").getValue(String.class);
                        if(namanominate!=null){ nameNominate.setText(namanominate); }
                        if(imageurlnominate.equals("") || imageurlnominate==null){}
                        else Picasso.with(getContext()).load(imageurlnominate).into(imageNominate);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
