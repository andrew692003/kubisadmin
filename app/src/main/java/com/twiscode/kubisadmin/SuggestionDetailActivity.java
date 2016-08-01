package com.twiscode.kubisadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestionDetailActivity extends AppCompatActivity {

    @BindView(R.id.accept)
    FloatingActionButton btnAcc;
    @BindView(R.id.decline)
    FloatingActionButton btnDec;

    ProgressDialog mAuthProgressDialog;

    String key;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_detail);
        database = FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this, this);
        Intent intent = getIntent();
        key=intent.getStringExtra("key");
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();
        database.child("request").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Long status=dataSnapshot.child("status").getValue(Long.class);
                    final String userid=dataSnapshot.child("userid").getValue(String.class);
                    if(status==0)
                    {
                        btnDec.setVisibility(View.VISIBLE);
                        btnAcc.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        btnDec.setVisibility(View.GONE);
                        btnAcc.setVisibility(View.GONE);
                    }

                    btnAcc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database.child("users").child(userid).child("status").setValue(2);
                            database.child("request").orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        String postKey=postSnapshot.getKey();
                                        database.child("request").child(postKey).child("status").setValue(1);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                    btnDec.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database.child("request").orderByChild("userid").equalTo(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        String postKey=postSnapshot.getKey();
                                        database.child("request").child(postKey).child("status").setValue(2);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                    mAuthProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
