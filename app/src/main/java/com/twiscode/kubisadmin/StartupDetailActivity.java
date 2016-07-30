package com.twiscode.kubisadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartupDetailActivity extends AppCompatActivity {

    @BindView(R.id.accept)
    Button btnAcc;
    @BindView(R.id.decline)
    Button btnDec;

    String key;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_detail);
        database = FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this, this);
        Intent intent = getIntent();
        key=intent.getStringExtra("key");
        Log.e("KEY",key);
        database.child("startupstatus").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DATASNAP",dataSnapshot.toString());
                Boolean status=(Boolean)dataSnapshot.getValue();
                if(!status)
                {
                    btnAcc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database.child("startupstatus").child(key).setValue(true);
                        }
                    });
                    btnDec.setKeyListener(null);
                }
                else
                {
                    btnDec.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database.child("startupstatus").child(key).setValue(false);
                        }
                    });
                    btnAcc.setKeyListener(null);
                }
                Log.v("STATUS", String.valueOf(status));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
