package com.twiscode.kubisadmin;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twiscode.kubisadmin.POJO.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileUserActivity extends AppCompatActivity {

    @BindView(R.id.iniDeskripsi)
    TextView textDescription;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;
    @BindView(R.id.namaJudul)
    TextView textNama;
    @BindView(R.id.civProfilePicture)
    ImageView imageProfile;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        ButterKnife.bind(this, this);
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager) ;
        //Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String memberId = "default";
        if(getIntent()!=null){
            memberId = getIntent().getStringExtra("memberId");
        }

        // TOOLBAR
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // VIEWPAGER
        if(viewPager != null) {
            ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());

            ProfileStartupFragment mProfileStartupFragment = new ProfileStartupFragment();
            UpvotedPostsFragment mUpvotedPostsFragment = new UpvotedPostsFragment();

            Bundle mBundle = new Bundle();

            mBundle.putString("memberID", memberId);

            mProfileStartupFragment.setArguments(mBundle);
            mUpvotedPostsFragment.setArguments(mBundle);

            adapter.addFragment(mProfileStartupFragment, "Startups");
            adapter.addFragment(mUpvotedPostsFragment, "Upvoted Startups");
            viewPager.setAdapter(adapter);
        }

        // TABLAYOUT
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setTitle("");
        Log.v("memberIdProfileUser",memberId);

        Query userRef = mDatabase.child("users").child(memberId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    User getUser = dataSnapshot.getValue(User.class);
                    textNama.setText(getUser.getName());
                    Log.v("description",getUser.getDescription());
                    textDescription.setText(getUser.getDescription());
                    if(getUser.getImageUrl()==null || getUser.getImageUrl().equals("")){}
                    else Picasso.with(getApplicationContext()).load(getUser.getImageUrl()).into(imageProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(ProfileUserActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class ProfilePagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ProfilePagerAdapter(FragmentManager fm) {
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
}
