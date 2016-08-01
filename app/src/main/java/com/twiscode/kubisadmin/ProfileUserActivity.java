package com.twiscode.kubisadmin;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.twiscode.kubisadmin.POJO.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileUserActivity extends AppCompatActivity {

    @BindView(R.id.text_description)
    TextView textDescription;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager viewPager;
    @BindView(R.id.namaJudul)
    TextView textNama;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // TOOLBAR
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // VIEWPAGER
        if(viewPager != null) {
            ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());

            ProfileStartupFragment mProfileStartupFragment = new ProfileStartupFragment();

            Bundle mBundle = new Bundle();
            mBundle.putString("memberID", "LRP2xkqObPRC3pRqrZxm0vyrdBf1");

            mProfileStartupFragment.setArguments(mBundle);

            adapter.addFragment(mProfileStartupFragment, "Startups");

            viewPager.setAdapter(adapter);
        }

        // TABLAYOUT
        tabLayout.setupWithViewPager(viewPager);

        Query userRef = mDatabase.child("users").child("LRP2xkqObPRC3pRqrZxm0vyrdBf1");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    User getUser = dataSnapshot.getValue(User.class);
                    textNama.setText(getUser.getName());
                    textDescription.setText(getUser.getDescription());
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
}
