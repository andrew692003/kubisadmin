package com.twiscode.kubisadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartupDetailActivity extends AppCompatActivity {

    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;

    /*
    @BindView(R.id.accept)
    Button btnAcc;
    @BindView(R.id.decline)
    Button btnDec;*/

    @BindView(R.id.accept)
    FloatingActionButton btnAcc;
    @BindView(R.id.decline)
    FloatingActionButton btnDec;

    String key;
    ProgressDialog mAuthProgressDialog;

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
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading");
        mAuthProgressDialog.setMessage("Please Wait...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();

        ButterKnife.bind(this);

        // TOOLBAR
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // VIEWPAGER
        if(viewPager != null) {
            DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new StartupDiscussionFragment(), "Discussion");
            adapter.addFragment(new StartupInfoFragment(), "Information");
            adapter.addFragment(new StartupMediaFragment(), "Media");
            adapter.addFragment(new StartupDiscussionFragment(), "Similar");
            viewPager.setAdapter(adapter);
        }

        // TABLAYOUT
        tabLayout.setupWithViewPager(viewPager);

        btnDec.setVisibility(View.GONE);
        btnAcc.setVisibility(View.GONE);

        database.child("startupstatus").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.e("DATASNAP",dataSnapshot.toString());
                Boolean status=(Boolean)dataSnapshot.getValue();
                if(status==null)
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
                        database.child("startupstatus").child(key).setValue(true);
                    }
                });

                btnDec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.child("startupstatus").child(key).setValue(false);
                    }
                });
                mAuthProgressDialog.dismiss();
                Log.v("STATUS", String.valueOf(status));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class DetailPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //Intent j = new Intent(ChatActivity2.this, LobbyChatActivity.class);
                //j.putExtra("username",mUsername);
                //startActivity(j);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
    */
}
